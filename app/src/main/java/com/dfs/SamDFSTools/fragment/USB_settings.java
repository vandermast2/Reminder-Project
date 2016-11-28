package com.dfs.SamDFSTools.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.TransportMediator;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dfs.SamDFSTools.R;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


public class USB_settings extends AbstractTagFragment {
    private static final int LAYOUT= R.layout.fragment_usb_settings;


    private static String[] SET_DM_PORT_STATUS_LIST1;
    public String[] SET_CPAP_CONFIG_LIST;
    public String[] SET_DM_PORT_CONFIG_LIST;
    public String[] SET_DM_PORT_STATUS_LIST;
    private int currentMode;
    private RadioButton dm_modem_adb_mode;
    private RadioButton mass_storage_adb_mode;
    private RadioButton mass_storage_mode;
    private int mode_ID;
    private RadioGroup modem_cable;
    private RadioButton mtp_adb_mode;
    private RadioButton mtp_mode;
    private RadioButton ptp_adb_mode;
    private RadioButton ptp_mode;
    private RadioButton rmnet_dm_modem_mode;
    private RadioButton rndis_dm_modem_mode;
    private RadioGroup usbradio;


    public USB_settings() {
        this.currentMode = 0;
        this.SET_DM_PORT_STATUS_LIST = new String[10];
        this.SET_DM_PORT_STATUS_LIST[0] = "setMTP";
        this.SET_DM_PORT_STATUS_LIST[1] = "setMTPADB";
        this.SET_DM_PORT_STATUS_LIST[2] = "setPTP";
        this.SET_DM_PORT_STATUS_LIST[3] = "setPTPADB";
        this.SET_DM_PORT_STATUS_LIST[4] = "setRNDISADB";
        this.SET_DM_PORT_STATUS_LIST[5] = "setRNDISDMMODEM";
        this.SET_DM_PORT_STATUS_LIST[6] = "setRMNETDMMODEM";
        this.SET_DM_PORT_STATUS_LIST[7] = "setDMMODEMADB";
        this.SET_DM_PORT_STATUS_LIST[8] = "setMASSSTORAGE";
        this.SET_DM_PORT_STATUS_LIST[9] = "setMASSSTORAGEADB";
        this.SET_DM_PORT_CONFIG_LIST = new String[10];
        this.SET_DM_PORT_CONFIG_LIST[0] = "diag,modem";
        this.SET_DM_PORT_CONFIG_LIST[1] = "mtp,adb";
        this.SET_DM_PORT_CONFIG_LIST[2] = "ptp";
        this.SET_DM_PORT_CONFIG_LIST[3] = "ptp,adb";
        this.SET_DM_PORT_CONFIG_LIST[4] = "rndis,adb";
        this.SET_DM_PORT_CONFIG_LIST[5] = "rndis,acm,diag";
        this.SET_DM_PORT_CONFIG_LIST[6] = "rmnet,acm,diag";
        this.SET_DM_PORT_CONFIG_LIST[7] = "diag,acm,adb";
        this.SET_DM_PORT_CONFIG_LIST[8] = "mass_storage";
        this.SET_DM_PORT_CONFIG_LIST[9] = "mass_storage,adb";
        this.SET_CPAP_CONFIG_LIST = new String[2];
        this.SET_CPAP_CONFIG_LIST[0] = "AP";
        this.SET_CPAP_CONFIG_LIST[1] = "CP";
    }

    class USB_sett implements RadioGroup.OnCheckedChangeListener {
        USB_sett() {
        }

        public void onCheckedChanged(RadioGroup rg, int checkedId) {
            USB_settings.this.SaveFile(rg.indexOfChild(USB_settings.this.view.findViewById(rg.getCheckedRadioButtonId())));
        }
    }

