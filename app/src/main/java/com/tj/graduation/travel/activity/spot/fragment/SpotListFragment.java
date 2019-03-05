package com.tj.graduation.travel.activity.spot.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tj.graduation.travel.Constant;
import com.tj.graduation.travel.R;
import com.tj.graduation.travel.activity.spot.activity.SpotDetailActivity;
import com.tj.graduation.travel.activity.spot.adapter.SpotListAdapter;
import com.tj.graduation.travel.base.BaseFragment;
import com.tj.graduation.travel.model.SpotListModel;
import com.tj.graduation.travel.util.http.RequestUtil;
import com.tj.graduation.travel.util.http.listener.DisposeDataListener;
import com.tj.graduation.travel.util.http.request.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * 景点列表界面
 * Created by wangsong on 2019/3/3.
 */

public class SpotListFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private ListView spotLv;
    private SpotListAdapter adapter;
    private List<SpotListModel.Data.Item> list;

    public static SpotListFragment newInstance() {
        SpotListFragment spotListFragment = new SpotListFragment();
        return spotListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.spot_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        spotLv = getView().findViewById(R.id.lv_spot_list);
        spotLv.setOnItemClickListener(this);


        doQrySpotList();


    }

    private void doQrySpotList() {
        doRequest(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                dismissProgressDialog();
                SpotListModel model = (SpotListModel) responseObj;
                list = model.getData().getList();
                adapter = new SpotListAdapter(getActivity(), list);
                spotLv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Object responseObj) {
                dismissProgressDialog();
                Log.e("msg", "failure");
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        SpotDetailActivity.startSpotDetailActivity(getActivity(), list.get(i).getId() + "");

    }

    private void doRequest(DisposeDataListener listener) {
        RequestParams params = new RequestParams();

        params.put("curpagenum", "1");
        params.put("pagecount", "10");

        RequestUtil.getRequest(Constant.URL + "querySpotList.api", params, listener, SpotListModel.class);
        showProgressDialog();
    }


}
