package com.dfs.reminder.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dfs.reminder.R;

/**
 * Created by Sam on 6/25/2016.
 */
public class UnlockMenuFragment extends AbstractTagFragment {
    private static final int LAYOUT= R.layout.fragment_unlock;

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
        return view;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
