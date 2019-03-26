package com.tj.graduation.travel.model;

import java.util.List;

public class CollectionModel {
    private int code;
    private List<Model> data;

    public List<Model> getData() {
        return data;
    }

    public void setData(List<Model> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class Model{
        private String createTime;
        private String createUserId;
        private String createUserName;
        private String guideTitle;
        private String id;
        private String ifCurUserLike;
        private String linkId;
        private String linkName;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(String createUserId) {
            this.createUserId = createUserId;
        }

        public String getCreateUserName() {
            return createUserName;
        }

        public void setCreateUserName(String createUserName) {
            this.createUserName = createUserName;
        }

        public String getGuideTitle() {
            return guideTitle;
        }

        public void setGuideTitle(String guideTitle) {
            this.guideTitle = guideTitle;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIfCurUserLike() {
            return ifCurUserLike;
        }

        public void setIfCurUserLike(String ifCurUserLike) {
            this.ifCurUserLike = ifCurUserLike;
        }

        public String getLinkId() {
            return linkId;
        }

        public void setLinkId(String linkId) {
            this.linkId = linkId;
        }

        public String getLinkName() {
            return linkName;
        }

        public void setLinkName(String linkName) {
            this.linkName = linkName;
        }
    }
}
