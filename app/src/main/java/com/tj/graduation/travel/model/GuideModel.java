package com.tj.graduation.travel.model;

/**
 * 景点详情攻略model
 * Created by wangsong on 2019/3/14.
 */

public class GuideModel {

    private String id;
    private String publishTime;
    private String guideTitle;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getGuideTitle() {
        return guideTitle;
    }

    public void setGuideTitle(String guideTitle) {
        this.guideTitle = guideTitle;
    }
}
