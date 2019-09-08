package com.competition.pdking.loginandregister.login;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;

import com.competition.pdking.lib_base.BaseActivity;
import com.competition.pdking.loginandregister.R;
import com.competition.pdking.loginandregister.register.RegisterActivity;

public class LoginActivity extends BaseActivity implements LoginContract.View,
        View.OnClickListener {

    private Dialog dialog;
    private Button btnRegister;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_login);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
            getWindow().setStatusBarColor(Color.WHITE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏黑色字体
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_register) {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        }
    }

    @Override
    public void loginSucceed() {

    }

    @Override
    public void loginFailure() {

    }

    @Override
    public void setPresenter(Object o) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToast() {

    }

}
