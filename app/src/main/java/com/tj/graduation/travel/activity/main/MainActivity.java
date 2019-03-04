package com.tj.graduation.travel.activity.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;

import com.tj.graduation.travel.R;
import com.tj.graduation.travel.activity.Strategy.fragment.StrategyListFragment;
import com.tj.graduation.travel.activity.me.fragment.MeFragment;
import com.tj.graduation.travel.activity.spot.fragment.SpotListFragment;
import com.tj.graduation.travel.base.BaseFragmentActivity;

/**
 * Created by wangsong on 2019/3/3.
 */

public class MainActivity extends BaseFragmentActivity implements View.OnClickListener {

    private SpotListFragment spotListFragment;
    private StrategyListFragment strategyListFragment;
    private MeFragment meFragment;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideTitle();
        setContentView(R.layout.main_activity);
        initView();
        initFirstFragment();

    }

    private void initView() {

        LinearLayout spotLL = findViewById(R.id.ll_main_spot);
        LinearLayout strategyLL = findViewById(R.id.ll_main_strategy);
        LinearLayout meLL = findViewById(R.id.ll_main_me);

        spotLL.setOnClickListener(this);
        strategyLL.setOnClickListener(this);
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
        if (meFragment != null) {
            fragmentTransaction.hide(meFragment);
        }

    }

    @Override
    public void onClick(View view) {

        hideFragment();
        switch (view.getId()) {
            case R.id.ll_main_spot:

                if (spotListFragment == null) {
                    spotListFragment = SpotListFragment.newInstance();
                    fragmentTransaction.add(R.id.fl_main_fragment, spotListFragment);
                } else {
                    fragmentTransaction.show(spotListFragment);
                }
                break;

            case R.id.ll_main_strategy:

                if (strategyListFragment == null) {
                    strategyListFragment = StrategyListFragment.newInstance();
                    fragmentTransaction.add(R.id.fl_main_fragment, strategyListFragment);

                } else {
                    fragmentTransaction.show(strategyListFragment);
                }
                break;

            case R.id.ll_main_me:

                if (meFragment == null) {
                    meFragment = MeFragment.newInstance();
                    fragmentTransaction.add(R.id.fl_main_fragment, meFragment);
                } else {
                    fragmentTransaction.show(meFragment);
                }

                break;

        }
        fragmentTransaction.commit();

    }
}
