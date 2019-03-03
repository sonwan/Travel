package com.tj.graduation.travel.util.http;

import com.tj.graduation.travel.util.http.listener.DisposeDataHandle;
import com.tj.graduation.travel.util.http.listener.DisposeDataListener;
import com.tj.graduation.travel.util.http.request.CommonRequest;
import com.tj.graduation.travel.util.http.request.RequestParams;

/**
 * Created by wangsong on 2019/3/2.
 */

public class RequestUtil {

    //根据参数发送所有的get请求
    public static void getRequest(String url, RequestParams params,
                                   DisposeDataListener listener,
                                   Class<?> clazz){
        CommonOkHttpClient.post(CommonRequest.createPostRequest(url, params),
                new DisposeDataHandle(listener,clazz));
    }

}
