package com.bupt.heartarea.bean;

import java.util.List;

/**
 * Created by yuqing on 2017/3/23.
 */
public class Result {
    private int total;
    private List<DataBean> tngou;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DataBean> getTngou() {
        return tngou;
    }

    public void setTngou(List<DataBean> tngou) {
        this.tngou = tngou;
    }

    public static class DataBean {
        private int count;
//        private String description;
        private int fcount;
        private int id;
        private String img;
        private String keywords;
//        private String message;
        private int rcount;
        private long time;
        private String title;
        private int topclass;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

//        public String getDescription() {
//            return description;
//        }
//
//        public void setDescription(String description) {
//            this.description = description;
//        }

        public int getFcount() {
            return fcount;
        }

        public void setFcount(int fcount) {
            this.fcount = fcount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

//        public String getMessage() {
//            return message;
//        }
//
//        public void setMessage(String message) {
//            this.message = message;
//        }

        public int getRcount() {
            return rcount;
        }

        public void setRcount(int rcount) {
            this.rcount = rcount;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getTopclass() {
            return topclass;
        }

        public void setTopclass(int topclass) {
            this.topclass = topclass;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "count=" + count +
//                    ", description='" + description + '\'' +
                    ", fcount=" + fcount +
                    ", id=" + id +
                    ", img='" + img + '\'' +
                    ", keywords='" + keywords + '\'' +
//                    ", message='" + message + '\'' +
                    ", rcount=" + rcount +
                    ", time=" + time +
                    ", title='" + title + '\'' +
                    ", topclass=" + topclass +
                    '}';
        }
    }
}
