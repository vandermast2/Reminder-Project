package com.dfs.SamDFSTools.backup;

import java.util.Map;

/**
 * Created by Sam on 11/28/2016.
 */

public abstract class BackupAbstract {
    public Process process = null;
    public Map<String, String> listPartitions;

    public String destPath;
    public String localTmpPath="/data/local/tmp";
    private String filenameModemst1 = "/modemst1";
    private String filenameModemst2 = "/modemst2";

    public String destModemst1 = localTmpPath + filenameModemst1;
    public String destModemst2 = localTmpPath + filenameModemst2;

    public final String[] efsFilename = {"/efs.img.ext4"};
    public String fullTmpPath = localTmpPath + "/efs.img.ext4";

    public int BUFFER_SIZE=1024;
}
