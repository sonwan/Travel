package com.tj.graduation.travel.activity.emotion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tj.graduation.travel.R;
import com.tj.graduation.travel.activity.emotion.util.EmotionUtils;

import java.util.List;

/**
 * Created by wWX288287 on 2019/4/25.
 */
public class EmotionGridAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private int itemWidth;
    private LayoutInflater inflater;

    public EmotionGridAdapter(Context context, List<String> list, int itemWidth) {
        this.context = context;
        this.list = list;
        this.itemWidth = itemWidth;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size() == 0 ? 0 : list.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {

            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.emotion_grid_adapter, null);
            holder.iv = (ImageView) convertView.findViewById(R.id.iv_item_emotion);
            holder.iv.setPadding(itemWidth / 8, itemWidth / 8, itemWidth / 8, itemWidth / 8);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemWidth, itemWidth);
            holder.iv.setLayoutParams(params);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (list.size() > position) {

            holder.iv.setImageResource(EmotionUtils.getImgByName(list.get(position)));

        } else {
            holder.iv.setImageResource(R.drawable.compose_emotion_delete);
        }

        return convertView;
    }

    class ViewHolder {

        ImageView iv;
    }

}
