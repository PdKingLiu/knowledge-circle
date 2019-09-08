package com.competition.pdking.loginandregister.login;

import com.competition.pdking.lib_base.BasePresenter;
import com.competition.pdking.lib_base.BaseView;

/**
 * @author liupeidong
 * Created on 2019/9/8 12:28
 */
public interface LoginContract {

    interface View extends BaseView {
        public void loginSucceed();

        public void loginFailure();
    }

    interface Presenter extends BasePresenter {
        public void startLogin();
    }

}
