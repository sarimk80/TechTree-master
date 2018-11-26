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

        back.setVisibility(View.INVISIBLE);
        textView.setText("Raspberry Pi");

        logout.setVisibility(View.INVISIBLE);
        imageView = view.findViewById(R.id.usb);

        Toast.makeText(getContext(),"Tap on components to read more about it",Toast.LENGTH_LONG).show();


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
    public void back(){
        Intent intent=new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.usb)
    public void usb() {
        heading.setText("USB Port");
        content.setText("USB port allow USB devices to be connected to each other with and transfer digital data over the USB Cable. It allows to connect generic keyboard and mouse which is utilize for operating Raspberry Pi.");
    }

    @OnClick(R.id.usb1)
    public void usb1() {
        heading.setText("USB Port");
        content.setText("USB port allow USB devices to be connected to each other with and transfer digital data over the USB Cable. It allows to connect generic keyboard and mouse which is utilize for operating Raspberry Pi.");
    }

    @OnClick(R.id.ethernet)
    public void etherner() {
        heading.setText("Ethernet");
        content.setText("The Ethernet port allows the raspberry pi to connect to a network using a wired connection. It allows raspberry pi to transmit and receive data.");
    }


    @OnClick(R.id.audio)
    public void audio() {
        heading.setText("Audio Jack");
        content.setText("Raspberry pi comes with built-in audio jack. You can either connect speakers or headphones to it.");
    }

    @OnClick(R.id.hdmi)
    public void hdmi() {
        heading.setText("HDMI Port");
        content.setText("The High Definition Multimedia Interface (HDMI) is an interface which allows to transmit audio/video data. Through HDMI we can connect either monitor or LCDs.");
    }

    @OnClick(R.id.power)
    public void power() {
        heading.setText("USB Power Port");
        content.setText("The raspberry pi comes with the micro USB power port. We can power up our raspberry pi using this micro USB port.");
    }


    @OnClick(R.id.processor)
    public void processor() {
        heading.setText("Processor");
        content.setText("Raspberry Pi have used a Broadcom BCM2837 system-on-chip (SoC) which includes, four high-performance ARM Cortex-A53 processing cores running at 1.2GHz with Level 1 (32kB) and Level 2 (512kB) cache memory, a VideoCore IV graphics processor, and is linked to 1GB low power DDR2 memory module. ");
    }

    @OnClick(R.id.camera)
    public void camera() {
        heading.setText("CSI Connector");
        content.setText("The Camera Serial Interface (CSI) is an interface between a camera and host processor. It allows camera to be connected with raspberry pi with a CSI ribbon.");
    }


    @OnClick(R.id.gpio)
    public void gpio() {
        heading.setText("GPIO");
        content.setText(" A powerful feature of Raspberry Pi is the row of GPIO pins along the top edge of the board. GPIO pins allows the raspberry pi to be configured for different purposes, interact with different circuitries and work with several type of electronic component, for instance, sensors, motors, DC drives etc.");
    }


    @OnClick(R.id.lcd)
    public void lcd() {
        heading.setText("DSI Connector");
        content.setText("the Display Serial Interface (DSI) allows the touchscreens (LCD/OLED) to be connected with raspberry pi via DSI ribbon. Through this interface we can make touchscreen panels.");
    }


}
