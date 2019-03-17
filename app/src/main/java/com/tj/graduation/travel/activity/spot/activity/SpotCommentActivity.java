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

import java.util.ArrayList;
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
        initCommentData();
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

        commentAdapter = new SpotCommentAdapter(this, list);
        commentLv.setAdapter(commentAdapter);

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
                loadMoreData();
                commentAdapter.notifyDataSetChanged();
            }

            super.onPostExecute(result);
        }
    }

    private void loadMoreData() {
        list.add(new CommentModel("1", "张三", "都说女人是衣服，姐是你们穿不起的牌子。", "2019-03-01 07:20"));
        list.add(new CommentModel("1", "里斯", "我在等待不是等待你回来，而是等待自己释怀。", "2019-03-01 07:20"));
        list.add(new CommentModel("1", "王五", "喜欢在心情不好的时候，跑去篮球场，把那些坏心情都投进篮筐里。", "2019-03-01 07:20"));

    }

    private void initCommentData() {
        list = new ArrayList<>();
        list.add(new CommentModel("1", "张三", "都说女人是衣服，姐是你们穿不起的牌子。", "2019-03-01 07:20"));
        list.add(new CommentModel("1", "里斯", "我在等待不是等待你回来，而是等待自己释怀。", "2019-03-01 07:20"));
        list.add(new CommentModel("1", "王五", "喜欢在心情不好的时候，跑去篮球场，把那些坏心情都投进篮筐里。", "2019-03-01 07:20"));
        list.add(new CommentModel("1", "找刘", "有些事情无须争辩，表面服从，偷偷反抗。", "2019-03-01 07:20"));
        list.add(new CommentModel("1", "测试一", "那么牛X，为什么天安门挂的照片不是你的。", "2019-03-01 07:20"));
        list.add(new CommentModel("1", "张伟", "我们的空间，彼此都没有访问权限。", "2019-03-01 07:20"));
        list.add(new CommentModel("1", "丽丽", "你是黑社会怎么了?靠，我就不会入党?", "2019-03-01 07:20"));
        list.add(new CommentModel("1", "上官", "青春不散场，离别的时刻请带上我们的祝愿，在未知的将来迎风远航、劈波斩浪、一路豪歌!", "2019-03-01 07:20"));
        list.add(new CommentModel("1", "西门", "人就为争一口气，逼出来的却是一坨屎!", "2019-03-01 07:20"));
        list.add(new CommentModel("1", "欧阳", "一个男的最无能的一句话就是：你要是这么想我也没办法。", "2019-03-01 07:20"));

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
                        list.add(0, new CommentModel("1", "测试", content, "2018-03-09 12:30"));
                        commentAdapter.notifyDataSetChanged();
                        commentDialog.dismiss();
                    }
                });
                break;

        }
    }
}
