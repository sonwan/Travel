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
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tj.graduation.travel.Constant;
import com.tj.graduation.travel.R;
import com.tj.graduation.travel.activity.spot.adapter.SpotCommentAdapter;
import com.tj.graduation.travel.activity.spot.adapter.SpotDetailPicAdapter;
import com.tj.graduation.travel.activity.spot.adapter.SpotGuideAdapter;
import com.tj.graduation.travel.base.BaseActivity;
import com.tj.graduation.travel.dialog.SpotBuyDialog;
import com.tj.graduation.travel.dialog.SpotCommentDialog;
import com.tj.graduation.travel.model.BuyTickModel;
import com.tj.graduation.travel.model.CommentModel;
import com.tj.graduation.travel.model.CommentSubmitModel;
import com.tj.graduation.travel.model.GuideModel;
import com.tj.graduation.travel.model.SpotDetailModel;
import com.tj.graduation.travel.util.StringUtils;
import com.tj.graduation.travel.util.ToastUtil;
import com.tj.graduation.travel.util.Utils;
import com.tj.graduation.travel.util.http.RequestUtil;
import com.tj.graduation.travel.util.http.listener.DisposeDataListener;
import com.tj.graduation.travel.util.http.request.RequestParams;
import com.tj.graduation.travel.view.NoScrollListView;

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
    private NoScrollListView guideLv;
    private ScrollView sv;
    private TextView nodataTv;
    private LinearLayout commentLL;

    private ViewPager spotPicVp;

    private String spotid;
    private SpotDetailModel model;


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

        LinearLayout commentLl = findViewById(R.id.ll_comment);
        LinearLayout guideLL = findViewById(R.id.ll_guide);
        commentLl.setOnClickListener(this);
        guideLL.setOnClickListener(this);

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

        TextView lookAllTv = findViewById(R.id.tv_spot_comment_lookall);
        lookAllTv.setOnClickListener(this);
        commentLv = findViewById(R.id.lv_spot_detail_comment);
        commentLv.setFocusable(false);

        commentLL = findViewById(R.id.ll_spot_detail_comment);
        nodataTv = findViewById(R.id.tv_comment_nodata);

        guideLv = findViewById(R.id.lv_spot_detail_guide);
        guideLv.setFocusable(false);

        sv = findViewById(R.id.sv_spot_detail);

    }

    private void doQrySpotDetail() {

        doRequest(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                dismissProgressDialog();
                model = (SpotDetailModel) responseObj;
                setData(model);

            }

            @Override
            public void onFailure(Object responseObj) {

            }
        });
    }

    private void setData(SpotDetailModel model) {
        spotNameTv.setText(model.getData().getDetail().getName());
        if (model.getData().getPiclist() != null && model.getData().getPiclist().size() > 0) {
            spotPicVp.setVisibility(View.VISIBLE);
            SpotDetailPicAdapter adapter = new SpotDetailPicAdapter(SpotDetailActivity.this, model.getData().getPiclist());
            spotPicVp.setAdapter(adapter);
        } else {
            spotPicVp.setVisibility(View.GONE);
        }

        spotAddressTv.setText(model.getData().getDetail().getAddress());
        spotOpentimeTv.setText(model.getData().getDetail().getOpenTime().trim());
        spotDescTv.setText(model.getData().getDetail().getDescInfo());

        if (Float.parseFloat(model.getData().getDetail().getTicketPrice()) == 0) {
            spotPriceTv.setText("免费");
            buyTv.setVisibility(View.GONE);

        } else {
            spotPriceTv.setText("需购票 ¥" + model.getData().getDetail().getTicketPrice() + "起");
            buyTv.setVisibility(View.VISIBLE);
        }

        if (!StringUtils.isEmpty(model.getData().getDetail().getTrafficInfo())) {
            spotTrafficTv.setText(model.getData().getDetail().getTrafficInfo());
        } else {
            spotTrafficTv.setText("无");
        }

        spotTeleTv.setText(Html.fromHtml("电话：<font color=#1196EE>" + model.getData().getDetail().getTelephone() + "</font>"));

        final List<GuideModel> guideModels = model.getData().getGuidelist();
        if (guideModels != null && guideModels.size() > 0) {
            guideLv.setVisibility(View.VISIBLE);
            SpotGuideAdapter spotGuideAdapter = new SpotGuideAdapter(this, guideModels);
            guideLv.setAdapter(spotGuideAdapter);
            guideLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    SpotGuideDetailActivity.startSpotGuideDetailActivity(SpotDetailActivity.this, guideModels.get(i).getId());
                }
            });
        } else {
            guideLv.setVisibility(View.GONE);
        }


        sv.fullScroll(ScrollView.FOCUS_UP);
        doQryCommentList();
    }

    private void doQryCommentList() {
        RequestParams params = new RequestParams();
        params.put("curpagenum", "1");
        params.put("pagecount", "3");
        params.put("type", "JD");
        params.put("linkId", spotid);
        RequestUtil.getRequest(Constant.COMMENT_URL + "queryCommentList.api", params, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                dismissProgressDialog();
                CommentModel model = (CommentModel) responseObj;
                setCommentData(model);
            }

            @Override
            public void onFailure(Object responseObj) {
                dismissProgressDialog();
            }
        }, CommentModel.class);
        showProgressDialog();
    }

    private void setCommentData(CommentModel model) {
        List<CommentModel.CommentData.CommentList> commentList = model.getData().getList();
        if (commentList != null && commentList.size() > 0) {
            commentLL.setVisibility(View.VISIBLE);
            nodataTv.setVisibility(View.GONE);
            SpotCommentAdapter commentAdapter = new SpotCommentAdapter(this, commentList);
            commentLv.setAdapter(commentAdapter);
        } else {
            commentLL.setVisibility(View.GONE);
            nodataTv.setVisibility(View.VISIBLE);
        }
    }

    private void doRequest(DisposeDataListener listener) {
        RequestParams params = new RequestParams();

        params.put("spotId", spotid);

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

    /**
     * 购票接口
     */
    private void dobuyTicket(int ticknum) {

        RequestParams params = new RequestParams();
        params.put("buyUserId", "");
        params.put("buySpotId", model.getData().getDetail().getId() + "");
        params.put("buyFee", model.getData().getDetail().getTicketPrice());
        params.put("ticketNum", ticknum + "");
        RequestUtil.getRequest(Constant.URL3 + "buyTicket.api", params, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {

                dismissProgressDialog();

            }

            @Override
            public void onFailure(Object responseObj) {

                dismissProgressDialog();
            }
        }, BuyTickModel.class);

        showProgressDialog();

    }

    /**
     * 评论提交接口
     *
     * @param content
     */
    private void doSubmitComment(String content) {

        RequestParams params = new RequestParams();
        params.put("linkId", model.getData().getDetail().getId() + "");
        params.put("type", "JD");
        params.put("userId", "");
        params.put("content", content);
        RequestUtil.getRequest(Constant.URL + "submitComment.api", params, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                dismissProgressDialog();
                ToastUtil.showToastText(SpotDetailActivity.this, "评论成功");
            }

            @Override
            public void onFailure(Object responseObj) {
                dismissProgressDialog();
                ToastUtil.showToastText(SpotDetailActivity.this, "评论失败");
            }
        }, CommentSubmitModel.class);
        showProgressDialog();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_spot_buy:

                SpotBuyDialog dialog = new SpotBuyDialog(this, model.getData().getDetail().getTicketPrice());
                dialog.show();
                dialog.setOnSpotBuyFinishListener(new SpotBuyDialog.onSpotBuyFinishListener() {
                    @Override
                    public void onSpotBuyFinish(int ticknum) {
                        ToastUtil.showToastText(SpotDetailActivity.this, ticknum + "");
//                        dobuyTicket(ticknum);

                    }
                });

                break;

            case R.id.tv_spot_detail_comment:

                break;

            case R.id.ll_comment:

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
//                        doSubmitComment(content);
//                        commentAdapter.notifyDataSetChanged();
//                        commentDialog.dismiss();
                    }
                });

                break;

            case R.id.ll_guide:

//                ToastUtil.showToastText(this, "发表攻略");
                SpotGuideSubmitActivity.startSpotGuideSubmitActivity(this);

                break;

            case R.id.tv_spot_comment_lookall:

                SpotCommentActivity.startSpotCommentActivity(this, model.getData().getDetail().getName(), spotid, "JD");
                break;

        }
    }


}
