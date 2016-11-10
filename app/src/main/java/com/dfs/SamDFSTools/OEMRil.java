package com.dfs.SamDFSTools;

import android.content.Context;
import android.os.IBinder;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;

/**
 * Created by Sam on 6/26/2016.
 */
public class OEMRil {private static final String TAG = "RIL";
    public static String lock;
    public static String stru;

    static {
        stru = null;
        lock = null;
    }

    public static int sendOemRilRequestRaw(Context ctx, byte[] data, byte[] reply) {
        try {
            Object obj_remote = Class.forName("android.os.ServiceManager").getDeclaredMethod("getService", new Class[]{String.class}).invoke(null, new Object[]{"phone"});
            Object service = Class.forName("com.android.internal.telephony.ITelephony$Stub").getDeclaredMethod("asInterface", new Class[]{IBinder.class}).invoke(null, new Object[]{obj_remote});
            return ((Integer) service.getClass().getDeclaredMethod("sendOemRilRequestRaw", new Class[]{byte[].class, byte[].class}).invoke(service, new Object[]{data, reply})).intValue();
        } catch (Exception e) {
            return -1;
        }
    }

    public static String getMSL(Context ctx) {
        byte[] reply = new byte[AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD];
        int result = sendOemRilRequestRaw(ctx, new byte[]{(byte) 81, (byte) 2, (byte) 0, (byte) 5, (byte) 1}, reply);
        if (result <= 0) {
            return null;
        }
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < result; i++) {
            str.append((char) reply[i]);
        }
        stru = str.substring(0, 6);
        return str.toString();
    }

    public static int setSimLocked(Context ctx) {
        return sendOemRilRequestRaw(ctx, new byte[]{(byte) 81, (byte) 65, (byte) 0, (byte) 5, (byte) 0}, new byte[AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD]);
    }

    public static int setSimLock(Context ctx) {
        return sendOemRilRequestRaw(ctx, new byte[]{(byte) 81, (byte) 65, (byte) 0, (byte) 5, (byte) 1}, new byte[AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD]);
    }

    public static int getSimLock(Context ctx) {
        byte[] reply = new byte[AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD];
        if (sendOemRilRequestRaw(ctx, new byte[]{(byte) 81, (byte) 66, (byte) 0, (byte) 4}, reply) == 1) {
            return reply[0];
        }
        return -1;
    }

    public static int resetRadio(Context ctx) {
        return sendOemRilRequestRaw(ctx, new byte[]{(byte) 10, (byte) 33, (byte) 0, (byte) 4}, new byte[AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD]);
    }






}