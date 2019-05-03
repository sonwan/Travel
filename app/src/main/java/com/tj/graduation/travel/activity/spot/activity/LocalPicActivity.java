package com.tj.graduation.travel.activity.spot.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.tj.graduation.travel.R;
import com.tj.graduation.travel.activity.spot.adapter.LocalPicAdapter;
import com.tj.graduation.travel.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangsong on 2019/4/6.
 */

public class LocalPicActivity extends BaseActivity implements View.OnClickListener {

    private GridView localPicGv;
    private LocalPicAdapter adapter;
    private List<String> localPicList;
    private List<String> selectList;
    private int selectMaxNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestCustomTitle();
        setContentView(R.layout.local_pic_activity);
        setCustomTitle();
        setTItle("本地图库");
        getIntentData();
        initView();
        LoadLocalPic();
    }

    private void initView() {
        localPicGv = findViewById(R.id.gv_local_pic);
        adapter = new LocalPicAdapter(this, new ArrayList<String>());
        localPicGv.setAdapter(adapter);

        TextView finishTv = findViewById(R.id.tv_finish);
        finishTv.setOnClickListener(this);

    }

    /**
     * 加载本地图片
     */
    private void LoadLocalPic() {

        localPicList = new ArrayList<>();
        new Thread() {
            @Override
            public void run() {
                super.run();

                Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

                Cursor cursor = getContentResolver().query(uri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
                while (cursor.moveToNext()) {
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    localPicList.add(path);
                }
                handler.sendEmptyMessage(0);


            }
        }.start();

    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0) {

                adapter.setPicList(localPicList);
                adapter.setSelectMaxNum(selectMaxNum);
                adapter.setSelectList(selectList);
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_finish:

                Intent intent = new Intent();
                intent.putStringArrayListExtra("selectList", (ArrayList<String>) adapter.getSelectList());
                setResult(SpotGuideSubmitActivity.SELECT_PIC_RESPCODE, intent);
                finish();

                break;
        }
    }

    private void getIntentData() {

        selectList = getIntent().getStringArrayListExtra("selectList");
        selectMaxNum = getIntent().getIntExtra("selectMaxNum", 0);
    }
}
