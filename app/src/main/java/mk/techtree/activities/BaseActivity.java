package mk.techtree.activities;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import mk.techtree.abstracts.BaseFragment;
import mk.techtree.helperclasses.ui.helper.UIHelper;

public abstract class BaseActivity extends AppCompatActivity {


    //    protected TitleBar titleBar;
    public BaseFragment baseFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewId());
//        setAndBindTitleBar();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    protected abstract int getViewId();

    protected abstract int getTitlebarLayoutId();

    protected abstract int getNavigationLayoutId();

    protected abstract int getDockableFragmentId();

//
//    protected abstract int getPermanentViewId();

//
//    private void setAndBindTitleBar() {
//        titleBar = (TitleBar) findViewById(getTitlebarLayoutId());
//        titleBar.setVisibility(View.GONE);
//        titleBar.resetViews();
//    }
//

    public void closeApp() {
        UIHelper.showAlertDialog("Do you want to quit this application?", "Quit",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        dialog.dismiss();
                    }
                }
                , this);
    }

    public void addDockableFragment(Fragment fragment, boolean isTransition) {
        baseFragment = (BaseFragment) fragment;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (isTransition) {
//            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        fragmentTransaction.replace(getDockableFragmentId(), fragment).addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }




    public void replaceDockableFragment(Fragment fragment, boolean isTransition) {
        baseFragment = (BaseFragment) fragment;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (isTransition) {
//            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        fragmentTransaction.replace(getDockableFragmentId(), fragment).commit();
    }


//    public TitleBar getTitleBar() {
//        return titleBar;
//    }


    public void openActivity(Class<?> tClass) {
        Intent i = new Intent(this, tClass);
        startActivity(i);
    }


    public void openImagePreviewActivity(String url, String title) {
//        Intent i = new Intent(this, ImagePreviewActivity.class);
//        i.putExtra(IMAGE_PREVIEW_TITLE, title);
//        i.putExtra(IMAGE_PREVIEW_URL, url);
//        startActivity(i);
    }


    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    public void clearAllActivitiesExceptThis(Class<?> cls) {
        Intent intents = new Intent(this, cls);
        intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intents);
        finish();
    }


    public void emptyBackStack() {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        if (fm == null) return;
        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            fm.popBackStack();
        }
    }

    public void popBackStack() {
        if (getSupportFragmentManager() == null) {
            return;
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    public void popStackTill(int stackNumber) {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        if (fm == null) return;
        for (int i = stackNumber; i < fm.getBackStackEntryCount(); i++) {
            fm.popBackStack();
        }
    }

    public void popStackTill(String tag) {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        if (fm == null) {
            return;
        }

        int backStackEntryCount = fm.getBackStackEntryCount();
        for (int i = backStackEntryCount - 1; i > 0; i--) {
            if (fm.getBackStackEntryAt(i).getName().equalsIgnoreCase(tag)) {
                return;
            } else {
                fm.popBackStack();
            }
        }
    }


    public void refreshFragment(BaseFragment fragment) {
        popBackStack();
        addDockableFragment(fragment, false);

    }
}