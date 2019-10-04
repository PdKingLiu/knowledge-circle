package com.competition.pdking.module_community.community.new_post;

import android.content.Context;

import com.competition.pdking.lib_base.BasePresenter;
import com.competition.pdking.lib_base.BaseView;
import com.competition.pdking.module_community.community.new_post.bean.Post;

import java.io.File;

/**
 * @author liupeidong
 * Created on 2019/10/4 13:06
 */
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
