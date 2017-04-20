package com.bupt.heartarea.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuqing on 2017/3/24.
 */
public class MeasureData implements Serializable {
    private int heart_rate=-1;
    private int blood_oxygen=-1;
    private List<Integer> rr_interval=new ArrayList<>();
    private List<Float> data= new ArrayList<>();

    public int getHeart_rate() {
        return heart_rate;
    }

    public void setHeart_rate(int heart_rate) {
        this.heart_rate = heart_rate;
    }

    public int getBlood_oxygen() {
        return blood_oxygen;
    }

    public void setBlood_oxygen(int blood_oxygen) {
        this.blood_oxygen = blood_oxygen;
    }

    public List<Integer> getRr_interval() {
        return rr_interval;
    }

    public void setRr_interval(List<Integer> rr_interval) {
        this.rr_interval = rr_interval;
    }

    public List<Float> getData() {
        return data;
    }

    public void setData(List<Float> data) {
        this.data = data;
    }
}
