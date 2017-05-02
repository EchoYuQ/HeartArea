package com.bupt.heartarea.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bupt.heartarea.bean.MeasureData;
import com.bupt.heartarea.bean.ResponseBean;
import com.bupt.heartarea.utils.GlobalData;
import com.bupt.heartarea.utils.TimeUtil;
import com.google.gson.Gson;
import com.bupt.heartarea.R;

import net.lemonsoft.lemonbubble.LemonBubble;
import net.lemonsoft.lemonhello.LemonHello;
import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.adapter.LemonHelloEventDelegateAdapter;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuqing on 2017/1/1.
 */
public class SaveDataActivity extends Activity implements View.OnClickListener {

    private static final String URL_COLLECT_DATA = GlobalData.URL_HEAD + ":8080/detect3/CollectServlet";
    private EditText et_mUserName;
    private EditText et_mAge;
    private RadioGroup rg_mSex;
    private RadioButton rb_mMan;
    private RadioButton rb_mWoman;
//    private EditText et_mFatigue;
    private Button btn_mSaveDataBtn;
    private String mfileName;
    private Spinner mSpinnerPressure,mSpinnerDiet, mSpinnerWeather, mSpinnerHealth, mSpinnerSport, mSpinnerNap;
    private EditText mEtHeight, mEtWeight;
    private String mUserName = "";
    private MeasureData mMeasureData = new MeasureData();
    private int mHeight = -1;
    private int mWeight = -1;
    private int mAge = -1;
    private int mSex = 0;
    private int mPressure = 0;
    private String mMeasureDataString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_savedata);
        init();
        mMeasureData = (MeasureData) getIntent().getSerializableExtra("measure_data");
        Gson gson = new Gson();
        mMeasureDataString = gson.toJson(mMeasureData);
        Log.i("mMeasureDataString", mMeasureDataString);

    }



    void init() {
        et_mUserName = (EditText) findViewById(R.id.id_et_usermame);
        et_mAge = (EditText) findViewById(R.id.id_et_age);
//        et_mFatigue = (EditText) findViewById(R.id.id_et_fatigue);
        et_mUserName = (EditText) findViewById(R.id.id_et_usermame);
        mEtHeight = (EditText) findViewById(R.id.id_et_height);
        mEtWeight = (EditText) findViewById(R.id.id_et_weight);
        btn_mSaveDataBtn = (Button) findViewById(R.id.id_btn_savedata);
        btn_mSaveDataBtn.setOnClickListener(this);

        rg_mSex = (RadioGroup) findViewById(R.id.id_rg_sex);
        rb_mMan = (RadioButton) findViewById(R.id.id_rb_man);
        rb_mMan.setChecked(true);
        rb_mWoman = (RadioButton) findViewById(R.id.id_rb_woman);

        et_mUserName.setText(GlobalData.getUsername());
        switch (GlobalData.getSex()) {
            case 0:
                rb_mMan.setChecked(true);
                break;
            case 1:
                rb_mWoman.setChecked(true);
                break;
            default:
                rb_mMan.setChecked(true);
                break;
        }

        mSpinnerPressure = (Spinner) findViewById(R.id.id_spinner_pressure);
        mSpinnerDiet = (Spinner) findViewById(R.id.id_spinner_diet);
        mSpinnerWeather = (Spinner) findViewById(R.id.id_spinner_weather);
        mSpinnerHealth = (Spinner) findViewById(R.id.id_spinner_health);
        mSpinnerSport = (Spinner) findViewById(R.id.id_spinner_sport);
        mSpinnerNap = (Spinner) findViewById(R.id.id_spinner_nap);

        getUserInformationFromSP();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_savedata:

//                Log.i("Constants.datas", mUserDataBean.getDatas().toString());


                // 将源数据List转成数组
                /*
                List<Double> data_origin_list=userDataBean.getDatas();
                double[] data_origin=new double[data_origin_list.size()];
                for(int i=0;i<data_origin_list.size();i++)
                {
                    data_origin[i]=data_origin_list.get(i);
                }
                double[] data_smoothed=new double[data_origin_list.size()];

                // SG算法的参数矩阵
                double[] coeffs = SGFilter.computeSGCoefficients(5, 5, 5);
                // SG算法去噪处理
                data_smoothed=new SGFilter(5, 5).smooth(data_origin, coeffs);
                // data_smoothed_list为SG算法处理后的值列表
                List<Double> data_smoothed_list=new ArrayList<Double>();
                for(int i=10;i<data_smoothed.length-10;i++)
                {
                    data_smoothed_list.add(data_smoothed[i]);
                }
                Log.i("data_smoothed.length",data_smoothed.length+"");
                System.out.println(data_smoothed_list);
                // peaksList为峰的横坐标列表
                List<Integer> peaksList=CalHeartRate.findPeaks(data_smoothed_list);
                int heartRate=CalHeartRate.calHeartRate(peaksList,100);
                Log.i("heart rate",heartRate+"");
                userDataBean.setRr_datas(CalHeartRate.calRRIntevalOrigin(peaksList));
                System.out.println(userDataBean.getRr_datas());
                userDataBean.setNew_datas(data_smoothed_list);
                */

//                long currenttime = System.currentTimeMillis();
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Date date = new Date(currenttime);
//                String currentTimeStr = dateFormat.format(date);
//                mUserDataBean.setCurrentTime(currentTimeStr);



                mUserName = et_mUserName.getText().toString().trim();
                if (mUserName.equals("")) {
                    Toast.makeText(SaveDataActivity.this, "请输入您的姓名", Toast.LENGTH_SHORT).show();
                    break;
                } else {
//                    mUserDataBean.setUserName(mUserName);
                }

                if (et_mAge.getText().toString().equals("")) {
                    Toast.makeText(SaveDataActivity.this, "请输入您的年龄", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    mAge = Integer.parseInt(et_mAge.getText().toString().trim());
                    Log.i("age", mAge + "");
                    if (mAge >= 1 && mAge <= 100) {
//                        mUserDataBean.setAge(mAge);
                    } else {
                        Toast.makeText(SaveDataActivity.this, "请输入您正确的年龄，范围1-100", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

                if (mEtHeight.getText().toString().trim().equals("")) {
                    Toast.makeText(SaveDataActivity.this, "请输入您的身高", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    mHeight = Integer.parseInt(mEtHeight.getText().toString().trim());
                    if (mHeight >= 100 && mHeight <= 200) {

                    } else {
                        Toast.makeText(SaveDataActivity.this, "请输入您正确的身高，范围100-200（cm）", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

                if (mEtWeight.getText().toString().trim().equals("")) {
                    Toast.makeText(SaveDataActivity.this, "请输入您的体重", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    mWeight = Integer.parseInt(mEtWeight.getText().toString().trim());
                    if (mHeight >= 40 && mHeight <= 200) {

                    } else {
                        Toast.makeText(SaveDataActivity.this, "请输入您正确的体重，范围40-200（kg）", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

                rg_mSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId == rb_mMan.getId()) {
//                            mUserDataBean.setSex(0);
                            mSex = 0;
                        } else {
//                            mUserDataBean.setSex(1);
                            mSex = 1;
                        }
                    }
                });


//                Gson gson = new Gson();
//                final String jsonstring = gson.toJson(mUserDataBean);

//                mfileName = currentTimeStr;
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        FileUtil.saveData2Sdcard(jsonstring, mfileName);
//
//                    }
//                }).start();
//                Toast.makeText(SaveDataActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                postToServer();
                break;


        }

    }

    /**
     * 将采集到的数据发送到服务器端
     */
    void postToServer() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_COLLECT_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i("Response", s);
                System.out.println(s);
                Gson gson = new Gson();
                ResponseBean responseBean = gson.fromJson(s, ResponseBean.class);
                if (responseBean!=null&&responseBean.getCode() == 0) {
                    saveUserInformationToSP();

//                    System.out.println("上传成功");
//                    Toast.makeText(SaveDataActivity.this, "上传成功", Toast.LENGTH_LONG).show();
//                    LemonBubble.getRightBubbleInfo()// 增加无限点语法修改bubbleInfo的特性
//                            .setTitle("上传成功")
//                            .setTitleFontSize(12)// 修改字体大小
//                            .setTitleColor(Color.parseColor("#a269af73"))
//                            .setMaskColor(Color.argb(100, 0, 0, 0))// 修改蒙版颜色
//                            .show(SaveDataActivity.this, 2000);

                    LemonHello.getSuccessHello("上传成功", "感谢您对我们工作的支持，谢谢您的使用")
                            .setContentFontSize(14)
                            .addAction(new LemonHelloAction("我知道啦", new LemonHelloActionDelegate() {
                                @Override
                                public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                    helloView.hide();
                                    finish();

                                }
                            }))
                            .setEventDelegate(new LemonHelloEventDelegateAdapter() {
                                @Override
                                public void onMaskTouch(LemonHelloView helloView, LemonHelloInfo helloInfo) {
                                    super.onMaskTouch(helloView, helloInfo);
                                    helloView.hide();
                                    finish();
                                }
                            })
                            .show(SaveDataActivity.this);





                } else if(responseBean!=null){
                    Toast.makeText(SaveDataActivity.this, responseBean.getMsg(), Toast.LENGTH_LONG).show();

                }else
                {
                    Toast.makeText(SaveDataActivity.this, "上传失败", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(SaveDataActivity.this, "连接服务器失败", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> map = new HashMap<>();
                map.put("userid", GlobalData.getUserid());
                map.put("username", mUserName);
                map.put("sex", String.valueOf(mSex));
                map.put("time", TimeUtil.getCurrentTime());
                map.put("date", TimeUtil.getCurrentDate());
                map.put("age", String.valueOf(mAge));
                map.put("pressure", mSpinnerPressure.getSelectedItem().toString());
                map.put("height", String.valueOf(mHeight));
                map.put("weight", String.valueOf(mWeight));


                map.put("weather", mSpinnerWeather.getSelectedItem().toString());
                map.put("health", mSpinnerHealth.getSelectedItem().toString());
                map.put("sport", mSpinnerSport.getSelectedItem().toString());
                map.put("diet", mSpinnerDiet.getSelectedItem().toString());
                map.put("nap", mSpinnerNap.getSelectedItem().toString());

                map.put("all_data", mMeasureDataString);
                Log.i("收集数据", map.toString());
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void saveUserInformationToSP() {
        SharedPreferences sp = getSharedPreferences("information", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", mUserName);
        editor.putInt("sex", mSex);
        editor.putInt("age", mAge);
        editor.putInt("height", mHeight);
        editor.putInt("weight", mWeight);

        editor.apply();
    }

    private void getUserInformationFromSP() {
        SharedPreferences sp = getSharedPreferences("information", Context.MODE_PRIVATE);
        if (sp != null) {
            et_mUserName.setText(sp.getString("username", "")); // 第二个参数为默认值
            et_mAge.setText(sp.getInt("age", -1) + "");
            mEtHeight.setText(sp.getInt("height", -1) + "");
            mEtWeight.setText(sp.getInt("weight", -1) + "");
            switch (sp.getInt("sex", 0)) {
                case 0:
                    rb_mMan.setChecked(true);
                    break;
                case 1:
                    rb_mWoman.setChecked(true);
                    break;
                default:
                    rb_mMan.setChecked(true);
                    break;
            }
        }
    }
}

