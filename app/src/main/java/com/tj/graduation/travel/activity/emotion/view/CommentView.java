package com.tj.graduation.travel.activity.emotion.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tj.graduation.travel.R;
import com.tj.graduation.travel.activity.emotion.adapter.EmotionGridAdapter;
import com.tj.graduation.travel.activity.emotion.adapter.EmotionPagerAdapter;
import com.tj.graduation.travel.activity.emotion.util.EmotionUtils;
import com.tj.graduation.travel.activity.emotion.util.SpanStringUtils;
import com.tj.graduation.travel.util.StringUtils;
import com.tj.graduation.travel.util.ToastUtil;
import com.tj.graduation.travel.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wWX288287 on 2019/4/26.
 */
public class CommentView extends LinearLayout {

    private Activity activity;
    private static final int GVCOLUME = 7;
    private ViewPager vp;
    private LinearLayout emotionLL;
    private EditText contentEt;
    private View contentView;
    private EmojiIndicatorView indicatorView;
    private LinearLayout mCommentLL;
    private TextView titleTv;
    private ImageView emotionImg;
    private Handler handler;
    private int keyHeight;

    private onCommentFinishListener listener;

    public void setOnCommentFinishListener(onCommentFinishListener listener) {
        this.listener = listener;
    }

    public interface onCommentFinishListener {
        void onCommentFinish(String content);

        void onCommentCancel();

    }


    public CommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.activity = (Activity) context;
        this.handler = new Handler();
    }

    /**
     * 绑定内容布局
     *
     * @param contentView
     */
    public void bindContentView(View contentView) {

        this.contentView = contentView;
    }

    /**
     * 初始化评论弹框
     */
    public void initCommentView() {

        TextView cancelTv = findViewById(R.id.tv_spot_comment_dialog_cancel);
        cancelTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onCommentCancel();
                }
