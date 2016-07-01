package com.dfs.SamDFSTools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dfs.SamDFSTools.fragment.USB_settings;

/**
 * Created by Sam on 6/29/2016.
 */
public class BCReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context arg0, Intent arg1) {
        Intent i;
//        if (arg1.toString().contains("GTNet")) {
//            i = new Intent(arg0, HotspotConfig.class);
//            i.addFlags(268435456);
//            arg0.startActivity(i);
//        }
        if (arg1.toString().contains("GalaxyTools")) {
            i = new Intent(arg0, MainActivity.class);
            i.addFlags(268435456);
            arg0.startActivity(i);
        }
//        if (arg1.toString().contains("GTAPConf")) {
//            i = new Intent(arg0, HotspotConfig.class);
//            i.addFlags(268435456);
//            arg0.startActivity(i);
//        }
//        if (arg1.toString().contains("GTXOR")) {
//            i = new Intent(arg0, XORBytes.class);
//            i.addFlags(268435456);
//            arg0.startActivity(i);
//        }
//        if (arg1.toString().contains("OEMRaw")) {
//            i = new Intent(arg0, OEMRawByteBanger.class);
//            i.addFlags(268435456);
//            arg0.startActivity(i);
//        }
//        if (arg1.toString().contains("GTCSC")) {
//            i = new Intent(arg0, CscActivity.class);
//            i.addFlags(268435456);
//            arg0.startActivity(i);
//        }
        if (arg1.toString().contains("GTUSBUtil")) {
            i = new Intent(arg0, USB_settings.class);
            i.addFlags(268435456);
            arg0.startActivity(i);
        }
    }
}
