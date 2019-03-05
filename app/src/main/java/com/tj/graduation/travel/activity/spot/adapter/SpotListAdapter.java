package com.tj.graduation.travel.activity.spot.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tj.graduation.travel.R;
import com.tj.graduation.travel.model.SpotListModel;
import com.tj.graduation.travel.util.glide.GlideUtil;

import java.util.List;

/**
 * 景点列表adapter
 * Created by wangsong on 2019/3/4.
 */

public class SpotListAdapter extends BaseAdapter {

    private Context context;
    private List<SpotListModel.Data.Item> list;
    private LayoutInflater inflater;

    public SpotListAdapter(Context context, List<SpotListModel.Data.Item> list) {
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
        SpotListModel.Data.Item model = list.get(i);
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.spot_list_adapter, viewGroup, false);
            holder.nameTv = view.findViewById(R.id.tv_spot_list_name);
            holder.descTv = view.findViewById(R.id.tv_spot_list_desc);
            holder.img = view.findViewById(R.id.img_spot_list);
            holder.moneyTv = view.findViewById(R.id.tv_spot_list_money);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.nameTv.setText(model.getName());
        holder.descTv.setText(model.getDescInfo());
        holder.moneyTv.setText("¥" + model.getTicketPrice() + "起");

        GlideUtil.LoadPic(context, model.getShortPicUrl(), holder.img);

        return view;
    }

    class ViewHolder {
        TextView nameTv;
        TextView descTv;
        ImageView img;
        TextView moneyTv;
    }


}
