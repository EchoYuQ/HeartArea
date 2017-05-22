package com.bupt.heartarea.utils;

import java.util.List;

public class CalBloodOxygen {


    /**
     * 计算血氧值
     * @param RED 获取的R通道值数组
     * @param IR  获取的B通道值数组
     * @return
     */
    public static double SpO2(double[] RED, double[] IR) {

        int fs = 20;
        int N = 128;
        int L = (int) (IR.length / (2 * fs) - 2);
        System.out.println(IR.length);
        System.out.println(RED.length);
        double rer[] = new double[N];
        double imr[] = new double[N];
        double imir[] = new double[N];
        double reir[] = new double[N];
        double R[] = new double[7];
        double I[] = new double[7];
        double SpO2_time[] = new double[L];
        double SpO2 = 0;
        for (int z = 0; z < L; z++) {
            //fft在另外一个class里
            FFT fft = new FFT(N);
            //计算红光的AC&DC
            for (int i = 0; i < N; i++) {
                rer[i] = RED[i + z * fs];
                imr[i] = 0;
            }
            fft.fft(rer, imr);

            for (int j = 6; j < 13; j++) {
                R[j - 6] = Math.sqrt(Math.pow(rer[j], 2) + Math.pow(imr[j], 2));
            }
            int local_max_i = 1;
            double local_max = R[0];
            for (int i = 2; i < 6; i++) {
                if (local_max < R[i]) {
                    local_max_i = i;
                    local_max = R[i];
                }
            }
            int pk_R_i = 6 - 1 + local_max_i;

            double R_R = Math.abs(RED[pk_R_i] / Math.abs(RED[0]));
            //计算所有？IR光的
            for (int i = 0; i < N; i++) {
                reir[i] = IR[i + z * fs];
                imir[i] = 0;
            }
            fft.fft(reir, imir);

            for (int j = 6; j < 13; j++) {
                I[j - 6] = Math.sqrt(Math.pow(reir[j], 2) + Math.pow(imir[j], 2));
            }
            int local_max_ir = 1;
            double local_maxir = I[0];
            for (int i = 2; i < 6; i++) {
                if (local_maxir < I[i]) {
                    local_max_ir = i;
                    local_maxir = I[i];
                }
            }
            int pk_IR_i = 6 - 1 + local_max_ir;
            double R_IR = Math.abs(IR[pk_IR_i] / Math.abs(IR[0]));

            double spo = R_R / R_IR;
            SpO2_time[z] = (double) (104 - 5 * spo);

            SpO2 = SpO2 + SpO2_time[z];
        }
        double blood_oxygen = SpO2 / L;
        return blood_oxygen;
    }

    //  sp02 List类型参数的重载方法
    public static double SpO2(List<Double> RED, List<Double> IR) {

        if (RED == null || IR == null || RED.size() == 0 || IR.size() == 0) {
            return 0.0;
        }
        int m = RED.size();
        int n = IR.size();
        double[] red_array = new double[m];
        double[] blue_array = new double[n];

        for (int i = 0; i < m; i++) {
            red_array[i] = RED.get(i);
        }

        for (int i = 0; i < n; i++) {
            blue_array[i]=IR.get(i);
        }
        return SpO2(red_array,blue_array);
    }

}
