package com.competition.pdking.module_circle.framework;

import android.content.res.Resources;

/**
 * Created by vimerzhao
 * Date: 2018/9/30
 * Description:
 */
public class PluginApp {
    public Resources mResources;
    public ClassLoader mClassLoader;


    public PluginApp(Resources mResources) {
        this.mResources = mResources;
    }
}
