package com.tj.graduation.travel.model;

import java.util.List;

/**
 * 景点/攻略评论
 * Created by wangsong on 2019/3/9.
 */

public class CommentModel {


    private String msg;
    private int code;
    private CommentData data;

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

    public CommentData getData() {
        return data;
    }

    public void setData(CommentData data) {
        this.data = data;
    }

    public static class CommentData {

        private String linkId;
        private String linkName;
        private String type;  //JD:景点评论类型  GL:攻略评论类型
        private List<CommentList> list;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<CommentList> getList() {
            return list;
        }

        public void setList(List<CommentList> list) {
            this.list = list;
        }

        public static class CommentList {
            private String id;
            private String userId;
            private String userName;
            private String comContent;
            private String comTime;
            private List<ReplyModel> replayList;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public List<ReplyModel> getReplayList() {
                return replayList;
            }

            public void setReplayList(List<ReplyModel> replayList) {
                this.replayList = replayList;
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

            public static class ReplyModel {
                private String id;
                private String replayContent;
                private String replayTime;
                private String userId;
                private String userName;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getReplayContent() {
                    return replayContent;
                }

                public void setReplayContent(String replayContent) {
                    this.replayContent = replayContent;
                }

                public String getReplayTime() {
                    return replayTime;
                }

                public void setReplayTime(String replayTime) {
                    this.replayTime = replayTime;
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
            }
        }

    }


}
