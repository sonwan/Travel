package com.tj.graduation.travel.model;

/**
 * Created by wangsong on 2019/3/9.
 */

public class CommentModel {

    private String userId;
    private String userName;
    private String comContent;
    private String comTime;

    public CommentModel(String userId, String userName, String comContent, String comTime) {
        this.userId = userId;
        this.userName = userName;
        this.comContent = comContent;
        this.comTime = comTime;
    }

    public CommentModel() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComContent() {
        return comContent;
    }

    public void setComContent(String comContent) {
        this.comContent = comContent;
    }

    public String getComTime() {
        return comTime;
    }

    public void setComTime(String comTime) {
        this.comTime = comTime;
    }
}
