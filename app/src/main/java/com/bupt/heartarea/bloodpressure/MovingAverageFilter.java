package com.bupt.heartarea.bloodpressure;

public class MovingAverageFilter {
	private static final int WINDOWS = 1;
    private double[] mTemp = null; // ֻ������ʱ����ʼ��,������¼���ò�����ֵ����ĵ�
    private double[] mBufout = null;
    private int mWindowSize = WINDOWS;

    public MovingAverageFilter(int size) {
        mWindowSize = size;
    }

    // ��ֵ�˲�����������һ��buf���飬����һ��buf1���飬�����±�һ�������Զ��岻ͬ���±�buf���±�Ϊi��buf1���±�Ϊbuf1Sub.�����ڴ����治������
    // ʹ�þ�ֵһ����win���Ǵ�����˼��,���Ҫ������ѧ��T-T
    //ͬ����ʱ��winArray�����±�ΪwinArraySub
    public double[] movingAverageFilter(double[] buf) {
        int bufoutSub = 0;
        int winArraySub = 0;
        double[] winArray = new double[mWindowSize];

        if (mTemp == null) {
            mBufout = new double[buf.length - mWindowSize + 1];
            for (int i = 0; i < buf.length; i++) {
                if ((i + mWindowSize) > buf.length) {
                    break;
                } else {
                    for (int j = i; j < (mWindowSize + i); j++) {
                        winArray[winArraySub] = buf[j];
                        winArraySub = winArraySub + 1;
                    }

                    mBufout[bufoutSub] = mean(winArray);
                    bufoutSub = bufoutSub + 1;
                    winArraySub = 0;
                }
            }
            mTemp = new double[mWindowSize - 1];
            System.arraycopy(buf, buf.length - mWindowSize + 1, mTemp, 0,
                    mWindowSize - 1);
            return mBufout;
        } else {
           double[] bufadd = new double[buf.length + mTemp.length];
            mBufout = new double[bufadd.length - mWindowSize + 1];
            System.arraycopy(mTemp, 0, bufadd, 0, mTemp.length);
            System.arraycopy(buf, 0, bufadd, mTemp.length, buf.length); // ��temp��bufƴ�ӵ�һ��
            for (int i = 0; i < bufadd.length; i++) {
                if ((i + mWindowSize) > bufadd.length)
                    break;
                else {
                    for (int j = i; j < (mWindowSize + i); j++) {
                        winArray[winArraySub] = bufadd[j];
                        winArraySub = winArraySub + 1;
                    }
                    mBufout[bufoutSub] = mean(winArray);
                    bufoutSub = bufoutSub + 1;
                    winArraySub = 0;
                    System.arraycopy(bufadd, bufadd.length - mWindowSize + 1,
                            mTemp, 0, mWindowSize - 1);
                }
            }
            return mBufout;
        }
    }

    public double mean(double[] winArray) {
        long sum = 0;
        for (int i = 0; i < winArray.length; i++) {
            sum += winArray[i];
        }
        return (double) (sum / winArray.length);
    }
}
