package com.tj.graduation.travel.activity.spot.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tj.graduation.travel.R;
import com.tj.graduation.travel.base.BaseFragment;

/**
 * 景点列表界面
 * Created by wangsong on 2019/3/3.
 */

public class SpotListFragment extends BaseFragment {

    public static SpotListFragment newInstance(){
        SpotListFragment spotListFragment = new SpotListFragment();
        return spotListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.spot_list_fragment,container,false);
    }
}
