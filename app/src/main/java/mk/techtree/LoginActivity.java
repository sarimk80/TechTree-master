package mk.techtree;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mk.techtree.constatnts.AppConstants;
import mk.techtree.constatnts.WebServiceConstants;
import mk.techtree.helperclasses.DateHelper;
import mk.techtree.helperclasses.Helper;
import mk.techtree.helperclasses.ui.helper.UIHelper;
import mk.techtree.managers.SharedPreferenceManager;
import mk.techtree.models.UserModel;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.login_button)
    LoginButton btnFBLoginButton;
    @BindView(R.id.sign_in_button)
    SignInButton signInButton;


    private UserModel facebookProfile;
    CallbackManager callbackManager;
    private ProfileTracker profileTracker;


    KProgressHUD mDialog;
    String TAG = "Firebase";

    // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build();
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 7070;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

//        printHashKey(getApplicationContext());


        callbackManager = CallbackManager.Factory.create();


        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        if (isLoggedIn) {
            disconnectSocialNetworks();
        }


        btnFBLoginButton.setReadPermissions("email");
        // If using in a fragment
//        btnFBLoginButton.setFragment(this);
        // Other app specific specialization

        // Callback registration
        btnFBLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
//                // App code

                AccessToken accessToken = loginResult.getAccessToken();
                fetchUserInfo(accessToken);
                Log.e("fb", accessToken.getUserId());

            }

            @Override
            public void onCancel() {
                // App code
                Log.e("fb", "on Cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.e("fb", "on Error");
                Helper.isNetworkConnected(getApplicationContext(), true);
            }
        });

    }

    private void sendLoginCall(UserModel userModel) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.setFirestoreSettings(BaseApplication.getFirebaseSetting());
        mDialog = UIHelper.getProgressHUD(this);
        mDialog.show();


        db.collection(WebServiceConstants.WS_KEY_USER_COLLECTION).document(userModel.userID)
                .set(userModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        mDialog.dismiss();
                        changeActivity();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                        mDialog.dismiss();
                        changeActivity();
                    }
                });


    }


    private void fetchUserInfo(AccessToken accessToken) {
        facebookProfile = new UserModel();
        if (accessToken != null) {
            GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                    if (jsonObject == null) {
                        return;
                    }
//                    onSocialInfoFetched(jsonObject);
                    Log.e("FbUserData", jsonObject.toString());
                    Gson gson = new Gson();
                    facebookProfile = gson.fromJson(jsonObject.toString(), UserModel.class);

//                    ProfileTracker profileTracker = new ProfileTracker() {
//                        @Override
//                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
//                            Log.d("**", "onCurrentProfileChanged: " + facebookProfile.picture);
//                            facebookProfile.picture = currentProfile.getProfilePictureUri(200, 200).toString();
//                        }
//                    };

                    facebookProfile.setPicture("https://graph.facebook.com/" + facebookProfile.getUserID() + "/picture?width=200&height=200");
                    Log.i("profile_pic", facebookProfile.getPicture() + "");
                    disconnectSocialNetworks();
                    userLogin(facebookProfile);

                }
            });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id, email, name");
//            parameters.putString("fields", "id,first_name,last_name,email,hometown,name");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void disconnectSocialNetworks() {
        logoutFromFacebook();
    }

    protected void logoutFromFacebook() {
        LoginManager.getInstance().logOut();
    }


    private void userLogin(UserModel userModel) {

        disconnectSocialNetworks();


        userModel.setLoginDate(DateHelper.getCurrentTime());


        // User is successfully logged in and saved in your Shared preferences. You can get User from Shared Preferences to use in your app.

        SharedPreferenceManager.getInstance(this).putObject(AppConstants.KEY_CURRENT_USER_MODEL, userModel);

        sendLoginCall(userModel);


    }

    private void changeActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.offline)
    public void offline_click() {
        changeActivity();
    }


//    public static void printHashKey(Context pContext) {
//        try {
//            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
//            for (android.content.pm.Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                String hashKey = new String(Base64.encode(md.digest(), 0));
//                Log.i("FB", "printHashKey() Hash Key: " + hashKey);
//            }
//        } catch (NoSuchAlgorithmException e) {
//            Log.e("FB", "printHashKey()", e);
//        } catch (Exception e) {
//            Log.e("FB", "printHashKey()", e);
//        }
//    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            createModelFromGoogleAndLogin(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            UIHelper.showToast(getApplicationContext(), e.getLocalizedMessage());

        }
    }


    @OnClick(R.id.sign_in_button)
    public void onViewClicked() {

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account == null) {
            signIn();
        } else {
            createModelFromGoogleAndLogin(account);
        }

    }

    private void createModelFromGoogleAndLogin(GoogleSignInAccount account) {
        UserModel userModel = new UserModel();
        userModel.setName(account.getDisplayName());
        userModel.setEmail(account.getEmail());
        userModel.setUserID(account.getId());
        if (account.getPhotoUrl() != null)
            userModel.setPicture(account.getPhotoUrl().toString());
        userLogin(userModel);
    }
}
