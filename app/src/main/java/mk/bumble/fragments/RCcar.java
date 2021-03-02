package mk.bumble.fragments;


import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import mk.bumble.Bluetooth.DeviceAdapter;
import mk.bumble.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RCcar extends Fragment {


    public RCcar() {
        // Required empty public constructor
    }

//    BluetoothAdapter mBluetoothAdapter;
//
//
//    DeviceConnectionService mBluetoothConnection;


    ImageView discover;

    @BindView(R.id.arrow_up)
    ImageView arrow_up;

    @BindView(R.id.arrow_down)
    ImageView arrow_down;

    @BindView(R.id.arrow_left)
    ImageView arrow_left;

    @BindView(R.id.arrow_right)
    ImageView arrow_right;

    @BindView(R.id.imageView2)
    ImageView imageView2;

    @BindView(R.id.imgLogout)
    ImageView logout;


    @BindView(R.id.loading)
    LottieAnimationView loading;




    @BindView(R.id.progress)
    LottieAnimationView lottieAnimationView;

    @BindView(R.id.textview)
    TextView textView;

    @BindView(R.id.textId)
    TextView textId;


    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    //00001101-0000-1000-8000-00805F9B34FB

    BluetoothDevice mBTDevice;
    //BluetoothDevice bluetoothDevice=null;

    public ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();
    List<String> s = new ArrayList<>();

    public DeviceAdapter mDeviceListAdapter;

    ListView lvNewDevices;


    // Create a BroadcastReceiver for ACTION_FOUND
//    private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            // When discovery finds a device
//            if (action.equals(mBluetoothAdapter.ACTION_STATE_CHANGED)) {
//                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, mBluetoothAdapter.ERROR);
//
//                switch (state) {
//                    case BluetoothAdapter.STATE_OFF:
//                        // Log.d(TAG, "onReceive: STATE OFF");
//                        break;
//                    case BluetoothAdapter.STATE_TURNING_OFF:
//                        //Log.d(TAG, "mBroadcastReceiver1: STATE TURNING OFF");
//                        break;
//                    case BluetoothAdapter.STATE_ON:
//                        //Log.d(TAG, "mBroadcastReceiver1: STATE ON");
//                        break;
//                    case BluetoothAdapter.STATE_TURNING_ON:
//                        // Log.d(TAG, "mBroadcastReceiver1: STATE TURNING ON");
//                        break;
//                }
//            }
//        }
//    };

    /**
     * Broadcast Receiver for changes made to bluetooth states such as:
     * 1) Discoverability mode on/off or expire.
//     */
//    private final BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver() {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            final String action = intent.getAction();
//
//            if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {
//
//                int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);
//
//                switch (mode) {
//                    //Device is in Discoverable Mode
//                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
//                        // Log.d(TAG, "mBroadcastReceiver2: Discoverability Enabled.");
//                        break;
//                    //Device not in discoverable mode
//                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
//                        // Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Able to receive connections.");
//                        break;
//                    case BluetoothAdapter.SCAN_MODE_NONE:
//                        // Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Not able to receive connections.");
//                        break;
//                    case BluetoothAdapter.STATE_CONNECTING:
//                        //Log.d(TAG, "mBroadcastReceiver2: Connecting....");
//                        break;
//                    case BluetoothAdapter.STATE_CONNECTED:
//                        // Log.d(TAG, "mBroadcastReceiver2: Connected.");
//                        break;
//                }
//
//            }
//        }
//    };


    /**
     * Broadcast Receiver for listing devices that are not yet paired
     * -Executed by btnDiscover() method.
     */
   /* private BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d(TAG, "onReceive: ACTION FOUND.");

            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mBTDevices.add(device);
                Log.d(TAG, "onReceive: " + device.getName() + ": " + device.getAddress());
                mDeviceListAdapter = new DeviceListAdapter(context, R.layout.device_adapter, mBTDevices);
                lvNewDevices.setAdapter(mDeviceListAdapter);
            }
        }
    };*/

    /**
     * Broadcast Receiver that detects bond state changes (Pairing status changes)
     */
//    private final BroadcastReceiver mBroadcastReceiver4 = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            final String action = intent.getAction();
//
//            if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
//                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                //3 cases:
//                //case1: bonded already
//                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
//                    //Log.d(TAG, "BroadcastReceiver: BOND_BONDED.");
//                    //inside BroadcastReceiver4
//                    mBTDevice = mDevice;
//                }
//                //case2: creating a bone
//                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
//                    //Log.d(TAG, "BroadcastReceiver: BOND_BONDING.");
//                }
//                //case3: breaking a bond
//                if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {
//                    //Log.d(TAG, "BroadcastReceiver: BOND_NONE.");
//                }
//            }
//        }
//    };


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View _view = inflater.inflate(R.layout.fragment_rccar, container, false);

        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();
         String unique_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        String android_id=unique_id.substring(0,7);



        lvNewDevices = _view.findViewById(R.id.lvNewDevices);
        Map<String, Object> up = new HashMap<>();
        Map<String, Object> down = new HashMap<>();
        Map<String, Object> left = new HashMap<>();
        Map<String, Object> right = new HashMap<>();
        Map<String, Object> stop = new HashMap<>();
        up.put("direction", "F");
        down.put("direction", "D");
        left.put("direction", "L");
        right.put("direction", "R");
        stop.put("direction","S");


        ButterKnife.bind(this, _view);

        logout.setVisibility(View.INVISIBLE);

        //arrow_up = _view.findViewById(R.id.arrow_up);
        //arrow_down = _view.findViewById(R.id.arrow_down);
        //arrow_right = _view.findViewById(R.id.arrow_right);
        //arrow_left = _view.findViewById(R.id.arrow_left);
        //imageView2 = _view.findViewById(R.id.imageView2);
        //loading = _view.findViewById(R.id.loading);
        //cat = _view.findViewById(R.id.gif1);
        //lottieAnimationView = _view.findViewById(R.id.progress);


//        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
//        getActivity().registerReceiver(mBroadcastReceiver4, filter);


       // mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //lvNewDevices.setOnItemClickListener(RCcar.this);
        //catLoadingView = new CatLoadingView();

        textView.setText("Bumble Pi");
        textId.setText(android_id);

       // enableDisableBT();

        //lottieAnimationView.setVisibility(View.VISIBLE);
        //lottieAnimationView.playAnimation();

//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Do something after 5s = 5000ms
//                btnDiscover();
//            }
//        }, 7000);

        arrow_up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    //Log.d("Main","Motors are not moving");
                    String down = "S";
                    byte[] bytes = down.toString().getBytes(Charset.defaultCharset());
                   // mBluetoothConnection.write(bytes);
                    //Log.d("Main","Motors is stop");
                    db.collection("car").document(android_id)
                            .set(stop).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG", "onSuccess: ");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG", e.toString());
                        }
                    });
                    return true;
                }

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    String down = "F";
                    byte[] bytes = down.toString().getBytes(Charset.defaultCharset());
                    //mBluetoothConnection.write(bytes);
                    //Log.d("Main","Motors are moving forward");
                    db.collection("car").document(android_id)
                            .set(up).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG", "onSuccess: ");

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG", e.toString());
                        }
                    });
                    return true;
                }

                return false;
            }
        });

        arrow_down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    //Log.d("Main","Motors are not moving");
                    //String down = "S";
                    //byte[] bytes = down.toString().getBytes(Charset.defaultCharset());
                    //mBluetoothConnection.write(bytes);
                    //Log.d("Main","Motors are not moving");
                    db.collection("car").document(android_id)
                            .set(stop).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                    return true;
                }

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    //String down = "D";
                    //byte[] bytes = down.toString().getBytes(Charset.defaultCharset());
                   // mBluetoothConnection.write(bytes);
                    //Log.d("Main","Motors are moving backward");
                    db.collection("car").document(android_id)
                            .set(down).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                    return true;
                }

                return false;
            }
        });


        arrow_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    //Log.d("Main","Motors are not moving");
                    String down = "S";
                    byte[] bytes = down.toString().getBytes(Charset.defaultCharset());
                    //mBluetoothConnection.write(bytes);
                    //Log.d("Main","Motors are not moving");
                    db.collection("car").document(android_id)
                            .set(stop).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                    return true;
                }

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    String down = "L";
                    byte[] bytes = down.toString().getBytes(Charset.defaultCharset());
                   // mBluetoothConnection.write(bytes);
                    //Log.d("Main","Motors are moving left");
                    db.collection("car").document(android_id)
                            .set(left).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                    return true;
                }

                return false;
            }
        });


        arrow_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    //Log.d("Main","Motors are not moving");
                    String down = "S";
                    byte[] bytes = down.toString().getBytes(Charset.defaultCharset());
                   // mBluetoothConnection.write(bytes);
                    //Log.d("Main","Motors are not moving");
                    db.collection("car").document(android_id)
                            .set(stop).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                    return true;
                }

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    String down = "R";
                    byte[] bytes = down.toString().getBytes(Charset.defaultCharset());
                   // mBluetoothConnection.write(bytes);
                    //Log.d("Main","Motors are moving right");
                    db.collection("car").document(android_id)
                            .set(right).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                    return true;
                }
                return false;
            }
        });


        return _view;
    }

