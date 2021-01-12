package com.competition.pdking.knowledgecircle;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.competition.pdking.lib_base.BaseActivity;
import com.competition.pdking.lib_base.BaseView;
import com.competition.pdking.lib_base.com.competition.pdking.bean.User;
import com.competition.pdking.lib_common_resourse.loadingview.LoadingDialog;
import com.competition.pdking.lib_common_resourse.utils.ARouterUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;


@Route(path = "/module_main/main_activity")
public class MainActivity extends BaseActivity implements BaseView {

    private User user;
    private BottomNavigationView bnv;
    private Fragment[] fragments;
    private FragmentManager mFragmentManager;
    private LoadingDialog loading;
    private int bnvIndex = R.id.bnv_circle;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_main);
        checkIsLogin();
        initFragment();
        initView();
    }

    private void initFragment() {
        if (fragments == null) {
            fragments = new Fragment[3];
        }
        fragments[0] = ARouterUtils.getCircleFragment();
        if (mFragmentManager == null) {
            mFragmentManager = MainActivity.this.getSupportFragmentManager();
        }
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.fl_container, fragments[0], "circle");
        transaction.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        bnv = findViewById(R.id.bnv);
        bnv.setItemIconTintList(null);
        bnv.setOnNavigationItemSelectedListener(menuItem -> {
            FragmentTransaction transaction;
            if (fragments == null) {
                initFragment();
            }
            if (mFragmentManager == null) {
                mFragmentManager = MainActivity.this.getSupportFragmentManager();
            }
            transaction = mFragmentManager.beginTransaction();
            switch (menuItem.getItemId()) {
                case R.id.bnv_circle:
                    if (bnvIndex == R.id.bnv_circle) {
                        break;
                    }
                    hideFragment();
                    bnvIndex = R.id.bnv_circle;
                    if (fragments[0] == null) {
                        fragments[0] = ARouterUtils.getCircleFragment();
                        transaction.add(R.id.fl_container, fragments[0], "circle");
                    } else {
                        transaction.show(fragments[0]);
                    }
                    break;
                case R.id.bnv_community:
                    if (bnvIndex == R.id.bnv_community) {
                        break;
                    }
                    hideFragment();
                    bnvIndex = R.id.bnv_community;
                    if (fragments[1] == null) {
                        fragments[1] = ARouterUtils.getCommunityFragment();
                        transaction.add(R.id.fl_container, fragments[1], "community");
                    } else {
                        transaction.show(fragments[1]);
                    }
                    break;
                case R.id.bnv_my:
                    if (bnvIndex == R.id.bnv_my) {
                        break;
                    }
                    hideFragment();
                    bnvIndex = R.id.bnv_my;
                    if (fragments[2] == null) {
                        fragments[2] = ARouterUtils.getMyFragment();
                        transaction.add(R.id.fl_container, fragments[2], "my");
                    } else {
                        transaction.show(fragments[2]);
                    }
                    break;
            }
            transaction.commit();
            return true;
        });
    }

    private void hideFragment() {
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        switch (bnvIndex) {
            case R.id.bnv_circle:
                if (fragments != null && fragments[0] != null && !fragments[0].isHidden()) {
                    fragmentTransaction.hide(fragments[0]);
                }
                break;
            case R.id.bnv_community:
                if (fragments != null && fragments[1] != null && !fragments[1].isHidden()) {
                    fragmentTransaction.hide(fragments[1]);
                }
                break;
            case R.id.bnv_my:
                if (fragments != null && fragments[2] != null && !fragments[2].isHidden()) {
                    fragmentTransaction.hide(fragments[2]);
                }
                break;
        }
        fragmentTransaction.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void checkIsLogin() {
        if (BmobUser.isLogin()) {
            BmobUser.fetchUserInfo(new FetchUserInfoListener<BmobUser>() {
                @Override
                public void done(BmobUser user, BmobException e) {
                }
            });
        } else {
            ARouter.getInstance().build(ARouterUtils.LoginActivity).navigation();
            finish();
        }
    }

    @Override
    public void setPresenter(Object o) {

    }

    @Override
    public void showLoading(String msg) {
        if (loading == null) {
            loading = new LoadingDialog(this, "加载中...");
        }
        if (!loading.isShowing()) {
            loading.show();
        }
    }

    @Override
    public void hideLoading() {
        if (loading != null && loading.isShowing()) {
            loading.dismiss();
        }
    }

    @Override
    public void showToast(String msg) {

    }
}
