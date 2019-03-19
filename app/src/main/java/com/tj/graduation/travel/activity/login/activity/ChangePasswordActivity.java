package com.tj.graduation.travel.activity.login.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tj.graduation.travel.R;
import com.tj.graduation.travel.base.BaseActivity;

public class ChangePasswordActivity extends BaseActivity {

    private EditText btn_pwd_sure;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestCustomTitle();
        setContentView(R.layout.register);
        setCustomTitle();
        setTItle("注册");
        btn_pwd_sure = findViewById(R.id.register_name);
        btn_pwd_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