//    public void startConnection() {
//        startBTConnection(mBTDevice, MY_UUID_INSECURE);
//
//
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Do something after 5s = 5000ms
////                Intent intent = new Intent(MainActivity.this, DataSend.class);
////                startActivity(intent);
////                Bungee.slideLeft(MainActivity.this);
//                String up = "S";
//                byte[] bytes = up.getBytes(Charset.defaultCharset());
//                //mBluetoothConnection.write(bytes);
//                lvNewDevices.setVisibility(View.GONE);
//                //catLoadingView.dismiss();
//                //pacman.setVisibility(View.INVISIBLE);
//                //pacman.hide();
//                loading.setVisibility(View.INVISIBLE);
//
//
//
//            }
//        }, 6000);
//    }

//    public void startBTConnection(BluetoothDevice device, UUID uuid) {
//        //Log.d(TAG, "startBTConnection: Initializing RFCOM Bluetooth Connection.");
//
//        mBluetoothConnection.startClient(device, uuid);
//    }
//
//
//    public void enableDisableBT() {
//        if (mBluetoothAdapter == null) {
//            // Log.d(TAG, "enableDisableBT: Does not have BT capabilities.");
//
//        }
//        if (!mBluetoothAdapter.isEnabled()) {
//            //Log.d(TAG, "enableDisableBT: enabling BT.");
//            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivity(enableBTIntent);
//
//            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
//            getActivity().registerReceiver(mBroadcastReceiver1, BTIntent);
//
//        }
//        if (mBluetoothAdapter.isEnabled()) {
//            btnDiscover();
//        }
//
//    }

