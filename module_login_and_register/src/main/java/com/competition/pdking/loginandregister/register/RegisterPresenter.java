package com.competition.pdking.loginandregister.register;


public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View view;
    private RegisterTasks tasks;

    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
        tasks = new RegisterTasks(this);
    }

    @Override
    public void getSMS(String phone) {
        tasks.sendSMS(phone, new RegisterTasks.GetSMSCallBack() {
            @Override
            public void succeed() {
                view.SMSSucceed();
            }

            @Override
            public void failure(String msg) {
                view.SMSFailure(msg);
            }
        });
    }

    @Override
    public void register(String phone, String password, String SMS) {
        tasks.register(phone, password, SMS, new RegisterTasks.RegisterCallBack() {
            @Override
            public void succeed() {
                view.registerSucceed();
            }

            @Override
            public void failure(String msg) {
                view.registerFailure(msg);
            }
        });
    }
}
