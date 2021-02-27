package mk.bumble;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;

import mk.bumble.fragments.OverView;
import mk.bumble.fragments.Projects;
import mk.bumble.fragments.RCcar;
import mk.bumble.models.UserModel;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    BottomNavigationView navigation;
    UserModel userModel = new UserModel();


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
        return false;
    }
}
