package com.bupt.heartarea.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * 保存数据到手机的工具类
 * Created by yuqing on 2016/12/23.
 */
public class FileUtil {

    /**
     * 保存数据到SD卡中
     * @param datas
     * @param filename
     */
    public static void saveData2Sdcard(String datas, String filename) {
        // 创建文件对象，由于不同手机SDcard目录不同，通过Environment.getExternalStorageDirectory()获得路径。
        File file = new File(Environment.getExternalStorageDirectory(), filename+".txt");
        Log.i("File path", Environment.getExternalStorageDirectory().getAbsolutePath() + "");
        if (!file.exists()) {
            try {
                boolean result=file.createNewFile();
                Log.i("create file",result+"");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(datas);
            bw.flush();
            bw.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存头像到本地
     * @param bitmap
     */
    public static void saveImageToLocal(Context context,Bitmap bitmap, String filename) {
        File filesDir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//判断sd卡是否挂载
            // 路径1：storage/sdcard/Android/data/包名/files
            filesDir = context.getExternalFilesDir("");
            Log.i("SD卡存储路径", filesDir + "");
        } else {//手机内部存储
            // 路径：data/data/包名/files
            filesDir = context.getFilesDir();
            Log.i("手机内部存储路径", filesDir + "");
        }
        FileOutputStream fos = null;
        try {
            File file = new File(filesDir, filename);
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 从本地读取头像图片
     * @return
     */
    public static Bitmap readImageFromLocal(Context context,String filename) {
        File filesDir;
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){//判断sd卡是否挂载
            // sd卡路径1：storage/sdcard/Android/data/包名/files
            filesDir = context.getExternalFilesDir("");
        }else{
            // 手机内部存储路径：data/data/包名/files
            filesDir = context.getFilesDir();
        }
        File file = new File(filesDir,filename);
        if(file.exists()){
            //存储--->内存
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            return bitmap;
        }
        return null;
    }
}
