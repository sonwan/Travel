package com.tj.graduation.travel.activity.purchase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tj.graduation.travel.R;
import com.tj.graduation.travel.model.PurchaseModel;

import java.util.List;

public class PurchaseRecordsAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<PurchaseModel.Data.Item> purchaseList;

    public PurchaseRecordsAdapter(Context context, List<PurchaseModel.Data.Item> purchaseList) {
        this.context = context;
        this.purchaseList = purchaseList;
        inflater = LayoutInflater.from(context);
    }

    public void updateData(List<PurchaseModel.Data.Item> purchaseList) {
        if (purchaseList == null) return;
        this.purchaseList = purchaseList;
        notifyDataSetChanged();

    }

    public void clearData(){
        this.purchaseList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return purchaseList == null ? 0 : purchaseList.size();
    }

    @Override
    public Object getItem(int i) {
        return purchaseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PurchaseModel.Data.Item model = purchaseList.get(i);
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.purchase_records_item, viewGroup, false);
            holder.item_name = view.findViewById(R.id.item_name);
            holder.item_id = view.findViewById(R.id.item_id);
            holder.item_price = view.findViewById(R.id.item_price);
            holder.item_time = view.findViewById(R.id.item_time);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.item_name.setText(model.getSpotName());
        holder.item_id.setText("票号：" + model.getTicketId());
        holder.item_price.setText("价格：" + model.getTicketFee());
        holder.item_time.setText("购票时间:  " + model.getTicketBuyTime());
        return view;
    }

    class ViewHolder {
        TextView item_name;
        TextView item_id;
        TextView item_price;
        TextView item_time;
    }
}
