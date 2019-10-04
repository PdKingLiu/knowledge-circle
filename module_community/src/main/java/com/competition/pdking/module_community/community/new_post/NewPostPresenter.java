package com.competition.pdking.module_community.community.new_post;

import android.content.Context;

import java.io.File;

/**
 * @author liupeidong
 * Created on 2019/10/4 13:18
 */
public class NewPostPresenter implements NewPostContract.PresenterOfNewPostPage {

    private NewPostContract.ViewOfNewPostPage viewOfNewPostPage;
    private NewPostTasks tasks;

    public NewPostPresenter(NewPostContract.ViewOfNewPostPage viewOfNewPostPage) {
        this.viewOfNewPostPage = viewOfNewPostPage;
        tasks = new NewPostTasks(this);
    }

    @Override
    public void UploadFile(File file, Context context) {
        tasks.uploadFile(file, new NewPostTasks.UploadFileCallBack() {
            @Override
            public void succeed(String url, File file) {
                viewOfNewPostPage.UploadFileSucceed(url, file);
            }

            @Override
            public void failure(String msg) {
                viewOfNewPostPage.UploadFileFailure(msg);
            }
        }, context);
    }
}
