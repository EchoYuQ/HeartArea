package com.bupt.heartarea.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bupt.heartarea.fragment.MainPageFragment;
import com.bupt.heartarea.fragment.MeasureFragment;
import com.bupt.heartarea.fragment.MineFragment;
import com.bupt.heartarea.fragment.NewsFragment;
import com.bupt.heartarea.ui.AlphaIndicator;
import com.bupt.heartarea.ui.AlphaView;
import com.bupt.heartarea.fragment.HistroyFragment;
import com.bupt.heartarea.utils.DownloadUtil;

import net.lemonsoft.lemonhello.LemonHello;
import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;

import java.util.ArrayList;
import java.util.List;

//import com.lzy.widget.AlphaIndicator;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    AlphaView mTab1;
    AlphaView mTab2;
    AlphaView mTab3;
    AlphaView mTab4;
    AlphaView mTab5;
    ViewPager viewPager;
    AlphaIndicator alphaIndicator;

    //    MainAdapter mainAdapter;
    //    public static MainActivity instance = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("MainActivity onCreate()");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(com.bupt.heartarea.R.layout.activity_main);

        viewPager = (ViewPager) findViewById(com.bupt.heartarea.R.id.viewPager);
        alphaIndicator = (AlphaIndicator) findViewById(com.bupt.heartarea.R.id.alphaIndicator);
        mTab1 = (AlphaView) findViewById(com.bupt.heartarea.R.id.av_tab1);
        mTab2 = (AlphaView) findViewById(com.bupt.heartarea.R.id.av_tab2);
        mTab3 = (AlphaView) findViewById(com.bupt.heartarea.R.id.av_tab3);
        mTab4 = (AlphaView) findViewById(com.bupt.heartarea.R.id.av_tab4);
        mTab5 = (AlphaView) findViewById(com.bupt.heartarea.R.id.av_tab5);

        //        mainAdapter=new MainAdapter(getSupportFragmentManager());

        initEvent();


    }


    private void initEvent() {

        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));
        alphaIndicator.setViewPager(viewPager);
        mTab1.setOnClickListener(this);
        mTab2.setOnClickListener(this);
        mTab3.setOnClickListener(this);
        mTab4.setOnClickListener(this);
        mTab5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case com.bupt.heartarea.R.id.av_tab1:
                alphaIndicator.setPagerNum(0);
                break;
            case com.bupt.heartarea.R.id.av_tab2:
                alphaIndicator.setPagerNum(1);
                break;
            case com.bupt.heartarea.R.id.av_tab3:
                alphaIndicator.setPagerNum(2);
                break;
            case com.bupt.heartarea.R.id.av_tab4:
                alphaIndicator.setPagerNum(3);
                break;
            case com.bupt.heartarea.R.id.av_tab5:
                alphaIndicator.setPagerNum(4);
                break;

        }
    }

    private class MainAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        //        private String[] titles = {//
        //                "第一页\n\n重点看下面的的图标是渐变色，随着滑动距离的增加，颜色逐渐过度",//
        //                "第二页\n\n重点看下面的的图标是渐变色，随着滑动距离的增加，颜色逐渐过度",//
        //                "第三页\n\n重点看下面的的图标是渐变色，随着滑动距离的增加，颜色逐渐过度", //
        //                "第四页\n\n重点看下面的的图标是渐变色，随着滑动距离的增加，颜色逐渐过度"};

        public MainAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(new MainPageFragment());
            fragments.add(new MeasureFragment());
            fragments.add(new HistroyFragment());
            //            fragments.add(new TextFragment());
            fragments.add(new NewsFragment());
            fragments.add(new MineFragment());

        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        checkPermissions();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MainActivity", "onDestroy");
    }

    /**
     * 检查相机权限
     */
    private Boolean checkPermissions() {

        int cameraPermissionCode = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA);
        int writeSDcardPermissionCode = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readSDcardPermissionCode = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (cameraPermissionCode != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.CAMERA)) {

                LemonHello.getInformationHello("缺少相机权限", "当前应用缺少相机权限,请去设置界面授权")
                        .addAction(new LemonHelloAction("取消", new LemonHelloActionDelegate() {
                            @Override
                            public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                helloView.hide();
                                Toast.makeText(MainActivity.this, "您拒绝了权限，"
                                        + "可能会导致该应用内部发生错误,请尽快授权", Toast.LENGTH_LONG)
                                        .show();
                            }
                        }))
                        .addAction(new LemonHelloAction("设置", Color.RED, new LemonHelloActionDelegate() {
                            @Override
                            public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                helloView.hide();
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                // 根据包名打开对应的设置界面
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                startActivity(intent);
                            }
                        }))
                        .show(MainActivity.this);

//                new AlertDialog.Builder(MainActivity.this)
//                        .setTitle("")
//                        .setMessage("当前应用缺少相机权限,请去设置界面授权.")
//                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//
//                            }
//                        })
//                        .setPositiveButton("设置", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                // 根据包名打开对应的设置界面
//                                intent.setData(Uri.parse("package:" + getPackageName()));
//                                startActivity(intent);
//                            }
//                        })
//                        .show();

            }
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    123);
            return false;
        }

        return true;
    }
}
