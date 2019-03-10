package com.tj.graduation.travel.util.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tj.graduation.travel.R;

/**
 * Created by wangsong on 2019/3/3.
 */

public class GlideUtil {

    /**
     * 加载网络图片
     *
     * @param context
     * @param picurl
     * @param imageView
     */

    public static void LoadPic(Context context, String picurl, ImageView imageView) {

//        Glide.with(context).load(picurl).into(imageView);
        Glide.with(context).load(picurl).error(R.drawable.show_image_loading).into(imageView);

    }

}
