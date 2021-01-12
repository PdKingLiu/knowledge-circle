package com.competition.pdking.lib_base;


public interface BaseView<T> {

    public void setPresenter(T t);

    public void showLoading(String msg);

    public void hideLoading();

    public void showToast(String msg);

}
