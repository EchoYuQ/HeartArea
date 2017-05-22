package com.bupt.heartarea.activity;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
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
import com.bupt.heartarea.utils.DownloadUtil;
import com.bupt.heartarea.utils.GlobalData;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity {

    // 对控件的声明
    private EditText et_login_username;
    private EditText et_login_password;
    private Button btn_login_yes;
    private Button btn_login_register;
    private Button btn_login_resetpwd;
    private String str_name;
    private String str_pwd;
    private String str_json;

    // 对端口号和URI的定义
    private static final String URL_LOGIN = GlobalData.URL_HEAD + ":8080/detect3/LoginServlet";

    private static final String URL_HEAD_IMAGE = "http://img04.tooopen.com/images/20130701/tooopen_10055061.jpg";
    // 用户头像的url
    private String mUrlHeadImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置该Activity界面为无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置该Activity界面的布局文件为activity_login.xml
        setContentView(com.bupt.heartarea.R.layout.activity_login);


        et_login_username = (EditText) findViewById(com.bupt.heartarea.R.id.et_login_username);
        et_login_password = (EditText) findViewById(com.bupt.heartarea.R.id.et_login_password);

        // 在布局文件中捕获Id为btn_login_yes的Button控件，并创建Button控件实例
        btn_login_yes = (Button) findViewById(com.bupt.heartarea.R.id.btn_login_yes);
        // 为btn_login_yes（Button控件）绑定单击监听器
        btn_login_yes.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                str_name = et_login_username.getText().toString().trim();
                str_pwd = et_login_password.getText().toString().trim();
                // 验证手机号格式，要求首字母为1,11位
                if (str_name.equals("") || str_name.length() != 11 || str_name.charAt(0) != '1') {
                    Toast.makeText(LoginActivity.this, "手机号格式不正确，请重新输入",
                            Toast.LENGTH_LONG).show();
                } else {
                    login();

                }


            }
        });
        btn_login_register = (Button) findViewById(com.bupt.heartarea.R.id.btn_login_register);
        // 为btn_login_register（Button控件）绑定单击监听器
        btn_login_register.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 单击该Button时就会创建一个Intent实例，该Intent主要实现从该Activity到RegisterActivity的跳转
                Intent it_toRegisterActivity = new Intent(LoginActivity.this,
                        RegisterActivity.class);
                // startActivity方法激活该Intent实现跳转
                startActivity(it_toRegisterActivity);
            }
        });
        btn_login_resetpwd = (Button) findViewById(com.bupt.heartarea.R.id.btn_login_resetpwd);
        // 为btn_login_resetpwd（Button控件）绑定单击监听器
        btn_login_resetpwd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//                 单击该Button时就会创建一个Intent实例，该Intent主要实现从该Activity到ResetPwdActivity的跳转
                Intent it_toResetPwdActivity = new Intent(LoginActivity.this,
                        ResetPwdActivity.class);
                // startActivity方法激活该Intent实现跳转
                startActivity(it_toResetPwdActivity);
            }
        });

    }


    void login() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        //将JSONObject作为，将上一步得到的JSONObject对象作为参数传入
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("请求成功", "response -> " + response);
                        Log.i("LOGIN response", response);
                        Gson gson = new Gson();

                        ResponseBean responseBean = gson.fromJson(response, ResponseBean.class);
                        Toast.makeText(LoginActivity.this, responseBean.getMsg(), Toast.LENGTH_LONG).show();

                        // TODO: 2017/3/20 加一个GlobalData

                        if (responseBean != null) {
                            if (responseBean.getCode() == 0) {
                                // 保存账号密码
                                saveAccountAndPwd();

                                String jsonString = responseBean.getBody();
                                try {
                                    JSONObject jsonObject = new JSONObject(jsonString);
                                    if (jsonObject.has("username"))
                                        GlobalData.username = jsonObject.getString("username");
                                    if (jsonObject.has("userid"))
                                        GlobalData.userid = (jsonObject.getString("userid"));
                                    if (jsonObject.has("sex"))
                                        GlobalData.sex = (jsonObject.getInt("sex"));
                                    if (jsonObject.has("birthday"))
                                        GlobalData.birthday = (jsonObject.getString("birthday"));
                                    if (jsonObject.has("tel"))
                                        GlobalData.tel = (jsonObject.getString("tel"));
                                    if (jsonObject.has("iscompletedinfo"))
                                        GlobalData.is_complete_info = (jsonObject.getInt("iscompletedinfo"));
                                    if (jsonObject.has("icon")) {
                                        mUrlHeadImage = (jsonObject.getString("icon"));
                                        if (mUrlHeadImage != null && !mUrlHeadImage.trim().equals("")) {
                                            final String url_headimage = GlobalData.URL_HEAD + mUrlHeadImage;
                                            final String filename = GlobalData.userid + ".jpg";
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    DownloadUtil.get().downloadFile(url_headimage, filename, "download", new DownloadUtil.OnDownloadListener() {
                                                        @Override
                                                        public void onDownloadSuccess() {
                                                            Log.i("下载图片完成", "下载图片完成");

                                                        }

                                                        @Override
                                                        public void onDownloading(int progress) {
//                                        progressBar.setProgress(progress);
                                                        }

                                                        @Override
                                                        public void onDownloadFailed() {
                                                            Log.i("下载图片失败", "下载图片失败");


                                                        }
                                                    });
                                                }
                                            }).start();

                                        }

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                if (GlobalData.is_complete_info == 1) {
                                    // 到主界面
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, CompleteInformationActivity.class);
                                    startActivity(intent);
                                }
                                finish();


                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "连接服务器失败，请检查网络", Toast.LENGTH_SHORT).show();

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("请求失败", error.getMessage(), error);
                Toast.makeText(LoginActivity.this, "连接服务器失败，请检查网络", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                map.put("phone", str_name);
                map.put("tel", str_name);
                map.put("password", str_pwd);

//                map.put("phone", str_name);
//                map.put("password", str_pwd);
                return map;
            }
        };
//        requestQueue.add(stringRequest);
        //添加唯一标识
//        objectRequest.setTag("lhdpost");
        //将请求添加到请求队列
        requestQueue.add(stringRequest);
    }

    private void saveAccountAndPwd() {
        SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        GlobalData.setTel(str_name);

        editor.putString("username", str_name);
        editor.putString("password", str_pwd);
        // editor.putBoolean()、editor.putInt()、editor.putFloat()……
        editor.apply();
    }


}



