package com.tj.graduation.travel.activity.login.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.tj.graduation.travel.Constant;
import com.tj.graduation.travel.R;
import com.tj.graduation.travel.base.BaseActivity;
import com.tj.graduation.travel.model.CommentSubmitModel;
import com.tj.graduation.travel.util.StringUtils;
import com.tj.graduation.travel.util.ToastUtil;
import com.tj.graduation.travel.util.http.RequestUtil;
import com.tj.graduation.travel.util.http.listener.DisposeDataListener;
import com.tj.graduation.travel.util.http.request.RequestParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ForgotPasswordActivity extends BaseActivity implements AdapterView.OnItemSelectedListener{

    private EditText forgot_name;
    private EditText forgot_answer;
    private Spinner spinner;
    private List<String> list;
    private ArrayAdapter<String> adapter;
    private String question = "";
    private Button forgot_submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestCustomTitle();
        setContentView(R.layout.forgot_passward);
        setCustomTitle();
        setTItle("找回密码");
        initView();
    }
    private void initView(){
        forgot_name = findViewById(R.id.forgot_name);
        forgot_answer = findViewById(R.id.forgot_answer);
        spinner = findViewById(R.id.forgot_down);
        forgot_submit = findViewById(R.id.forgot_submit);

        list=new ArrayList<String>();
        list.add("您母亲的生日是？");
        list.add("您最熟悉的学校宿舍舍友名字是？");
        list.add("您小学班主任的名字是？");
        list.add("您的学号（或工号）是？");
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        forgot_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(StringUtils.isEmpty(forgot_name.getText().toString())||StringUtils.isEmpty(forgot_answer.getText().toString())){
                    ToastUtil.showToastText(getBaseContext(), "请将信息填写完整");
                }else{
                    doQryMeList();
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
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void doQryMeList() {
        doRequest(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                dismissProgressDialog();
                //返回参数和评论提交一样--复用
                CommentSubmitModel model = (CommentSubmitModel) responseObj;
                ToastUtil.showToastTextLong(getBaseContext(), model.getMsg());
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        finish(); //执行
                    }
                };
                timer.schedule(task, 1000 * 4);
            }

            @Override
            public void onFailure(Object responseObj) {
                dismissProgressDialog();
                Log.e("msg", "failure");
//                CommentSubmitModel model = (CommentSubmitModel) responseObj;
//                ToastUtil.showToastText(getBaseContext(), model.getMsg());
            }
        });
    }

    private void doRequest(DisposeDataListener listener) {
        RequestParams params = new RequestParams();
        params.put("loginName", forgot_name.getText().toString());
        params.put("cusAnswer", forgot_answer.getText().toString());
        RequestUtil.getRequest(Constant.URL_user + "forgetPassWord.api", params, listener, CommentSubmitModel.class);
        showProgressDialog();
    }
}
