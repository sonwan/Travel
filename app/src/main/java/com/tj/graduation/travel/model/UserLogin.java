package com.tj.graduation.travel.model;

public class UserLogin {
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

    public static class Data{
        private String loginname;
        private String sex;
        private String id;
        private String username;
        private String age;
        private String accountfee;

        public String getAccountfee() {
            return accountfee;
        }

        public void setAccountfee(String accountfee) {
            this.accountfee = accountfee;
        }

        public String getLoginname() {
            return loginname;
        }

        public void setLoginname(String loginname) {
            this.loginname = loginname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }
}
