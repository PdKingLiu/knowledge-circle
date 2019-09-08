package com.competition.pdking.knowledgecircle;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.competition.pdking.loginandregister.BuildConfig;

import cn.bmob.v3.Bmob;

/**
 * @author liupeidong
 * Created on 2019/9/5 22:58
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
        Bmob.initialize(this, "4527189a38e9393bebbaaf3940982580");
    }
}
