package com.dfs.SamDFSTools.backup;

import android.support.annotation.Nullable;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.widget.Toast;

import com.dfs.SamDFSTools.R;
import com.dfs.SamDFSTools.fragment.GeneralSettingsFragment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Sam on 11/24/2016.
 */

public class AndroidBackup extends BackupAbstract {

    /*

    */
    public void createEFSBackup(){
        try {
            clearLocalTmp();
            getListPartitions();
            zip(EFSBackup(listPartitions.get("efs")),"/sdcard/efs_");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void copyFiles(){

    }

    /*
        This method create EFS (nv_settings, imei, cert and other)
     */
    @Nullable
    private String[] EFSBackup(String blockEfsPath) {
        if (GeneralSettingsFragment.isRooted()) {
            try {
                process = Runtime.getRuntime().exec(new String[]{"su", "-c", "make_ext4fs", "-s", "-l", "14M","/sdcard/efs.img.ext4", " /efs" });
                DataOutputStream dataOutputStream = new DataOutputStream(process.getOutputStream());
                dataOutputStream.writeBytes("chmod 0755 /sdcard/efs.img.ext4");
                dataOutputStream.flush();
                efsFilename[0] = "/sdcard/efs.img.ext4";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return efsFilename;
    }

    /*
        This method create backup for 2 modemst partitions
     */
    private void SECBackup(String modemst1BlockPath, String modemst2BlockPath) {

        if (GeneralSettingsFragment.isRooted()) {
            try {
                process = Runtime.getRuntime().exec("su");
                DataOutputStream dataOutputStream = new DataOutputStream(process.getOutputStream());
                dataOutputStream.writeBytes("rm " + localTmpPath + "/*");
                dataOutputStream.writeBytes("dd if=" + modemst1BlockPath + " of=" + destModemst1);
                dataOutputStream.writeBytes("chmod 0755 " + destModemst1);
                dataOutputStream.writeBytes("dd if=" + modemst2BlockPath + " of=" + destModemst2);
                dataOutputStream.writeBytes("chmod 0755 " + destModemst2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
        This method clear local TMP directory(/data/local/tmp)
     */
    private void clearLocalTmp() {
        try {
            process = Runtime.getRuntime().exec("rm /data/local/tmp/*");
            process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*

     */
    private void getListPartitions() {
        listPartitions = new TreeMap<>();
        try {
            Process nativeApp = Runtime.getRuntime().exec(new String[]{"su", "-c", "ls", "-al", "/dev/block/platform/*/by-name"});
            BufferedReader reader = new BufferedReader(new InputStreamReader(nativeApp.getInputStream()));
            String str = reader.readLine();
            while (str != null){
                int idxOfPointer = str.lastIndexOf("->");
                listPartitions.put(str.substring(55,idxOfPointer-1),str.substring(idxOfPointer +2).trim());
                str=reader.readLine();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void zip(String[] files, String zipFile) throws IOException {
        DateFormat df = new SimpleDateFormat("yyyyMMdd_HH:mm");
        zipFile+=df.format(new Date(System.currentTimeMillis()))+".zip";
        BufferedInputStream origin = null;
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
        try {
            byte data[] = new byte[BUFFER_SIZE];

            for (int i = 0; i < files.length; i++) {
                FileInputStream fi = new FileInputStream(files[i]);
                fi.available();
                origin = new BufferedInputStream(fi);
                try {
                    ZipEntry entry = new ZipEntry(files[i].substring(files[i].lastIndexOf("/") + 1));
                    out.putNextEntry(entry);
                    int count;
                    while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
                        out.write(data, 0, count);
                    }
                }
                finally {
                    origin.close();
                }
            }
        }
        finally {
            out.close();
        }
    }





}
