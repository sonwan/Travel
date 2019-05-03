package com.tj.graduation.travel.activity.spot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tj.graduation.travel.R;
import com.tj.graduation.travel.util.ToastUtil;
import com.tj.graduation.travel.util.Utils;
import com.tj.graduation.travel.util.glide.GlideUtil;

import java.util.List;

/**
 * Created by wangsong on 2019/4/6.
 */

public class LocalPicAdapter extends BaseAdapter {

    private Context context;
    private List<String> picList;
    private LayoutInflater inflater;
    private List<String> selectList;
    private int selectMaxNum = 5;

    public LocalPicAdapter(Context context, List<String> picList) {
        this.context = context;
        this.picList = picList;
        inflater = LayoutInflater.from(context);
    }

    public List<String> getSelectList() {
        return selectList;
    }

    public void setSelectList(List<String> selectList) {
        this.selectList = selectList;
    }

    public void setSelectMaxNum(int selectMaxNum) {
        this.selectMaxNum = selectMaxNum;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }

    @Override
    public int getCount() {
        return picList.size();
    }

    @Override
    public Object getItem(int i) {
        return picList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final String picPath = picList.get(i);
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.local_pic_adapter, null);
            holder.picCb = view.findViewById(R.id.cb_pic);
            holder.picImage = view.findViewById(R.id.img_local);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.picImage.getLayoutParams();
            params.height = Utils.getScreenWidth(context) / 3;
            holder.picImage.setLayoutParams(params);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        if (selectList != null && selectList.contains(picPath)) {
            holder.picCb.setChecked(true);
        } else {
            holder.picCb.setChecked(false);
        }

        GlideUtil.LoadPic(context, picPath, holder.picImage);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectList.contains(picPath)) {
                    selectList.remove(picPath);
                } else {

                    if (selectList.size() == selectMaxNum) {
                        ToastUtil.showToastText(context, "最多选择" + selectMaxNum + "张图片");
                    } else {
                        selectList.add(picPath);
                    }

                }
                notifyDataSetChanged();
            }
        });


        return view;
    }

    class ViewHolder {

        ImageView picImage;
        CheckBox picCb;

    }

}
