package com.competition.pdking.module_community.community.new_post;

import android.content.Context;

import com.competition.pdking.lib_base.com.competition.pdking.bean.Post;

import java.io.File;


public class NewPostPresenter implements NewPostContract.PresenterOfNewPostPage {

    private NewPostContract.ViewOfNewPostPage viewOfNewPostPage;
    private NewPostContract.ViewOfReleasePost viewOfReleasePost;
    private NewPostTasks tasks;

    public NewPostPresenter(NewPostContract.ViewOfNewPostPage viewOfNewPostPage) {
        this.viewOfNewPostPage = viewOfNewPostPage;
        tasks = new NewPostTasks(this);
    }

    public NewPostPresenter(NewPostContract.ViewOfReleasePost viewOfReleasePost) {
        this.viewOfReleasePost = viewOfReleasePost;
        tasks = new NewPostTasks(this);
    }

    @Override
    public void uploadFile(File file, Context context) {
        tasks.uploadFile(file, new NewPostTasks.UploadFileCallBack() {
            @Override
            public void succeed(String url, File file) {
                viewOfNewPostPage.uploadFileSucceed(url, file);
            }

            @Override
            public void failure(String msg) {
                viewOfNewPostPage.uploadFileFailure(msg);
            }
        }, context);
    }

    @Override
    public void releasePost(Post post) {
        tasks.releasePost(post, new NewPostTasks.ReleaseCallBack() {
            @Override
            public void succeed(String postId) {
                viewOfReleasePost.releaseSucceed(postId);
            }

            @Override
            public void failure(String msg) {
                viewOfReleasePost.releaseFailure(msg);
            }
        });
    }
}
