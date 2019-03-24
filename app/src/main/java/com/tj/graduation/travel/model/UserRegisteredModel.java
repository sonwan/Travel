package com.tj.graduation.travel.model;

public class UserRegisteredModel {
    private String code;
    private String msg;
    private Data data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
        private String loginName;
        private String sex;
        private String passWord;
        private String userName;
        private String age;
        private String cusQuestion;
        private String cusAnswer;

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getPassWord() {
            return passWord;
        }

        public void setPassWord(String passWord) {
            this.passWord = passWord;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getCusQuestion() {
            return cusQuestion;
        }

        public void setCusQuestion(String cusQuestion) {
            this.cusQuestion = cusQuestion;
        }

        public String getCusAnswer() {
            return cusAnswer;
        }

        public void setCusAnswer(String cusAnswer) {
            this.cusAnswer = cusAnswer;
        }
    }
}
