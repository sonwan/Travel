package com.tj.graduation.travel.activity.spot.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tj.graduation.travel.Constant;
import com.tj.graduation.travel.R;
import com.tj.graduation.travel.activity.spot.adapter.SpotCommentAdapter;
import com.tj.graduation.travel.base.BaseActivity;
import com.tj.graduation.travel.dialog.SpotCommentDialog;
import com.tj.graduation.travel.model.CommentModel;
import com.tj.graduation.travel.model.CommentSubmitModel;
import com.tj.graduation.travel.model.GuideDetailModel;
import com.tj.graduation.travel.model.UserLikeModel;
import com.tj.graduation.travel.util.ShareUtil;
import com.tj.graduation.travel.util.StringUtils;
import com.tj.graduation.travel.util.ToastUtil;
import com.tj.graduation.travel.util.Utils;
import com.tj.graduation.travel.util.http.RequestUtil;
import com.tj.graduation.travel.util.http.listener.DisposeDataListener;
import com.tj.graduation.travel.util.http.request.RequestParams;
import com.tj.graduation.travel.view.NoScrollListView;

import java.util.List;

/**
 * 景点攻略详情页
 * Created by wangsong on 2019/3/14.
 */

public class SpotGuideDetailActivity extends BaseActivity implements View.OnClickListener {

    private String guideId;
    private TextView spotNameTv;
    private TextView titleTv;
    private TextView contentTv;
    private TextView personTv;
    private TextView dateTv;
    private ImageView scImg;
    private TextView scTv;

