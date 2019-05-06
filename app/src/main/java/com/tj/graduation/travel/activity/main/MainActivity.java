package com.tj.graduation.travel.activity.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;

import com.tj.graduation.travel.Constant;
import com.tj.graduation.travel.R;
import com.tj.graduation.travel.activity.Strategy.fragment.StrategyListFragment;
import com.tj.graduation.travel.activity.me.fragment.MeFragment;
import com.tj.graduation.travel.activity.order.fragment.OrderListFragment;
import com.tj.graduation.travel.activity.spot.fragment.SpotListFragment;
import com.tj.graduation.travel.base.BaseFragmentActivity;
import com.tj.graduation.travel.util.ShareUtil;
import com.tj.graduation.travel.util.StringUtils;

/**
 * Created by wangsong on 2019/3/3.
 */

public class MainActivity extends BaseFragmentActivity implements View.OnClickListener, MeFragment.onLoginStatusListener {

    private SpotListFragment spotListFragment;
    private StrategyListFragment strategyListFragment;
    private OrderListFragment orderListFragment;
    private MeFragment meFragment;
    private FragmentTransaction fragmentTransaction;

    private LinearLayout spotLL;
    private LinearLayout strategyLL;
    private LinearLayout orderLL;
    private LinearLayout meLL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideTitle();
        setContentView(R.layout.main_activity);
        initView();
        initFirstFragment();

    }

    private void initView() {

        spotLL = findViewById(R.id.ll_main_spot);
        spotLL.setSelected(true);
        strategyLL = findViewById(R.id.ll_main_strategy);
        orderLL = findViewById(R.id.ll_main_order);
        meLL = findViewById(R.id.ll_main_me);

        spotLL.setOnClickListener(this);
        strategyLL.setOnClickListener(this);
        orderLL.setOnClickListener(this);
        meLL.setOnClickListener(this);


    }

    private void initFirstFragment() {
        spotListFragment = SpotListFragment.newInstance();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fl_main_fragment, spotListFragment);
        fragmentTransaction.commit();
    }

    private void hideFragment() {

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (spotListFragment != null) {
            fragmentTransaction.hide(spotListFragment);
        }
        if (strategyListFragment != null) {
            fragmentTransaction.hide(strategyListFragment);
        }
        if (orderListFragment != null) {
            fragmentTransaction.hide(orderListFragment);
        }
        if (meFragment != null) {
            fragmentTransaction.hide(meFragment);
        }

    }

    private void setTabDefault() {
        spotLL.setSelected(false);
        strategyLL.setSelected(false);
        orderLL.setSelected(false);
        meLL.setSelected(false);
    }

    @Override
    public void onClick(View view) {

        hideFragment();
        setTabDefault();
        switch (view.getId()) {
            case R.id.ll_main_spot:

                spotLL.setSelected(true);
                if (spotListFragment == null) {
                    spotListFragment = SpotListFragment.newInstance();
                    fragmentTransaction.add(R.id.fl_main_fragment, spotListFragment);
                } else {
                    fragmentTransaction.show(spotListFragment);
                }
                break;

            case R.id.ll_main_strategy:

                strategyLL.setSelected(true);
                if (strategyListFragment == null) {
                    strategyListFragment = StrategyListFragment.newInstance();
                    fragmentTransaction.add(R.id.fl_main_fragment, strategyListFragment);

                } else {
                    fragmentTransaction.show(strategyListFragment);
//                    String isPublishGl = (String) ShareUtil.get(this, Constant.IS_PUBLISH_GL, "");
//                    if (StringUtils.isNotEmpty(isPublishGl) && "true".equals(isPublishGl)) {
                    strategyListFragment.setType("refresh");
                    strategyListFragment.doGuidelistQuery();
//                        ShareUtil.put(this, Constant.IS_PUBLISH_GL, "");
//                    }
                }
                break;

            case R.id.ll_main_order:

                orderLL.setSelected(true);
                if (orderListFragment == null) {
                    orderListFragment = OrderListFragment.newInstance();
                    fragmentTransaction.add(R.id.fl_main_fragment, orderListFragment);
                } else {
                    fragmentTransaction.show(orderListFragment);
                    String isHaveBuy = (String) ShareUtil.get(this, Constant.IS_HAVE_BUY, "");
                    if (StringUtils.isNotEmpty(isHaveBuy) && "TRUE".equals(isHaveBuy)) {
                        orderListFragment.doQryMeList();
                        ShareUtil.put(this, Constant.IS_HAVE_BUY, "FALSE");
                    }
                }

                break;
            case R.id.ll_main_me:

                meLL.setSelected(true);
                if (meFragment == null) {
                    meFragment = MeFragment.newInstance();
                    meFragment.setOnLoginStatusListener(this);
                    fragmentTransaction.add(R.id.fl_main_fragment, meFragment);
                } else {
                    fragmentTransaction.show(meFragment);
                }

                break;

        }
        fragmentTransaction.commit();

    }

    //监听是否退出
    @Override
    public void onLogout() {

        if (orderListFragment != null) {
            orderListFragment.clearData();
            orderListFragment = null;
        }
    }
}
