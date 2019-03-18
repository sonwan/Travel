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
import com.tj.graduation.travel.util.pulltorefresh.ILoadingLayout;
import com.tj.graduation.travel.util.pulltorefresh.PullToRefreshBase;
import com.tj.graduation.travel.util.pulltorefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 景点列表界面
 * Created by wangsong on 2019/3/3.
 */

public class SpotListFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private PullToRefreshListView spotLv;
    private SpotListAdapter adapter;
    private List<SpotListModel.Data.Item> list = new ArrayList<>();
    private String type;
    private int requestcount = 10;
    private int index = 1;

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
        spotLv.setMode(PullToRefreshBase.Mode.BOTH);
        ILoadingLayout startLabelse = spotLv.getLoadingLayoutProxy(true, false);
        startLabelse.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
        startLabelse.setRefreshingLabel("加载中");// 刷新时
        startLabelse.setReleaseLabel("释放开始加载");// 下来达到一定距离时，显示的提示


        ILoadingLayout endLabelsr = spotLv.getLoadingLayoutProxy(false, true);
        endLabelsr.setPullLabel("上拉加载更多");// 刚下拉时，显示的提示
        endLabelsr.setRefreshingLabel("加载中");// 刷新时
        endLabelsr.setReleaseLabel("释放开始加载");// 下来达到一定距离时，显示的提示


        spotLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                type = "refresh";
                index = 1;
                doQrySpotList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                type = "load";
                ++index;
                doQrySpotList();
            }
        });
        adapter = new SpotListAdapter(getActivity(), new ArrayList<SpotListModel.Data.Item>());
        spotLv.setAdapter(adapter);

        spotLv.setOnItemClickListener(this);

        doQrySpotList();


    }

    private void doQrySpotList() {
        doRequest(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                spotLv.onRefreshComplete();
                dismissProgressDialog();
                SpotListModel model = (SpotListModel) responseObj;
                List<SpotListModel.Data.Item> curlist = model.getData().getList();

                if ("refresh".equals(type)) {
                    adapter.clear();
                    list.clear();

                }

                list.addAll(curlist);
                adapter.setList(list);

            }

            @Override
            public void onFailure(Object responseObj) {
                dismissProgressDialog();
                spotLv.onRefreshComplete();
                Log.e("msg", "failure");
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        SpotDetailActivity.startSpotDetailActivity(getActivity(), list.get(i - 1).getId() + "");

    }

    private void doRequest(DisposeDataListener listener) {
        RequestParams params = new RequestParams();

        params.put("curpagenum", index + "");
        params.put("pagecount", requestcount + "");

        RequestUtil.getRequest(Constant.URL + "querySpotList.api", params, listener, SpotListModel.class);
        showProgressDialog();
    }


}
