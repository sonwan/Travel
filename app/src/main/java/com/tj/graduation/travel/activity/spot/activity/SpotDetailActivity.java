package com.tj.graduation.travel.activity.spot.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.tj.graduation.travel.Constant;
import com.tj.graduation.travel.R;
import com.tj.graduation.travel.base.BaseActivity;
import com.tj.graduation.travel.model.SpotDetailModel;
import com.tj.graduation.travel.model.SpotListModel;
import com.tj.graduation.travel.util.http.RequestUtil;
import com.tj.graduation.travel.util.http.listener.DisposeDataListener;
import com.tj.graduation.travel.util.http.request.RequestParams;

/**
 * Created by wangsong on 2019/3/5.
 */

public class SpotDetailActivity extends BaseActivity {

    private TextView spotNameTv;
    private String spotid;

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

        spotNameTv = findViewById(R.id.tv_spot_name);

    }

    private void doQrySpotDetail() {

        doRequest(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                dismissProgressDialog();
                SpotDetailModel model = (SpotDetailModel) responseObj;
                spotNameTv.setText(model.getData().getDetail().getName());
            }

            @Override
            public void onFailure(Object responseObj) {

            }
        });
    }

    private void doRequest(DisposeDataListener listener) {
        RequestParams params = new RequestParams();

        params.put("spotId", "1");

        RequestUtil.getRequest(Constant.URL + "querySpotDetail.api", params, listener, SpotDetailModel.class);
        showProgressDialog();
    }

    private void getIntentData() {
        spotid = getIntent().getStringExtra("spotid");
    }

    public static void startSpotDetailActivity(Context context, String spotid) {
        Intent intent = new Intent(context, SpotDetailActivity.class);
        intent.putExtra("spotid", spotid);
        context.startActivity(intent);
    }
}
