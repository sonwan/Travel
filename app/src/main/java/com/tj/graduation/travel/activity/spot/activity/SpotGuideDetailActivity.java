package com.tj.graduation.travel.activity.spot.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.tj.graduation.travel.Constant;
import com.tj.graduation.travel.R;
import com.tj.graduation.travel.base.BaseActivity;
import com.tj.graduation.travel.model.GuideDetailModel;
import com.tj.graduation.travel.util.http.RequestUtil;
import com.tj.graduation.travel.util.http.listener.DisposeDataListener;
import com.tj.graduation.travel.util.http.request.RequestParams;

/**
 * 景点攻略详情页
 * Created by wangsong on 2019/3/14.
 */

public class SpotGuideDetailActivity extends BaseActivity {

    private String guideId;
    private TextView spotNameTv;
    private TextView titleTv;
    private TextView contentTv;
    private TextView personTv;
    private TextView dateTv;


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
        params.put("userId", "");
        RequestUtil.getRequest(Constant.URL + "queryGuideDetail.api", params, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {

                dismissProgressDialog();
                GuideDetailModel model = (GuideDetailModel) responseObj;
                setData(model);
            }

            @Override
            public void onFailure(Object responseObj) {

            }
        }, GuideDetailModel.class);
        showProgressDialog();

    }

    private void setData(GuideDetailModel model) {
        GuideDetailModel.GuideDetail guideDetail = model.getData();
        spotNameTv.setText(guideDetail.getLinkName());
        titleTv.setText(guideDetail.getGuideTitle());
        contentTv.setText(guideDetail.getGuideDetail());
        personTv.setText(guideDetail.getCreateUserName());
        dateTv.setText(guideDetail.getCreateTime());

    }

    private void initView() {

        spotNameTv = findViewById(R.id.tv_guide_spotname);
        titleTv = findViewById(R.id.tv_guide_title);
        contentTv = findViewById(R.id.tv_guide_content);
        personTv = findViewById(R.id.tv_guide_person);
        dateTv = findViewById(R.id.tv_guide_date);
    }

    private void getIntentData() {
        guideId = getIntent().getStringExtra("guideId");
    }

    public static void startSpotGuideDetailActivity(Context context, String guideId) {

        Intent intent = new Intent(context, SpotGuideDetailActivity.class);
        intent.putExtra("guideId", guideId);
        context.startActivity(intent);


    }
}
