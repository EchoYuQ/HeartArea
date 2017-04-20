/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.bupt.heartarea.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * 网络工具类
 *
 * @author rendayun
 */
public final class NetworkUtil2 {

    /**
     * 构造函数
     */
    private NetworkUtil2() {

    }

    /**
     * 网络是否可用。(
     *
     * @param context context
     *
     * @return 连接并可用返回 true
     */
    public static boolean isNetworkConnected(Context context) {
        NetworkInfo networkInfo = getActiveNetworkInfo(context);
        // return networkInfo != null && networkInfo.isConnected();
        boolean flag = networkInfo != null && networkInfo.isAvailable();
        return flag;
    }

    /**
     * wifi网络是否可用
     *
     * @param context context
     *
     * @return wifi连接并可用返回 true
     */
    public static boolean isWifiNetworkConnected(Context context) {
        NetworkInfo networkInfo = getActiveNetworkInfo(context);
        // return networkInfo != null && networkInfo.isConnected();
        boolean flag =
                networkInfo != null && networkInfo.isAvailable()
                        && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
        return flag;

    }

    /**
     * 获取活动的连接。
     *
     * @param context context
     *
     * @return 当前连接
     */
    public static NetworkInfo getActiveNetworkInfo(Context context) {
        if (context == null) {
            return null;
        }
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return null;
        }
        return connectivity.getActiveNetworkInfo();
    }

    /**
     * 手机网络是否可用
     *
     * @param context context
     *
     * @return 手机网络连接并可用返回 true
     */
    public static boolean isMobileNetworkConnected(Context context) {
        NetworkInfo networkInfo = getActiveNetworkInfo(context);
        boolean flag =
                networkInfo != null && networkInfo.isAvailable()
                        && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        return flag;
    }

    /**
     * 获取活动连接的手机网络类型
     *
     * @param context 上下文
     *
     * @return 当前活动连接的手机网络类型
     */
    public static int getActiveNetworkType(Context context) {
        NetworkInfo networkInfo = getActiveNetworkInfo(context);

        boolean flag =
                networkInfo != null && networkInfo.isAvailable()
                        && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        if (flag) {
            String subTypeName = networkInfo.getSubtypeName();
            int subType = networkInfo.getSubtype();
            switch (subType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return Constants.NETWORKTYPE_2G;
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return Constants.NETWORKTYPE_3G;
                case TelephonyManager.NETWORK_TYPE_LTE:
                    return Constants.NETWORKTYPE_4G;
                default:
                    // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                    if (subTypeName.equalsIgnoreCase("TD-SCDMA") || subTypeName.equalsIgnoreCase("WCDMA")
                            || subTypeName.equalsIgnoreCase("CDMA2000")) {
                        return Constants.NETWORKTYPE_3G;
                    }
                    break;
            }
        }
        return Integer.MIN_VALUE;

    }

}
