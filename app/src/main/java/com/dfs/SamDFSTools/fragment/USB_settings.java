package com.dfs.SamDFSTools.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.dfs.SamDFSTools.R;

/**
 * Created by Sam on 6/25/2016.
 */
public class USB_settings extends AbstractTagFragment {
    private static final int LAYOUT= R.layout.fragment_usb_settings;
    private  RadioGroup radiogroup;


    public static USB_settings getInstanse(Context context) {
        Bundle args=new Bundle();
        USB_settings fragment=new USB_settings();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_USB_settings));
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
