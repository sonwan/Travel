package com.tj.graduation.travel.activity.login.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tj.graduation.travel.R;
import com.tj.graduation.travel.model.CollectionModel;

import java.util.List;

public class CollectionAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<CollectionModel.Model> models;

    public CollectionAdapter(Context context,List<CollectionModel.Model> model){
        this.context =context;
        this.models = model;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int i) {
        return models.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CollectionModel.Model model = models.get(i);
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.collection_item, null);
            holder.guideTitleTv = view.findViewById(R.id.tv_guide_title);
            holder.guideDateTv = view.findViewById(R.id.tv_guide_date);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.guideTitleTv.setText(model.getGuideTitle());
        holder.guideDateTv.setText(model.getCreateTime());

        return view;
    }
    class ViewHolder {

        TextView guideTitleTv;
        TextView guideDateTv;
    }

}
