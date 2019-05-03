package com.tj.graduation.travel.activity.spot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tj.graduation.travel.R;
import com.tj.graduation.travel.util.Utils;
import com.tj.graduation.travel.util.glide.GlideUtil;

import java.util.List;

/**
 * Created by wangsong on 2019/4/6.
 */

public class GuidePicAdapter extends BaseAdapter {

    private Context context;
    private List<String> selectList;
    private LayoutInflater inflater;
    private onPicAddAndRemoveListener listener;

    public void setOnPicAddAndRemoveListener(onPicAddAndRemoveListener listener) {
        this.listener = listener;
    }

    public interface onPicAddAndRemoveListener {
        void onAdd();

        void onRemove(String path);
    }

    public GuidePicAdapter(Context context, List<String> selectList) {
        this.context = context;
        this.selectList = selectList;
        inflater = LayoutInflater.from(context);
    }

    public void setSelectList(List<String> selectList) {
        this.selectList = selectList;
    }

    @Override
    public int getCount() {
        return selectList == null ? 1 : selectList.size() + 1;
    }

    @Override
    public Object getItem(int i) {
        return selectList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;
        if (view == null) {
            int padding = (int) context.getResources().getDimension(R.dimen.pic_padding);
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.guide_pic_adapter, null);
            holder.picImg = view.findViewById(R.id.img_guide);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.picImg.getLayoutParams();
            params.height = (Utils.getScreenWidth(context) - padding * 7) / 4;
            holder.picImg.setLayoutParams(params);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if (selectList.size() > i) {
            GlideUtil.LoadPic(context, selectList.get(i), holder.picImg);

        } else {
            GlideUtil.LoadResPic(context, R.drawable.icon_plus, holder.picImg);

        }


        return view;
    }

    class ViewHolder {
        ImageView picImg;
    }
}
