package com.tj.graduation.travel.model;

/**
 * 景点攻略详情
 * Created by wangsong on 2019/3/14.
 */

public class GuideDetailModel {

    private String msg;
    private int code;
    private GuideDetail data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public GuideDetail getData() {
        return data;
    }

    public void setData(GuideDetail data) {
        this.data = data;
    }

    public static class GuideDetail {

        private String id;
        private String linkId;
        private String linkName;
        private String guideTitle;
        private String guideDetail;
        private String createUserId;
        private String createUserName;
        private String createTime;
        private String ifCurUserLike;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getGuideTitle() {
            return guideTitle;
        }

        public void setGuideTitle(String guideTitle) {
            this.guideTitle = guideTitle;
        }

        public String getGuideDetail() {
            return guideDetail;
        }

        public void setGuideDetail(String guideDetail) {
            this.guideDetail = guideDetail;
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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getIfCurUserLike() {
            return ifCurUserLike;
        }

        public void setIfCurUserLike(String ifCurUserLike) {
            this.ifCurUserLike = ifCurUserLike;
        }
    }

}
