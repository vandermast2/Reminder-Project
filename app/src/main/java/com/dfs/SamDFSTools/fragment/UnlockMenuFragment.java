package com.dfs.SamDFSTools.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dfs.SamDFSTools.R;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Sam on 6/25/2016.
 */
public class UnlockMenuFragment extends AbstractTagFragment {
    private static final int LAYOUT= R.layout.fragment_unlock;
    private java.lang.Process su = null;

    public static UnlockMenuFragment getInstanse(Context context) {
        Bundle args=new Bundle();
        UnlockMenuFragment fragment=new UnlockMenuFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_Unlock));
        return fragment;

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        Button btnDebranding = (Button) view.findViewById(R.id.btnDebranding);

        btnDebranding.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                debranding();
            }
        });

        return view;
    }

    private void debranding(){
        try {
            su = Runtime.getRuntime().exec("su");
            DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());
            outputStream.writeBytes("mount -o remount,rw /dev/block/platform/msm_sdcc.1/system /system\n");
            outputStream.writeBytes("mv /system/media/bootsamsungloop.qmg /system/media/bootsamsungloop.qmg1\n");
            outputStream.writeBytes("mv /system/media/shutdown.qmg /system/media/shutdown.qmg1\n");
            outputStream.writeBytes("mv /system/media/video/shutdown/shutdown.qmg /system/media/video/shutdown/shutdown.qmg1\n");
            outputStream.writeBytes("mv /system/media/video/shutdown/SPR_shutdown.qmg /system/media/video/shutdown/SPR_shutdown.qmg1\n");
            outputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setContext(Context context) {
        this.context = context;
    }

}
