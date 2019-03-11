package com.tj.graduation.travel.activity.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tj.graduation.travel.R;
import com.tj.graduation.travel.base.BaseActivity;
import com.tj.graduation.travel.util.ShareUtil;

public class LoginActivity extends BaseActivity {

    private Button login_btn_login;
    private TextView tv_btn_change_pwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestCustomTitle();
        setContentView(R.layout.login);
        setCustomTitle();
        setTItle("登录");
        initView();
        event();
    }
    private void initView(){
        login_btn_login = findViewById(R.id.login_btn_login);
        tv_btn_change_pwd = findViewById(R.id.login_text_change_pwd);
    }
    private void event(){
        login_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareUtil.put(getBaseContext(),"login","true");
                finish();
            }
        });
        tv_btn_change_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), ChangePasswordActivity.class);
                startActivity(i);
            }
        });
    }
}
