package com.tj.graduation.travel.activity.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.tj.graduation.travel.Constant;
import com.tj.graduation.travel.R;
import com.tj.graduation.travel.activity.main.MainActivity;
import com.tj.graduation.travel.base.BaseActivity;
import com.tj.graduation.travel.model.UserLogin;
import com.tj.graduation.travel.util.ShareUtil;
import com.tj.graduation.travel.util.ToastUtil;
import com.tj.graduation.travel.util.http.RequestUtil;
import com.tj.graduation.travel.util.http.listener.DisposeDataListener;
import com.tj.graduation.travel.util.http.request.RequestParams;

public class LoginActivity extends BaseActivity {

    private Button login_btn_login;
    private Button login_btn_register;
    private EditText user_name;
    private EditText pwd;
    private CheckBox checkBox;

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

    private void initView() {
        login_btn_login = findViewById(R.id.login_btn_login);
        login_btn_register = findViewById(R.id.login_btn_register);
        user_name = findViewById(R.id.login_edit_account);
        checkBox = findViewById(R.id.login_remember);
        pwd = findViewById(R.id.login_edit_pwd);
        pwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    if((ShareUtil.get(getBaseContext(),"loginName","")).equals(user_name.getText())){
                        pwd.setText((String)ShareUtil.get(getBaseContext(),"userpwd",""));
                    }
                }
            }
        });
    }

    private void event() {
        login_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(user_name.getText())||"".equals(pwd.getText())) {
                    ToastUtil.showToastText(getBaseContext(), "用户名密码不许为空");
                } else {
                    doQryMeList();
                }
            }
        });
        login_btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), ChangePasswordActivity.class);
                startActivity(i);
            }
        });
    }

    private void doQryMeList() {
        doRequest(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                dismissProgressDialog();
                UserLogin model = (UserLogin) responseObj;
//                if (!"0".equals(model.getCode()+"")) {
//                    ToastUtil.showToastText(getBaseContext(), model.getMsg());
//                } else {

                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if(b){
                                ShareUtil.put(getBaseContext(),"userpwd",pwd.getText());
                            }else{
                                ShareUtil.put(getBaseContext(),"userpwd","");
                            }
                        }
                    });

                    ShareUtil.put(getBaseContext(), "loginName", model.getData().getLoginName());
                    ShareUtil.put(getBaseContext(), "username", model.getData().getUserName());
                    ShareUtil.put(getBaseContext(), "login", "true");
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
//                }
            }

            @Override
            public void onFailure(Object responseObj) {
                dismissProgressDialog();
                Log.e("msg", "failure");
            }
        });
    }

    private void doRequest(DisposeDataListener listener) {
        RequestParams params = new RequestParams();
        String userid = user_name.getText().toString();
        String password = pwd.getText().toString();
        params.put("loginName", userid);
        params.put("passWord", password);
        RequestUtil.getRequest(Constant.URL2 + "queryUserInfo.api", params, listener, UserLogin.class);
        showProgressDialog();
    }
}
