package com.bupt.heartarea.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bupt.heartarea.bean.ResponseBean;
import com.bupt.heartarea.utils.GlobalData;
import com.bupt.heartarea.utils.NetworkUtil2;
import com.bupt.heartarea.utils.TimeCount;
import com.google.gson.Gson;
import com.bupt.heartarea.R;
import com.bupt.heartarea.utils.NetworkUtil;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends Activity {
    // 对控件的声明
    private EditText et_register_tel;
    private EditText et_register_vercode;
    private EditText et_register_pwd;
    private Button btn_register_getvercode;
    private Button btn_register_yes;
    private String str_phone;
    private String str_vercode;
    private String str_pwd;


    private TimeCount mTimeCount;


    private static final String URL_REGISTER = GlobalData.URL_HEAD+":8080/detect3/RegServlet";
    private static final String URL_REGISTER_VERCODE = GlobalData.URL_HEAD+":8080/detect3/GetVercodeServlet";

    private String mTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_register_tel = (EditText) findViewById(R.id.et_register_tel);
        et_register_vercode = (EditText) findViewById(R.id.et_register_vercode);
        et_register_pwd = (EditText) findViewById(R.id.et_register_pwd);

        // 通过ID匹配发送验证码按钮
        btn_register_getvercode = (Button) findViewById(R.id.btn_register_getvercode);
        // 创建TimeCount实例，并设置倒计时时间60秒、计时间隔1秒，绑定btn_register_getvercode
        mTimeCount = new TimeCount(60 * 1000, 1000, btn_register_getvercode);
        // 为btn_register_getvercode设置单击监听器
        btn_register_getvercode.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 调用注册发送验证码方法
                mTel = et_register_tel.getText().toString().trim();
                if (mTel.equals("")) {
                    Toast.makeText(RegisterActivity.this, "请输入手机号",
                            Toast.LENGTH_LONG).show();
                } else {
                    if (!NetworkUtil2.isNetworkConnected(getApplicationContext())) {
                        Toast.makeText(RegisterActivity.this, "访问服务器失败，请检查网络",
                                Toast.LENGTH_LONG).show();
                    } else {

                        getVercodeToServer();

                    }
                }

            }
        });

        btn_register_yes = (Button) findViewById(R.id.btn_register_yes);
        btn_register_yes.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                str_phone = et_register_tel.getText().toString().trim();
                str_vercode = et_register_vercode.getText().toString().trim();
                str_pwd = et_register_pwd.getText().toString().trim();
                // 如果手机号、验证码、密码不为空时执行
                if (!str_phone.equals("") && !str_vercode.equals("")
                        && !str_pwd.equals("")) {

                    // NetworkUtil.isNetworkAvailable(RegisterActivity.this)来判断当前网络环境是否可用，可用返回true
                    if (!NetworkUtil.isNetworkAvailable(RegisterActivity.this)) {
                        Toast.makeText(RegisterActivity.this, "访问服务器失败，请检查网络",
                                Toast.LENGTH_LONG).show();
                    } else {

                        registerToServer();

                    }
                } else {
                    if (str_phone.equals("")) {
                        Toast.makeText(RegisterActivity.this, "请输入手机号",
                                Toast.LENGTH_LONG).show();
                    } else {
                        if (str_vercode.equals("")) {
                            Toast.makeText(RegisterActivity.this, "请输入验证码",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            if (str_pwd.equals("")) {
                                Toast.makeText(RegisterActivity.this, "请输入密码",
                                        Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                }
            }
        });

    }


    /**
     * 向服务器注册用户
     */
    void registerToServer() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        //将JSONObject作为，将上一步得到的JSONObject对象作为参数传入
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("请求成功", "response -> " + response);
//                        Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_LONG).show();

                        Gson gson = new Gson();

                        ResponseBean responseBean = gson.fromJson(response, ResponseBean.class);

                        // 判断解析后的code状态码
                        switch (responseBean.getCode()) {
                            case 0:
                                Toast.makeText(RegisterActivity.this, "注册成功",
                                        Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case 20000:
                                Toast.makeText(RegisterActivity.this, "用户已存在",
                                        Toast.LENGTH_LONG).show();
                                break;
                            case 20005:
                                Toast.makeText(RegisterActivity.this, "密码无效",
                                        Toast.LENGTH_LONG).show();
                                break;
                            case 20007:
                                Toast.makeText(RegisterActivity.this, "手机号无效",
                                        Toast.LENGTH_LONG).show();
                                break;
                            case 20010:
                                Toast.makeText(RegisterActivity.this, "验证码错误",
                                        Toast.LENGTH_LONG).show();
                                break;
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("请求失败", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();

                map.put("tel", str_phone);
                map.put("password", str_pwd);
                map.put("vercode", str_vercode);
                Log.i("REGISTER params", map.toString());
                return map;
            }
        };
        //添加唯一标识
//        objectRequest.setTag("lhdpost");
        //将请求添加到请求队列
        requestQueue.add(stringRequest);
    }

    /**
     * 从服务器拿注册验证码
     */
    void getVercodeToServer() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        //将JSONObject作为，将上一步得到的JSONObject对象作为参数传入
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER_VERCODE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("请求成功", "response -> " + response);

                        Gson gson = new Gson();

                        ResponseBean responseBean = gson.fromJson(response, ResponseBean.class);
                        // 倒计时开始
                        mTimeCount.start();
                        switch (responseBean.getCode()) {
                            case 0:
                                Toast.makeText(RegisterActivity.this, "发送成功",
                                        Toast.LENGTH_LONG).show();
                                mTimeCount.start();// 开始计时
                                break;
                            
                            default:
                                Toast.makeText(RegisterActivity.this,
                                        responseBean.getMsg(), Toast.LENGTH_LONG)
                                        .show();
                                break;
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("请求失败", error.getMessage(), error);
                Toast.makeText(RegisterActivity.this, "请求服务器失败，请检查网络", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                map.put("tel", mTel);
                return map;
            }
        };
        //添加唯一标识
//        objectRequest.setTag("lhdpost");
        //将请求添加到请求队列
        requestQueue.add(stringRequest);
    }

}