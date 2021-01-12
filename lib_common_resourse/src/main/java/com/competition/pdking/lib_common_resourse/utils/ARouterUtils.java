package com.competition.pdking.lib_common_resourse.utils;

import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;


public class ARouterUtils {

    public static String CircleFragmentPath = "/module_circle/circle_fragment";
    public static String CommunityFragmentPath = "/module_community/community_fragment";
    public static String NewsFragmentPath = "/module_news/news_fragment";
    public static String MyFragmentPath = "/module_my/my_fragment";

    public static String MainActivity = "/module_main/main_activity";
    public static String LoginActivity = "/module_login/login_activity";


    public static Fragment getCircleFragment() {
        Fragment fragment =
                (Fragment) ARouter.getInstance().build(ARouterUtils.CircleFragmentPath).navigation();
        return fragment;
    }

    public static Fragment getCommunityFragment() {
        Fragment fragment =
                (Fragment) ARouter.getInstance().build(ARouterUtils.CommunityFragmentPath).navigation();
        return fragment;
    }

    public static Fragment getNewsFragment() {
        Fragment fragment =
                (Fragment) ARouter.getInstance().build(ARouterUtils.NewsFragmentPath).navigation();
        return fragment;
    }

    public static Fragment getMyFragment() {
        Fragment fragment =
                (Fragment) ARouter.getInstance().build(ARouterUtils.MyFragmentPath).navigation();
        return fragment;
    }

}
