package com.bupt.heartarea.fragment;

/**
 * Created by yuqing on 2017/3/4.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bupt.heartarea.activity.WebActivity;
import com.bupt.heartarea.bean.ResponseBean;
import com.bupt.heartarea.bean.Result;
import com.bupt.heartarea.ui.AlphaIndicator;
import com.bupt.heartarea.utils.GlobalData;
import com.bupt.heartarea.utils.TimeUtil;
import com.google.gson.Gson;
import com.bupt.heartarea.R;
import com.bupt.heartarea.activity.MainActivity;
import com.bupt.heartarea.adapter.GuidePageAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import cn.aigestudio.datepicker.bizs.calendars.DPCManager;
import cn.aigestudio.datepicker.bizs.decors.DPDecor;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker2;


public class MainPageFragment extends Fragment implements OnClickListener {
    private static final String URL_NEWS = "http://www.tngou.net/api/info/list?rows=3";
    private static final String URL_DETAILS = "http://www.tngou.net/info/show/";
    private static final String URL_GETSIGNEDDATE = GlobalData.URL_HEAD+":8080/detect3/SignInMonth";
    private static final String URL_POSTSIGNEDDATE = GlobalData.URL_HEAD+":8080/detect3/SignIn";
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

    private LinearLayout mLlNews1, mLlNews2, mLlNews3;
    private LinearLayout mLlDuxin;
    private LinearLayout mLlSign;
    private TextView mTvNews1, mTvNews2, mTvNews3;
    private int mId;

    private DPCManager dpcManager;

    int mYear;
    int mMonth;
    int mDay;
    String mCurrentDate = "";
    boolean mIsSigned = false;

    List<String> mSignedDateList = new ArrayList<>();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) activity;
            alphaIndicator = (AlphaIndicator) mainActivity.findViewById(R.id.alphaIndicator);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mainpage, null);

        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mYear = c.get(Calendar.YEAR); // 获取当前年份
        mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码
        mCurrentDate = TimeUtil.getCurrentDate();
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

        mLlDuxin = (LinearLayout) view.findViewById(R.id.id_ll_duxin);
        mLlDuxin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                alphaIndicator.setPagerNum(1);
            }
        });

        mLlSign = (LinearLayout) view.findViewById(R.id.ll_sign_mainpage);
        mLlSign.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

//                showSignDialog();
                  getSignedDateAndShowSignDialog();


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

        mLlNews1 = (LinearLayout) view.findViewById(R.id.id_ll_news1);
        mLlNews2 = (LinearLayout) view.findViewById(R.id.id_ll_news2);
        mLlNews3 = (LinearLayout) view.findViewById(R.id.id_ll_news3);

        mTvNews1 = (TextView) view.findViewById(R.id.id_tv_news1);
        mTvNews2 = (TextView) view.findViewById(R.id.id_tv_news2);
        mTvNews3 = (TextView) view.findViewById(R.id.id_tv_news3);

    }

    private void showSignDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.show();
        final DatePicker2 picker = new DatePicker2(getActivity());

        dpcManager = DPCManager.getInstance();
        dpcManager.clearnDATE_CACHE(); //清除cache

        //自定义背景绘制示例
//        List<String> tmp = new ArrayList<>();
//        tmp.add("2017-05-01"); //yyyy-M-d
//        tmp.add("2017-05-02"); //yyyy-M-d
//        tmp.add("2017-05-03"); //yyyy-M-d
//        tmp.add("2017-05-04"); //yyyy-M-d
////                tmp.add("2017-05-05"); //yyyy-M-d
//
//        mSignedDateList.clear();
//        for (int i = 0; i < tmp.size(); i++) {
//            String[] temp = tmp.get(i).split("-");
//            mSignedDateList.add(temp[0] + "-" + Integer.parseInt(temp[1]) + "-" + Integer.parseInt(temp[2]));
//        }

        dpcManager.setDecorBG(mSignedDateList); //预先设置日期背景 一定要在在开始设置

        picker.setDate(mYear, mMonth); //设置日期

        picker.setMode(DPMode.NONE); //设置选择模式

        picker.setFestivalDisplay(false); //是否显示节日
        picker.setTodayDisplay(false); //是否高亮显示今天
        picker.setHolidayDisplay(false); //是否显示假期
        picker.setDeferredDisplay(false); //是否显示补休
        picker.setIsScroll(false); //是否允许滑动 false表示左右上下都不能滑动  单项设置上下or左右 你需要自己扩展
        picker.setIsSelChangeColor(true, getResources().getColor(R.color.bg_white)); //设置选择的日期字体颜色,不然有的背景颜色和默认的字体颜色不搭

        picker.setLeftTitle(mYear + "年" + mMonth + "月"); //左上方text

        // 判断今日是否签到过
        if (mSignedDateList != null) {
            int length = mSignedDateList.size();
            String last = mSignedDateList.get(length - 1);
            if (last.equals(mYear + "-" + mMonth + "-" + mDay)) {
                mIsSigned = true;
            } else {
                mIsSigned = false;
            }

        } else {
            mIsSigned = false;
        }

        picker.setRightTitle(mIsSigned); //是否签到
        picker.setOnClickSignIn(new DatePicker2.OnClickSignIn() {
            @Override
            public void signIn() {
//                if (!mIsSigned) {
//                    //动态更新的时候必须  清除cache
//                    dpcManager.clearnDATE_CACHE(); //清除cache
//                    //重新设置日期
//                    mSignedDateList.add(mYear + "-" + mMonth + "-" + mDay);
//                    dpcManager.setDecorBG(mSignedDateList);
//
//                    picker.setDate(mYear, mMonth);
//                    picker.setLeftTitle(mYear + "年" + mMonth + "月");
//                    picker.setRightTitle(true);
//
//                    picker.setDPDecor(new DPDecor() {
//                        @Override
//                        public void drawDecorBG(Canvas canvas, Rect rect, Paint paint) {
//                            paint.setColor(getResources().getColor(R.color.bg_green));
//                            canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2.5F, paint);
//                        }
//                    });
//                    picker.invalidate(); //刷新
//                }
                setSignedDateToServer(picker);

            }
        }); //点击签到事件

        //设置预先选中日期的背景颜色
        picker.setDPDecor(new DPDecor() {
            @Override
            public void drawDecorBG(Canvas canvas, Rect rect, Paint paint) {
                paint.setColor(getResources().getColor(R.color.bg_green));
                canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2.5F, paint);
            }
        });

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setContentView(picker, params);
        dialog.getWindow().setGravity(Gravity.CENTER);
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
        switch (v.getId()) {
            case R.id.id_ll_news1:
                mId = dataBeanList1.get(0).getId();
                break;
            case R.id.id_ll_news2:
                mId = dataBeanList1.get(1).getId();

                break;
            case R.id.id_ll_news3:
                mId = dataBeanList1.get(2).getId();
                break;

        }
        String url = URL_DETAILS + mId;
        Intent intent = new Intent(getActivity(), WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    /**
     * 向服务器请求用户的签到日期
     *
     */
    private void getSignedDateAndShowSignDialog() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, URL_GETSIGNEDDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Gson gson = new Gson();


                        Log.d("onResponse",s);
                        ResponseBean responseBean = gson.fromJson(s, ResponseBean.class);


                        if (responseBean != null) {
                            if (responseBean.getCode() == 0) {
                                String jsonstr = responseBean.getBody();
                                try {
                                    JSONObject jsonObject = new JSONObject(jsonstr);
                                    if (jsonObject.has("signed_date")) {
                                        String str = jsonObject.getString("signed_date").trim();
                                        mSignedDateList.clear();
                                        if(str!=null&&!str.equals(""))
                                        {
                                            String[] date_array = str.split(",");
                                            for (int i = 0; i < date_array.length; i++) {

                                                String[] temp = date_array[i].split("-");
                                                // yyyy-MM-dd转成yyyy-M-d
                                                mSignedDateList.add(temp[0] + "-" + Integer.parseInt(temp[1]) + "-" + Integer.parseInt(temp[2]));
                                            }
                                        }
                                        showSignDialog();

                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                Toast.makeText(getActivity(), "获取签到日期失败，请检查网络", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getActivity(), "获取签到日期失败，请检查网络", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("请求失败", volleyError.getMessage(), volleyError);
                Toast.makeText(getActivity(), "获取签到日期失败，请检查网络", Toast.LENGTH_SHORT).show();

                String str="2017-05-01,2017-05-02,2017-05-03,2017-05-04,2017-05-05";
                String[] date_array = str.split(",");
                mSignedDateList.clear();
                for (int i = 0; i < date_array.length; i++) {

                    String[] temp = date_array[i].split("-");
                    // yyyy-MM-dd转成yyyy-M-d
                    mSignedDateList.add(temp[0] + "-" + Integer.parseInt(temp[1]) + "-" + Integer.parseInt(temp[2]));
                }

                showSignDialog();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<>();
                map.put("userid", GlobalData.userid);
                map.put("date", mCurrentDate);

                return map;
            }
        };
        requestQueue.add(request);

    }

    /**
     * 向服务器发送用户本次签到请求
     *
     */
    private void setSignedDateToServer(final DatePicker2 picker) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, URL_POSTSIGNEDDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Gson gson = new Gson();

                        ResponseBean responseBean = gson.fromJson(s, ResponseBean.class);


                        if (responseBean != null) {
                            if (responseBean.getCode() == 0) {
                                String jsonstr = responseBean.getBody();
                                Toast.makeText(getActivity(), "签到成功", Toast.LENGTH_SHORT).show();
                                if (!mIsSigned) {
                                    //动态更新的时候必须  清除cache
                                    dpcManager.clearnDATE_CACHE(); //清除cache
                                    //重新设置日期
                                    mSignedDateList.add(mYear + "-" + mMonth + "-" + mDay);
                                    dpcManager.setDecorBG(mSignedDateList);

                                    picker.setDate(mYear, mMonth);
                                    picker.setLeftTitle(mYear + "年" + mMonth + "月");
                                    picker.setRightTitle(true);

                                    picker.setDPDecor(new DPDecor() {
                                        @Override
                                        public void drawDecorBG(Canvas canvas, Rect rect, Paint paint) {
                                            paint.setColor(getResources().getColor(R.color.bg_green));
                                            canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2.5F, paint);
                                        }
                                    });
                                    picker.invalidate(); //刷新
                                }

                            } else {
                                Toast.makeText(getActivity(), "请求服务器失败，请检查网络", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getActivity(), "请求服务器失败，请检查网络", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("请求失败", volleyError.getMessage(), volleyError);
                Toast.makeText(getActivity(), "请求服务器失败，请检查网络", Toast.LENGTH_SHORT).show();
                if (!mIsSigned) {
                    //动态更新的时候必须  清除cache
                    dpcManager.clearnDATE_CACHE(); //清除cache
                    //重新设置日期
                    mSignedDateList.add(mYear + "-" + mMonth + "-" + mDay);
                    dpcManager.setDecorBG(mSignedDateList);

                    picker.setDate(mYear, mMonth);
                    picker.setLeftTitle(mYear + "年" + mMonth + "月");
                    picker.setRightTitle(true);

                    picker.setDPDecor(new DPDecor() {
                        @Override
                        public void drawDecorBG(Canvas canvas, Rect rect, Paint paint) {
                            paint.setColor(getResources().getColor(R.color.bg_green));
                            canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2.5F, paint);
                        }
                    });
                    picker.invalidate(); //刷新
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<>();
                map.put("userid", GlobalData.userid);
                map.put("date", mCurrentDate); // 2017-05-05
                return map;
            }
        };
        requestQueue.add(request);

    }
}

