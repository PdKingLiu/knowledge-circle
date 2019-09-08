package com.competition.pdking.loginandregister.login;

import com.competition.pdking.loginandregister.bean.User;

/**
 * @author liupeidong
 * Created on 2019/9/8 12:36
 */
public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;
    private LoginTasks tasks;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        tasks = new LoginTasks(this);
    }

    @Override
    public void startLogin(String phone, String password) {
        tasks.login(phone, password, new LoginTasks.LoginCallBack() {
            @Override
            public void loginFailure(String msg) {
                view.loginFailure(msg);
            }

            @Override
            public void loginSucceed(User user) {
                view.loginSucceed(user);
            }
        });
    }
}
