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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tj.graduation.travel.Constant;
import com.tj.graduation.travel.R;
import com.tj.graduation.travel.activity.spot.adapter.SpotCommentAdapter;
import com.tj.graduation.travel.base.BaseActivity;
import com.tj.graduation.travel.dialog.SpotCommentDialog;
import com.tj.graduation.travel.model.CommentModel;
import com.tj.graduation.travel.model.CommentSubmitModel;
import com.tj.graduation.travel.util.ToastUtil;
import com.tj.graduation.travel.util.Utils;
import com.tj.graduation.travel.util.http.RequestUtil;
import com.tj.graduation.travel.util.http.listener.DisposeDataListener;
import com.tj.graduation.travel.util.http.request.RequestParams;
import com.tj.graduation.travel.util.pulltorefresh.ILoadingLayout;
import com.tj.graduation.travel.util.pulltorefresh.PullToRefreshBase;
import com.tj.graduation.travel.util.pulltorefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 景点全部点评
 * Created by wangsong on 2019/3/12.
 */

public class SpotCommentActivity extends BaseActivity implements View.OnClickListener {

    private String spotname;
    private List<CommentModel.CommentData.CommentList> list = new ArrayList<>();
    private PullToRefreshListView commentLv;
    private TextView nodataTv;

    private SpotCommentAdapter commentAdapter;
    private int requestcount = 10;
    private int index = 1;
    private String type;
    private String spotId;
    private String commenttype;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestCustomTitle();
        setContentView(R.layout.spot_comment_activity);
        setCustomTitle();
        getIntentData();
        setTItle("点评");
        setSecondTitle(spotname);
        initView();
        doQryCommentList();
    }

    private void initView() {
        LinearLayout writeLL = findViewById(R.id.tv_spot_comment_write);
        nodataTv = findViewById(R.id.tv_comment_nodata);
        writeLL.setOnClickListener(this);
        commentLv = findViewById(R.id.lv_spot_detail_comment);
        commentLv.setMode(PullToRefreshBase.Mode.BOTH);
        ILoadingLayout startLabelse = commentLv.getLoadingLayoutProxy(true, false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("加载中");// 刷新时
        startLabelse.setReleaseLabel("释放开始加载");// 下来达到一定距离时，显示的提示


        ILoadingLayout endLabelsr = commentLv.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("加载中");// 刷新时
        endLabelsr.setReleaseLabel("释放开始加载");// 下来达到一定距离时，显示的提示


        commentLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                type = "refresh";

                index = 1;
                doQryCommentList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                type = "load";
                ++index;
                doQryCommentList();
            }
        });

        commentAdapter = new SpotCommentAdapter(this, new ArrayList<CommentModel.CommentData.CommentList>());
        commentLv.setAdapter(commentAdapter);

    }

    private void doQryCommentList() {
        RequestParams params = new RequestParams();
        params.put("curpagenum", index + "");
        params.put("pagecount", requestcount + "");
        params.put("type", commenttype);
        params.put("linkId", spotId);
        RequestUtil.getRequest(Constant.COMMENT_URL + "queryCommentList.api", params, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                dismissProgressDialog();
                commentLv.onRefreshComplete();
                CommentModel model = (CommentModel) responseObj;
                setCommentData(model);
            }

            @Override
            public void onFailure(Object responseObj) {
                dismissProgressDialog();
                commentLv.onRefreshComplete();
            }
        }, CommentModel.class);
        showProgressDialog();
    }

    private void setCommentData(CommentModel model) {
        List<CommentModel.CommentData.CommentList> commentList = model.getData().getList();
        if ("refresh".equals(type)) {
            list.clear();
            commentAdapter.clear();
        }
        list.addAll(commentList);
        if (list.size() > 0) {
            commentLv.setVisibility(View.VISIBLE);
            nodataTv.setVisibility(View.GONE);
            commentAdapter.setList(list);
        } else {
            commentLv.setVisibility(View.GONE);
            nodataTv.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 评论提交接口
     *
     * @param content
     */
    private void doSubmitComment(String content) {

        RequestParams params = new RequestParams();
        params.put("linkId", spotId);
        params.put("type", commenttype);
        params.put("userId", "");
        params.put("content", content);
        RequestUtil.getRequest(Constant.URL + "submitComment.api", params, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                dismissProgressDialog();
                ToastUtil.showToastText(SpotCommentActivity.this, "评论成功");
            }

            @Override
            public void onFailure(Object responseObj) {
                dismissProgressDialog();
                ToastUtil.showToastText(SpotCommentActivity.this, "评论失败");
            }
        }, CommentSubmitModel.class);
        showProgressDialog();

    }

    private void getIntentData() {
        spotname = getIntent().getStringExtra("spotname");
        spotId = getIntent().getStringExtra("spotId");
        commenttype = getIntent().getStringExtra("type");
    }

    public static void startSpotCommentActivity(Context context, String spotname, String spotId, String type) {
        Intent intent = new Intent(context, SpotCommentActivity.class);
        intent.putExtra("spotname", spotname);
        intent.putExtra("spotId", spotId);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_spot_comment_write:

                final SpotCommentDialog commentDialog = new SpotCommentDialog(SpotCommentActivity.this);
                commentDialog.setCanceledOnTouchOutside(false);
                commentDialog.show();
                Window window = commentDialog.getWindow();
                window.setGravity(Gravity.BOTTOM);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams params = window.getAttributes();
                params.width = Utils.getScreenWidth(SpotCommentActivity.this);
                window.setAttributes(params);

                commentDialog.setOnSpotCommentPublishListener(new SpotCommentDialog.OnSpotCommentPublishListener() {
                    @Override
                    public void OnSpotCommentPublish(String content) {
                        doSubmitComment(content);
//                        commentAdapter.notifyDataSetChanged();
//                        commentDialog.dismiss();
                    }
                });
                break;

        }
    }
}
