package com.competition.pdking.loginandregister.login;

import com.competition.pdking.lib_base.BasePresenter;
import com.competition.pdking.lib_base.BaseView;
import com.competition.pdking.lib_base.com.competition.pdking.bean.User;


public interface LoginContract {
    interface View extends BaseView {
        void loginSucceed(User user);
        void loginFailure(String msg);
    }

    interface Presenter extends BasePresenter {
        void startLogin(String phone, String password);
    }
}
