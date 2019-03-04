package com.tj.graduation.travel.base;

import android.support.v4.app.Fragment;

import com.tj.graduation.travel.dialog.DataLoadingProgressDialog;

/**
 * Created by wangsong on 2019/3/3.
 */

public class BaseFragment extends Fragment {

    private DataLoadingProgressDialog progressDialog;

    /**
     * 显示网略加载弹框
     */
    public void showProgressDialog() {

        if (progressDialog == null) {
            progressDialog = new DataLoadingProgressDialog(getActivity());
        }
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }

    }

    /**
     * 隐藏加载框
     */
    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

}
