package com.tj.graduation.travel.activity.spot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tj.graduation.travel.R;
import com.tj.graduation.travel.activity.emotion.util.SpanStringUtils;
import com.tj.graduation.travel.model.CommentModel.CommentData.CommentList.ReplyModel;

import java.util.List;

/**
 * 评论回复adapter
 * Created by wangsong on 2019/3/25.
 */

public class ReplyAdapter extends BaseAdapter {

    private Context context;
    private List<ReplyModel> list;
    private LayoutInflater inflater;

    public ReplyAdapter(Context context, List<ReplyModel> list) {
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

        ReplyModel model = list.get(i);
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.reply_adapter, null);
            holder.replyNameTv = view.findViewById(R.id.tv_reply_name);
            holder.replyContentTv = view.findViewById(R.id.tv_reply_content);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.replyNameTv.setText(model.getUserName() + "：");
        holder.replyContentTv.setText(SpanStringUtils.getEmotionContent(context, holder.replyContentTv, model.getReplayContent()));
        return view;
    }

    class ViewHolder {

        TextView replyNameTv;
        TextView replyContentTv;

    }
}
