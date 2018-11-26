package mk.techtree.constatnts;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

import java.security.MessageDigest;
import java.util.UUID;


import mk.techtree.BaseApplication;
import mk.techtree.managers.SharedPreferenceManager;

import static android.provider.Settings.Secure.getString;


/**
 * Created by khanhamza on 4/20/2017.
 */

public class AppConstants {

    // Temporary User
    public static String tempUserName = "Developer/Tester";


    /**
     * Static Booleans
     */

    public static boolean isForcedResetFragment;

    /**
     * Date Formats
     */

    public static final String INPUT_DATE_FORMAT = "yyyy-dd-MM hh:mm:ss";
    public static final String INPUT_DATE_FORMAT_AM_PM = "yyyy-dd-MM hh:mm:ss a";
    public static final String OUTPUT_DATE_FORMAT = "EEEE dd,yyyy";
    public static final String INPUT_TIME_FORMAT = "yyyy-dd-MM hh:mm:ss a";
    public static final String OUTPUT_TIME_FORMAT = "hh:mm a";
    public static final String OUTPUT_UTC = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String OUTPUT_DATE_TIME_FORMAT = "EEEE dd,yyyy hh:mm a";
    public static final String INPUT_LAB_DATE_FORMAT_AM_PM = "mm/dd/yyyy hh:mm:ss a";

    // Custom For AKUH
    public static final String INPUT_DATE_FORMAT_IMMUNIZATION = "dd/MM/yyyy";
    public static final String GENERAL_DATE_FORMAT = "dd-MM-yy";
    public static final String FORMAT_DATE_SHOW = "MMM dd, yyyy";
    public static final String FORMAT_DATE_SEND = "dd/MM/yyyy HH:mm:ss";


    /**
     * MASKING FORMATs
     */

    public static final String CNIC_MASK = "99999-9999999-9";
    public static final String CARD_MASK = "9999-9999-9999";
    //    public static final String CARD_MASK = "wwww-wwww-wwww";
    public static final String MR_NUMBER_MASK = "999-99-99";


    /*************** INTENT DATA KEYS **************/
    public static final String LABORATORY_MODEL = "laboratoryModel";
    public static final String JSON_STRING_KEY = "JSON_STRING_KEY";
    public static final String IMAGE_PREVIEW_URL = "url";
    public static final String IMAGE_PREVIEW_TITLE = "title";
    public static final String GCM_DATA_OBJECT = "gcmDataObject";


    /*******************Preferences KEYS******************/
    public static final String KEY_CURRENT_USER_MODEL = "userModel";
    public static final String KEY_NAME = "cardMemberDetail";
    public static final String KEY_CARD_NUMBER = "card_number";
    public static final String KEY_CODE = "code";
    public static final String USER_NOTIFICATION_DATA = "USER_NOTIFICATION_DATA";
    public static String FORCED_RESTART = "forced_restart";
    public static final String KEY_REGISTER_VM = "register_vm";
    public static final String KEY_TOKEN = "getToken";
    public static final String KEY_ONE_TIME_TOKEN = "one_time_token";
    public static final String KEY_CROSS_TAB_DATA = "cross_tab";
    public static final String KEY_REGISTERED_DEVICE = "registered_device";
    public static final String KEY_INSERT_REGISTERED_DEVICE = "registered_device";
    public static final String KEY_FIREBASE_TOKEN_UPDATED = "FIREBASE_TOKEN_UPDATED";
    public static final String KEY_PIN_CODE = "pin_code";
    public static final String KEY_IS_PIN_ENABLE = "is_pin_enable";
    public static final String KEY_CURRENT_LOCATION = "current_location";


    /**
     * File Name initials if user download the pdf
     */
    public static String FILE_NAME = "AKUH-PatientReport";


    /**
     * Data Static Strings
     */

