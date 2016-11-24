package com.dfs.SamDFSTools.backup;

import com.dfs.SamDFSTools.fragment.GeneralSettingsFragment;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Sam on 11/24/2016.
 */

public class SecurityBackup {
    private Process process=null;

    /*

     */
    private String EFSBackup(String tmpCatPath, String blockEfsPath){
        String filename = "/efs.img.ext4";
        String fullTmpPath = tmpCatPath + filename;
        if(GeneralSettingsFragment.isRooted()){
            try {
                process = Runtime.getRuntime().exec("su");
                DataOutputStream dataOutputStream = new DataOutputStream(process.getOutputStream());
                dataOutputStream.writeBytes("rm "+ fullTmpPath);
                dataOutputStream.writeBytes("cat "+ blockEfsPath+"/size");
                dataOutputStream.writeBytes("make_ext4fs -s -l 14M " + fullTmpPath + " /efs");
                dataOutputStream.writeBytes("chmod 0755 " + fullTmpPath);
                dataOutputStream.flush();
                return filename;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /*

     */
    private void SECBackup(String tmpCatPath, String modemst1BlockPath, String modemst2BlockPath){
        String filenameModemst1 = "/modemst1";
        String filenameModemst2 = "/modemst2";
        String destModemst1 = tmpCatPath + filenameModemst1;
        String destModemst2 = tmpCatPath + filenameModemst2;

        if(GeneralSettingsFragment.isRooted()) {
            try {
                process = Runtime.getRuntime().exec("su");
                DataOutputStream dataOutputStream = new DataOutputStream(process.getOutputStream());
                dataOutputStream.writeBytes("rm " + tmpCatPath);
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
        This method clear local TMP directory(/data/local/tmp
     */
    private void ClearLocalTmp(){
        try {
            process = Runtime.getRuntime().exec("rm /data/local/tmp/*");
            process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
