package com.competition.pdking.lib_base;

/**
 * @author liupeidong
 * Created on 2019/9/8 10:11
 */
public interface BaseView<T> {

    public void setPresenter(T t);

    public void showLoading();

    public void hideLoading();

    public void showToast();

}
