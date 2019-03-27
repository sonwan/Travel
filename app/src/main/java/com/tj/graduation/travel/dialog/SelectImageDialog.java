package com.tj.graduation.travel.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.tj.graduation.travel.R;

/**
 * Created by wangsong on 2019/3/26.
 */

public class SelectImageDialog extends Dialog {

    private onImageSelectListener listener;

    public void setOnImageSelectListener(onImageSelectListener listener) {
        this.listener = listener;
    }

    public interface onImageSelectListener {
        void onImageSelect();

        void onImageCamera();
    }

    public SelectImageDialog(@NonNull Context context) {
        super(context, R.style.dialog_anim);
        setContentView(R.layout.select_image_dialog);

        TextView cameraTv = findViewById(R.id.tv_image_camera);
        TextView selectTv = findViewById(R.id.tv_image_select);
        TextView cancelTv = findViewById(R.id.tv_cancel);

        cameraTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onImageCamera();
                dismiss();
            }
        });

        selectTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onImageSelect();
                dismiss();
            }
        });

        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
