package com.dfs.SamDFSTools;

/**
 * Created by Sam on 6/29/2016.
 */
public class DfsItems { private String activity;
    private String name;

    public DfsItems(String activity, String name) {
        this.activity = activity;
        this.name = name;
    }

    public Object getItem(int position) {
        return get(position);
    }

    static DfsItems get(int position) {
        return get(position);
    }

    public String getActivity() {
        return this.activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Object getItemAtPosition(int arg2) {
        return null;
    }
}