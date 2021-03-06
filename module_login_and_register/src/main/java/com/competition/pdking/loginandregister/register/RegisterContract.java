package com.competition.pdking.loginandregister.register;

import com.competition.pdking.lib_base.BasePresenter;
import com.competition.pdking.lib_base.BaseView;

/**
 * @author liupeidong
 * Created on 2019/9/8 15:41
 */
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
