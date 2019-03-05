package com.tj.graduation.travel.activity.me.fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tj.graduation.travel.R;
import com.tj.graduation.travel.base.BaseFragment;

/**
 * 我的界面
 * Created by wangsong on 2019/3/3.
 */

public class MeFragment extends BaseFragment {

    private ImageView img_tx;
    private TextView user_name;

    public static MeFragment newInstance(){
        MeFragment meFragment = new MeFragment();
        return meFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_fragment,container,false);
        img_tx = view.findViewById(R.id.h_head);
        user_name = view.findViewById(R.id.user_name);
        return view;
    }

}
