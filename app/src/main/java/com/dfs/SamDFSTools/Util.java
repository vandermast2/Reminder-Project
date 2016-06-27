package com.dfs.SamDFSTools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import com.dfs.SamDFSTools.fragment.GeneralSettingsFragment;

import java.util.List;

public class Util {
    Context context;

    public Util(Context context) {
        this.context = context;
    }

    public static NetworkInfo getNetworkInfo(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    }

    public String imei() {
        return ((TelephonyManager) this.context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }

    public List test() {
        return ((TelephonyManager) this.context.getSystemService(Context.TELEPHONY_SERVICE)).getAllCellInfo();
    }

    public String radiotype() {
        TelephonyManager telephonyManager = (TelephonyManager) this.context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager.getPhoneType() == 2) {
            return "CDMA";
        }
        if (telephonyManager.getPhoneType() == 1) {
            return "GSM";
        }
        if (telephonyManager.getPhoneType() == 0) {
            return "NONE";
        }
        if (telephonyManager.getPhoneType() == 3) {
            return "SIP";
        }
        return null;
    }

    public String MNCMCC() {
        return ((TelephonyManager) this.context.getSystemService(Context.TELEPHONY_SERVICE)).getNetworkOperator().toString();
    }

    public String sim() {
        TelephonyManager telephonyManager = (TelephonyManager) this.context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager.getSimState() == 1) {
            return "ABSENT";
        }
        if (telephonyManager.getSimState() == 4) {
            return "NETWORK LOCKED";
        }
        if (telephonyManager.getSimState() == 2) {
            return "PIN REQUIRED";
        }
        if (telephonyManager.getSimState() == 3) {
            return "PUK REQUIRED";
        }
        if (telephonyManager.getSimState() == 5) {
            return "READY";
        }
        if (telephonyManager.getSimState() == 0) {
            return "UNKNOWN";
        }
        return null;
    }

    public String operator() {
        TelephonyManager telephonyManager = (TelephonyManager) this.context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager.getNetworkOperatorName().toString().length() > 0) {
            return telephonyManager.getNetworkOperatorName().toString();
        }
        return "No Service";
    }

    public String radio() {
        WifiInfo wifiInfo = ((WifiManager) this.context.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo();
        TelephonyManager telephonyManager = (TelephonyManager) this.context.getSystemService(Context.TELEPHONY_SERVICE);
        if (((ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(1).isConnected()) {
            return "WIFI " + wifiInfo.getSSID();
        }
        if (telephonyManager.getNetworkType() == 8) {
            return telephonyManager.getNetworkOperatorName().toString() + " HSDPA";
        }
        if (telephonyManager.getNetworkType() == 15) {
            return telephonyManager.getNetworkOperatorName().toString() + " HSPAP";
        }
        if (telephonyManager.getNetworkType() == 1) {
            return telephonyManager.getNetworkOperatorName().toString() + " GPRS";
        }
        if (telephonyManager.getNetworkType() == 2) {
            return telephonyManager.getNetworkOperatorName().toString() + " EDGE";
        }
        if (telephonyManager.getNetworkType() == 13) {
            return telephonyManager.getNetworkOperatorName().toString() + " LTE";
        }
        if (telephonyManager.getNetworkType() == 4) {
            return telephonyManager.getNetworkOperatorName().toString() + " CDMA";
        }
        if (telephonyManager.getNetworkType() == 7) {
            return telephonyManager.getNetworkOperatorName().toString() + " 1xRTT";
        }
        if (telephonyManager.getNetworkType() == 11) {
            return telephonyManager.getNetworkOperatorName().toString() + " iDEN";
        }
        if (telephonyManager.getNetworkType() == 3) {
            return telephonyManager.getNetworkOperatorName().toString() + " UMTS";
        }
        if (telephonyManager.getNetworkType() == 5) {
            return telephonyManager.getNetworkOperatorName().toString() + " EVDO_0";
        }
        if (telephonyManager.getNetworkType() == 6) {
            return telephonyManager.getNetworkOperatorName().toString() + " EVDO_A";
        }
        if (telephonyManager.getNetworkType() == 9) {
            return telephonyManager.getNetworkOperatorName().toString() + " HSUPA";
        }
        if (telephonyManager.getNetworkType() == 10) {
            return telephonyManager.getNetworkOperatorName().toString() + " HSPA";
        }
        if (telephonyManager.getNetworkType() == 12) {
            return telephonyManager.getNetworkOperatorName().toString() + " EVDO_B";
        }
        if (telephonyManager.getNetworkType() == 14) {
            return telephonyManager.getNetworkOperatorName().toString() + " EHRPD";
        }
        if (telephonyManager.getNetworkType() == 0) {
            return telephonyManager.getNetworkOperatorName().toString() + " UNKNOWN";
        }
        return null;
    }
}