    public static USB_settings getInstanse(Context context) {
        Bundle args=new Bundle();
        USB_settings fragment=new USB_settings();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_USB_settings));
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        //isMDMBaseband();
        readCurrentSettings();
        cpap();
        //RadioGroup cs = (RadioGroup) view.findViewById(R.id.modem_cable);
        ((RadioGroup) view.findViewById(R.id.radioGroup1)).setOnCheckedChangeListener(new USB_sett());
        //cs.setOnCheckedChangeListener(new C01082());
        return view;
    }

    private void setDefaultSelection(int paramInt) {
        RadioButton mtp_mode = (RadioButton) view.findViewById(R.id.radioButtonMTP);
        RadioButton mtp_adb_mode = (RadioButton) view.findViewById(R.id.radioButtonMTP_ADB);
        RadioButton ptp_mode = (RadioButton) view.findViewById(R.id.radioButtonPTP);
        RadioButton ptp_adb_mode = (RadioButton) view.findViewById(R.id.radioButtonPTP_ADB);
        RadioButton rndis_adb_mode = (RadioButton) view.findViewById(R.id.radioButtonRNDIS_ADB);
        RadioButton rndis_dm_modem_mode = (RadioButton) view.findViewById(R.id.radioButtonRNDIS_DM_Modem);
        RadioButton rmnet_dm_modem_mode = (RadioButton) view.findViewById(R.id.radioButtonRMNET_DM_Modem);
        RadioButton dm_modem_adb_mode = (RadioButton) view.findViewById(R.id.radioButtonDM_Modem_ADB);
        RadioButton mass_storage_mode = (RadioButton) view.findViewById(R.id.radioButtonMassStorage);
        RadioButton mass_storage_adb_mode = (RadioButton) view.findViewById(R.id.radioButtonMassStorage_ADB);
       // RadioButton ap_sel = (RadioButton) view.findViewById(R.id.ap_sel);
       // RadioButton cp_sel = (RadioButton) view.findViewById(R.id.cp_sel);
        Log.i("USBSettings", "CurrentDefaultSelection : " + paramInt);
        switch (paramInt) {
            case CursorAdapter.FLAG_AUTO_REQUERY /*1*/:
                mtp_mode.setChecked(true);
            case CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER /*2*/:
                mtp_adb_mode.setChecked(true);
            case NotificationCompat.WearableExtender.SIZE_MEDIUM /*3*/:
                ptp_mode.setChecked(true);
            case TransportMediator.FLAG_KEY_MEDIA_PLAY /*4*/:
                ptp_adb_mode.setChecked(true);
            case NotificationCompat.WearableExtender.SIZE_FULL_SCREEN /*5*/:
                rndis_adb_mode.setChecked(true);
//            case FragmentManagerImpl.ANIM_STYLE_FADE_EXIT /*6*/:
//                rndis_dm_modem_mode.setChecked(true);
//            case R.styleable.Spinner_spinnerMode /*7*/:
//                rmnet_dm_modem_mode.setChecked(true);
            case TransportMediator.FLAG_KEY_MEDIA_PLAY_PAUSE /*8*/:
                dm_modem_adb_mode.setChecked(true);
//            case R.styleable.Spinner_disableChildrenWhenDisabled /*9*/:
//                mass_storage_mode.setChecked(true);
            case R.styleable.MenuItem_android_numericShortcut /*10*/:
                mass_storage_adb_mode.setChecked(true);
//            case R.styleable.MenuItem_android_checkable /*11*/:
//                ap_sel.setChecked(true);
//            case R.styleable.MenuItem_android_onClick /*12*/:
//                cp_sel.setChecked(true);
            default:
        }
    }

    public void cpap() {
        try {
            Process proc = Runtime.getRuntime().exec("cat /data/misc/radio/uart.txt");
            Process proc1 = Runtime.getRuntime().exec("cat /data/radio/uart.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(proc1.getInputStream()));
            StringBuilder log = new StringBuilder();
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                log.append(line + "\n");
            }
            String str1 = log.toString();
            StringBuilder log1 = new StringBuilder();
            while (true) {
                String line1 = bufferedReader1.readLine();
                if (line1 == null) {
                    break;
                }
                log1.append(line1 + "\n");
            }
            String str2 = log1.toString();
            try {
                proc.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (str1.contains("AP")) {
                Log.i("USBSettings", 11 + "Check UART Button is AP");
                setDefaultSelection(11);
            }
            if (str1.contains("CP")) {
                Log.i("USBSettings", 12 + "Check UART Button is CP");
                setDefaultSelection(12);
            }
            if (str2.contains("AP")) {
                Log.i("USBSettings", 11 + "Check UART Button is AP");
                setDefaultSelection(11);
            }
            if (str2.contains("CP")) {
                Log.i("USBSettings", 12 + "Check UART Button is CP");
                setDefaultSelection(12);
            }
        } catch (IOException e2) {
        }
    }

    protected void SaveFile(int paramInt) {
        Log.i("USBSettings", this.SET_DM_PORT_STATUS_LIST[paramInt]);
        Log.i("USBSettings", "SaveFile Index = " + paramInt);
        try {
            Process su = Runtime.getRuntime().exec("su");
            DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());
            outputStream.writeBytes("setprop sys.usb.config " + this.SET_DM_PORT_CONFIG_LIST[paramInt] + "\n");
            if (this.SET_DM_PORT_STATUS_LIST[paramInt].contains("adb")) {
                outputStream.writeBytes("settings put global adb_enabled 1\n");
            }
            outputStream.writeBytes("exit\n");
            outputStream.flush();
            su.waitFor();
        } catch (IOException e) {
        } catch (InterruptedException e2) {
        }
    }

    protected void SaveFile1(int paramInt) {
        Log.i("USBSettings", this.SET_DM_PORT_STATUS_LIST[paramInt]);
        Log.i("USBSettings", "SaveFile Index = " + paramInt);
        String path = null;
        if (new File("/data/misc/radio/uart.txt").exists()) {
            path = "/data/misc/radio/uart.txt";
           // Toast.makeText(getBaseContext(), path, 1).show();
        }
        if (new File("/data/radio/uart.txt").exists()) {
            path = "/data/radio/uart.txt";
           // Toast.makeText(getBaseContext(), path, 1).show();
        }
        try {
            Process su = Runtime.getRuntime().exec("su");
            DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());
            outputStream.writeBytes("mount -o remount,rw /data\n");
            outputStream.writeBytes("echo -n " + this.SET_CPAP_CONFIG_LIST[paramInt] + " > " + path + "\n");
            outputStream.writeBytes("mount -o remount,ro /data\n");
          //  Toast.makeText(getBaseContext(), this.SET_CPAP_CONFIG_LIST[paramInt], 1).show();
            outputStream.writeBytes("exit\n");
            outputStream.flush();
            su.waitFor();
        } catch (IOException e) {
        } catch (InterruptedException e2) {
        }
    }

    public void readCurrentSettings() {
        Log.i("USBSettings", "readCurrentSettings" + 0);
        String str = new String();
        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec("getprop sys.usb.config");
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        StringBuilder log = new StringBuilder();
        while (true) {
            try {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                log.append(line + "\n");
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        String str2 = log.toString().trim();
        Log.i("USBSettings", 0 + "CurrentUSB Setting : " + str2);
        if (str2.equals("diag,modem")) {
            Log.i("USBSettings", 1 + "Check Radio Button is mtp");
            setDefaultSelection(1);
        }
        if (str2.equals("mtp,adb")) {
            Log.i("USBSettings", 2 + "Check Radio Button is mtp,adb");
            setDefaultSelection(2);
        }
        if (str2.equals("ptp")) {
            Log.i("USBSettings", "Check Radio Button is ptp");
            setDefaultSelection(3);
        }
        if (str2.equals("ptp,adb")) {
            Log.i("USBSettings", "Check Radio Button is ptp,adb");
            setDefaultSelection(4);
        }
        if (str2.equals("rndis,adb")) {
            Log.i("USBSettings", "Check Radio Button is rndis,adb");
            setDefaultSelection(5);
        }
        if (str2.equals("rndis,acm,diag")) {
            Log.i("USBSettings", "Check Radio Button is rndis,acm,diag");
            setDefaultSelection(6);
        }
        if (str2.equals("rmnet,acm,diag")) {
            Log.i("USBSettings", "Check Radio Button is rmnet,acm,diag");
            setDefaultSelection(7);
        }
        if (str2.equals("diag,acm,adb")) {
            Log.i("USBSettings", "Check Radio Button is diag,acm,adb");
            setDefaultSelection(8);
        }
        if (str2.equals("mass_storage,adb")) {
            Log.i("USBSettings", "Check Radio Button is mass_storage,adb");
            setDefaultSelection(10);
        }
        if (str2.equals("mass_storage")) {
            Log.i("USBSettings", "Check Radio Button is mass_storage");
            setDefaultSelection(9);
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }




}
