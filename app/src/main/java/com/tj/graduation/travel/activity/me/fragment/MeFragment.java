package com.tj.graduation.travel.activity.me.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tj.graduation.travel.R;
import com.tj.graduation.travel.base.BaseFragment;

/**
 * 我的界面
 * Created by wangsong on 2019/3/3.
 */

public class MeFragment extends BaseFragment {

    public static MeFragment newInstance(){
        MeFragment meFragment = new MeFragment();
        return meFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.me_fragment,container,false);
    }


}
