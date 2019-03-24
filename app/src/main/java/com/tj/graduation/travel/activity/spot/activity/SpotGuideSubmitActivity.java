package com.tj.graduation.travel.activity.spot.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tj.graduation.travel.R;
import com.tj.graduation.travel.base.BaseActivity;

/**
 * Created by wangsong on 2019/3/21.
 */

public class SpotGuideSubmitActivity extends BaseActivity implements View.OnClickListener {

    private EditText titleEt;
    private EditText conentEt;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestCustomTitle();
        setContentView(R.layout.spot_guide_submit_activity);
        setCustomTitle();
        setTItle("发表攻略");
        initView();
    }

    private void initView() {
        titleEt = findViewById(R.id.et_guide_title);
        conentEt = findViewById(R.id.et_guide_content);
        Button submitBtn = findViewById(R.id.btn_guide_submit);
        submitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_guide_submit:

                break;
        }
    }

    public static void startSpotGuideSubmitActivity(Context context) {
        Intent intent = new Intent(context, SpotGuideSubmitActivity.class);
        context.startActivity(intent);

    }
}
