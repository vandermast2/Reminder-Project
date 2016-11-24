package com.dfs.SamDFSTools.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dfs.SamDFSTools.OEMRil;
import com.dfs.SamDFSTools.R;
import com.dfs.SamDFSTools.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sam on 6/25/2016.
 */
public class GeneralSettingsFragment extends AbstractTagFragment {
    private static final int LAYOUT= R.layout.fragment_general_settings;

    private TextView itemModel;
    private TextView itemSoftware;
    private TextView itemBaseband;
    private TextView itemBootloader;
    private TextView itemSpc;
    private TextView itemIMEI;
    private TextView itemPhone;
    private TextView itemOperator;
    private TextView itemPhonetype;
    private TextView itemSU;
    private TextView itemMNCMCC;
    private TextView itemRadiotype;
    private TextView itemSimReady;
    private TextView itemUsbf;
    private TextView itemSuVersion;

    private static final String baseband;
    private static final String bootloader;
    private static final String model;
    private static final String software;

    private String board;
    private String operator;
    private String phonetype;
    private String sim;
    private String imei;
    private String mncmcc;
    private String radiotype;
    private String nativePath;

    public static GeneralSettingsFragment getInstanse(Context context) {
        Bundle args=new Bundle();
        GeneralSettingsFragment fragment=new GeneralSettingsFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_General));

        return fragment;
    }

    static {
        model = Build.MODEL;
        baseband = Build.getRadioVersion();
        bootloader = Build.BOOTLOADER;
        software = Build.DISPLAY;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        itemModel=(TextView) view.findViewById(R.id.textModel);
        itemSoftware=(TextView)view.findViewById(R.id.textSoftware);
        itemBaseband=(TextView)view.findViewById(R.id.textBaseband);
        itemBootloader=(TextView)view.findViewById(R.id.textBootloader);
        itemSU=(TextView)view.findViewById(R.id.textHasRoot);
        itemSpc = (TextView)view.findViewById(R.id.textSPC);
        itemIMEI = (TextView)view.findViewById(R.id.textIMEI);
        itemPhone = (TextView)view.findViewById(R.id.textPhone);
        itemOperator = (TextView)view.findViewById(R.id.textOperator);
        itemPhonetype = (TextView)view.findViewById(R.id.textPhohetype);
        itemMNCMCC = (TextView)view.findViewById(R.id.textMNCMCC);
        itemSimReady = (TextView)view.findViewById(R.id.textSimReady);
        itemUsbf = (TextView)view.findViewById(R.id.textUsbf);
        itemSuVersion = (TextView)view.findViewById(R.id.textSUVersion);

        changeGeneral();

        suSetIt();
        suVersion();
        usbf();


        return view;
    }

    private void changeGeneral(){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Util util = new Util(context);
        //String board = System.getProperty("ro.board");

        String network = util.radio();
        String sim = util.sim();
        String mncmcc = util.MNCMCC();
        String radiotype = util.radiotype();
        String imei = tm.getDeviceId();
        String phone = tm.getLine1Number();
        String operator = tm.getNetworkOperatorName();

        itemModel.setText("Model: "+model);
        itemSoftware.setText("Software: "+software);
        itemBaseband.setText("Baseband: "+baseband);
        itemBootloader.setText("Bootloader: "+bootloader);
        itemIMEI.setText("IMEI: "+imei);
        itemPhone.setText("Phone №: "+phone);
        itemOperator.setText("Operator: " + operator);
        itemPhonetype.setText("Phonetype: "+radiotype);
        try {
            String mnc= mncmcc.substring(0, 3);
            String mcc = mncmcc.substring(3, 6);
            itemMNCMCC.setText("MNC: "+mnc + " MCC: " + mcc);
        } catch (Exception e6) {
            e6.printStackTrace();
        }
        itemSimReady.setText("Sim Ready: "+sim);

        msl();


    }

    public void usbf() {
        try {
            InputStreamReader isr = new InputStreamReader(Runtime.getRuntime().exec("getprop sys.usb.state\n").getInputStream());
            //String foo = BuildConfig.VERSION_NAME;
            String foo = "Usb State: ";
            while (true) {
                int c = isr.read();
                if (c >= 0) {
                    foo =foo+((char) c);
                   // foo = ((char) c);
                } else {
                    itemUsbf.setText(foo);
                    return;
                }
            }
        } catch (IOException e) {
        }
    }

    public void suSetIt() {
        if (isRooted()) {
            itemSU.setText("Has Root: Yes!!!");
            itemSU.setTextColor(Color.GREEN);
            return;
        }
        itemSU.setText("Has Root: Device NOT rooted");
        itemSU.setTextColor(Color.RED);
    }

    public static boolean findBinary(String binaryName) {
        int i = 0;
        if (null != null) {
            return false;
        }
        String[] places = new String[]{"/sbin/", "/system/bin/", "/system/xbin/", "/data/local/xbin/", "/data/local/bin/", "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/"};
        int length = places.length;
        while (i < length) {
            if (new File(places[i] + binaryName).exists()) {
                return true;
            }
            i++;
        }
        return false;
    }

    public static boolean isRooted() {
        return findBinary("su");
    }

    public void suVersion() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("su -v").getInputStream()));
            StringBuilder log = new StringBuilder();
            while (true) {
                String line = bufferedReader.readLine();
                if (line != null) {
                    log.append(line + "\n");
                } else {
                    itemSuVersion.setText("Binary: " + log.toString());
                    return;
                }
            }
        } catch (IOException e) {
        }
    }

    public void msl() {
            try {
                File fCmd = new File(context.getFilesDir(), "rilcmd");
                copyFile("rilcmd", fCmd.getAbsolutePath());
                fCmd.setExecutable(true, false);
                String cmdResult = execCmd(fCmd.getAbsolutePath());

                Matcher mSpc = Pattern.compile("MEIDAUTH:1,(\\d{6})").matcher(cmdResult);
                if (mSpc.find()) {
                    itemSpc.setText("SPC: " + mSpc.group(1));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    private String execCmd(String path) {
        try {
            Process nativeApp = Runtime.getRuntime().exec(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(nativeApp.getInputStream()));
            char[] buffer = new char[AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD];
            StringBuffer output = new StringBuffer();
            while (true) {
                int read = reader.read(buffer);
                if (read > 0) {
                    output.append(buffer, 0, read);
                } else {
                    reader.close();
                    nativeApp.waitFor();
                    return output.toString();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String copyFile(String assetPath, String localPath) {
        try {
            InputStream in = context.getAssets().open(assetPath);
            FileOutputStream out = new FileOutputStream(localPath);
            byte[] buffer = new byte[AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD];
            while (true) {
                int read = in.read(buffer);
                if (read > 0) {
                    out.write(buffer, 0, read);
                } else {
                    out.close();
                    in.close();
                    return localPath;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }


}
