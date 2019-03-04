package com.tj.graduation.travel.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.tj.graduation.travel.R;
import com.tj.graduation.travel.util.CustomTitleHelper;

/**
 * Created by wangsong on 2019/3/3.
 */

public class BaseActivity extends Activity {

    private CustomTitleHelper customTitleHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    /**
     * 在setContentView之前调用
     */
    public void requestCustomTitle() {

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

    }

    public void setCustomTitle() {

        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout);
        this.customTitleHelper = new CustomTitleHelper(this);

    }

    public void setTItle(String title) {
        customTitleHelper.setTitle(title);
    }

}
