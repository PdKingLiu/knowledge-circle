package com.competition.pdking.module_community.community.new_post;

import android.content.Context;

import com.competition.pdking.lib_base.BasePresenter;
import com.competition.pdking.lib_base.BaseView;
import com.competition.pdking.lib_base.com.competition.pdking.bean.Post;

import java.io.File;


public interface NewPostContract {

    interface ViewOfNewPostPage extends BaseView {

        void uploadFileSucceed(String url, File file);

        void uploadFileFailure(String msg);

    }

    interface ViewOfReleasePost extends BaseView {

        void releaseSucceed(String postId);

        void releaseFailure(String msg);

    }


    interface PresenterOfNewPostPage extends BasePresenter {

        void uploadFile(File file, Context context);

        void releasePost(Post post);

    }

}
