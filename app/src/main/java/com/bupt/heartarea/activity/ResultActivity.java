package com.bupt.heartarea.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bupt.heartarea.ui.CircleIndicator;
import com.bupt.heartarea.ui.LineIndicator;
import com.bupt.heartarea.R;
import com.bupt.heartarea.ui.IndicatorItem;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends Activity implements View.OnClickListener{

    CircleIndicator ci1;
    LineIndicator mLiHeartRateProgress;
    LineIndicator mLiBloodOxygenProgress;

    private int mHeartRate = 0;
    private int mFatigue = 0;
    private int mBloodOxygen=0;
    int mMiddleColor;
    int mLowColor;
    int mHighColor;
    int mFeedBackValue = 1;
    String mAlert;
    Button mBtnFeedBackYes;
    Button mBtnFeedBackNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cicle_view);


        Bundle bundle = getIntent().getExtras();
        mHeartRate = bundle.getInt("heart_rate");
        mFatigue = bundle.getInt("pressure");
        mBloodOxygen = bundle.getInt("blood_oxygen");

        mAlert = bundle.getString("alert");

        initColor();
        initView();
        setHeartRateProgress(mHeartRate);
        testIndicator();
        setBloodOxygenProgress(mBloodOxygen);
        Log.e("mBloodOxygen",mBloodOxygen+"");
    }

    private void initView() {
        mLiHeartRateProgress = (LineIndicator) findViewById(R.id.li_progress_heart_rate);
        mLiBloodOxygenProgress = (LineIndicator) findViewById(R.id.li_progress_blood_oxygen);
        ci1 = (CircleIndicator) findViewById(R.id.ci_1);

        mBtnFeedBackYes= (Button) findViewById(R.id.btn_result_yes);
        mBtnFeedBackYes.setOnClickListener(this);

        mBtnFeedBackNo= (Button) findViewById(R.id.btn_result_no);
        mBtnFeedBackNo.setOnClickListener(this);



    }

    private void initColor() {
        mMiddleColor = getResources().getColor(R.color.pressure_middle);
        mLowColor = getResources().getColor(R.color.pressure_low);
        mHighColor = getResources().getColor(R.color.pressure_high);
    }


    private void setHeartRateProgress(int value) {
        String leftAlert = "慢";
        String leftContent = "0";
        String rightAlert = "快";
        String rightContent = "150";
        if (value > 100) {
            mLiHeartRateProgress.setProgressColor(mHighColor);
            mLiHeartRateProgress.setIndicatorBackground(mHighColor);
        } else {
            if (value > 65) {
                mLiHeartRateProgress.setProgressColor(mMiddleColor);
                mLiHeartRateProgress.setIndicatorBackground(mMiddleColor);

            } else {
                mLiHeartRateProgress.setProgressColor(mLowColor);
                mLiHeartRateProgress.setIndicatorBackground(mLowColor);

            }
        }
        mLiHeartRateProgress.setContent(leftAlert, leftContent, rightAlert, rightContent);
        mLiHeartRateProgress.setIndicator(40, 150, value, value + " BMP");
    }

    /**
     * 绘制 血氧值 进度条
     * @param value
     */
    private void setBloodOxygenProgress(int value) {
        String leftAlert = "低";
        String leftContent = "90";
        String rightAlert = "高";
        String rightContent = "100";
        if (value > 97) {
            mLiBloodOxygenProgress.setProgressColor(mHighColor);
            mLiBloodOxygenProgress.setIndicatorBackground(mHighColor);
        } else {
            if (value > 94) {
                mLiBloodOxygenProgress.setProgressColor(mMiddleColor);
                mLiBloodOxygenProgress.setIndicatorBackground(mMiddleColor);

            } else {
                mLiBloodOxygenProgress.setProgressColor(mLowColor);
                mLiBloodOxygenProgress.setIndicatorBackground(mLowColor);

            }
        }
        mLiBloodOxygenProgress.setContent(leftAlert, leftContent, rightAlert, rightContent);
        mLiBloodOxygenProgress.setIndicator(90, 100, value, value + " %");
    }

    /**
     * 疲劳度仪表盘
     */
    private void testIndicator() {

        List<IndicatorItem> dividerIndicator = new ArrayList<>();
        IndicatorItem item1 = new IndicatorItem();
        item1.start = 0;
        item1.end = 30;
        item1.value = "过低";
        item1.color = mLowColor;
        dividerIndicator.add(item1);

        IndicatorItem item2 = new IndicatorItem();
        item2.start = 30;
        item2.end = 70;
        item2.value = "正常";
        item2.color = mMiddleColor;
        dividerIndicator.add(item2);

        IndicatorItem item3 = new IndicatorItem();
        item3.start = 70;
        item3.end = 100;
        item3.value = "过高";
        item3.color = mHighColor;
        dividerIndicator.add(item3);


        String title = "疲劳度";
        String content = mFatigue + "";
        String unit = "";
        String alert = "愉快的心情";
        if (mFatigue < 30) {

            ci1.setContentColor(mLowColor, mLowColor);
            ci1.setmAlertColor(mLowColor);

        } else {
            if (mFatigue < 70) {
                ci1.setContentColor(mMiddleColor, mMiddleColor);
                ci1.setmAlertColor(mMiddleColor);
            } else {
                ci1.setContentColor(mHighColor, mHighColor);
                ci1.setmAlertColor(mHighColor);
            }
        }
        ci1.setContent(title, content, unit, mAlert);
        ci1.setIndicatorValue(dividerIndicator, mFatigue);
    }

    /**
     * 显示带有radiobutton的对话框
     */
    private void showRadioButtonDialog() {
        View radiobuttonview;       //使用view来接入方法写出的dialog，方便相关初始化
        LayoutInflater inflater;        //引用自定义dialog布局
        inflater = LayoutInflater.from(ResultActivity.this);
        radiobuttonview = (LinearLayout) inflater.inflate(R.layout.radiogroup_feedback, null);                                           //那个layout就是我们可以dialog自定义的布局啦
        final RadioGroup radiogroup = (RadioGroup) radiobuttonview.findViewById(R.id.rg_feedback);
        final RadioButton radioButton1 = (RadioButton) radiobuttonview.findViewById(R.id.rb_pressure_1);
        final RadioButton radioButton2 = (RadioButton) radiobuttonview.findViewById(R.id.rb_pressure_2);
        final RadioButton radioButton3 = (RadioButton) radiobuttonview.findViewById(R.id.rb_pressure_3);
        final RadioButton radioButton4 = (RadioButton) radiobuttonview.findViewById(R.id.rb_pressure_4);
        final RadioButton radioButton5 = (RadioButton) radiobuttonview.findViewById(R.id.rb_pressure_5);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_pressure_1:
                        mFeedBackValue = 1;
                        break;
                    case R.id.rb_pressure_2:
                        mFeedBackValue = 2;
                        break;
                    case R.id.rb_pressure_3:
                        mFeedBackValue = 3;
                        break;
                    case R.id.rb_pressure_4:
                        mFeedBackValue = 4;
                        break;
                    case R.id.rb_pressure_5:
                        mFeedBackValue = 5;
                        break;
                }

            }
        });

        radiogroup.check(R.id.rb_pressure_3);

        new AlertDialog.Builder(ResultActivity.this)
                .setView(radiobuttonview)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ResultActivity.this, mFeedBackValue+"", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_result_yes:

                break;
            case R.id.btn_result_no:
                showRadioButtonDialog();
                break;
        }
    }
}
