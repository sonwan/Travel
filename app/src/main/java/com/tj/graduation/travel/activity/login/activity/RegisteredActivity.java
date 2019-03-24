package com.tj.graduation.travel.activity.login.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.tj.graduation.travel.Constant;
import com.tj.graduation.travel.R;
import com.tj.graduation.travel.base.BaseActivity;
import com.tj.graduation.travel.model.UserRegisteredModel;
import com.tj.graduation.travel.util.StringUtils;
import com.tj.graduation.travel.util.ToastUtil;
import com.tj.graduation.travel.util.http.RequestUtil;
import com.tj.graduation.travel.util.http.listener.DisposeDataListener;
import com.tj.graduation.travel.util.http.request.RequestParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RegisteredActivity extends BaseActivity  implements AdapterView.OnItemSelectedListener {

    private EditText register_name;
    private EditText register_username;
    private EditText register_pwd;
    private EditText register_pwd_confirm;
    private EditText register_answer;
    private RadioGroup radioGroup_sex_id;
    private RadioButton boy_id;
    private RadioButton girl_id;
    private TextView aaaa;
    private TextView tvShow;
    private TextView register_submit;
    private Spinner spDwon;
    private List<String> list;
    private ArrayAdapter<String> adapter;
    private String question = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestCustomTitle();
        setContentView(R.layout.register);
        setCustomTitle();
        setTItle("注册");
        initView();
    }
    public void initView(){
        register_name = findViewById(R.id.register_name);
        register_username = findViewById(R.id.register_username);
        register_pwd = findViewById(R.id.register_pwd);
        register_pwd_confirm = findViewById(R.id.register_pwd_confirm);
        register_answer = findViewById(R.id.register_answer);
        radioGroup_sex_id = findViewById(R.id.radioGroup_sex_id);
        boy_id = findViewById(R.id.boy_id);
        girl_id = findViewById(R.id.girl_id);
        tvShow = findViewById(R.id.tvShow);
        spDwon = findViewById(R.id.spDwon);
        register_submit = findViewById(R.id.register_submit);
        aaaa = findViewById(R.id.aaaa);

        tvShow.setText("请选择您的密保问题");
        list=new ArrayList<String>();
        list.add("您母亲的生日是？");
        list.add("您最熟悉的学校宿舍舍友名字是？");
        list.add("您小学班主任的名字是？");
        list.add("您的学号（或工号）是？");
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spDwon.setAdapter(adapter);
        spDwon.setOnItemSelectedListener(this);

        register_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(StringUtils.isEmpty(register_name.getText())||StringUtils.isEmpty(register_username.getText())
                    || StringUtils.isEmpty(register_pwd.getText())||StringUtils.isEmpty(register_pwd_confirm.getText())
                    || StringUtils.isEmpty(register_answer.getText())){
                    ToastUtil.showToastText(getBaseContext(),"请将信息填写完整");
                }else if(!(register_pwd.getText().toString()).equals(register_pwd_confirm.getText().toString())){
                    ToastUtil.showToastText(getBaseContext(),"两次输入密码不一致");
                }else{
                    doQryMeList();
                }
            }
        });

        aaaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boy_id.isChecked()){
                    ToastUtil.showToastText(getBaseContext(),"男");
                }
                if (girl_id.isChecked()){
                    ToastUtil.showToastText(getBaseContext(),"女");
                }
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        question=adapter.getItem(position);   //获取选中的那一项
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    private void doQryMeList() {
        doRequest(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                dismissProgressDialog();
                UserRegisteredModel model = (UserRegisteredModel) responseObj;
                if ("-1".equals(model.getCode())) {
                    ToastUtil.showToastText(getBaseContext(), "注册失败！该用户已存在！");
                } else {
//                    ShareUtil.put(getBaseContext(), Constant.loginName, model.getData().getLoginName());
//                    ShareUtil.put(getBaseContext(), Constant.username, model.getData().getUserName());
//                    ShareUtil.put(getBaseContext(), Constant.login, "true");
//                    ToastUtil.showToastText(getBaseContext(), model.getMsg());
//                    finish();
                    ToastUtil.showToastText(getBaseContext(), model.getMsg());
                    Timer timer = new Timer();
                      TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                          finish(); //执行
                        }
                      };
                    timer.schedule(task, 1000 * 3);//10秒后
                }
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
        String name = register_name.getText().toString();
        String userid = register_username.getText().toString();
        String password = register_pwd.getText().toString();
        String answer = register_answer.getText().toString();
        params.put("loginName", userid);
        params.put("userName", name);
        params.put("passWord", password);
        params.put("cusQuestion", question);
        params.put("cusAnswer", answer);
        params.put("age", "21");
        String sex = "";
        if (boy_id.isChecked()){
            sex = "男";
        }
        if (girl_id.isChecked()){
            sex = "女";
        }
        params.put("sex", sex);
        RequestUtil.getRequest(Constant.URL_user + "register.api", params, listener, UserRegisteredModel.class);
        showProgressDialog();
    }
}
