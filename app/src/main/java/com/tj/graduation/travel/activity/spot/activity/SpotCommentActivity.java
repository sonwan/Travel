package com.tj.graduation.travel.activity.spot.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tj.graduation.travel.R;
import com.tj.graduation.travel.activity.spot.adapter.SpotCommentAdapter;
import com.tj.graduation.travel.base.BaseActivity;
import com.tj.graduation.travel.dialog.SpotCommentDialog;
import com.tj.graduation.travel.model.CommentModel;
import com.tj.graduation.travel.util.Utils;
import com.tj.graduation.travel.util.pulltorefresh.ILoadingLayout;
import com.tj.graduation.travel.util.pulltorefresh.PullToRefreshBase;
import com.tj.graduation.travel.util.pulltorefresh.PullToRefreshListView;

import java.util.List;

/**
 * 景点全部点评
 * Created by wangsong on 2019/3/12.
 */

public class SpotCommentActivity extends BaseActivity implements View.OnClickListener {

    private String spotname;
    private List<CommentModel> list;
    private PullToRefreshListView commentLv;
    private SpotCommentAdapter commentAdapter;
    private int requestcount = 20;
    private int index = 1;
    private String type;

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
    }

    private void initView() {
        LinearLayout writeLL = findViewById(R.id.tv_spot_comment_write);
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

                Log.e("SpotCommentActivity", "refresh---");
                index = 1;
                new GetDataTask().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                type = "load";
                ++index;
                new GetDataTask().execute();
                Log.e("SpotCommentActivity", "load---");
            }
        });

//        commentAdapter = new SpotCommentAdapter(this, list);
//        commentLv.setAdapter(commentAdapter);

    }

    private class GetDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            commentLv.onRefreshComplete();
            if ("refresh".endsWith(type)) {

            } else {
                commentAdapter.notifyDataSetChanged();
            }

            super.onPostExecute(result);
        }
    }


    private void getIntentData() {
        spotname = getIntent().getStringExtra("spotname");
    }

    public static void startSpotCommentActivity(Context context, String spotname) {
        Intent intent = new Intent(context, SpotCommentActivity.class);
        intent.putExtra("spotname", spotname);
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
//                        commentAdapter.notifyDataSetChanged();
//                        commentDialog.dismiss();
                    }
                });
                break;

        }
    }
}
