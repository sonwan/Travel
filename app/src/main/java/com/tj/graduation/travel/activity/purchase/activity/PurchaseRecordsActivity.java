package com.tj.graduation.travel.activity.purchase.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;

import com.tj.graduation.travel.Constant;
import com.tj.graduation.travel.R;
import com.tj.graduation.travel.activity.purchase.adapter.PurchaseRecordsAdapter;
import com.tj.graduation.travel.base.BaseActivity;
import com.tj.graduation.travel.model.PurchaseModel;
import com.tj.graduation.travel.util.ShareUtil;
import com.tj.graduation.travel.util.http.RequestUtil;
import com.tj.graduation.travel.util.http.listener.DisposeDataListener;
import com.tj.graduation.travel.util.http.request.RequestParams;

import java.util.List;

public class PurchaseRecordsActivity extends BaseActivity {

    private ListView lv;
    private PurchaseRecordsAdapter adapter;
    private List<PurchaseModel.Data.Item> purchaseList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestCustomTitle();
        setContentView(R.layout.purchase_records_list);
        setCustomTitle();
        setTItle("购票清单");
        lv = findViewById(R.id.purchase_list);
        doQryMeList();
    }

    private void doQryMeList() {
        doRequest(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                dismissProgressDialog();
                PurchaseModel model = (PurchaseModel) responseObj;
//                if(!"0".equals(model.getCode())){
////                    ToastUtil.showToastText(getBaseContext(),model.getMsg());
//                }else {
                    purchaseList = model.getData().getList();
                    adapter = new PurchaseRecordsAdapter(getBaseContext(),purchaseList);
                    lv.setAdapter(adapter);
//                }
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
        params.put("buyUserId",  "1");
        RequestUtil.getRequest(Constant.URL3 + "queryUserBuyTicketList.api", params, listener, PurchaseModel.class);
        showProgressDialog();
    }
}
