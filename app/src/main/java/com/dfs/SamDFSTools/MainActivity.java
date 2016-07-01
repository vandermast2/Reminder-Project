package com.dfs.SamDFSTools;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dfs.SamDFSTools.adapter.TabsFragmentAdapter;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends AppCompatActivity {
    private static final int LAYOUT=R.layout.activity_main;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;

    private TextView model_item;

    private static final int BUFFER_SIZE = 1024;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        //update();
        initToolbar();
        initNavigationView();
        initTabs();



    }

    private void initToolbar() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        toolbar.inflateMenu(R.menu.menu);
    }

    private void initTabs() {
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        TabsFragmentAdapter adapter = new TabsFragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initNavigationView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.view_navigation_open,R.string.view_navigation_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();
                switch(menuItem.getItemId()){
                    case R.id.actionNotificationItem:
                        showNotificationTab(Constants.TAB_TWO);
                    case R.id.actionNotificationItemCheckIMEI:
                        showNotificationTab(Constants.TAB_TWO);
                }
                return true;
            }
        });
    }



    public void update() {
        try {
            Process su = Runtime.getRuntime().exec("su");
            DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());
            outputStream.writeBytes("mkdir" + Environment.getExternalStorageDirectory().getPath() + "/DFSTools\n");
            outputStream.writeBytes("mount -o remount,rw /system\n");
            outputStream.writeBytes("mount -o remount,rw /data\n");
            outputStream.flush();
            AssetManager assetFiles = getAssets();
            String[] files = assetFiles.list("gt");
            for (int i = 0; i < files.length; i++) {
                copyassetFiles(assetFiles.open("gt/" + files[i]), new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/DFSTools/" + files[i]));
            }
            outputStream.writeBytes("cat " + Environment.getExternalStorageDirectory().getPath() + "/DFSTools/bb7 > /system/xbin/busybox7\n");
            outputStream.flush();
            outputStream.writeBytes("chmod 755 /system/xbin/busybox7\n");
            outputStream.flush();
            outputStream.writeBytes("busybox7 mv /data/data/com.dfs.reminder /data/data/com.dfs.reminder.bk\n");
            outputStream.flush();
            outputStream.writeBytes("pm uninstall com.dfs.reminder\n");
            outputStream.flush();
            outputStream.writeBytes("cat " + Environment.getExternalStorageDirectory().getPath() + "/DFSTools/com.dfs.reminder.apk > /system/priv-app/com.dfs.reminder.apk\n");
            outputStream.flush();
            outputStream.writeBytes("chmod 644 /system/priv-app/com.dfs.reminder.apk\n");
            outputStream.flush();
            outputStream.writeBytes("busybox7 rm " + Environment.getExternalStorageDirectory().getPath() + "/DFSTools/com.dfs.reminder.apk\n");
            outputStream.flush();
            outputStream.writeBytes("busybox7 rm " + Environment.getExternalStorageDirectory().getPath() + "/DFSTools/bb7\n");
            outputStream.flush();
            outputStream.writeBytes("sleep 2; pm uninstall com.idone.gtupdate\n");
            outputStream.flush();
            outputStream.writeBytes("exit\n");
            outputStream.flush();
            su.waitFor();
            //su.destroy();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }


    private void copyassetFiles(InputStream in, OutputStream out) {
        try {
            byte[] buffer = new byte[BUFFER_SIZE];
            while (true) {
                int read = in.read(buffer);
                if (read != -1) {
                    out.write(buffer, 0, read);
                } else {
                    in.close();
                    out.flush();
                    out.close();
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }




    private void showNotificationTab(int fragmentPage){
        viewPager.setCurrentItem(fragmentPage);


    }

}
