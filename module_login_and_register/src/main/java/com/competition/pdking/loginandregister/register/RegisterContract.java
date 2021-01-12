package com.competition.pdking.loginandregister.register;

import com.competition.pdking.lib_base.BasePresenter;
import com.competition.pdking.lib_base.BaseView;


public interface RegisterContract {

    interface View extends BaseView<RegisterPresenter> {

        void registerFailure(String msg);

        void registerSucceed();

        void SMSFailure(String msg);

        void SMSSucceed();

    }

    interface Presenter extends BasePresenter {

        void getSMS(String phone);

        void register(String phone, String password, String SMS);

    }

}
