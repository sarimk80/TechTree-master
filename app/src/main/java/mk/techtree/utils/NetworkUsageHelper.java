package mk.techtree.utils;

import android.net.TrafficStats;

/**
 * Created by muhammadhumzakhan on 8/7/2017.
 */

public class NetworkUsageHelper {

    private static NetworkUsageHelper usageHelper;
    /*Saving Data in Bytes*/
    private long RxInitial;
    private long TxInitial;
    private long RxFinal;
    private long TxFinal;

    public static NetworkUsageHelper getInstance(){
        if(usageHelper == null)
            usageHelper = new NetworkUsageHelper();

        return usageHelper;
    }

    public void setInitialValues(){
        int uid = android.os.Process.myUid();
        this.RxInitial = TrafficStats.getUidRxBytes(uid);
        this.TxInitial = TrafficStats.getUidTxBytes(uid);
    }

    public void setInitialValues(long RxInitial , long TxInitial){
        this.RxInitial = RxInitial;
        this.TxInitial = TxInitial;
    }


    public void setFinalValues() {
        int uid = android.os.Process.myUid();
        this.RxFinal = TrafficStats.getUidRxBytes(uid);
        this.TxFinal = TrafficStats.getUidTxBytes(uid);
    }

    public void setFinalValues(long RxFinal , long TxFinal){
        this.RxFinal = RxFinal;
        this.TxFinal = TxFinal;
    }

    public long getRxDifferenceBytes(){
        return RxFinal - RxInitial;
    }

    public long getTxDifferenceBytes(){
        return TxFinal - TxInitial;
    }

    // 1 KB = 1024 Bytes
    public long getRxDifferenceKB(){
        return (RxFinal - RxInitial)/1024;
    }

    public long getTxDifferenceKB(){
        return (TxFinal - TxInitial)/1024;
    }

    // 1 MB = 1024 * 1024 Bytes
    public long getRxDifferenceMB(){
        return (RxFinal - RxInitial)/(1024 * 1024);
    }

    public long getTxDifferenceMB(){
        return (TxFinal - TxInitial)/(1024 * 1024);
    }

}
