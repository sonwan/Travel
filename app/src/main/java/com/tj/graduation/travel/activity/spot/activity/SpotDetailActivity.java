package com.tj.graduation.travel.activity.spot.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tj.graduation.travel.Constant;
import com.tj.graduation.travel.R;
import com.tj.graduation.travel.activity.spot.adapter.SpotCommentAdapter;
import com.tj.graduation.travel.activity.spot.adapter.SpotDetailPicAdapter;
import com.tj.graduation.travel.base.BaseActivity;
import com.tj.graduation.travel.dialog.SpotBuyDialog;
import com.tj.graduation.travel.dialog.SpotCommentDialog;
import com.tj.graduation.travel.model.CommentModel;
import com.tj.graduation.travel.model.SpotDetailModel;
import com.tj.graduation.travel.util.Utils;
import com.tj.graduation.travel.util.http.RequestUtil;
import com.tj.graduation.travel.util.http.listener.DisposeDataListener;
import com.tj.graduation.travel.util.http.request.RequestParams;
import com.tj.graduation.travel.view.NoScrollListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 景点详情
 * Created by wangsong on 2019/3/5.
 */

public class SpotDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView spotNameTv;
    private TextView spotAddressTv;
    private TextView spotOpentimeTv;
    private TextView spotDescTv;
    private TextView spotPriceTv;
    private TextView spotTrafficTv;
    private TextView spotTeleTv;
    private TextView spotCommentTv;
    private TextView buyTv;
    private NoScrollListView commentLv;
    private ScrollView sv;

    private ViewPager spotPicVp;

    private String spotid;
    private List<CommentModel> list;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestCustomTitle();
        setContentView(R.layout.spot_detail_activity);
        setCustomTitle();
        setTItle("景点详情");
        getIntentData();
        initView();
        doQrySpotDetail();
    }

    private void initView() {

        spotPicVp = findViewById(R.id.vp_spot_detail);
        spotNameTv = findViewById(R.id.tv_spot_name);
        spotAddressTv = findViewById(R.id.tv_spot_address);
        spotOpentimeTv = findViewById(R.id.tv_spot_opentime);
        spotDescTv = findViewById(R.id.tv_spot_desc);
        spotPriceTv = findViewById(R.id.tv_spot_price);
        spotTrafficTv = findViewById(R.id.tv_spot_traffic);
        spotTeleTv = findViewById(R.id.tv_spot_tele);
        spotCommentTv = findViewById(R.id.tv_spot_detail_comment);
        spotCommentTv.setOnClickListener(this);
        buyTv = findViewById(R.id.tv_spot_buy);
        buyTv.setOnClickListener(this);

        commentLv = findViewById(R.id.lv_spot_detail_comment);
        commentLv.setFocusable(false);
        sv = findViewById(R.id.sv_spot_detail);

    }

    private void doQrySpotDetail() {

        doRequest(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                dismissProgressDialog();
                SpotDetailModel model = (SpotDetailModel) responseObj;
                setData(model);

            }

            @Override
            public void onFailure(Object responseObj) {

            }
        });
    }

    private SpotCommentAdapter commentAdapter;

    private void setData(SpotDetailModel model) {
        spotNameTv.setText(model.getData().getDetail().getName());
        SpotDetailPicAdapter adapter = new SpotDetailPicAdapter(SpotDetailActivity.this, model.getData().getPiclist());
        spotPicVp.setAdapter(adapter);
        spotAddressTv.setText("地址：" + model.getData().getDetail().getAddress());
        spotOpentimeTv.setText("开放时间：" + model.getData().getDetail().getOpenTime());
        spotDescTv.setText(model.getData().getDetail().getDescInfo());

        spotPriceTv.setText("需购票 ¥" + model.getData().getDetail().getTicketPrice() + "起");
        spotTrafficTv.setText(model.getData().getDetail().getTrafficInfo());
        spotTeleTv.setText("电话：" + Html.fromHtml("<font color=#1196EE>" + model.getData().getDetail().getTelephone() + "</font>"));


        initCommentData();
        commentAdapter = new SpotCommentAdapter(this, list);
        commentLv.setAdapter(commentAdapter);

        sv.fullScroll(ScrollView.FOCUS_UP);
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

    private void doRequest(DisposeDataListener listener) {
        RequestParams params = new RequestParams();

        params.put("spotId", "1");

        RequestUtil.getRequest(Constant.URL + "querySpotDetail.api", params, listener, SpotDetailModel.class);
        showProgressDialog();
    }

    private void getIntentData() {
        spotid = getIntent().getStringExtra("spotid");
        Log.e("spotid", spotid);
    }

    public static void startSpotDetailActivity(Context context, String spotid) {
        Intent intent = new Intent(context, SpotDetailActivity.class);
        intent.putExtra("spotid", spotid);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_spot_buy:

                SpotBuyDialog dialog = new SpotBuyDialog(this);
                dialog.show();

                break;

            case R.id.tv_spot_detail_comment:

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
                        list.add(0, new CommentModel("1", "测试", content, "2018-03-09 12:30"));
                        commentAdapter.notifyDataSetChanged();
                        commentDialog.dismiss();
                    }
                });

                break;

        }
    }


}
