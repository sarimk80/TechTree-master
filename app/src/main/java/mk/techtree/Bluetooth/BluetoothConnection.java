package mk.techtree.Bluetooth;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//import com.roger.catloadinglibrary.CatLoadingView;
//import com.wang.avi.AVLoadingIndicatorView;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import mk.techtree.R;
import spencerstudios.com.bungeelib.Bungee;


public class BluetoothConnection extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "MainActivity";

    BluetoothAdapter mBluetoothAdapter;
    //Button btnEnableDisable_Discoverable;

    DeviceConnectionService mBluetoothConnection;

    // Button btnStartConnection;
    //Button btnSend;
    ImageView discover;
    ImageView arrow_up, arrow_down, arrow_left, arrow_right, imageView2;
    TextView sonar;
    Button distance;
    //CatLoadingView catLoadingView;
    //AVLoadingIndicatorView pacman;
    TextView loading;

    //EditText etSend;

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
    private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (action.equals(mBluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, mBluetoothAdapter.ERROR);

                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        // Log.d(TAG, "onReceive: STATE OFF");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        //Log.d(TAG, "mBroadcastReceiver1: STATE TURNING OFF");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        //Log.d(TAG, "mBroadcastReceiver1: STATE ON");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        // Log.d(TAG, "mBroadcastReceiver1: STATE TURNING ON");
                        break;
                }
            }
        }
    };

    /**
     * Broadcast Receiver for changes made to bluetooth states such as:
     * 1) Discoverability mode on/off or expire.
     */
    private final BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {

                int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);

                switch (mode) {
                    //Device is in Discoverable Mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        // Log.d(TAG, "mBroadcastReceiver2: Discoverability Enabled.");
                        break;
                    //Device not in discoverable mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        // Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Able to receive connections.");
                        break;
                    case BluetoothAdapter.SCAN_MODE_NONE:
                        // Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Not able to receive connections.");
                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        //Log.d(TAG, "mBroadcastReceiver2: Connecting....");
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        // Log.d(TAG, "mBroadcastReceiver2: Connected.");
                        break;
                }

            }
        }
    };


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
    private final BroadcastReceiver mBroadcastReceiver4 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //3 cases:
                //case1: bonded already
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
                    //Log.d(TAG, "BroadcastReceiver: BOND_BONDED.");
                    //inside BroadcastReceiver4
                    mBTDevice = mDevice;
                }
                //case2: creating a bone
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
                    //Log.d(TAG, "BroadcastReceiver: BOND_BONDING.");
                }
                //case3: breaking a bond
                if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {
                    //Log.d(TAG, "BroadcastReceiver: BOND_NONE.");
                }
            }
        }
    };


    @Override
    protected void onDestroy() {
        //Log.d(TAG, "onDestroy: called.");
        super.onDestroy();

//        unregisterReceiver(mBroadcastReceiver1);
//        unregisterReceiver(mBroadcastReceiver2);
//        unregisterReceiver(mBroadcastReceiver3);
        unregisterReceiver(mBroadcastReceiver4);
        mBluetoothAdapter.cancelDiscovery();
        //mBluetoothAdapter.disable();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Button btnONOFF = findViewById(R.id.btnONOFF);
        //btnEnableDisable_Discoverable = findViewById(R.id.btnDiscoverable_on_off);
        lvNewDevices = findViewById(R.id.lvNewDevices);
        //mBTDevices = new ArrayList<>();

        //btnStartConnection = findViewById(R.id.btnStartConnection);
        //btnSend = findViewById(R.id.btnSend);
        //etSend = findViewById(R.id.editText);
        //discover = findViewById(R.id.findDevices);
        arrow_up = findViewById(R.id.arrow_up);
        arrow_down = findViewById(R.id.arrow_down);
        arrow_right = findViewById(R.id.arrow_right);
        arrow_left = findViewById(R.id.arrow_left);
        imageView2 = findViewById(R.id.imageView2);
        //sonar = findViewById(R.id.sonar);
        //distance = findViewById(R.id.getdistance);
       // pacman = findViewById(R.id.pacman);
        //loading=findViewById(R.id.loading);


        //Broadcasts when bond state changes (ie:pairing)
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver4, filter);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        lvNewDevices.setOnItemClickListener(BluetoothConnection.this);
        //catLoadingView = new CatLoadingView();

        enableDisableBT();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                btnDiscover();
            }
        }, 7000);


