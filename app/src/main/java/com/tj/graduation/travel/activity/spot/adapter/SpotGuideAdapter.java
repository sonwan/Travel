package com.tj.graduation.travel.activity.spot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tj.graduation.travel.R;
import com.tj.graduation.travel.model.GuideModel;

import java.util.List;

/**
 * 景点详情攻略adapter
 * Created by wangsong on 2019/3/14.
 */

public class SpotGuideAdapter extends BaseAdapter {

    private Context context;
    private List<GuideModel> list;
    private LayoutInflater inflater;

    public SpotGuideAdapter(Context context, List<GuideModel> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        GuideModel model = list.get(i);
        ViewHolder holder = null;

        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.spot_guide_adapter, null);
            holder.guideTitleTv = view.findViewById(R.id.tv_guide_title);
            holder.guideDateTv = view.findViewById(R.id.tv_guide_date);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.guideTitleTv.setText(model.getGuideTitle());
        holder.guideDateTv.setText(model.getPublishTime());


        return view;
    }

    class ViewHolder {

        TextView guideTitleTv;
        TextView guideDateTv;
    }
}
