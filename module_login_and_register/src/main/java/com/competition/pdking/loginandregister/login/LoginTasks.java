package com.competition.pdking.loginandregister.login;


import com.competition.pdking.lib_base.com.competition.pdking.bean.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;


public class LoginTasks {

    LoginContract.Presenter presenter;

    public LoginTasks(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public void login(String phone, String password, LoginCallBack callBack) {
        BmobUser.loginByAccount(phone, password, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    callBack.loginSucceed(user);
                } else {
                    callBack.loginFailure(e.getMessage());
                }
            }
        });
    }

    public interface LoginCallBack {
        void loginFailure(String msg);

        void loginSucceed(User user);
    }
}
