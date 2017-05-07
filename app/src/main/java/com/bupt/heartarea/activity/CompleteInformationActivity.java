package com.bupt.heartarea.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bupt.heartarea.R;
import com.bupt.heartarea.bean.ResponseBean;
import com.bupt.heartarea.utils.GlobalData;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;


public class CompleteInformationActivity extends Activity implements View.OnClickListener {


    TextView mTvSex, mTvBirthday;
    EditText mEtName;
    LinearLayout mRL_birthday, mRL_sex;
    Button mBtnSubmit;
    Activity mActivity;
    ImageView mIconBack;
    Context mContext;
    private int mSex_Int = 0;
    private String mBirthday;
    private String mName;
    private String mEmail;


    // 弹窗item
    private String[] items_sex = new String[]{"男", "女"};


    // 用户缓存
    private SharedPreferences preferences;
    private static final String URL_CHANGE_INFORMATION = GlobalData.URL_HEAD + ":8080/detect3/ChangeServlet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_complete_information);
        mContext = this;
        mActivity = this;
        initView();


        //透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    private void initView() {
        mEtName = (EditText) findViewById(R.id.et_nickname_complete_information);
        mBtnSubmit = (Button) findViewById(R.id.btn_yes_complete_information);
        mTvSex = (TextView) findViewById(R.id.tv_sex_complete_information);
        mTvBirthday = (TextView) findViewById(R.id.tv_birthday_complete_information);
        mRL_birthday = (LinearLayout) findViewById(R.id.ll_birthday_complete_information);
        mRL_sex = (LinearLayout) findViewById(R.id.ll_sex_complete_information);
        mIconBack= (ImageView) findViewById(R.id.iv_back_complete_information);

        mTvSex.setText(items_sex[mSex_Int]);


//        mBackIcon.setOnClickListener(this);
        mEtName.setOnClickListener(this);
        mBtnSubmit.setOnClickListener(this);
        mRL_birthday.setOnClickListener(this);
        mRL_sex.setOnClickListener(this);
        mIconBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
//            case com.bupt.heartarea.R.id.id_iv_back:
//                finish();
//                break;
            case R.id.btn_yes_complete_information:
                mName = mEtName.getText().toString().trim();
                if (mName == null || mName.equals("")) {
                    Toast.makeText(CompleteInformationActivity.this, "请输入您的昵称", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    int length = mName.length();
                    if (length > 8 || length < 2) {
                        Toast.makeText(CompleteInformationActivity.this, "昵称长度应为2~8个字符", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

                if (mTvSex.getText().toString() == null || mTvSex.getText().toString().equals("")) {
                    Toast.makeText(CompleteInformationActivity.this, "请选择您的性别", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (mTvBirthday.getText().toString() == null || mTvBirthday.getText().toString().equals("")) {
                    Toast.makeText(CompleteInformationActivity.this, "请选择您的生日", Toast.LENGTH_SHORT).show();
                    break;
                }

                saveToSever();
                break;
            case R.id.ll_birthday_complete_information:
                showDatePickerDialog1();
                break;
            case R.id.ll_sex_complete_information:
                showSexDialog();
                break;
            case R.id.iv_back_complete_information:
                finish();
                break;

        }
    }


    /**
     * 弹出选项窗口方法
     */
    private void showSexDialog() {
        new AlertDialog.Builder(this)
                .setTitle("选择性别")
                .setItems(items_sex, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, final int which) {

                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mSex_Int = which;
                                mTvSex.setText(items_sex[which]);
                            }
                        });


                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // 隐藏对话框,释放对话框所占的资源
                        arg0.dismiss();
                    }
                }).show();
    }


    //         对话框下的DatePicker示例 Example in dialog
    private void showDatePickerDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(CompleteInformationActivity.this).create();
        dialog.show();
        DatePicker picker = new DatePicker(CompleteInformationActivity.this);
        picker.setDate(1990, 1);
        picker.setMode(DPMode.SINGLE);
        picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(final String date) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] dates = date.split("-");
                        if (Integer.parseInt(dates[1]) < 10) dates[1] = "0" + dates[1];
                        if (Integer.parseInt(dates[2]) < 10) dates[2] = "0" + dates[2];
                        String new_date = dates[0] + "-" + dates[1] + "-" + dates[2];
                        mBirthday = new_date;
                        mTvBirthday.setText(new_date);
                    }
                });
                dialog.dismiss();
            }
        });
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setContentView(picker, params);
        dialog.getWindow().setGravity(Gravity.CENTER);
    }

    private void saveToSever() {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CHANGE_INFORMATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                ResponseBean responseBean = gson.fromJson(s, ResponseBean.class);
                if (responseBean != null) {
                    if (responseBean.getCode() == 0) {
                        GlobalData.username = (mName);
                        GlobalData.sex = (mSex_Int);
                        GlobalData.email = (mEmail);
                        GlobalData.birthday = (mTvBirthday.getText().toString().trim());
                        Toast.makeText(mContext, "修改成功", Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(CompleteInformationActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                } else {
                    Toast.makeText(mContext, "数据解析错误", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                mName = mEtName.getText().toString();
                HashMap<String, String> map = new HashMap<>();
                map.put("userid", GlobalData.getUserid());
                map.put("username", mName);
                map.put("sex", String.valueOf(mSex_Int));
                map.put("email", "");
                map.put("birthday", mTvBirthday.getText().toString().trim());
                map.put("identification", "");
                Log.i("Information Params", map.toString());
                return map;
            }
        };
        queue.add(stringRequest);


    }

    private DatePickerDialog.OnDateSetListener Datelistener = new DatePickerDialog.OnDateSetListener() {
        String year, month, day;

        @Override
        public void onDateSet(android.widget.DatePicker view, int myyear, int monthOfYear, int dayOfMonth) {
            //修改year、month、day的变量值，以便以后单击按钮时，DatePickerDialog上显示上一次修改后的值
            year = String.valueOf(myyear);
            month = String.valueOf(monthOfYear + 1);
            day = String.valueOf(dayOfMonth);
            //更新日期
            updateDate();
        }


        //当DatePickerDialog关闭时，更新日期显示
        private void updateDate() {
            if (Integer.parseInt(month) < 10) month = "0" + month;
            if (Integer.parseInt(day) < 10) day = "0" + day;
            String new_date = year + "-" + month + "-" + day;
            mBirthday = new_date;
            mTvBirthday.setText(new_date);
        }
    };

    private void showDatePickerDialog1() {
        DatePickerDialog dpd = new DatePickerDialog(CompleteInformationActivity.this, Datelistener, 1990, 0, 1);
        dpd.show();//显示DatePickerDialog组件
    }


}



