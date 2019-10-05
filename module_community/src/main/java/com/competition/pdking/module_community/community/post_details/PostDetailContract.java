package com.competition.pdking.module_community.community.post_details;

import android.app.Activity;

import com.competition.pdking.lib_base.BasePresenter;
import com.competition.pdking.lib_base.BaseView;
import com.competition.pdking.module_community.community.new_post.bean.Post;

/**
 * @author liupeidong
 * Created on 2019/10/5 14:59
 */
public interface PostDetailContract {

    interface View extends BaseView {

        void loadDataSucceed(Post post, boolean isAuthor);

        void loadDataFailure(String msg);

        void loadContentText(CharSequence text);

    }

    interface Presenter extends BasePresenter {

        void loadPostData(String postId, Activity activity, int width);

    }

}
