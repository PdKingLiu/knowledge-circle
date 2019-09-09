package com.competition.pdking.knowledgecircle;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.competition.pdking.lib_base.BaseView;
import com.competition.pdking.lib_common_resourse.loadingview.LoadingDialog;
import com.competition.pdking.lib_common_resourse.utils.ARouterUtils;
import com.competition.pdking.loginandregister.bean.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;


@Route(path = "/module_main/main_activity")
public class MainActivity extends AppCompatActivity implements BaseView {

    private User user;
    private BottomNavigationView bnv;
    private List<Fragment> fragmentList;
    private FragmentManager mFragmentManager;
    private LoadingDialog loading;
    private String TAG = "Lpp";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_main);
        checkIsLogin();

    }

    private void initFragment() {
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        }
        fragmentList.clear();
        fragmentList.add(ARouterUtils.getCircleFragment());
        fragmentList.add(ARouterUtils.getCommunityFragment());
        fragmentList.add(ARouterUtils.getNewsFragment());
        fragmentList.add(ARouterUtils.getMyFragment());
        if (mFragmentManager == null) {
            mFragmentManager = MainActivity.this.getSupportFragmentManager();
        }
        System.out.println(fragmentList);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.fl_container, fragmentList.get(0), "circle");
        transaction.add(R.id.fl_container, fragmentList.get(1), "community");
        transaction.add(R.id.fl_container, fragmentList.get(2), "news");
        transaction.add(R.id.fl_container, fragmentList.get(3), "my");
        transaction.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        bnv = findViewById(R.id.bnv);
        bnv.setItemIconTintList(null);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction transaction;
                if (fragmentList == null || fragmentList.size() != 4) {
                    initFragment();
                }
                if (mFragmentManager == null) {
                    mFragmentManager = MainActivity.this.getSupportFragmentManager();
                }
                transaction = mFragmentManager.beginTransaction();
                switch (menuItem.getItemId()) {
                    case R.id.bnv_circle:
                        transaction.show(fragmentList.get(0));
                        break;
                    case R.id.bnv_community:
                        transaction.show(fragmentList.get(1));
                        break;
                    case R.id.bnv_news:
                        transaction.show(fragmentList.get(2));
                        break;
                    case R.id.bnv_my:
                        transaction.show(fragmentList.get(3));
                        break;
                }
                transaction.commit();
                return true;
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
            getWindow().setStatusBarColor(Color.WHITE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏黑色字体
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void checkIsLogin() {
        if (BmobUser.isLogin()) {
            user = BmobUser.getCurrentUser(User.class);
        } else {
            ARouter.getInstance().build(ARouterUtils.LoginActivity).navigation();
            finish();
        }
    }

    @Override
    public void setPresenter(Object o) {

    }

    @Override
    public void showLoading() {
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
