package com.competition.pdking.module_community.community.post_details;

import android.app.Activity;

import com.competition.pdking.lib_base.BasePresenter;
import com.competition.pdking.lib_base.BaseView;
import com.competition.pdking.module_community.community.new_post.bean.Post;
import com.competition.pdking.module_community.community.post_details.bean.Comment;

import java.util.List;

/**
 * @author liupeidong
 * Created on 2019/10/5 14:59
 */
public interface PostDetailContract {

    interface View extends BaseView {

        void loadDataSucceed(Post post, boolean isAuthor);

        void loadDataFailure(String msg);

        void loadContentText(CharSequence text);

        void sendCommentSucceed(Comment comment);

        void sendCommentFailure(String msg);

        void loadCommentSucceed(List<Comment> commentList);

        void loadCommentFailure(String msg);

    }

    interface Presenter extends BasePresenter {

        void loadPostData(String postId, Activity activity, int width);

        void sendComment(String content, String postId);

        void loadComment(String postId);

    }

}
