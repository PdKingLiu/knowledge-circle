package com.competition.pdking.lib_base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.competition.pdking.lib_common_resourse.loadingview.LoadingDialog;
import com.competition.pdking.lib_common_resourse.toast.ToastUtils;


public class BaseActivity extends AppCompatActivity {

    private LoadingDialog loading;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
            getWindow().setStatusBarColor(Color.WHITE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏黑色字体
            }
        }
    }


    public void showLoading(String msg) {
        if (loading == null) {
            loading = new LoadingDialog(this, msg);
        }
        if (!loading.isShowing()) {
            runOnUiThread(() -> loading.show());
        }
    }

    public void hideLoading() {
        if (loading != null && loading.isShowing()) {
            runOnUiThread(() -> loading.dismiss());
        }
    }

    public void showToast(String msg) {
        runOnUiThread(() -> ToastUtils.showToast(this, msg));
    }
}
