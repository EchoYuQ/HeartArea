package com.bupt.heartarea.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.bupt.heartarea.utils.GlobalData;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

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
    private static final String URL_LOGIN = GlobalData.URL_HEAD+":8080/detect3/LoginServlet";
//    private static final String URL_LOGIN = "http://10.108.224.77:8080/detect3/LoginServlet";

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
//                    if (!NetworkUtil.isNetworkAvailable(LoginActivity.this)) {
//                        Toast.makeText(LoginActivity.this, "访问服务器失败，请检查网络",
//                                Toast.LENGTH_LONG).show();
//                    } else {
                    // 用MyJsonParser类中的parserForLoginNormal()方法解析JSON字符串
                    // 返回实体类LoginInfo
                    // parserForLoginNormal()方法详情请见MyJsonParser.class
//                        str_json = invokeLoginAPI();// 调用LoginAPI，服务器返回JSON字符串
//                        loginInfo = new MyJsonParser(str_json)
//                                .parserForLoginNormal();
//                        switch (loginInfo.getCode()) {
//                            case 0:
//                                Toast.makeText(LoginActivity.this, "登录成功",
//                                        Toast.LENGTH_LONG).show();
////                                System.out.println(loginInfo.toString());
////                                GlobalData.setUserid(loginInfo.getId());
////                                GlobalData.setSid(loginInfo.getSid());
////
////                                // 创建一个Intent实例，该Intent主要实现从该Activity到ChooseFunctionActivity的跳转
////                                Intent it_toChoFuncActivity = new Intent(
////                                        LoginActivity.this,
////                                        ChooseFunctionActivity.class);
////                                // startActivity方法激活该Intent实现跳转
////                                startActivity(it_toChoFuncActivity);
//                                break;
//                            case 20000:
//                                Toast.makeText(LoginActivity.this, "登录被锁定, 失败次数过多",
//                                        Toast.LENGTH_LONG).show();
//                                break;
//                            case 20001:
//                                Toast.makeText(LoginActivity.this, "用户不存在",
//                                        Toast.LENGTH_LONG).show();
//                                break;
//                            case 20005:
//                                Toast.makeText(LoginActivity.this, "密码验证错误",
//                                        Toast.LENGTH_LONG).show();
//                                break;
//                            case 20016:
//                                Toast.makeText(LoginActivity.this, "该账户已被禁用",
//                                        Toast.LENGTH_LONG).show();
//                                break;
//                            default:
//                                Toast.makeText(LoginActivity.this, "登录失败" + loginInfo.getCode(),
//                                        Toast.LENGTH_LONG).show();
//                                break;
//                        }

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

                        if (responseBean.getCode() == 0) {
                            // 保存账号密码
                            saveAccountAndPwd();
//                            JSONObject jsonObject = (JSONObject) responseBean.getBody();
//                            try {
//                                if (jsonObject.has("userid"))
//                                    GlobalData.setUserid(jsonObject.getString("userid"));
//                                if (jsonObject.has("sex"))
//                                    GlobalData.setSex(jsonObject.getInt("sex"));
//                                if (jsonObject.has("birthday"))
//                                    GlobalData.setBirthday(jsonObject.getString("birthday"));
//                                if (jsonObject.has("tel"))
//                                    GlobalData.setTel(jsonObject.getString("tel"));
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }

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
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            // 到主界面
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
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
                map.put("tel",str_name);
                map.put("password",str_pwd);

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