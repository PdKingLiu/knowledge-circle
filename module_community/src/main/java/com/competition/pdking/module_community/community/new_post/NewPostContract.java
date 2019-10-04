package com.competition.pdking.module_community.community.new_post;

import android.content.Context;

import com.competition.pdking.lib_base.BasePresenter;
import com.competition.pdking.lib_base.BaseView;

import java.io.File;

/**
 * @author liupeidong
 * Created on 2019/10/4 13:06
 */
public interface NewPostContract {

    interface ViewOfNewPostPage extends BaseView {

        void UploadFileSucceed(String url, File file);

        void UploadFileFailure(String msg);

    }

    interface PresenterOfNewPostPage extends BasePresenter {
        void UploadFile(File file, Context context);
    }

}
