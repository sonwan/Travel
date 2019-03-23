package com.tj.graduation.travel.activity.spot.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tj.graduation.travel.Constant;
import com.tj.graduation.travel.R;
import com.tj.graduation.travel.base.BaseActivity;
import com.tj.graduation.travel.util.ShareUtil;
import com.tj.graduation.travel.util.StringUtils;
import com.tj.graduation.travel.util.ToastUtil;
import com.tj.graduation.travel.util.http.RequestUtil;
import com.tj.graduation.travel.util.http.listener.DisposeDataListener;
import com.tj.graduation.travel.util.http.request.RequestParams;

/**
 * Created by wangsong on 2019/3/21.
 */

public class SpotGuideSubmitActivity extends BaseActivity implements View.OnClickListener {

    private EditText titleEt;
    private EditText conentEt;
    private String spotId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestCustomTitle();
        setContentView(R.layout.spot_guide_submit_activity);
        setCustomTitle();
        setTItle("发表攻略");
        initView();
        getIntentData();
    }

    private void initView() {
        titleEt = findViewById(R.id.et_guide_title);
        conentEt = findViewById(R.id.et_guide_content);
        Button submitBtn = findViewById(R.id.btn_guide_submit);
        submitBtn.setOnClickListener(this);
    }

    private void doUserGuideSubmit(String title, String content) {
        RequestParams params = new RequestParams();
        params.put("userId", (String) ShareUtil.get(this, Constant.LOGINNAME, ""));
        params.put("spotId", spotId);
        params.put("guideTitle", title);
        params.put("guideDetail", content);

        RequestUtil.getRequest(Constant.URL + "userGuideSubmit.api", params, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                dismissProgressDialog();
                ToastUtil.showToastText(SpotGuideSubmitActivity.this, "提交成功");
                setResult(1);
                finish();
            }

            @Override
            public void onFailure(Object responseObj) {
                dismissProgressDialog();
                ToastUtil.showToastText(SpotGuideSubmitActivity.this, "提交失败");
            }
        }, null);
        showProgressDialog();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_guide_submit:

                String title = titleEt.getText().toString().trim();
                String content = conentEt.getText().toString().trim();

                if (StringUtils.isEmpty(title)) {
                    ToastUtil.showToastText(this, getResources().getString(R.string.no_title));
                    return;
                }
                if (StringUtils.isEmpty(content)) {
                    ToastUtil.showToastText(this, getResources().getString(R.string.no_content));
                    return;
                }
                doUserGuideSubmit(title, content);

                break;
        }
    }

    private void getIntentData() {
        spotId = getIntent().getStringExtra("spotId");
    }

    public static void startSpotGuideSubmitActivity(Context context, String spotId) {
        Intent intent = new Intent(context, SpotGuideSubmitActivity.class);
        intent.putExtra("spotId", spotId);
        context.startActivity(intent);

    }
}
