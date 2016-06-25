package com.dfs.reminder.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dfs.reminder.R;

/**
 * Created by Sam on 6/25/2016.
 */
public class ExampleFragment extends Fragment {
    private static final int LAYOUT= R.layout.fragment_example;
    private View view;

    public static ExampleFragment getInstanse() {
        Bundle args=new Bundle();
        ExampleFragment fragment=new ExampleFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        return view;
    }
}
