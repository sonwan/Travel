package com.tj.graduation.travel.model;

import java.util.List;

/**
 * Created by wangsong on 2019/5/1.
 */

public class GuideListModel {

    private int code;
    private String msg;
    private Data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private int allnum;
        private List<GuideModel> guidelist;

        public int getAllnum() {
            return allnum;
        }

        public void setAllnum(int allnum) {
            this.allnum = allnum;
        }

        public List<GuideModel> getGuidelist() {
            return guidelist;
        }

        public void setGuidelist(List<GuideModel> guidelist) {
            this.guidelist = guidelist;
        }
    }

}
