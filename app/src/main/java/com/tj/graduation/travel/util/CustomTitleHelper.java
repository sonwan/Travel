package com.tj.graduation.travel.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.tj.graduation.travel.R;

/**
 * Created by wangsong on 2019/3/4.
 */

public class CustomTitleHelper {

    private Activity activity;
    private TextView backTv;
    private TextView titleTv;
    private TextView secondTitleTv;

    public CustomTitleHelper(Context context) {
        this.activity = (Activity) context;

        backTv = activity.findViewById(R.id.tv_custom_back);
        titleTv = activity.findViewById(R.id.tv_custom_title);
        secondTitleTv = activity.findViewById(R.id.tv_custom_second_title);
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });

    }

    public void setTitle(String title) {
        titleTv.setText(title);
    }

    public void setSecondTitle(String secondTitle) {
        secondTitleTv.setVisibility(View.VISIBLE);
        secondTitleTv.setText(secondTitle);
    }

}
