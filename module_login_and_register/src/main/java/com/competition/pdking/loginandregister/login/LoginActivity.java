package com.competition.pdking.loginandregister.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.competition.pdking.lib_base.BaseActivity;
import com.competition.pdking.lib_common_resourse.loadingview.LoadingDialog;
import com.competition.pdking.lib_common_resourse.toast.ToastUtils;
import com.competition.pdking.lib_common_resourse.utils.ARouterUtils;
import com.competition.pdking.loginandregister.R;
import com.competition.pdking.loginandregister.bean.User;
import com.competition.pdking.loginandregister.register.RegisterActivity;

@Route(path = "/module_login/login_activity")
public class LoginActivity extends BaseActivity implements LoginContract.View,
        View.OnClickListener {

    private LoadingDialog loading;
    private Button btnRegister;
    private TextView tvLogin;
    private EditText etPhone;
    private EditText etPassword;

    private LoginPresenter presenter;

    private String phone;
    private String password;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_login);
        initView();
        presenter = new LoginPresenter(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        btnRegister = findViewById(R.id.btn_register);
        tvLogin = findViewById(R.id.tv_login);
        etPhone = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_password);
        btnRegister.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
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
        } else if (v.getId() == R.id.tv_login) {
            phone = etPhone.getText().toString();
            password = etPassword.getText().toString();
            if (phone.length() == 11 && password.length() >= 6 && password.length() <= 16) {
                showLoading();
                new Handler().postDelayed(() -> presenter.startLogin(phone, password), 3000);
            } else {
                ToastUtils.showToast(this, "账号或密码长度不正确");
            }
        }
    }

    @Override
    public void loginSucceed(User user) {
        hideLoading();
        ToastUtils.showToast(this, "登录成功");
        ARouter.getInstance().build(ARouterUtils.MainActivity).navigation();
        finish();
    }

    @Override
    public void loginFailure(String msg) {
        hideLoading();
        ToastUtils.showToast(this, "登录失败：" + msg);
    }


    @Override
    public void setPresenter(Object o) {

    }

    @Override
    public void showLoading() {
        if (loading == null) {
            loading = new LoadingDialog(this, "登录中...");
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
