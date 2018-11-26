package mk.techtree.abstracts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.share.Share;
import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;

import mk.techtree.BaseApplication;
import mk.techtree.LoginActivity;
import mk.techtree.activities.BaseActivity;
import mk.techtree.helperclasses.ui.helper.KeyboardHelper;
import mk.techtree.helperclasses.ui.helper.UIHelper;
import mk.techtree.managers.SharedPreferenceManager;
import mk.techtree.models.UserModel;

/**
 * Created by khanhamza on 10-Feb-17.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener/*, OnNewPacketReceivedListener */ {

    protected View view;
    public SharedPreferenceManager sharedPreferenceManager;
    public String TAG = "Logging Tag";
    public boolean onCreated = false;


    /**
     * This is an abstract class, we should inherit our fragment from this class
     */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferenceManager = SharedPreferenceManager.getInstance(getContext());
        onCreated = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(getFragmentLayout(), container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        getBaseActivity().getTitleBar().resetViews();


//        subscribeToNewPacket(this);

    }

    public UserModel getCurrentUser() {
        return sharedPreferenceManager.getCurrentUser();
    }

//    public String getToken() {
//        return sharedPreferenceManager.getString(AppConstants.KEY_TOKEN);
//    }


    // Use  UIHelper.showSpinnerDialog
    @Deprecated
    public void setSpinner(ArrayAdapter adaptSpinner, final TextView textView, final Spinner spinner) {
        if (adaptSpinner == null || spinner == null)
            return;
        //selected item will look like a spinner set from XML
//        simple_list_item_single_choice
        adaptSpinner.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(adaptSpinner);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = spinner.getItemAtPosition(position).toString();
                if (textView != null)
                    textView.setText(str);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    protected abstract int getFragmentLayout();

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }


    public void emptyBackStack() {
        FragmentManager fm = getFragmentManager();
        if (fm == null) return;
        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            fm.popBackStack();
        }
    }

    public void popBackStack() {
        if (getFragmentManager() == null) {
            return;
        } else {
            getFragmentManager().popBackStack();
        }
    }

    public void popStackTill(int stackNumber) {
        FragmentManager fm = getFragmentManager();
        if (fm == null) return;
        for (int i = stackNumber; i < fm.getBackStackEntryCount(); i++) {
            fm.popBackStack();
        }
    }

    public void popStackTillReverse(int stackNumber) {
        FragmentManager fm = getFragmentManager();
        if (fm == null) return;
        for (int i = 0; i < stackNumber; i++) {
            fm.popBackStack();
        }
    }

//    public abstract void setTitlebar(TitleBar titleBar);



    public abstract void setListeners();

    @Override
    public void onResume() {
        super.onResume();
        onCreated = true;
        setListeners();

        if (getBaseActivity() != null && getBaseActivity().getWindow().getDecorView() != null) {
            KeyboardHelper.hideSoftKeyboard(getBaseActivity(), getBaseActivity().getWindow().getDecorView());
        }
    }

    @Override
    public void onPause() {

        if (getBaseActivity() != null && getBaseActivity().getWindow().getDecorView() != null) {
            KeyboardHelper.hideSoftKeyboard(getBaseActivity(), getBaseActivity().getWindow().getDecorView());
        }
        super.onPause();
    }

    public void showNextBuildToast() {
        UIHelper.showToast(getContext(), "This feature is in progress");
    }

    public static void logoutClick(final Context context, Activity activity) {

        new iOSDialogBuilder(context)
                .setTitle("Logout")
                .setSubtitle("Do you want to Logout?")
                .setBoldPositiveLabel(false)
                .setCancelable(false)
                .setPositiveListener("Yes", dialog -> {
                    dialog.dismiss();
                    SharedPreferenceManager.getInstance(context).clearDB();
                    Intent intents = new Intent(activity, LoginActivity.class);
                    intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    activity.startActivity(intents);
                    activity.finish();

                })
                .setNegativeListener("No", iOSDialog::dismiss)
                .build().show();


    }

}
