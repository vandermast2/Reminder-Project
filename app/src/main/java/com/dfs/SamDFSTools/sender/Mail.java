package com.dfs.SamDFSTools.sender;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.dfs.SamDFSTools.Constants;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by Sam on 11/11/2016.
 */

public class Mail {
    String model;
    String bootloader;
    String baseband;
    String software;

    public Mail() {
        model = Build.MODEL;
        bootloader = Build.BOOTLOADER;
        baseband = Build.getRadioVersion();
        software = Build.DISPLAY;
    }

    public void sendMail() {

        createBackupDB();

        File path = new File(Constants.TMP_PATH, "/telephony.db.bk");
        Intent emailIntent = new Intent("android.intent.action.SEND");
        String emailtext = "Request GalaxyTools support for my model!\n\nHere are my details\nModel is: " + model + "\n" + "Bootloader is: " + bootloader + "\n" + "Baseband is: " + baseband + "\n" + "Software version: " + software;
        emailIntent.setType("plain/text");
        emailIntent.putExtra("android.intent.extra.EMAIL", new String[]{"breez.dp@gmail.com"});
        emailIntent.putExtra("android.intent.extra.STREAM", Uri.parse("file://" + path));
        emailIntent.putExtra("android.intent.extra.TEXT", emailtext);

    }

    private void createBackupDB() {
        try {
            Process su = Runtime.getRuntime().exec("su");
            DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());
            outputStream.writeBytes("cat /data/data/com.android.providers.telephony/databases/telephony.db > /mnt/sdcard/telephony.db.bk\n");
            outputStream.flush();
            outputStream.writeBytes("exit\n");
            outputStream.flush();
            su.waitFor();
        } catch (IOException e) {
        } catch (InterruptedException e2) {
        }
    }
}
