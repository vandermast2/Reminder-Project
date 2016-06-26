package com.dfs.reminder.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.dfs.reminder.fragment.AbstractTagFragment;
import com.dfs.reminder.fragment.BirthdayFragment;
import com.dfs.reminder.fragment.HistoryFragment;
import com.dfs.reminder.fragment.IdeasFragment;
import com.dfs.reminder.fragment.TODOFragment;

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
        tabs.put(0, BirthdayFragment.getInstanse(context));
        tabs.put(1, HistoryFragment.getInstanse(context));
        tabs.put(2, IdeasFragment.getInstanse(context));
        tabs.put(3, TODOFragment.getInstanse(context));
    }
}
