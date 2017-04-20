package com.bupt.heartarea.fragment;

/**
 * Created by yuqing on 2017/3/4.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bupt.heartarea.activity.WebActivity;
import com.bupt.heartarea.bean.Result;
import com.bupt.heartarea.ui.AlphaIndicator;
import com.google.gson.Gson;
import com.bupt.heartarea.R;
import com.bupt.heartarea.activity.MainActivity;
import com.bupt.heartarea.adapter.GuidePageAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;


public class MainPageFragment extends Fragment implements OnClickListener {
    private static final String URL_NEWS = "http://www.tngou.net/api/info/list?rows=3";
    private static final String URL_DETAILS = "http://www.tngou.net/info/show/";
    private View view;
    // 广告
    private ViewPager viewPager;



    // 搜索
//    private ImageView mImgSearch;

    // 广告数组
    private List<View> ar;
    private GuidePageAdapter adapter;

    private AtomicInteger atomicInteger = new AtomicInteger();

    // ImageView
    private ImageView mImages[];
    private ImageView mImage;


    private List brandList;

    private TextView mFindMoreNews;
    private Timer timer;
    private TimerTask task;
    private Handler handler;
//    private Timer timer;
//    private TimerTask task;
    AlphaIndicator alphaIndicator;
    private RequestQueue mQueue;
    private List<Result.DataBean> dataBeanList1;

    private LinearLayout mLlNews1,mLlNews2,mLlNews3;
    private LinearLayout mLlDuxin;
    private TextView mTvNews1,mTvNews2,mTvNews3;
    private int mId;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MainActivity)
        {
            MainActivity mainActivity=(MainActivity)activity;
            alphaIndicator= (AlphaIndicator) mainActivity.findViewById(R.id.alphaIndicator);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mainpage, null);
        // 初始化控件
        initView();
        // 加事件
        initEvent();
        // 从服务器中获取新闻数据
        getNewsFromServer();
        return view;
    }

    // 加事件
    private void initEvent() {

        mLlNews1.setOnClickListener(this);
        mLlNews2.setOnClickListener(this);
        mLlNews3.setOnClickListener(this);


    }



    // 初始化控件
    private void initView() {
//        mFindMoreNews = (TextView) view.findViewById(R.id.tv_morenews);
//        mFindMoreNews.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alphaIndicator.setPagerNum(3);
//            }
//        });

        mFindMoreNews = (TextView) view.findViewById(R.id.tv_morenews);
        mFindMoreNews.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                alphaIndicator.setPagerNum(3);
            }
        });

        mLlDuxin= (LinearLayout) view.findViewById(R.id.id_ll_duxin);
        mLlDuxin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                alphaIndicator.setPagerNum(1);
            }
        });


        // 第一步：初始化ViewPager
        viewPager = (ViewPager) view.findViewById(R.id.vp_advertise);

        // 创建ViewGroup对象，用来存放图片数组
        ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.rounddot);

        // 第二步：创建广告对象
        ar = new ArrayList<View>();
        View v0 = getActivity().getLayoutInflater().inflate(
                R.layout.advertise_item, null);
        LinearLayout l0 = (LinearLayout) v0.findViewById(R.id.advertise_item);
        l0.setBackgroundResource(R.drawable.ad1);
        ar.add(l0);

        View v1 = getActivity().getLayoutInflater().inflate(
                R.layout.advertise_item, null);
        LinearLayout l1 = (LinearLayout) v1.findViewById(R.id.advertise_item);
        l1.setBackgroundResource(R.drawable.ad2);
        ar.add(l1);

        View v2 = getActivity().getLayoutInflater().inflate(
                R.layout.advertise_item, null);
        LinearLayout l2 = (LinearLayout) v2.findViewById(R.id.advertise_item);
        l2.setBackgroundResource(R.drawable.ad3);
        ar.add(l2);

        View v3 = getActivity().getLayoutInflater().inflate(
                R.layout.advertise_item, null);
        LinearLayout l3 = (LinearLayout) v3.findViewById(R.id.advertise_item);
        l3.setBackgroundResource(R.drawable.ad4);
        ar.add(l3);

        adapter = new GuidePageAdapter(getActivity(), ar);
        viewPager.setAdapter(adapter);

        mImages = new ImageView[ar.size()];
        for (int i = 0; i < ar.size(); i++) {
            mImage = new ImageView(getActivity());
            // 设置图片宽和高
            LayoutParams layoutParams = new LayoutParams(9, 9);
            layoutParams.setMargins(10, 5, 10, 5);
            mImage.setLayoutParams(layoutParams);

            mImages[i] = mImage;

            if (i == 0) {
                mImages[i].setBackgroundResource(R.drawable.small_bg1);
            } else {
                mImages[i].setBackgroundResource(R.drawable.small_bg);
            }
            viewGroup.addView(mImages[i]);
        }

        viewPager.setOnPageChangeListener(vp_listener);

        if (handler == null) {
            handler = new Handler() {
                public void handleMessage(android.os.Message msg) {
                    // 显示第几项
                    viewPager.setCurrentItem(msg.what);

                    if (atomicInteger.get() == ar.size()) {
                        atomicInteger.set(0);
                    }
                }


            };
        }

        // 创建定时器
        if (timer == null)
            timer = new Timer();

        if (task == null) {
            task = new TimerTask() {

                @Override
                public void run() {
                    handler.sendEmptyMessage(atomicInteger.incrementAndGet() - 1);
                    long time = System.currentTimeMillis();
                    Log.e("timertask", time + "");
                }
            };
            timer.schedule(task, 2000, 2000);
        }

        mLlNews1= (LinearLayout) view.findViewById(R.id.id_ll_news1);
        mLlNews2= (LinearLayout) view.findViewById(R.id.id_ll_news2);
        mLlNews3= (LinearLayout) view.findViewById(R.id.id_ll_news3);

        mTvNews1= (TextView) view.findViewById(R.id.id_tv_news1);
        mTvNews2= (TextView) view.findViewById(R.id.id_tv_news2);
        mTvNews3= (TextView) view.findViewById(R.id.id_tv_news3);

    }


    OnPageChangeListener vp_listener = new OnPageChangeListener() {

        @Override
        public void onPageSelected(int arg0) {
            atomicInteger.getAndSet(arg0);
            for (int i = 0; i < mImages.length; i++) {
                mImages[i].setBackgroundResource(R.drawable.small_bg1);
                if (arg0 != i) {
                    mImages[i].setBackgroundResource(R.drawable.small_bg);
                }
            }

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }
    };



    @Override
    public void onStop() {
        super.onStop();
        if (timer != null) {
            timer.cancel();
            timer = null;

        }
        if (task != null) {
            task.cancel();
            task = null;

        }
        handler = null;
    }

    private void getNewsFromServer() {
        mQueue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(URL_NEWS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG 请求成功", response);
//                updateUi(response);
                Gson gson = new Gson();
                Result result = gson.fromJson(response, Result.class);
                dataBeanList1 = result.getTngou();
                for (int i = 0; i < dataBeanList1.size(); i++) {

                    System.out.println(dataBeanList1.get(i).toString());
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    mTvNews1.setText(dataBeanList1.get(0).getTitle());
                    mTvNews2.setText(dataBeanList1.get(1).getTitle());
                    mTvNews3.setText(dataBeanList1.get(2).getTitle());
                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("TAG 请求失败", volleyError.getMessage());
            }
        }


        );
        mQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_ll_news1:
                mId=dataBeanList1.get(0).getId();
                break;
            case R.id.id_ll_news2:
                mId=dataBeanList1.get(1).getId();

                break;
            case R.id.id_ll_news3:
                mId=dataBeanList1.get(2).getId();
                break;

        }
        String url = URL_DETAILS  + mId;
        Intent intent = new Intent(getActivity(), WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}

