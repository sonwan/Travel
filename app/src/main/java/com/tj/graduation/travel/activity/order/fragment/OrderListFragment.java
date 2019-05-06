package com.tj.graduation.travel.activity.order.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.tj.graduation.travel.Constant;
import com.tj.graduation.travel.R;
import com.tj.graduation.travel.activity.purchase.adapter.PurchaseRecordsAdapter;
import com.tj.graduation.travel.base.BaseFragment;
import com.tj.graduation.travel.model.PurchaseModel;
import com.tj.graduation.travel.model.PurchaseModel.Data;
import com.tj.graduation.travel.util.ShareUtil;
import com.tj.graduation.travel.util.StringUtils;
import com.tj.graduation.travel.util.http.RequestUtil;
import com.tj.graduation.travel.util.http.listener.DisposeDataListener;
import com.tj.graduation.travel.util.http.request.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * 攻略列表界面
 * Created by wangsong on 2019/3/3.
 */

public class OrderListFragment extends BaseFragment {

    private ListView orderLv;
    private TextView noDataTv;
    private PurchaseRecordsAdapter adapter;

    public static OrderListFragment newInstance() {
        OrderListFragment strategyListFragment = new OrderListFragment();
        return strategyListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.order_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());
        if (isLogin()) {
            noDataTv.setVisibility(View.GONE);
            doQryMeList();
        } else {
            noDataTv.setVisibility(View.VISIBLE);
            noDataTv.setText(getActivity().getResources().getString(R.string.no_login));
        }


    }

    private boolean isLogin() {
        boolean isLogin = false;
        String userid = (String) ShareUtil.get(getActivity(), Constant.user_id, "");
        if (StringUtils.isNotEmpty(userid)) {
            isLogin = true;
        }
        return isLogin;
    }

    private void initView(View view) {
        noDataTv = view.findViewById(R.id.tv_order_nodata);
        orderLv = view.findViewById(R.id.purchase_list);
        adapter = new PurchaseRecordsAdapter(getActivity(), new ArrayList<Data.Item>());
        orderLv.setAdapter(adapter);
    }


    public void doQryMeList() {
        doRequest(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                dismissProgressDialog();
                PurchaseModel model = (PurchaseModel) responseObj;
                List<Data.Item> dataList = model.getData().getList();
                if (dataList != null && dataList.size() > 0) {
                    adapter.updateData(dataList);
                } else {
                    noDataTv.setVisibility(View.VISIBLE);
                    noDataTv.setText("暂无数据");
                }


            }

            @Override
            public void onFailure(Object responseObj) {
                dismissProgressDialog();
                Log.e("msg", "failure");
            }
        });
    }

    private void doRequest(DisposeDataListener listener) {
        RequestParams params = new RequestParams();
        params.put("buyUserId", (String) ShareUtil.get(getActivity(), Constant.user_id, ""));
        RequestUtil.getRequest(Constant.URL3 + "queryUserBuyTicketList.api", params, listener, PurchaseModel.class);
        showProgressDialog();
    }

    public void clearData(){
        if (adapter.isEmpty()) return;
        adapter.clearData();
    }


}
