package mk.techtree.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.wooplr.spotlight.SpotlightView;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mk.techtree.BaseApplication;
import mk.techtree.MainActivity;
import mk.techtree.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Kit extends Fragment {

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.textview)
    TextView textView;

    @BindView(R.id.sonar)
    ImageView sonar;

    @BindView(R.id.servo)
    ImageView servo;

    @BindView(R.id.battery)
    ImageView battrey;

    @BindView(R.id.pi)
    ImageView pi;

    ImageView imageView;

    @BindView(R.id.heading)
    TextView heading;

    @BindView(R.id.content)
    TextView content;

    @BindView(R.id.wheel)
    ImageView wheel;

    @BindView(R.id.robot)
    ImageView robot;

    @BindView(R.id.imgLogout)
    ImageView logout;

    public Kit() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kit, container, false);

        imageView=view.findViewById(R.id.sonar);

        ButterKnife.bind(this, view);
        back.setVisibility(View.VISIBLE);
        textView.setText(R.string.Bumble_Pi);
        logout.setVisibility(View.INVISIBLE);

        Toast.makeText(getContext(),"Tap on components to read more about it",Toast.LENGTH_LONG).show();

        if (!BaseApplication.isKitAnimationDisplayed) {
            BaseApplication.isKitAnimationDisplayed = true;
            showSpotlight(UUID.randomUUID().toString());
        }

        return view;
    }

    private void showSpotlight(String uniqueID) {
        new SpotlightView.Builder(getActivity())
                .introAnimationDuration(400)
                .enableRevealAnimation(true)
                .performClick(true)
                .fadeinTextDuration(400)
                .headingTvColor(Color.parseColor("#F1C40F"))
                .headingTvSize(32)
                .headingTvText("Sonar")
                .subHeadingTvColor(Color.parseColor("#F1C40F"))
                .subHeadingTvSize(16)
                .subHeadingTvText("Click on Sonar to read more about it !!!!")
                .maskColor(Color.parseColor("#dc000000"))
                .target(imageView)
                .lineAnimDuration(400)
                .lineAndArcColor(Color.parseColor("#F1C40F"))
                .dismissOnTouch(true)
                .dismissOnBackPress(true)
                .enableDismissAfterShown(true)
                .usageId(uniqueID) //UNIQUE ID
                .show();
    }

    @OnClick(R.id.back)
    public void back(){
        Intent intent=new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.servo)
    public void servo_click(){
        heading.setText(R.string.Servo_Heaing);
        content.setText(R.string.Servo_Text);
    }

    @OnClick(R.id.sonar)
    public void sonar_click(){
        heading.setText(R.string.Sonar_Heading);
        content.setText(R.string.Sonar_Text);
    }

    @OnClick(R.id.battery)
    public void battery_click(){
        heading.setText(R.string.Battery_Heading);
        content.setText(R.string.Battery_Text);
    }

    @OnClick(R.id.motor)
    public void motor_click(){
        heading.setText(R.string.Motor_Heading);
        content.setText(R.string.Motor_Text);
    }
    @OnClick(R.id.pi)
    public void pi_click(){
        heading.setText(R.string.Raspberry_Heading);
        content.setText(R.string.Raspberry_Text);
    }

    @OnClick(R.id.wheel)
    public void Wheel_click(){
        heading.setText(R.string.Wheel_Heading);
        content.setText(R.string.Wheel_Text);
    }

    @OnClick(R.id.robot)
    public void robot_click(){
        heading.setText(R.string.Rorobt_Heading);
        content.setText(R.string.Robot_Text);
    }

}
