package com.tj.graduation.travel.activity.me.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tj.graduation.travel.Constant;
import com.tj.graduation.travel.R;
import com.tj.graduation.travel.activity.login.activity.LoginActivity;
import com.tj.graduation.travel.activity.purchase.activity.PurchaseRecordsActivity;
import com.tj.graduation.travel.base.BaseFragment;
import com.tj.graduation.travel.model.SpotMeModel;
import com.tj.graduation.travel.util.ShareUtil;
import com.tj.graduation.travel.util.ToastUtil;
import com.tj.graduation.travel.util.glide.GlideUtil;
import com.tj.graduation.travel.util.http.RequestUtil;
import com.tj.graduation.travel.util.http.listener.DisposeDataListener;
import com.tj.graduation.travel.util.http.request.RequestParams;

/**
 * 我的界面
 * Created by wangsong on 2019/3/3.
 */

public class MeFragment extends BaseFragment {

    private ImageView head_img_tx;
    private TextView user_name;
    private TextView account_tv;
    private LinearLayout ll_collection;
    private LinearLayout ll_purchase;
    private TextView tv_login;
    private LinearLayout ll_login_btn;

    public static MeFragment newInstance(){
        MeFragment meFragment = new MeFragment();
        return meFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_fragment,container,false);
        head_img_tx = view.findViewById(R.id.h_head);
        user_name = view.findViewById(R.id.user_name);
        account_tv = view.findViewById(R.id.tv_right_text);
        ll_collection = view .findViewById(R.id.ll_root_collection);
        ll_purchase = view .findViewById(R.id.ll_root_purchase);
        tv_login = view.findViewById(R.id.login_btn);
        ll_login_btn = view.findViewById(R.id.ll_login_btn);
//        ShareUtil.put(getActivity(),"loginName","zhangsan");
        ShareUtil.put(getActivity(),"login","true");
//        ShareUtil.put(getActivity(),"username","张三");
        init();

        user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ShareUtil.put(getActivity(),"loginName","zhangsan");
//                ShareUtil.put(getActivity(),"login","true");
//                String a = (String) ShareUtil.get(getActivity(),"username","");
//                String b = (String) ShareUtil.get(getActivity(),"login","");
//                Toast.makeText(getActivity(), a+b, Toast.LENGTH_SHORT).show();
//                doQryMeList();
            }
        });
        ll_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("false".equals(ShareUtil.get(getActivity(),"login",""))){
                    ToastUtil.showToastText(getActivity(),"您还未登陆...");
                }else{
                    Intent i = new Intent(getActivity(), PurchaseRecordsActivity.class);
                    startActivity(i);
                }
            }
        });
        return view;
    }
    private void init(){
        if("false".equals(ShareUtil.get(getActivity(),"login",""))){
            user_name.setText("您还未登录...");
            account_tv.setText("0");
            ll_login_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), LoginActivity.class);
                    startActivity(i);
                }
            });
        }else if("true".equals(ShareUtil.get(getActivity(),"login",""))){
            doQryMeList();
            user_name.setText(ShareUtil.get(getActivity(),"username","")+"");
            tv_login.setText("退出登录");
            ll_login_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog alertDialog2 = new AlertDialog.Builder(getActivity())
                            .setTitle("是否退出登录？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ShareUtil.put(getActivity(),"login","false");
                                    user_name.setText("您还未登录...");
                                    account_tv.setText("0");
                                    tv_login.setText("点击登录");
//                                    ll_login_btn.setBackgroundColor(Color.parseColor("#FF8C00"));
                                    head_img_tx.setImageDrawable(getActivity().getDrawable(R.mipmap.tx_not));
                                    ll_login_btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(getActivity(), LoginActivity.class);
                                            startActivity(i);
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    Toast.makeText(getActivity(), "这是取消按钮", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .create();
                    alertDialog2.show();
                }
            });
        }
    }

    private void doQryMeList() {
        doRequest(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                dismissProgressDialog();
                SpotMeModel model = (SpotMeModel) responseObj;
                account_tv.setText(model.getData().getAccountFee()+"");
                GlideUtil.LoadPic(getActivity(),model.getData().getHeadPicUrl(),head_img_tx);
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
        params.put("loginName", (String) ShareUtil.get(getActivity(),"loginName",""));
        RequestUtil.getRequest(Constant.URL2 + "queryUserInfo.api", params, listener, SpotMeModel.class);
        showProgressDialog();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
