package com.bupt.heartarea.utils;

import com.bupt.heartarea.bean.HistoryDataItemBean;

import java.util.ArrayList;
import java.util.List;

/*
 * 该类用于存储关于用户登录信息的全局变量
 * 如 userid，sid
 */
public class GlobalData {

    public static String select_date="";
    public static String userid="2";
    public static String sid;
    public static final String URL_HEAD = "http://47.92.80.155";
    public static String tel;
    // 昵称
    public static String username;
    // 性别
    public static int sex;
    // 生日
    public static String birthday;
    // 邮箱
    public static String email;

    public static enum MeasureType {HEART_RATE, BLOOD_OXYGEN, PRESSURE}

    public static final MeasureType[] measuretypes =

            {
                    MeasureType.HEART_RATE, MeasureType.BLOOD_OXYGEN, MeasureType.PRESSURE
            };

    public static MeasureType currenttype=MeasureType.HEART_RATE;

    public static List<HistoryDataItemBean> historyDataItemBeanList=new ArrayList<>();

    public static String getUserid() {
        return userid;
    }

    public static void setUserid(String userid) {
        GlobalData.userid = userid;
    }

    public static String getSid() {
        return sid;
    }

    public static void setSid(String sid) {
        GlobalData.sid = sid;
    }

    public static void clearUserInfo() {
        userid = null;
        sid = null;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        GlobalData.username = username;
    }

    public static int getSex() {
        return sex;
    }

    public static void setSex(int sex) {
        GlobalData.sex = sex;
    }

    public static String getBirthday() {
        return birthday;
    }

    public static void setBirthday(String birthday) {
        GlobalData.birthday = birthday;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        GlobalData.email = email;
    }

    public static String getTel() {
        return tel;
    }

    public static void setTel(String tel) {
        GlobalData.tel = tel;
    }


}
