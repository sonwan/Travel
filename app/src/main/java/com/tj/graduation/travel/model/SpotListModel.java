package com.tj.graduation.travel.model;

import java.util.List;

/**
 * Created by wangsong on 2019/3/3.
 */

public class SpotListModel {

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
        private List<Item> list;

        public int getAllnum() {
            return allnum;
        }

        public void setAllnum(int allnum) {
            this.allnum = allnum;
        }

        public List<Item> getList() {
            return list;
        }

        public void setList(List<Item> list) {
            this.list = list;
        }

        public static class Item {

            private String address;
            private String descInfo;
            private int id;
            private String name;
            private String shortPicUrl;
            private int sortorder;
            private String state;
            private String ticketPrice;
            private String trafficInfo;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getDescInfo() {
                return descInfo;
            }

            public void setDescInfo(String descInfo) {
                this.descInfo = descInfo;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getShortPicUrl() {
                return shortPicUrl;
            }

            public void setShortPicUrl(String shortPicUrl) {
                this.shortPicUrl = shortPicUrl;
            }

            public int getSortorder() {
                return sortorder;
            }

            public void setSortorder(int sortorder) {
                this.sortorder = sortorder;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getTicketPrice() {
                return ticketPrice;
            }

            public void setTicketPrice(String ticketPrice) {
                this.ticketPrice = ticketPrice;
            }

            public String getTrafficInfo() {
                return trafficInfo;
            }

            public void setTrafficInfo(String trafficInfo) {
                this.trafficInfo = trafficInfo;
            }
        }

    }
}
