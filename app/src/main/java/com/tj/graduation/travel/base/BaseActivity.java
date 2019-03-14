package com.tj.graduation.travel.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.tj.graduation.travel.R;
import com.tj.graduation.travel.dialog.DataLoadingProgressDialog;
import com.tj.graduation.travel.util.CustomTitleHelper;

/**
 * Created by wangsong on 2019/3/3.
 */

public class BaseActivity extends Activity {

    private CustomTitleHelper customTitleHelper;
    private DataLoadingProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void hideTitle() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * 在setContentView之前调用
     */
    public void requestCustomTitle() {

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

    }

    /**
     * 自定义标题，在setContentView之后调用
     */
    public void setCustomTitle() {

        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout);
        this.customTitleHelper = new CustomTitleHelper(this);

    }

    public void setTItle(String title) {
        customTitleHelper.setTitle(title);
    }

    public void setSecondTitle(String secondTitle) {
        customTitleHelper.setSecondTitle(secondTitle);
    }

    /**
     * 显示网略加载弹框
     */
    public void showProgressDialog() {

        if (progressDialog == null) {
            progressDialog = new DataLoadingProgressDialog(this);
        }
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }

    }

    /**
     * 隐藏加载框
     */
    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
