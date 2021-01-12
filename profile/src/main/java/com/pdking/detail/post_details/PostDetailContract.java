package com.pdking.detail.post_details;

import android.app.Activity;

import com.competition.pdking.lib_base.BasePresenter;
import com.competition.pdking.lib_base.BaseView;
import com.competition.pdking.lib_base.com.competition.pdking.bean.Comment;
import com.competition.pdking.lib_base.com.competition.pdking.bean.Post;

import java.util.List;


public interface PostDetailContract {

    interface View extends BaseView {

        void loadDataSucceed(Post post, boolean isAuthor);

        void loadDataFailure(String msg);

        void loadContentText(CharSequence text);

        void sendCommentSucceed(Comment comment);

        void sendCommentFailure(String msg);

        void loadCommentSucceed(List<Comment> commentList);

        void loadCommentFailure(String msg);

        void loadPraiseSumAndIsPraise(int sum, boolean isPraise);

        void loadCollectSumAndIsCollect(int sum, boolean isCollect);

    }

    interface Presenter extends BasePresenter {

        void loadPostData(String postId, Activity activity, int width);

        void sendComment(String content, String postId);

        void loadComment(String postId);

        void getIsPraiseAndSum(String postId);

        void sendPraiseMessage(String postId);

        void sendCancelPraise(String postId);

        void getIsCollectAndSum(String postId);

        void sendCollectMessage(String postId);

        void sendCancelCollect(String postId);


    }

}
