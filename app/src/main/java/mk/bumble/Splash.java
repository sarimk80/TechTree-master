package mk.bumble;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import mk.bumble.managers.SharedPreferenceManager;
import mk.bumble.models.UserModel;


public class Splash extends AppCompatActivity {
    Class activity = LoginActivity.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Handler handler = new Handler();

        UserModel currentUser = SharedPreferenceManager.getInstance(this).getCurrentUser();

        if (currentUser == null) {
            activity = LoginActivity.class;
        } else {
            activity = MainActivity.class;
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeActivity(activity);
            }
        }, 3000);


        ImageView imageView = findViewById(R.id.centerImage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(activity);
            }
        });
    }

    private void changeActivity(Class activity) {
        Intent intent = new Intent(Splash.this, activity);
        startActivity(intent);
        Splash.this.finish();
    }
}
