package com.tj.graduation.travel.model;

import java.util.List;

public class PurchaseModel {

    private int code;
    private Data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{
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

        public static class Item{
            private String spotId;
            private String spotName;
            private String ticketBuyTime;
            private String ticketFee;
            private String ticketId;
            private String ticketNum;

            public String getSpotId() {
                return spotId;
            }

            public void setSpotId(String spotId) {
                this.spotId = spotId;
            }

            public String getSpotName() {
                return spotName;
            }

            public void setSpotName(String spotName) {
                this.spotName = spotName;
            }

            public String getTicketBuyTime() {
                return ticketBuyTime;
            }

            public void setTicketBuyTime(String ticketBuyTime) {
                this.ticketBuyTime = ticketBuyTime;
            }

            public String getTicketFee() {
                return ticketFee;
            }

            public void setTicketFee(String ticketFee) {
                this.ticketFee = ticketFee;
            }

            public String getTicketId() {
                return ticketId;
            }

            public void setTicketId(String ticketId) {
                this.ticketId = ticketId;
            }

            public String getTicketNum() {
                return ticketNum;
            }

            public void setTicketNum(String ticketNum) {
                this.ticketNum = ticketNum;
            }
        }
    }
}