    private LinearLayout commentLL;
    private NoScrollListView commentLv;
    private TextView nodataTv;
    private CommentModel model;
    private GuideDetailModel guideDetailModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestCustomTitle();
        setContentView(R.layout.spot_guide_detail_activity);
        setCustomTitle();
        setTItle("景点攻略");
        getIntentData();
        initView();
        doQryGuideDetail();
    }

    private void doQryGuideDetail() {

        RequestParams params = new RequestParams();
        params.put("guideId", guideId);
        params.put("userId", (String) ShareUtil.get(this, Constant.user_id, ""));
        RequestUtil.getRequest(Constant.URL + "queryGuideDetail.api", params, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {

                dismissProgressDialog();
                guideDetailModel = (GuideDetailModel) responseObj;
                setData(guideDetailModel);
            }

            @Override
            public void onFailure(Object responseObj) {

            }
        }, GuideDetailModel.class);
        showProgressDialog();

    }

    private void doQryCommentList() {
        RequestParams params = new RequestParams();
        params.put("curpagenum", "1");
        params.put("pagecount", "3");
        params.put("type", "GL");
        params.put("linkId", guideId);
        RequestUtil.getRequest(Constant.COMMENT_URL + "queryCommentList.api", params, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                dismissProgressDialog();
                model = (CommentModel) responseObj;
                setCommentData(model);
            }

            @Override
            public void onFailure(Object responseObj) {
                dismissProgressDialog();
            }
        }, CommentModel.class);
        showProgressDialog();
    }

    /**
     * 评论提交接口
     *
     * @param content
     */
    private void doSubmitComment(String content, String linkId, String type) {

        RequestParams params = new RequestParams();
        params.put("linkId", linkId);
        params.put("type", type);
        params.put("userId", (String) ShareUtil.get(this, Constant.user_id, ""));
        params.put("content", content);
        RequestUtil.getRequest(Constant.COMMENT_URL + "submitComment.api", params, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                dismissProgressDialog();
                ToastUtil.showToastText(SpotGuideDetailActivity.this, "评论成功");
                doQryCommentList();
            }

            @Override
            public void onFailure(Object responseObj) {
                dismissProgressDialog();
                ToastUtil.showToastText(SpotGuideDetailActivity.this, "评论失败");
            }
        }, CommentSubmitModel.class);
        showProgressDialog();

    }

    /**
     * 用户收藏/取消攻略接口
     */
    private void doUserLikeGuideSummit(String status) {

        RequestParams params = new RequestParams();
        params.put("userId", (String) ShareUtil.get(this, Constant.user_id, ""));
        params.put("guideId", guideId);
        params.put("type", status);
        RequestUtil.getRequest(Constant.URL_user + "userLikeGuideSummit.api", params, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {

                dismissProgressDialog();
                ToastUtil.showToastText(SpotGuideDetailActivity.this, "操作成功");
            }

            @Override
            public void onFailure(Object responseObj) {

                dismissProgressDialog();
                ToastUtil.showToastText(SpotGuideDetailActivity.this, "操作失败");
            }
        }, UserLikeModel.class);
        showProgressDialog();

    }


    private void setCommentData(CommentModel model) {
        List<CommentModel.CommentData.CommentList> commentList = model.getData().getList();
        if (commentList != null && commentList.size() > 0) {
            commentLL.setVisibility(View.VISIBLE);
            nodataTv.setVisibility(View.GONE);
            SpotCommentAdapter commentAdapter = new SpotCommentAdapter(this, commentList);
            commentLv.setAdapter(commentAdapter);
            commentAdapter.setOnReplyFinishListener(new SpotCommentAdapter.onReplyFinishListener() {
                @Override
                public void onReplyFinish(final String linkId, String username, final String type) {
                    if (!StringUtils.isEmpty((String) ShareUtil.get(SpotGuideDetailActivity.this, Constant.user_id, ""))) {
                        final SpotCommentDialog commentDialog = new SpotCommentDialog(SpotGuideDetailActivity.this);
                        commentDialog.setTitle("回复 " + username);
                        commentDialog.setCanceledOnTouchOutside(false);
                        commentDialog.show();
                        Window window = commentDialog.getWindow();
                        window.setGravity(Gravity.BOTTOM);
                        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        WindowManager.LayoutParams params = window.getAttributes();
                        params.width = Utils.getScreenWidth(SpotGuideDetailActivity.this);
                        window.setAttributes(params);

                        commentDialog.setOnSpotCommentPublishListener(new SpotCommentDialog.OnSpotCommentPublishListener() {
                            @Override
                            public void OnSpotCommentPublish(String content) {
                                doSubmitComment(content, linkId, type);
//                        commentAdapter.notifyDataSetChanged();
                                commentDialog.dismiss();
                            }
                        });
                    } else {
                        ToastUtil.showToastText(SpotGuideDetailActivity.this, getResources().getString(R.string.no_login));
                    }
                }
            });
        } else {
            commentLL.setVisibility(View.GONE);
            nodataTv.setVisibility(View.VISIBLE);
        }
    }


    private void setData(GuideDetailModel model) {
        GuideDetailModel.GuideDetail guideDetail = model.getData();
        titleTv.setText(guideDetail.getGuideTitle());
        contentTv.setText(guideDetail.getGuideDetail());
        personTv.setText(guideDetail.getCreateUserName());
        dateTv.setText(guideDetail.getCreateTime());

        if ("true".equals(guideDetail.getIfCurUserLike())) {
            scTv.setText("已收藏");
            scImg.setImageResource(R.drawable.icon_sc);
        } else {
            scTv.setText("我要收藏");
            scImg.setImageResource(R.drawable.icon_unsc);
        }

        doQryCommentList();

    }

    private void initView() {

        LinearLayout commentLl = findViewById(R.id.ll_comment);
        LinearLayout guideLL = findViewById(R.id.ll_guide);
        commentLl.setOnClickListener(this);
        guideLL.setOnClickListener(this);

        scImg = findViewById(R.id.img_guide_sc);
        scTv = findViewById(R.id.tv_guide_sc);


        titleTv = findViewById(R.id.tv_guide_title);
        contentTv = findViewById(R.id.tv_guide_content);
        personTv = findViewById(R.id.tv_guide_person);
        dateTv = findViewById(R.id.tv_guide_date);
        TextView commentTv = findViewById(R.id.tv_guide_detail_comment);
        commentTv.setOnClickListener(this);

        commentLL = findViewById(R.id.ll_guide_comment);
        commentLv = findViewById(R.id.lv_guide_detail_comment);
        TextView lookAllTv = findViewById(R.id.tv_guide_comment_lookall);
        lookAllTv.setOnClickListener(this);
        nodataTv = findViewById(R.id.tv_comment_nodata);
    }

    private void getIntentData() {
        guideId = getIntent().getStringExtra("guideId");
    }

    public static void startSpotGuideDetailActivity(Context context, String guideId) {

        Intent intent = new Intent(context, SpotGuideDetailActivity.class);
        intent.putExtra("guideId", guideId);
        context.startActivity(intent);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_guide_detail_comment:
                break;

            case R.id.tv_guide_comment_lookall:
                Intent intent = new Intent(this, SpotCommentActivity.class);
                intent.putExtra("spotname", guideDetailModel.getData().getGuideTitle());
                intent.putExtra("spotId", guideDetailModel.getData().getId());
                intent.putExtra("type", "GL");
                startActivityForResult(intent, 1);

                break;

            case R.id.ll_comment:
                if (!StringUtils.isEmpty((String) ShareUtil.get(this, Constant.user_id, ""))) {
                    final SpotCommentDialog commentDialog = new SpotCommentDialog(this);
                    commentDialog.setCanceledOnTouchOutside(false);
                    commentDialog.show();
                    Window window = commentDialog.getWindow();
                    window.setGravity(Gravity.BOTTOM);
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    WindowManager.LayoutParams params = window.getAttributes();
                    params.width = Utils.getScreenWidth(this);
                    window.setAttributes(params);

                    commentDialog.setOnSpotCommentPublishListener(new SpotCommentDialog.OnSpotCommentPublishListener() {
                        @Override
                        public void OnSpotCommentPublish(String content) {
                            doSubmitComment(content, guideId, "GL");
//                        commentAdapter.notifyDataSetChanged();
                            commentDialog.dismiss();
                        }
                    });
                } else {
                    ToastUtil.showToastText(this, getResources().getString(R.string.no_login));
                }
                break;

            case R.id.ll_guide:

                if (!StringUtils.isEmpty((String) ShareUtil.get(this, Constant.user_id, ""))) {
                    if ("我要收藏".equals(scTv.getText().toString())) {
                        doUserLikeGuideSummit("follow");
                        scTv.setText("已收藏");
                        scImg.setImageResource(R.drawable.icon_sc);
                    } else {
                        doUserLikeGuideSummit("cancel");
                        scTv.setText("我要收藏");
                        scImg.setImageResource(R.drawable.icon_unsc);
                    }
                } else {
                    ToastUtil.showToastText(this, getResources().getString(R.string.no_login));
                }


                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            doQryCommentList();
        }

    }
}
