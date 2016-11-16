package com.dfs.SamDFSTools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Sam on 11/15/2016.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent startServiceIntent = new Intent(context, MainActivity.class);
        context.startService(startServiceIntent);
    }
}