//        btnONOFF.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "onClick: enabling/disabling bluetooth.");
//                enableDisableBT();
//            }
//        });

//        btnStartConnection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startConnection();
//            }
//        });


        arrow_up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    //Log.d("Main","Motors are not moving");
                    String down = "S";
                    byte[] bytes = down.toString().getBytes(Charset.defaultCharset());
                    mBluetoothConnection.write(bytes);
                    //Log.d("Main","Motors is stop");
                    return true;
                }

                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    String down = "F";
                    byte[] bytes = down.toString().getBytes(Charset.defaultCharset());
                    mBluetoothConnection.write(bytes);
                    //Log.d("Main","Motors are moving forward");
                    return true;
                }

                return false;
            }
        });

//        arrow_up.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        arrow_down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    //Log.d("Main","Motors are not moving");
                    String down = "S";
                    byte[] bytes = down.toString().getBytes(Charset.defaultCharset());
                    mBluetoothConnection.write(bytes);
                    //Log.d("Main","Motors are not moving");
                    return true;
                }

                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    String down = "D";
                    byte[] bytes = down.toString().getBytes(Charset.defaultCharset());
                    mBluetoothConnection.write(bytes);
                    //Log.d("Main","Motors are moving backward");
                    return true;
                }

                return false;
            }
        });

        arrow_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String down = "D";
                byte[] bytes = down.toString().getBytes(Charset.defaultCharset());
                mBluetoothConnection.write(bytes);
            }
        });

        arrow_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    //Log.d("Main","Motors are not moving");
                    String down = "S";
                    byte[] bytes = down.toString().getBytes(Charset.defaultCharset());
                    mBluetoothConnection.write(bytes);
                    //Log.d("Main","Motors are not moving");
                    return true;
                }

                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    String down = "L";
                    byte[] bytes = down.toString().getBytes(Charset.defaultCharset());
                    mBluetoothConnection.write(bytes);
                    //Log.d("Main","Motors are moving left");
                    return true;
                }

                return false;
            }
        });
//        arrow_left.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String left = "L";
//                byte[] bytes = left.toString().getBytes(Charset.defaultCharset());
//                mBluetoothConnection.write(bytes);
//            }
//        });

        arrow_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    //Log.d("Main","Motors are not moving");
                    String down = "S";
                    byte[] bytes = down.toString().getBytes(Charset.defaultCharset());
                    mBluetoothConnection.write(bytes);
                    //Log.d("Main","Motors are not moving");
                    return true;
                }

                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    String down = "R";
                    byte[] bytes = down.toString().getBytes(Charset.defaultCharset());
                    mBluetoothConnection.write(bytes);
                    //Log.d("Main","Motors are moving right");
                    return true;
                }
                return false;
            }
        });
//        arrow_right.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String right = "R";
//                byte[] bytes = right.toString().getBytes(Charset.defaultCharset());
//                mBluetoothConnection.write(bytes);
//            }
//        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stop = "S";
                byte[] bytes = stop.getBytes(Charset.defaultCharset());
                mBluetoothConnection.write(bytes);
            }
        });
        distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stop = "W";
                byte[] bytes = stop.getBytes(Charset.defaultCharset());
                mBluetoothConnection.write(bytes);
            }
        });

//        btnEnableDisable_Discoverable.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                btnEnableDisable_Discoverable(view);
//            }
//        });

        discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDiscover();
            }
        });

    }

    //create method for starting connection
//***remember the conncction will fail and app will crash if you haven't paired first
    public void startConnection() {
        startBTConnection(mBTDevice, MY_UUID_INSECURE);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
//                Intent intent = new Intent(MainActivity.this, DataSend.class);
//                startActivity(intent);
//                Bungee.slideLeft(MainActivity.this);
                String up = "S";
                byte[] bytes = up.getBytes(Charset.defaultCharset());
                mBluetoothConnection.write(bytes);
                lvNewDevices.setVisibility(View.GONE);
                //catLoadingView.dismiss();
                //pacman.setVisibility(View.INVISIBLE);
                //pacman.hide();
                //loading.setVisibility(View.INVISIBLE);


            }
        }, 6000);
    }

    /**
     * starting chat service method
     */
    public void startBTConnection(BluetoothDevice device, UUID uuid) {
        //Log.d(TAG, "startBTConnection: Initializing RFCOM Bluetooth Connection.");

        mBluetoothConnection.startClient(device, uuid);
    }


    public void enableDisableBT() {
        if (mBluetoothAdapter == null) {
            Log.d(TAG, "enableDisableBT: Does not have BT capabilities.");

        }
        if (!mBluetoothAdapter.isEnabled()) {
            //Log.d(TAG, "enableDisableBT: enabling BT.");
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBTIntent);

            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1, BTIntent);
        }
