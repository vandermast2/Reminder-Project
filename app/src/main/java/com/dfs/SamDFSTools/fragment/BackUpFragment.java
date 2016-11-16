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

import com.dfs.SamDFSTools.R;
import com.dfs.SamDFSTools.sender.Mail;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Sam on 6/25/2016.
 */
public class BackUpFragment extends AbstractTagFragment  {
    private static final int LAYOUT= R.layout.fragment_backup;


    private Button btnSignIn;

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
        btnSignIn = (Button)view.findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mail m =new Mail();
                m.sendMail();
            }
        });
        return view;
    }

    public void setContext(Context context) {
        this.context = context;
    }



}
