package com.tj.graduation.travel.activity.spot.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.tj.graduation.travel.Constant;
import com.tj.graduation.travel.R;
import com.tj.graduation.travel.activity.spot.adapter.GuidePicAdapter;
import com.tj.graduation.travel.base.BaseActivity;
import com.tj.graduation.travel.dialog.CancelImageDialog;
import com.tj.graduation.travel.util.ShareUtil;
import com.tj.graduation.travel.util.StringUtils;
import com.tj.graduation.travel.util.ToastUtil;
import com.tj.graduation.travel.util.http.RequestUtil;
import com.tj.graduation.travel.util.http.listener.DisposeDataListener;
import com.tj.graduation.travel.util.http.request.RequestParams;
import com.tj.graduation.travel.view.NoScrollGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangsong on 2019/3/21.
 */

public class SpotGuideSubmitActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private EditText titleEt;
    private EditText conentEt;
    private NoScrollGridView picGv;
    private String spotId;
    private List<String> selectList = new ArrayList<>();
    private GuidePicAdapter adapter;
    private int selectMaxNum = 5;
    private static final int SELECT_PIC_REQCODE = 0;
    public static final int SELECT_PIC_RESPCODE = 1;


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
        picGv = findViewById(R.id.gv_guide_pic);
        adapter = new GuidePicAdapter(this, selectList);
        picGv.setAdapter(adapter);
        picGv.setOnItemClickListener(this);

        Button submitBtn = findViewById(R.id.btn_guide_submit);
        submitBtn.setOnClickListener(this);
    }

    private void doUserGuideSubmit(String title, String content) {
        RequestParams params = new RequestParams();
        params.put("userId", (String) ShareUtil.get(this, Constant.user_id, ""));
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

    /**
     * 添加图片监听
     */
    public void onAdd() {

        Intent intent = new Intent(this, LocalPicActivity.class);
        intent.putExtra("selectMaxNum", selectMaxNum);
        intent.putStringArrayListExtra("selectList", (ArrayList<String>) selectList);
        startActivityForResult(intent, SELECT_PIC_REQCODE);
    }

    /**
     * 取消图片监听
     *
     * @param path
     */
    public void onRemove(final String path) {

        CancelImageDialog dialog = new CancelImageDialog(this);
        dialog.show();
        dialog.setOnImageCancelListener(new CancelImageDialog.onImageCancelListener() {
            @Override
            public void onImageCancel() {
                selectList.remove(path);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        if (selectList.size() > i) {
            String path = selectList.get(i);
            onRemove(path);
        } else {
            onAdd();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PIC_REQCODE && resultCode == SELECT_PIC_RESPCODE && data != null) {
            selectList = data.getStringArrayListExtra("selectList");
            adapter.setSelectList(selectList);
            adapter.notifyDataSetChanged();
        }
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


}
