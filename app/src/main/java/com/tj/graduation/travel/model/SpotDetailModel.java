package com.tj.graduation.travel.model;

import java.util.List;

/**
 * Created by wangsong on 2019/3/5.
 */

public class SpotDetailModel {

    private String msg;
    private String code;
    private Data data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {

        private Detail detail;
        private List<picItem> piclist;

        public Detail getDetail() {
            return detail;
        }

        public void setDetail(Detail detail) {
            this.detail = detail;
        }

        public List<picItem> getPiclist() {
            return piclist;
        }

        public void setPiclist(List<picItem> piclist) {
            this.piclist = piclist;
        }

        public static class picItem {
            private String descInfo;
            private String picUrl;
            private int sortorder;
            private int id;

            public String getDescInfo() {
                return descInfo;
            }

            public void setDescInfo(String descInfo) {
                this.descInfo = descInfo;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public int getSortorder() {
                return sortorder;
            }

            public void setSortorder(int sortorder) {
                this.sortorder = sortorder;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }

        public static class Detail {
            private String descInfo;
            private String address;
            private String ticketPrice;
            private String tipInfo;
            private String telephone;
            private String shortPicUrl;
            private String trafficInfo;
            private String createTime;
            private String name;
            private int sortorder;
            private int id;
            private int state;
            private String openTime;

            public String getDescInfo() {
                return descInfo;
            }

            public void setDescInfo(String descInfo) {
                this.descInfo = descInfo;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getTicketPrice() {
                return ticketPrice;
            }

            public void setTicketPrice(String ticketPrice) {
                this.ticketPrice = ticketPrice;
            }

            public String getTipInfo() {
                return tipInfo;
            }

            public void setTipInfo(String tipInfo) {
                this.tipInfo = tipInfo;
            }

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public String getShortPicUrl() {
                return shortPicUrl;
            }

            public void setShortPicUrl(String shortPicUrl) {
                this.shortPicUrl = shortPicUrl;
            }

            public String getTrafficInfo() {
                return trafficInfo;
            }

            public void setTrafficInfo(String trafficInfo) {
                this.trafficInfo = trafficInfo;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getSortorder() {
                return sortorder;
            }

            public void setSortorder(int sortorder) {
                this.sortorder = sortorder;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getOpenTime() {
                return openTime;
            }

            public void setOpenTime(String openTime) {
                this.openTime = openTime;
            }
        }

    }

}
