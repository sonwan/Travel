package com.tj.graduation.travel.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tj.graduation.travel.R;
import com.tj.graduation.travel.activity.spot.activity.SpotDetailActivity;
import com.tj.graduation.travel.util.ToastUtil;
import com.tj.graduation.travel.util.Utils;

/**
 * Created by wangsong on 2019/3/8.
 */

public class SpotBuyDialog extends Dialog implements View.OnClickListener {
    private Activity activity;
    private EditText valueEt;
    private TextView leftTv, rightTv;
    private TextView allTv;
    private int currentnum = 1;
    private int maxnum = 99;
    private int price = 20;

    public SpotBuyDialog(@NonNull Context context) {
        super(context, R.style.loading_dialog);
        this.activity = (Activity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spot_buy_dialog);
        setDialogSize();

        valueEt = findViewById(R.id.et_spot_value);
        leftTv = findViewById(R.id.tv_value_left);
        rightTv = findViewById(R.id.tv_value_right);
        leftTv.setSelected(false);

        allTv = findViewById(R.id.tv_spot_all);
        allTv.setText(price + "");

        TextView buyTv = findViewById(R.id.tv_buy);

        leftTv.setOnClickListener(this);
        rightTv.setOnClickListener(this);
        buyTv.setOnClickListener(this);

        valueEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String str = s.toString();
                if (str != null && str.length() > 0) {
                    currentnum = (int) Double.parseDouble(str);
                    if (Double.parseDouble(str) > 1) {
                        if (Double.parseDouble(str) > 99) {
                            valueEt.setText(99 + "");
                            valueEt.setSelection(str.length() - 1);
                            currentnum = 99;
                            ToastUtil.showToastText(activity, "最多99个");

                        }
                        leftTv.setSelected(true);
                    } else {
                        leftTv.setSelected(false);
                    }
                    allTv.setText(currentnum * price + "");

                }


            }
        });
        valueEt.setOnClickListener(this);

    }

    private void setDialogSize() {
        DisplayMetrics d = activity.getResources().getDisplayMetrics();
        WindowManager.LayoutParams l = getWindow().getAttributes();
        l.width = (int) (d.widthPixels * 0.8);

        getWindow().setAttributes(l);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_value_left:

                if (currentnum == 1) {
                    leftTv.setSelected(false);
                    ToastUtil.showToastText(activity, "至少有一个");
                } else {
                    --currentnum;
                    valueEt.setText(currentnum + "");
                }

                allTv.setText(price * currentnum + "");

                break;

            case R.id.tv_value_right:

                if (currentnum >= maxnum) {
                    currentnum = maxnum;
                    valueEt.setText(currentnum + "");
                    ToastUtil.showToastText(activity, "最多99个");
                } else {
                    ++currentnum;
                    valueEt.setText(currentnum + "");
                }
                allTv.setText(price * currentnum + "");

                break;

            case R.id.et_spot_value:

                valueEt.setFocusable(true);
                valueEt.setFocusableInTouchMode(true);
                valueEt.requestFocus();
                Utils.showSoftKeyboard(valueEt, activity);

                break;

            case R.id.tv_buy:
                ToastUtil.showToastText(activity, "购买");
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                Log.e("dispatchTouchEvent", "333333333");
                Utils.hideSoftKeyboard(valueEt, activity);
                return super.dispatchTouchEvent(ev);
            }
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);

    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }


}