//        if (mBluetoothAdapter.isEnabled()) {
//            Log.d(TAG, "enableDisableBT: disabling BT.");
//            mBluetoothAdapter.disable();
//
//            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
//            registerReceiver(mBroadcastReceiver1, BTIntent);
//        }

    }


    public void btnEnableDisable_Discoverable(View view) {
        Log.d(TAG, "btnEnableDisable_Discoverable: Making device discoverable for 300 seconds.");

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);

        IntentFilter intentFilter = new IntentFilter(mBluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        registerReceiver(mBroadcastReceiver2, intentFilter);

    }

    public void btnDiscover() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        Log.d(TAG, "btnDiscover: Looking for unpaired devices.");

        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
            // Log.d(TAG, "btnDiscover: Canceling discovery.");

            //check BT permissions in manifest
            checkBTPermissions();

            if (pairedDevices.size() > 0) {
                // There are paired devices. Get the name and address of each paired device.
                for (BluetoothDevice device : pairedDevices) {
                    String deviceName = device.getName();
                    String deviceHardwareAddress = device.getAddress(); // MAC address
                    Log.d(TAG, device + deviceName + deviceHardwareAddress);
                    mBTDevices.add(device);

//                    if(device.getName().equals("raspberrypi")){
//                        mBTDevice=device;
//                    }

                }
                mDeviceListAdapter = new DeviceAdapter(BluetoothConnection.this, R.layout.device_adapter, mBTDevices);
                lvNewDevices.setAdapter(mDeviceListAdapter);
            }


//            mBluetoothAdapter.startDiscovery();
//            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
        if (!mBluetoothAdapter.isDiscovering()) {

            //check BT permissions in manifest
            checkBTPermissions();

            //Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            if (pairedDevices.size() > 0) {
                // There are paired devices. Get the name and address of each paired device.
                for (BluetoothDevice device : pairedDevices) {
                    String deviceName = device.getName();
                    String deviceHardwareAddress = device.getAddress(); // MAC address
                    Log.d(TAG, device + deviceName + deviceHardwareAddress);
                    mBTDevices.add(device);
                }
                mDeviceListAdapter = new DeviceAdapter(BluetoothConnection.this, R.layout.device_adapter, mBTDevices);
                lvNewDevices.setAdapter(mDeviceListAdapter);
            }
//            mBluetoothAdapter.startDiscovery();
//            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
    }

    /**
     * This method is required for all devices running API23+
     * Android must programmatically check the permissions for bluetooth. Putting the proper permissions
     * in the manifest is not enough.
     * <p>
     * NOTE: This will only execute on versions > LOLLIPOP because it is not needed otherwise.
     */
    private void checkBTPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
            }
        } else {
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //first cancel discovery because its very memory intensive.
        mBluetoothAdapter.cancelDiscovery();

        //Log.d(TAG, "onItemClick: You Clicked on a device.");
        String deviceName = mBTDevices.get(i).getName();
        String deviceAddress = mBTDevices.get(i).getAddress();


        //Log.d(TAG, "onItemClick: deviceName = " + deviceName);
        //Log.d(TAG, "onItemClick: deviceAddress = " + deviceAddress);

        //create the bond.
        //NOTE: Requires API 17+? I think this is JellyBean

        //Log.d(TAG, "Trying to pair with " + deviceName);
        mBTDevices.get(i).createBond();
        //Log.d(TAG, "Creating Bond " + deviceName);
        mBTDevice = mBTDevices.get(i);
        //Log.d(TAG, "Paired With " + deviceName);
        mBluetoothConnection = new DeviceConnectionService(BluetoothConnection.this);

        startConnection();
        //pacman.setVisibility(View.VISIBLE);
        //pacman.show();
        //loading.setVisibility(View.VISIBLE);
        //catLoadingView.show(getSupportFragmentManager(),"");


    }

    @Override
    protected void onStop() {
        mBluetoothAdapter.cancelDiscovery();
        mBluetoothAdapter.disable();
        super.onStop();
    }
}