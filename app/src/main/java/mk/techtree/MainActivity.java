package mk.techtree;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import mk.techtree.fragments.OverView;
import mk.techtree.fragments.Projects;
import mk.techtree.fragments.RCcar;


public class MainActivity extends AppCompatActivity implements  BottomNavigationView.OnNavigationItemSelectedListener{



    BottomNavigationView navigation;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initFragment();


    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        bindViews();
        setListener();

        fragmentTransaction.replace(R.id.placeholder, new OverView());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setListener() {
        navigation.setOnNavigationItemSelectedListener(this);
    }

    private void bindViews() {
        navigation = findViewById(R.id.navigation);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragmentTransaction.replace(R.id.placeholder, new OverView());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                return true;
            case R.id.navigation_dashboard:
                fragmentTransaction.replace(R.id.placeholder, new Projects());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                return true;
            case R.id.navigation_notifications:
                fragmentTransaction.replace(R.id.placeholder, new RCcar());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                return true;
        }
        return false;    }
}