//    public void btnDiscover() {
//
//        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
//        //Log.d(TAG, "btnDiscover: Looking for unpaired devices.");
//
//        if (mBluetoothAdapter.isDiscovering()) {
//            mBluetoothAdapter.cancelDiscovery();
//            // Log.d(TAG, "btnDiscover: Canceling discovery.");
//
//            //check BT permissions in manifest
//            checkBTPermissions();
//
//            if (pairedDevices.size() > 0) {
//                // There are paired devices. Get the name and address of each paired device.
//                for (BluetoothDevice device : pairedDevices) {
//                    String deviceName = device.getName();
//                    String deviceHardwareAddress = device.getAddress(); // MAC address
//                    //Log.d(TAG, device + deviceName + deviceHardwareAddress);
//                    mBTDevices.add(device);
//
////                    if(device.getName().equals("raspberrypi")){
////                        mBTDevice=device;
////                    }
//
//                }
//
//                mDeviceListAdapter = new DeviceAdapter(getActivity(), R.layout.device_adapter, mBTDevices);
//                lvNewDevices.setAdapter(mDeviceListAdapter);
//
//            }
//
//
////            mBluetoothAdapter.startDiscovery();
////            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
////            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
//        }
//        if (!mBluetoothAdapter.isDiscovering()) {
//
//            //check BT permissions in manifest
//            checkBTPermissions();
//
//            //Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
//
//            if (pairedDevices.size() > 0) {
//                // There are paired devices. Get the name and address of each paired device.
//                for (BluetoothDevice device : pairedDevices) {
//                    String deviceName = device.getName();
//                    String deviceHardwareAddress = device.getAddress(); // MAC address
//                    //Log.d(TAG, device + deviceName + deviceHardwareAddress);
//                    mBTDevices.add(device);
//                }
//                mDeviceListAdapter = new DeviceAdapter(getContext(), R.layout.device_adapter, mBTDevices);
//                lvNewDevices.setAdapter(mDeviceListAdapter);
//                lottieAnimationView.pauseAnimation();
//                lottieAnimationView.setVisibility(View.INVISIBLE);
//            }
////            mBluetoothAdapter.startDiscovery();
////            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
////            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
//        }
//    }
//
//
//    private void checkBTPermissions() {
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
//            int permissionCheck = 0;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                permissionCheck = getActivity().checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
//            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                permissionCheck += getActivity().checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
//            }
//            if (permissionCheck != 0) {
//
//                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
//            }
//        } else {
//            // Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
//        }
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//
//        mBluetoothAdapter.cancelDiscovery();
//
//        //Log.d(TAG, "onItemClick: You Clicked on a device.");
//        String deviceName = mBTDevices.get(i).getName();
//        String deviceAddress = mBTDevices.get(i).getAddress();
//
//
//        //Log.d(TAG, "onItemClick: deviceName = " + deviceName);
//        //Log.d(TAG, "onItemClick: deviceAddress = " + deviceAddress);
//
//        //create the bond.
//        //NOTE: Requires API 17+? I think this is JellyBean
//
//        //Log.d(TAG, "Trying to pair with " + deviceName);
//
//        mBTDevices.get(i).createBond();
//        //Log.d(TAG, "Creating Bond " + deviceName);
//        mBTDevice = mBTDevices.get(i);
//
//
//        //Log.d(TAG, "Paired With " + deviceName);
//        mBluetoothConnection = new DeviceConnectionService(getActivity());
//
//        startConnection();
//        loading.setVisibility(View.VISIBLE);
//
//
//    }

    @Override
    public void onStop() {

//        mBluetoothAdapter.cancelDiscovery();
//        mBluetoothAdapter.disable();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        getActivity().unregisterReceiver(mBroadcastReceiver4);
//        mBluetoothAdapter.cancelDiscovery();
//        mBluetoothAdapter.disable();
    }
}
