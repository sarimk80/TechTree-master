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
public class RaspberryPi extends Fragment {


    @BindView(R.id.usb)
    ImageView usb;

    @BindView(R.id.heading)
    TextView heading;

    @BindView(R.id.content)
    TextView content;

    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.textview)
    TextView textView;


    @BindView(R.id.imgLogout)
    ImageView logout;


    ImageView imageView;


    public RaspberryPi() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_raspberry_pi, container, false);

        ButterKnife.bind(this, view);

        back.setVisibility(View.VISIBLE);
        textView.setText(R.string.pi);

        logout.setVisibility(View.INVISIBLE);
        imageView = view.findViewById(R.id.usb);

        Toast.makeText(getContext(), "Tap on components to read more about it", Toast.LENGTH_LONG).show();


        if (!BaseApplication.isKitAnimationDisplayed) {
            BaseApplication.isKitAnimationDisplayed = true;
            showSpotLight(UUID.randomUUID().toString());
        }

        return view;

    }

    private void showSpotLight(String uniqueID) {
        new SpotlightView.Builder(getActivity())
                .introAnimationDuration(400)
                .enableRevealAnimation(true)
                .performClick(true)
                .fadeinTextDuration(400)
                .headingTvColor(Color.parseColor("#F1C40F"))
                .headingTvSize(32)
                .headingTvText("USB Port")
                .subHeadingTvColor(Color.parseColor("#F1C40F"))
                .subHeadingTvSize(16)
                .subHeadingTvText("Click on USB Port to read more about it !!!!")
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
    public void back() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.usb)
    public void usb() {
        heading.setText(R.string.Usb_Heading);
        content.setText(R.string.Usb_Text);
    }

    @OnClick(R.id.usb1)
    public void usb1() {
        heading.setText(R.string.Usb_Heading);
        content.setText(R.string.Usb_Text);
    }

    @OnClick(R.id.ethernet)
    public void etherner() {
        heading.setText(R.string.Ethernet_Heading);
        content.setText(R.string.Ethernet_text);
    }


    @OnClick(R.id.audio)
    public void audio() {
        heading.setText(R.string.Audio_Heading);
        content.setText(R.string.Audio_Text);
    }

    @OnClick(R.id.hdmi)
    public void hdmi() {
        heading.setText(R.string.Hdmi_Heading);
        content.setText(R.string.Hdmi_Text);
    }

    @OnClick(R.id.power)
    public void power() {
        heading.setText(R.string.Power_Heading);
        content.setText(R.string.Power_Text);
    }


    @OnClick(R.id.processor)
    public void processor() {
        heading.setText(R.string.Processor_Heading);
        content.setText(R.string.Processor_Text);
    }

    @OnClick(R.id.camera)
    public void camera() {
        heading.setText(R.string.Camera_Heading);
        content.setText(R.string.Camera_Text);
    }


    @OnClick(R.id.gpio)
    public void gpio() {
        heading.setText(R.string.GPIO_Heading);
        content.setText(R.string.GPIO_Text);
    }


    @OnClick(R.id.lcd)
    public void lcd() {
        heading.setText(R.string.LCD_Heading);
        content.setText(R.string.LCD_Text);
    }


}
