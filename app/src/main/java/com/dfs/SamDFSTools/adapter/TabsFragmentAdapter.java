package com.dfs.SamDFSTools.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dfs.SamDFSTools.fragment.AbstractTagFragment;

import com.dfs.SamDFSTools.fragment.GeneralSettingsFragment;
import com.dfs.SamDFSTools.fragment.BackUpFragment;
import com.dfs.SamDFSTools.fragment.UnlockMenuFragment;
import com.dfs.SamDFSTools.fragment.USB_settings;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sam on 6/25/2016.
 */
public class TabsFragmentAdapter extends FragmentPagerAdapter{


    private Map<Integer, AbstractTagFragment> tabs;
    private Context context;

    public TabsFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
        initTabsMap(context);

        }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTitle();
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    private void initTabsMap(Context context) {
        tabs = new HashMap<>();
        tabs.put(0, GeneralSettingsFragment.getInstanse(context));
        tabs.put(1, USB_settings.getInstanse(context));
        tabs.put(2, UnlockMenuFragment.getInstanse(context));
        tabs.put(3, BackUpFragment.getInstanse(context));
        //tabs.put(4, UnlockMenuFragment.getInstanse(context));

    }
}
