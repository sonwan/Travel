package com.tj.graduation.travel.activity.login.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tj.graduation.travel.Constant;
import com.tj.graduation.travel.R;
import com.tj.graduation.travel.activity.login.adapter.CollectionAdapter;
import com.tj.graduation.travel.activity.spot.activity.SpotGuideDetailActivity;
import com.tj.graduation.travel.base.BaseActivity;
import com.tj.graduation.travel.model.CollectionModel;
import com.tj.graduation.travel.util.ShareUtil;
import com.tj.graduation.travel.util.ToastUtil;
import com.tj.graduation.travel.util.http.RequestUtil;
import com.tj.graduation.travel.util.http.exception.OkHttpException;
import com.tj.graduation.travel.util.http.listener.DisposeDataListener;
import com.tj.graduation.travel.util.http.request.RequestParams;

import java.util.List;

/**
 * 收藏列表
 */

public class CollectionActivity extends BaseActivity {

    private ListView listView;
    private CollectionAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestCustomTitle();
        setContentView(R.layout.collection_list);
        setCustomTitle();
        setTItle("我的收藏");
        listView = findViewById(R.id.conllection_lv);

    }

    @Override
    protected void onResume() {
        super.onResume();
        doQryMeList();
    }

    private void doQryMeList() {
        doRequest(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                dismissProgressDialog();
                CollectionModel model = (CollectionModel) responseObj;
                final List<CollectionModel.Model> list = model.getData();
                adapter = new CollectionAdapter(getBaseContext(), list);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        SpotGuideDetailActivity.startSpotGuideDetailActivity(getBaseContext(), list.get(i).getId());
                    }
                });
            }

            @Override
            public void onFailure(Object responseObj) {
                dismissProgressDialog();
                Log.e("msg", "failure");
                OkHttpException resp = (OkHttpException)responseObj;
                ToastUtil.showToastText(getBaseContext(), resp.getEmsg().toString());
            }
        });
    }

    private void doRequest(DisposeDataListener listener) {
        RequestParams params = new RequestParams();
        params.put("userId", (String) ShareUtil.get(getBaseContext(), Constant.user_id, ""));
        RequestUtil.getRequest(Constant.URL + "queryLikeGuideList.api", params, listener, CollectionModel.class);
        showProgressDialog();
    }
}
