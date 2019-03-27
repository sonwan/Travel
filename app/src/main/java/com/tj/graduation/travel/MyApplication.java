package com.tj.graduation.travel;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

/**
 * Created by wangsong on 2019/3/3.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }
}
