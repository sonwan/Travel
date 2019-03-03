package com.tj.graduation.travel.activity.Strategy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tj.graduation.travel.R;
import com.tj.graduation.travel.base.BaseFragment;

/**
 * 攻略列表界面
 * Created by wangsong on 2019/3/3.
 */

public class StrategyListFragment extends BaseFragment {

    public static StrategyListFragment newInstance() {
        StrategyListFragment strategyListFragment = new StrategyListFragment();
        return strategyListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.strategy_list_fragment, container, false);
    }
}
