package com.tj.graduation.travel.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.tj.graduation.travel.R;

/**
 * Created by wangsong on 2019/3/26.
 */

public class CancelImageDialog extends Dialog {

    private onImageCancelListener listener;
    private Activity activity;

    public void setOnImageCancelListener(onImageCancelListener listener) {
        this.listener = listener;
    }

    public interface onImageCancelListener {
        void onImageCancel();
    }

    public CancelImageDialog(@NonNull Context context) {
        super(context, R.style.loading_dialog);
        this.activity = (Activity) context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancel_image_dialog);
        setDialogSize();
        TextView cancelTv = findViewById(R.id.tv_cancel);

        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onImageCancel();
                dismiss();
            }
        });
    }

    private void setDialogSize() {
        DisplayMetrics d = activity.getResources().getDisplayMetrics();
        WindowManager.LayoutParams l = getWindow().getAttributes();
        l.width = (int) (d.widthPixels * 0.8);

        getWindow().setAttributes(l);
    }
}