//                hideSoftInput();
                emotionImg.setImageResource(R.drawable.icon_emotion);

            }
        });
        titleTv = findViewById(R.id.tv_comment_dialog_title);
        indicatorView = findViewById(R.id.ll_point_group);
        mCommentLL = findViewById(R.id.ll_comment);
        emotionLL = findViewById(R.id.ll_emotion);
        vp = findViewById(R.id.vp_emotion);
        contentEt = findViewById(R.id.et_spot_comment_dialog_content);
        emotionImg = findViewById(R.id.img_emotion);
        TextView publishTv = findViewById(R.id.tv_spot_comment_dialog_publish);
        publishTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StringUtils.isNotEmpty(contentEt.getText().toString())) {
                    if (listener != null) {
                        listener.onCommentFinish(contentEt.getText().toString());
                    }
                    emotionImg.setImageResource(R.drawable.icon_emotion);
                } else {
                    ToastUtil.showToastText(activity, "内容不能为空");
                }

            }
        });

        contentEt.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP && emotionLL.isShown()) {
                    lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
                    hideEmotionLayout(true);//隐藏表情布局，显示软件盘
                    emotionImg.setImageResource(R.drawable.icon_emotion);

                    //软件盘显示后，释放内容高度
                    contentEt.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            unlockContentHeightDelayed();
                        }
                    }, 0);
                }


                return false;
            }
        });

        emotionImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (emotionLL.isShown()) {
                    lockContentHeight();
                    hideEmotionLayout(true);
                    unlockContentHeightDelayed();
                    emotionImg.setImageResource(R.drawable.icon_emotion);
                } else {
                    if (getSupportSoftInputHeight() != 0) {

                        lockContentHeight();
                        showEmotionLayout();
                        unlockContentHeightDelayed();
                        emotionImg.setImageResource(R.drawable.icon_key);

                    } else {
                        showEmotionLayout();
                    }
                }

            }

        });


    }

    /**
     * 设置弹框标题
     *
     * @param title
     */
    public void setTitle(String title) {
        titleTv.setText(title);
    }

    /**
     * 清除输入的内容
     */
    public void clearInputContent() {
        contentEt.setText("");
    }

    public boolean isShowEmotion() {
        return emotionLL.isShown();
    }

    public void initEmotionView() {
        initEmotionLayout();
        initListener();
    }

    /**
     * 锁定内容高度，防止跳闪
     */
    private void lockContentHeight() {
        LayoutParams params = (LayoutParams) contentView
                .getLayoutParams();
        params.height = contentView.getHeight();
        params.weight = 0.0F;
    }

    /**
     * 释放被锁定的内容高度
     */
    private void unlockContentHeightDelayed() {
        contentEt.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((LayoutParams) contentView.getLayoutParams()).weight = 1.0F;
            }
        }, 0);
    }

    /**
     * 显示表情编辑框
     */
    private void showEmotionLayout() {


        LayoutParams params = (LayoutParams) emotionLL
                .getLayoutParams();
        int softHeight = getSupportSoftInputHeight();
        if (softHeight == 0) {
            softHeight = keyHeight;
        }
        Utils.hideSoftKeyboard(contentEt, activity);
        params.height = softHeight;
        emotionLL.setLayoutParams(params);
        emotionLL.setVisibility(View.VISIBLE);
        emotionImg.setImageResource(R.drawable.icon_key);

    }

    /**
     * 隐藏表情编辑框并是否显示键盘
     *
     * @param isShowSoftInput
     */
    public void hideEmotionLayout(boolean isShowSoftInput) {

        if (emotionLL.isShown()) {
            emotionLL.setVisibility(View.GONE);
            if (isShowSoftInput) {
                Utils.showSoftKeyboard(contentEt, activity);
            }

        }
    }


    /**
     * 初始化监听器
     */
    protected void initListener() {

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int oldPagerPos = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indicatorView.playByStartPointToNext(oldPagerPos, position);
                oldPagerPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化表情编辑框的布局
     */
    private void initEmotionLayout() {

        List<String> emotionList = new ArrayList<>();
        for (String emotion : EmotionUtils.EMOTION_CLASSIC_MAP.keySet()) {
            emotionList.add(emotion);
        }
        List<GridView> gridViews = new ArrayList<>();

        int screenWidth = Utils.getScreenWidth(activity);
        int space = Utils.dp2px(activity, 12);
        int itemWidth = (screenWidth - space * 8) / 7;
        int gvHeight = space * 6 + itemWidth * 3;

        Log.e("CommentDialog", gvHeight + "---" + itemWidth);

        int count = getPageCount(emotionList.size(), 20);
        for (int i = 0; i < count; i++) {
            List<String> perList = getEmotionItemList(emotionList, i, 20);
            GridView gridView = new GridView(activity);
//            gridView.setBackgroundColor(activity.getResources().getColor(R.color.line_color));
            gridView.setNumColumns(GVCOLUME);
            gridView.setVerticalSpacing(space * 2);
            gridView.setPadding(space, space, space, space);
            gridView.setHorizontalSpacing(space);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(screenWidth, gvHeight);
            gridView.setLayoutParams(params);
            EmotionGridAdapter gridadapter = new EmotionGridAdapter(activity, perList, itemWidth);
            gridView.setAdapter(gridadapter);
            gridView.setOnItemClickListener(new onEmotionItemClickListener(perList));
            gridViews.add(gridView);

        }

        EmotionPagerAdapter pagerAdapter = new EmotionPagerAdapter(gridViews);
        vp.setAdapter(pagerAdapter);

        if (gridViews.size() > 1) {
            indicatorView.setVisibility(View.VISIBLE);
            indicatorView.initIndicator(gridViews.size());
        } else {
            indicatorView.setVisibility(View.GONE);
        }


        initSoftInput();

    }

    class onEmotionItemClickListener implements AdapterView.OnItemClickListener {

        private List<String> itemList;

        public onEmotionItemClickListener(List<String> itemList) {
            this.itemList = itemList;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if (position == itemList.size()) {

                contentEt.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent
                        .KEYCODE_DEL));
            } else {
                // 如果点击了表情,则添加到输入框中
                String emotionName = itemList.get(position);

                // 获取当前光标位置,在指定位置上添加表情图片文本
                int curPosition = contentEt.getSelectionStart();
                StringBuilder sb = new StringBuilder(contentEt.getText().toString());
                sb.insert(curPosition, emotionName);

                // 特殊文字处理,将表情等转换一下
                contentEt.setText(SpanStringUtils.getEmotionContent(activity, contentEt, sb.toString()));

                // 将光标设置到新增完表情的右侧
                contentEt.setSelection(curPosition + emotionName.length());
            }

        }
    }

    /**
     * 开始显示键盘
     */
    private void initSoftInput() {

        contentEt.requestFocus();
        Utils.showSoftKeyboard(contentEt, activity);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                keyHeight = getSupportSoftInputHeight();

            }
        }, 200);


    }

    public void hideSoftInput() {
        Utils.hideSoftKeyboard(contentEt, activity);
    }

    /**
     * 获取表情编辑框每一页的数据
     *
     * @param emotionList
     * @param index
     * @param percount
     * @return
     */
    private List<String> getEmotionItemList(List<String> emotionList, int index, int percount) {

        List<String> list = new ArrayList<>();

        int start = index * percount;
        int end = (index + 1) * percount;
        end = Math.min(end, emotionList.size());

        for (int i = start; i < end; i++) {
            list.add(emotionList.get(i));
        }
        return list;


    }

    /**
     * 通过总数和每页的表情个数得到有几页
     *
     * @param totalcount
     * @param percount
     * @return
     */
    private int getPageCount(int totalcount, int percount) {

        return (totalcount + percount - 1) / percount;

    }

    /**
     * 获取键盘的高度
     *
     * @return
     */
    private int getSupportSoftInputHeight() {

        Rect rect = new Rect();

        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int screenHeight = activity.getWindow().getDecorView().getRootView().getHeight();
        int softInputHeight = screenHeight - rect.bottom;
        /**
         * 某些Android版本下，没有显示软键盘时减出来的高度总是144，而不是零，
         * 这是因为高度是包括了虚拟按键栏的(例如华为系列)，所以在API Level高于20时，
         * 我们需要减去底部虚拟按键栏的高度（如果有的话）
         */
        if (Build.VERSION.SDK_INT >= 20) {
            // When SDK Level >= 20 (Android L), the softInputHeight will contain the height of softButtonsBar (if has)
            softInputHeight = softInputHeight - getSoftButtonsBarHeight();
        }
        return softInputHeight;


    }

    /**
     * 底部虚拟按键栏的高度
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private int getSoftButtonsBarHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        //这个方法获取可能不是真实屏幕的高度
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        //获取当前屏幕的真实高度
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }


}
