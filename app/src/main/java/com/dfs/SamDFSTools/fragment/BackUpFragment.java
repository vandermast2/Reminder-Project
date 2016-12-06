package com.dfs.SamDFSTools.fragment;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.dfs.SamDFSTools.BackUpPartition;
import com.dfs.SamDFSTools.MainActivity;
import com.dfs.SamDFSTools.R;
import com.dfs.SamDFSTools.backup.AndroidBackup;
import com.dfs.SamDFSTools.sender.Mail;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Sam on 6/25/2016.
 */
public class BackUpFragment extends AbstractTagFragment  {
    private static final int LAYOUT= R.layout.fragment_backup;

    private Button btnEFSBackup;
    private Button btnBackupPartition;

    public static BackUpFragment getInstanse(Context context) {

        Bundle args=new Bundle();
        BackUpFragment fragment=new BackUpFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_backup));
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        btnEFSBackup = (Button)view.findViewById(R.id.btnEFSBackup);
        btnBackupPartition = (Button)view.findViewById(R.id.btnBackupPartitions);

        btnEFSBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidBackup ab = new AndroidBackup();
                ab.createEFSBackup();
            }
        });

        btnBackupPartition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),BackUpPartition.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
