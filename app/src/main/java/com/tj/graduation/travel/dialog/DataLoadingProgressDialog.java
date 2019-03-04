package com.tj.graduation.travel.dialog;

import android.app.Dialog;
import android.content.Context;

import com.tj.graduation.travel.R;

/**
 * Created by wangsong on 2019/3/4.
 */

public class DataLoadingProgressDialog extends Dialog {

    public DataLoadingProgressDialog(Context context) {
        super(context, R.style.loading_dialog);
        setContentView(R.layout.progress_dialog);
    }
}
