package com.competition.pdking.knowledgecircle;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.competition.pdking.loginandregister.BuildConfig;

import cn.bmob.v3.Bmob;


public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
        Bmob.initialize(this, "060f143283bcbc6daa1fc0b82c9e0589");
    }
}
