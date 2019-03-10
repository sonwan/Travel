package com.tj.graduation.travel.activity.spot.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tj.graduation.travel.R;
import com.tj.graduation.travel.model.PicItem;
import com.tj.graduation.travel.model.SpotDetailModel;
import com.tj.graduation.travel.util.glide.GlideUtil;

import java.util.List;

/**
 * 景点详情图片展示
 * Created by wangsong on 2019/3/6.
 */

public class SpotDetailPicAdapter extends PagerAdapter {

    private Context context;
    private List<PicItem> picList;

    public SpotDetailPicAdapter(Context context, List<PicItem> picList) {
        this.context = context;
        this.picList = picList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PicItem model = picList.get(position);
        View view = LayoutInflater.from(context).inflate(R.layout.spot_detail_pic_layout, null);
        ImageView picImg = view.findViewById(R.id.img_detail_pic);
        TextView descTv = view.findViewById(R.id.tv_detail_desc);
        GlideUtil.LoadPic(context, model.getPicUrl(), picImg);
        descTv.setText(model.getDescInfo());
        ((ViewPager) container).addView(view);


        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
//        super.destroyItem(container, position, object);

    }

    @Override
    public int getCount() {
        return picList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
