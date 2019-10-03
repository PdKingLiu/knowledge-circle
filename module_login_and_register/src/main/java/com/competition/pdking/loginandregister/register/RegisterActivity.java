package com.competition.pdking.loginandregister.register;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.competition.pdking.lib_base.BaseActivity;
import com.competition.pdking.lib_common_resourse.toast.ToastUtils;
import com.competition.pdking.loginandregister.R;
import com.competition.pdking.loginandregister.login.LoginActivity;

public class RegisterActivity extends BaseActivity implements View.OnClickListener,
        RegisterContract.View {

    private Button btnGoLogin;
    private Button btnSMS;
    private TextView tvRegister;
    private EditText etPhone;
    private EditText etSMS;
    private EditText etPassword;

    private String phone;
    private String password;
    private String SMS;

    private RegisterPresenter presenter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_register);
        initView();
        presenter = new RegisterPresenter(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        btnGoLogin = findViewById(R.id.btn_go_login);
        btnSMS = findViewById(R.id.btn_get_sms);
        tvRegister = findViewById(R.id.tv_register);
        etPhone = findViewById(R.id.et_phone);
        etSMS = findViewById(R.id.et_sms);
        etPassword = findViewById(R.id.et_password);
        btnGoLogin.setOnClickListener(this);
        btnSMS.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_go_login) {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        } else if (v.getId() == R.id.btn_get_sms) {
            phone = etPhone.getText().toString();
            if (phone.length() == 11) {
                presenter.getSMS(phone);
            } else {
                ToastUtils.showToast(this, "请输入正确的手机号");
            }
        } else if (v.getId() == R.id.tv_register) {
            phone = etPhone.getText().toString();
            SMS = etSMS.getText().toString();
            password = etPassword.getText().toString();
            if (phone.length() == 11 && SMS.length() >= 4
                    && password.length() >= 6 && password.length() <= 16) {
                presenter.register(phone, password, SMS);
            } else {
                ToastUtils.showToast(this, "请输入正确的信息");
            }
        }
    }

    @Override
    public void registerFailure(String msg) {
        ToastUtils.showToast(this, "注册失败：" + msg);
    }

    @Override
    public void registerSucceed() {
        ToastUtils.showToast(this, "注册成功，请登录");
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void SMSFailure(String msg) {
        ToastUtils.showToast(this, "发送失败：" + msg);
    }

    @Override
    public void SMSSucceed() {
        ToastUtils.showToast(this, "发送成功");
    }

    @Override
    public void setPresenter(RegisterPresenter registerPresenter) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToast(String msg) {

    }
}
