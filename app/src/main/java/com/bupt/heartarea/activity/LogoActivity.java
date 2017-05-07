package com.bupt.heartarea.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bupt.heartarea.bean.ResponseBean;
import com.bupt.heartarea.utils.GlobalData;
import com.google.gson.Gson;
import com.bupt.heartarea.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LogoActivity extends Activity {

    private String mUsername;
    private String mPassword;
    private boolean mIsLoginSuccess = true;

//    private static final String URL_LOGIN = "http://101.200.89.170:9000/capp/login/normal";
    private static final String URL_LOGIN = GlobalData.URL_HEAD+":8080/detect3/LoginServlet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            login();


                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    void login() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        //将JSONObject作为，将上一步得到的JSONObject对象作为参数传入
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("请求成功", "response -> " + response);

                        Gson gson = new Gson();
//                        JsonObject jsonObject=new JsonObject();

                        ResponseBean responseBean = gson.fromJson(response, ResponseBean.class);

                        // TODO: 2017/3/20 加一个GlobalData

//                        Toast.makeText(LogoActivity.this,responseBean.toString(),Toast.LENGTH_LONG).show();
                        if (responseBean.getCode() == 0) {
                            // 跳转到主界面
//                            startActivity(new Intent(LogoActivity.this, MainActivity.class));
                            String jsonString =  responseBean.getBody();
                            try {
                                JSONObject jsonObject = new JSONObject(jsonString);
                                if (jsonObject.has("username"))
                                    GlobalData.username=jsonObject.getString("username");
                                if (jsonObject.has("userid"))
                                    GlobalData.userid=(jsonObject.getString("userid"));
                                if (jsonObject.has("sex"))
                                    GlobalData.sex=(jsonObject.getInt("sex"));
                                if (jsonObject.has("birthday"))
                                    GlobalData.birthday=(jsonObject.getString("birthday"));
                                if (jsonObject.has("tel"))
                                    GlobalData.tel=(jsonObject.getString("tel"));
                                if (jsonObject.has("iscompletedinfo"))
                                    GlobalData.is_complete_info=(jsonObject.getInt("iscompletedinfo"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if (GlobalData.is_complete_info==1)
                            {
                                // 到主界面
                                Intent intent = new Intent(LogoActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else
                            {
                                Intent intent = new Intent(LogoActivity.this, CompleteInformationActivity.class);
                                startActivity(intent);
                            }
                            finish();
                        } else {
                            // 跳转到登录界面
                            startActivity(new Intent(LogoActivity.this, LoginActivity.class));
                            finish();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("请求失败", error.getMessage(), error);
                // 跳转到登录界面
                startActivity(new Intent(LogoActivity.this, LoginActivity.class));
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                // 从sp中读出账号密码
                load();
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                map.put("phone", mUsername);
                map.put("tel", mUsername);
//                map.put("password", "123456");
                map.put("password", mPassword);
                return map;
            }
        };
//        requestQueue.add(stringRequest);
        //添加唯一标识
//        objectRequest.setTag("lhdpost");
        //将请求添加到请求队列
        requestQueue.add(stringRequest);
    }

    void load() {
        SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        if (sp != null) {
            mUsername = sp.getString("username", ""); // 第二个参数为默认值
            mPassword = sp.getString("password", ""); // 第二个参数为默认值
        }


    }
}
