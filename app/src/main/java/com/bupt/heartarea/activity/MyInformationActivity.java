package com.bupt.heartarea.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.bupt.heartarea.utils.FileUtil;
import com.bupt.heartarea.utils.GlobalData;
import com.bupt.heartarea.utils.Tools;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static java.lang.String.valueOf;
import static okhttp3.MultipartBody.FORM;


public class MyInformationActivity extends Activity implements View.OnClickListener {


    ImageView mHeadPicture;
    ImageView mBackIcon;
    TextView mSave, mTvSex, mTvBirthday;
    EditText mEtName, mEtEmail;
    RelativeLayout mRL_birthday, mRL_sex;
    Activity mActivity;
    Context mContext;
    private int mSex_Int = 0;
    private String mBirthday;
    private String mName;
    private String mEmail;


    // 弹窗item
    private String[] items_photo = new String[]{"从相册中选择", "拍照"};
    private String[] items_sex = new String[]{"男", "女"};

    private File tempFile = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());

    // 请求码
    private static final int IMAGE_REQUEST_CODE = 0;// 打开相册请求码
    private static final int CAMERA_REQUEST_CODE = 1;// 拍照请求码
    private static final int RESULT_REQUEST_CODE = 2;// 结果请求码

    // 用户缓存
    private SharedPreferences preferences;
    private static final String URL_CHANGE_INFORMATION = GlobalData.URL_HEAD + ":8080/detect3/ChangeServlet";
    // 上传用户头像的url
    private static final String URL_IMAGE_HEAD = GlobalData.URL_HEAD + ":8080/detect3/HeadIconServlet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_change_information);
        mContext = this;
        mActivity = this;
        initView();


        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    private void initView() {
        mHeadPicture = (ImageView) findViewById(R.id.id_iv_headpicture_change_information);

        String filepath = Environment.getExternalStorageDirectory() + "/download/" + getPhotoFileName();
        Log.i("filepath", filepath);
        Bitmap photo = FileUtil.readImageFromLocal(filepath);
//        Bitmap photo = FileUtil.readImageFromLocal(mContext, getPhotoName());
        if (photo != null) {
            mHeadPicture.setImageBitmap(photo);
        } else {
            mHeadPicture.setImageResource(R.drawable.user_image);
        }

        mBackIcon = (ImageView) findViewById(R.id.id_iv_back);
        mEtName = (EditText) findViewById(R.id.id_et_name_change_information);
        mEtEmail = (EditText) findViewById(R.id.id_et_email_value);
        mSave = (TextView) findViewById(R.id.id_tv_save);
        mTvSex = (TextView) findViewById(R.id.id_tv_sex_value);
        mTvBirthday = (TextView) findViewById(R.id.id_tv_birthday_value);
        mRL_birthday = (RelativeLayout) findViewById(R.id.id_rl_birthday);
        mRL_sex = (RelativeLayout) findViewById(R.id.id_rl_sex);

        mEtName.setText(GlobalData.getUsername());
        mEtEmail.setText(GlobalData.getEmail());
        if (GlobalData.getSex() != -1) {
            mTvSex.setText(items_sex[GlobalData.getSex()]);

        }
        mTvBirthday.setText(GlobalData.getBirthday());

        mHeadPicture.setOnClickListener(this);
        mBackIcon.setOnClickListener(this);
        mEtName.setOnClickListener(this);
        mEtEmail.setOnClickListener(this);
        mSave.setOnClickListener(this);
        mRL_birthday.setOnClickListener(this);
        mRL_sex.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.id_iv_headpicture_change_information:
                showPhotoDialog();
                break;
            case R.id.id_iv_back:
                finish();
                break;
            case R.id.id_tv_save:
                mName = mEtName.getText().toString().trim();
                if (mName == null || mName.equals("")) {
                    Toast.makeText(MyInformationActivity.this, "请输入您的昵称", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    int length = mName.length();
                    if (length > 8 || length < 2) {
                        Toast.makeText(MyInformationActivity.this, "昵称长度应为2~8个字符", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

                if (mTvSex.getText().toString() == null || mTvSex.getText().toString().equals("")) {
                    Toast.makeText(MyInformationActivity.this, "请选择您的性别", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (mTvBirthday.getText().toString() == null || mTvBirthday.getText().toString().equals("")) {
                    Toast.makeText(MyInformationActivity.this, "请选择您的生日", Toast.LENGTH_SHORT).show();
                    break;
                }


                saveToSever();
                final HashMap<String, String> params = new HashMap<>();
                params.put("userid", GlobalData.userid + "");

                final File file = new File(mContext.getExternalFilesDir(""), getPhotoFileName());
                Log.i("File", mContext.getExternalFilesDir("") + "");
//                filesDir = mContext.getExternalFilesDir("");
//                /storage/emulated/0/Android/data/com.bupt.heartarea/files


                String oldpath = mContext.getExternalFilesDir("") + "/" + getPhotoFileName();
                String newpath = Environment.getExternalStorageDirectory() + "/download/" + getPhotoFileName();
                Log.i("oldpath", oldpath);
                Log.i("newpath", newpath);
                // 拷贝头像
                FileUtil.copyFile(oldpath, newpath);
                // 向服务器上传头像
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        saveFileToServer(URL_IMAGE_HEAD, params, file);

                    }
                }).start();

                break;
            case R.id.id_rl_birthday:
                showDatePickerDialog1();
                break;
            case R.id.id_rl_sex:
                showSexDialog();
                break;

        }
    }

    /**
     * 使用系统当前日期加以调整作为照片的名称
     *
     * @return
     */
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'img'_yyyyMMdd_HHmmss");


        return GlobalData.userid + ".jpg";
    }

    /**
     * 调用系统裁剪功能：
     *
     * @param fromFile
     */
    private void startPhotoZoom(Uri fromFile) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(fromFile, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    /**
     * 保存裁剪后的图片
     *
     * @param data
     */
    private void sentPicToNext(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            mHeadPicture.setImageBitmap(photo);
            FileUtil.saveImageToLocal(MyInformationActivity.this, photo, getPhotoFileName());
        }
    }


    /**
     * 弹出选项窗口方法
     */
    private void showPhotoDialog() {
        new AlertDialog.Builder(this)
                .setTitle("设置头像")
                .setItems(items_photo, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int which) {
                        switch (which) {
                            case 0:
                                // 从相册中选择
                                Intent intentFromImage = new Intent();
                                intentFromImage.setType("image/*");
                                intentFromImage
                                        .setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intentFromImage,
                                        IMAGE_REQUEST_CODE);
                                break;
                            case 1:
                                // 拍照
                                Intent intentFromCamera = new Intent(
                                        MediaStore.ACTION_IMAGE_CAPTURE);
                                if (Tools.hasSdcard()) {
                                    // 指定调用相机拍照后照片的储存路径
                                    intentFromCamera.putExtra(
                                            MediaStore.EXTRA_OUTPUT,
                                            Uri.fromFile(tempFile));

                                }
                                startActivityForResult(intentFromCamera,
                                        CAMERA_REQUEST_CODE);
                                break;
                            default:
                                break;
                        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case IMAGE_REQUEST_CODE:
                startPhotoZoom(data.getData());
                break;
            case CAMERA_REQUEST_CODE:
                startPhotoZoom(Uri.fromFile(tempFile));
                break;
            case RESULT_REQUEST_CODE:
                if (data != null) {
                    sentPicToNext(data);
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //         对话框下的DatePicker示例 Example in dialog
    private void showDatePickerDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(MyInformationActivity.this).create();
        dialog.show();
        DatePicker picker = new DatePicker(MyInformationActivity.this);
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

    /**
     * 向服务器上传用户的信息数据
     */
    private void saveToSever() {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CHANGE_INFORMATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();

                ResponseBean responseBean = gson.fromJson(s, ResponseBean.class);
                if (responseBean != null) {
                    if (responseBean.getCode() == 0) {
                        GlobalData.username = mName;
                        GlobalData.sex = (mSex_Int);
                        GlobalData.email = (mEmail);
                        GlobalData.birthday = (mTvBirthday.getText().toString().trim());
                        Toast.makeText(mContext, "修改成功", Toast.LENGTH_LONG).show();
                        finish();
                    }
                } else {
                    Toast.makeText(mContext, "请求服务器错误", Toast.LENGTH_LONG).show();
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
                mEmail = mEtEmail.getText().toString();
                HashMap<String, String> map = new HashMap<>();
                map.put("userid", GlobalData.getUserid());
                map.put("username", mName);
                map.put("sex", String.valueOf(mSex_Int));
                map.put("email", mEmail);
                map.put("birthday", mTvBirthday.getText().toString().trim());
                map.put("identification", "");
                Log.i("Information Params", map.toString());
                return map;
            }
        };
        queue.add(stringRequest);


    }

    /**
     * 向服务器上传用户的信息数据
     */
    private void saveToSeverTest() {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.108.224.93:8080/detect3/HeadIconServlet", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();

                ResponseBean responseBean = gson.fromJson(s, ResponseBean.class);
                if (responseBean != null) {
                    if (responseBean.getCode() == 0) {
                        GlobalData.username = mName;
                        GlobalData.sex = (mSex_Int);
                        GlobalData.email = (mEmail);
                        GlobalData.birthday = (mTvBirthday.getText().toString().trim());
                        Toast.makeText(mContext, "修改成功", Toast.LENGTH_LONG).show();
                        finish();
                    }
                } else {
                    Toast.makeText(mContext, "请求服务器错误", Toast.LENGTH_LONG).show();
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
                mEmail = mEtEmail.getText().toString();
                HashMap<String, String> map = new HashMap<>();
                map.put("userid", GlobalData.getUserid());
                map.put("username", mName);
                map.put("sex", String.valueOf(mSex_Int));
                map.put("email", mEmail);
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
        DatePickerDialog dpd = new DatePickerDialog(MyInformationActivity.this, Datelistener, 1990, 0, 1);
        dpd.show();//显示DatePickerDialog组件
    }


    /**
     * 向服务器上传用户头像
     *
     * @param url
     * @param map
     * @param file
     */
    protected void saveFileToServer(final String url, final Map<String, String> map, File file) {
        OkHttpClient client = new OkHttpClient();
        // form 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(FORM);
        if (file != null) {
            // MediaType.parse() 里面是上传的文件类型。
            RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
            String filename = file.getName();
            // 参数分别为， 请求key ，文件名称 ， RequestBody
            requestBody.addFormDataPart("icon", file.getName(), body);
        }
        if (map != null) {
            // map 里面是请求中所需要的 key 和 value
            for (Map.Entry entry : map.entrySet()) {
                requestBody.addFormDataPart(valueOf(entry.getKey()), valueOf(entry.getValue()));
            }
        }


        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url).post(requestBody.build())
                .tag(getApplicationContext())
                .build();
        // readTimeout("请求超时时间" , 时间单位);
        client.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("saveFileToServer", "onFailure()" + e);

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {

                if (response.isSuccessful()) {
                    Log.i("response", response + "");
                    String str = response.body().string();
                    Log.i("saveFileToServer", response.message() + " , body " + str);

                } else {
                    Log.i("saveFileToServer", response.message() + " error : body " + response.body().string());
                }
            }


        });

    }
}



