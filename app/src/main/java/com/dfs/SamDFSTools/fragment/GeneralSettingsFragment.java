package com.dfs.SamDFSTools.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dfs.SamDFSTools.OEMRil;
import com.dfs.SamDFSTools.R;
import com.dfs.SamDFSTools.Util;

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
    private TextView itemSim;
    private TextView itemMNCMCC;
    private TextView itemRadiotype;
    private TextView itemSimReady;

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
        itemSpc = (TextView)view.findViewById(R.id.textSPC);
        itemIMEI = (TextView)view.findViewById(R.id.textIMEI);
        itemPhone = (TextView)view.findViewById(R.id.textPhone);
        itemOperator = (TextView)view.findViewById(R.id.textOperator);
        itemPhonetype = (TextView)view.findViewById(R.id.textPhohetype);
        itemMNCMCC = (TextView)view.findViewById(R.id.textMNCMCC);
        itemSimReady = (TextView)view.findViewById(R.id.textSimReady);


        changeGeneral();

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
        itemMNCMCC.setText("MNC/MCC: "+mncmcc);
        itemSimReady.setText("Sim Ready: "+sim);

        msl();

    }
    public void msl() {
        try {
            OEMRil.getMSL(getContext());
            itemSpc.setText("MSL/SPC: "+ OEMRil.stru.toString());
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void setContext(Context context) {
        this.context = context;
    }
}
