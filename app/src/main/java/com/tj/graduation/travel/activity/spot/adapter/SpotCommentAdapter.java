package com.tj.graduation.travel.activity.spot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tj.graduation.travel.R;
import com.tj.graduation.travel.model.CommentModel;
import com.tj.graduation.travel.view.NoScrollListView;

import java.util.List;

/**
 * Created by wangsong on 2019/3/9.
 */

public class SpotCommentAdapter extends BaseAdapter {

    private Context context;
    private List<CommentModel.CommentData.CommentList> list;
    private LayoutInflater inflater;

    private onReplyFinishListener listener;

    public void setOnReplyFinishListener(onReplyFinishListener listener) {
        this.listener = listener;
    }

    public interface onReplyFinishListener {
        void onReplyFinish(String linkId, String username, String type);
    }

    public void setList(List<CommentModel.CommentData.CommentList> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void clear() {
        this.list.clear();
        notifyDataSetChanged();
    }

    public SpotCommentAdapter(Context context, List<CommentModel.CommentData.CommentList> list) {
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

        final CommentModel.CommentData.CommentList model = list.get(i);
        ViewHolder holder = null;

        if (view == null) {

            holder = new ViewHolder();
            view = inflater.inflate(R.layout.spot_comment_adapter, null);
            holder.nameTv = view.findViewById(R.id.tv_spot_comment_name);
            holder.contentTv = view.findViewById(R.id.tv_spot_comment_content);
            holder.dateTv = view.findViewById(R.id.tv_spot_comment_date);
            holder.replyLv = view.findViewById(R.id.lv_comment_reply);
            holder.replyLL = view.findViewById(R.id.ll_reply);
            holder.replyTv = view.findViewById(R.id.tv_reply);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.nameTv.setText(model.getUserName());
        holder.contentTv.setText(model.getComContent());
        holder.dateTv.setText(model.getComTime());

        List<CommentModel.CommentData.CommentList.ReplyModel> replyList = model.getReplayList();
        if (replyList != null && replyList.size() > 0) {
            holder.replyLL.setVisibility(View.VISIBLE);
            ReplyAdapter adapter = new ReplyAdapter(context, replyList);
            holder.replyLv.setAdapter(adapter);

        } else {
            holder.replyLL.setVisibility(View.GONE);
        }

        holder.replyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.onReplyFinish(model.getId(), model.getUserName(), "PL");
            }
        });

        return view;
    }

    class ViewHolder {
        TextView replyTv;
        LinearLayout replyLL;
        TextView nameTv;
        TextView contentTv;
        TextView dateTv;
        NoScrollListView replyLv;
    }
}
