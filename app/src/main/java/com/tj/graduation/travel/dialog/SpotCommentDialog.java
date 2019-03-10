package com.tj.graduation.travel.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tj.graduation.travel.R;

/**
 * Created by wangsong on 2019/3/10.
 */

public class SpotCommentDialog extends Dialog {

    private OnSpotCommentPublishListener listener;

    public interface OnSpotCommentPublishListener {
        void OnSpotCommentPublish(String content);
    }

    public void setOnSpotCommentPublishListener(OnSpotCommentPublishListener listener) {
        this.listener = listener;
    }

    public SpotCommentDialog(@NonNull Context context) {
        super(context, R.style.dialog_anim);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spot_comment_dialog);
        TextView cancelTv = findViewById(R.id.tv_spot_comment_dialog_cancel);
        TextView publishTv = findViewById(R.id.tv_spot_comment_dialog_publish);
        final EditText contentEt = findViewById(R.id.et_spot_comment_dialog_content);

        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        publishTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnSpotCommentPublish(contentEt.getText().toString().trim());
            }
        });

    }
}