    public static String AboutUs = "<B>Family Hifazat</B> is a product of Aga Khan University Hospital Karachi. It provides discounts on medical services as well as patients’ access to their personal health records through web portal and mobile applications." + "<BR><BR>" +
            "The <B>Family Hifazat</B> “Patient Portal” is secure, confidential and easy to use web portal &amp; mobile apps to provide AKUH patients 24 hour access to their personal health records including diagnostic results, imaging, medications, vaccines and discharge summaries.";


    public static String DEVICE_OS_ANDROID = "ANDROID";


    private static String getDeviceID(Context context) {

/*String Return_DeviceID = USERNAME_and_PASSWORD.getString(DeviceID_key,"Guest");
return Return_DeviceID;*/

        TelephonyManager TelephonyMgr = (TelephonyManager) context.getApplicationContext().getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);

        String m_szImei = ""; // Requires
        if (TelephonyMgr != null) {
            m_szImei = TelephonyMgr.getDeviceId();
        }
// READ_PHONE_STATE

// 2 compute DEVICE ID
        String m_szDevIDShort = "35"
                + // we make this look like a valid IMEI
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10
                + Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10
                + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10
                + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10
                + Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10
                + Build.TAGS.length() % 10 + Build.TYPE.length() % 10
                + Build.USER.length() % 10; // 13 digits
// 3 android ID - unreliable
        String m_szAndroidID = "";
        if (getString(context.getContentResolver(), Settings.Secure.ANDROID_ID) != null) {
            m_szAndroidID = getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
// 4 wifi manager, read MAC address - requires
// android.permission.ACCESS_WIFI_STATE or comes as null
//        WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        String m_szWLANMAC = "";
//        if (wm != null) {
//            m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
//        }
// 5 Bluetooth MAC address android.permission.BLUETOOTH required
//        BluetoothAdapter m_BluetoothAdapter = null; // Local Bluetooth adapter
//        m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        String m_szBTMAC = "";
//        if (m_BluetoothAdapter != null) {
//            m_szBTMAC = m_BluetoothAdapter.getAddress();
//        }
//        System.out.println("m_szBTMAC " + m_szBTMAC);

// 6 SUM THE IDs
//        String m_szLongID = m_szImei + m_szDevIDShort + m_szAndroidID + m_szWLANMAC + m_szBTMAC;
        String m_szLongID = m_szImei + m_szDevIDShort + m_szAndroidID;
        System.out.println("m_szLongID " + m_szLongID);
        MessageDigest m = null;

        // FIXME: 5/28/2018 commenting algo, 30 character value

//        try {
////            m = MessageDigest.getInstance("MD5");
//            m = MessageDigest.getInstance("SHA-256");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }

        // If SHA-256
        if (m == null) {
            if (!m_szLongID.isEmpty()) {
                if (m_szLongID.length() > 30) {
                    return m_szLongID.substring(0, 30);
                } else {
                    return m_szLongID;
                }
            } else {
                return getDeviceID2(context);
            }
        }


        m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
        byte p_md5Data[] = m.digest();

        String m_szUniqueID = "";
        for (int i = 0; i < p_md5Data.length; i++) {
            int b = (0xFF & p_md5Data[i]);
// if it is a single digit, make sure it have 0 in front (proper
// padding)
            if (b <= 0xF)
                m_szUniqueID += "0";
// add number to string
            m_szUniqueID += Integer.toHexString(b);
        }
        m_szUniqueID = m_szUniqueID.toUpperCase();

        Log.i("------DeviceID------", m_szUniqueID);
        Log.d("DeviceIdCheck", "DeviceId that generated MPreferenceActivity:" + m_szUniqueID);

        return m_szUniqueID;
    }
    public static String getDeviceID2(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        String tmDevice = "", tmSerial = "", androidId = "";
        UUID deviceUuid;

        if (tm != null) {
            tmDevice = "" + tm.getDeviceId();
            tmSerial = "" + tm.getSimSerialNumber();
            androidId = "" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        } else {
            androidId = "" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        }

        return deviceUuid.toString();

    }


}
