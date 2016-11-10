package com.dfs.SamDFSTools.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import com.dfs.SamDFSTools.R;
import com.dfs.SamDFSTools.Util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Sam on 6/25/2016.
 */
public class UnlockMenuFragment extends AbstractTagFragment {
    private static final int LAYOUT= R.layout.fragment_unlock;
    private Process su = null;
    private Switch switchHiddenMenu;
    private Switch switchFactory;
    private Button btnDebranding;


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

//        btnDebranding = (Button) view.findViewById(R.id.btnDebranding);
//        switchHiddenMenu = (Switch) view.findViewById(R.id.switchHiddenMenu);
//        switchFactory = (Switch) view.findViewById(R.id.switchFactory);

//        btnDebranding.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                debranding();
//            }
//        });

//        try {
//            factory();
//        } catch (Exception e) {
//        }
////        try {
////            keystring();
////        } catch (Exception e3) {
////        }
////        try {
////            buildtype();
////        } catch (Exception e4) {
////        }
//        try {
//            hidden();
//        } catch (Exception e5) {
//        }



        return view;
    }

    public void hiddenMenuOpen() {
        Exception e1;
        try {
            su = Runtime.getRuntime().exec(new String[]{"cat", "/efs/carrier/HiddenMenu"});
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader = null;
        try {
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(su.getInputStream()));
            try {

                reader = reader2;
            } catch (Exception e2) {
                e1 = e2;
                reader = reader2;
                e1.printStackTrace();
                if (!reader.readLine().equals("ON")) {
                    switchHiddenMenu.setChecked(true);
                } else if (!reader.readLine().equals("OFF")) {
                    this.switchHiddenMenu.setChecked(false);
                }
            }
        } catch (Exception e3) {
            e1 = e3;
            e1.printStackTrace();
            try {
                if (!reader.readLine().equals("ON")) {
                    this.switchHiddenMenu.setChecked(true);
                } else if (!reader.readLine().equals("OFF")) {
                    this.switchHiddenMenu.setChecked(false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            if (!reader.readLine().equals("ON")) {
                this.switchHiddenMenu.setChecked(true);
            } else if (!reader.readLine().equals("OFF")) {
                this.switchHiddenMenu.setChecked(false);
            }
        } catch (IOException e4) {
            e4.printStackTrace();
        }
    }

    public void factory() {
        Exception e1;
        try {
            su = Runtime.getRuntime().exec(new String[]{"cat", "/efs/FactoryApp/factorymode"});
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader = null;
        try {
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(su.getInputStream()));
            try {
//                this.switchFactory = (Switch) view.findViewById(R.id.swFactory);
                reader = reader2;
            } catch (Exception e2) {
                e1 = e2;
                reader = reader2;
                e1.printStackTrace();
                if (!reader.readLine().equals("ON")) {
                    this.switchFactory.setChecked(true);
                } else if (!reader.readLine().equals("OFF")) {
                    this.switchFactory.setChecked(false);
                }
            }
        } catch (Exception e3) {
            e1 = e3;
            e1.printStackTrace();
            try {
                if (!reader.readLine().equals("ON")) {
                    this.switchFactory.setChecked(true);
                } else if (!reader.readLine().equals("OFF")) {
                    this.switchFactory.setChecked(false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            if (!reader.readLine().equals("ON")) {
                this.switchFactory.setChecked(true);
            } else if (!reader.readLine().equals("OFF")) {
                this.switchFactory.setChecked(false);
            }
        } catch (IOException e4) {
            e4.printStackTrace();
        }
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
