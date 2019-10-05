package com.competition.pdking.module_community.community.post_details;

import android.app.Activity;

import com.competition.pdking.module_community.community.new_post.bean.Post;

/**
 * @author liupeidong
 * Created on 2019/10/5 15:03
 */
public class PostDetailPresenter implements PostDetailContract.Presenter {

    private PostDetailTasks tasks;

    private PostDetailContract.View view;

    public PostDetailPresenter(PostDetailContract.View view) {
        this.view = view;
        tasks = new PostDetailTasks(this);
    }

    @Override
    public void loadPostData(String postId, Activity activity, int width) {
        tasks.loadPostData(postId, activity, width, new PostDetailTasks.CallBack() {
            @Override
            public void contentText(CharSequence text) {
                view.loadContentText(text);
            }

            @Override
            public void succeed(Post post, boolean isAuthor) {
                view.loadDataSucceed(post, isAuthor);
            }

            @Override
            public void failure(String msg) {
                view.loadDataFailure(msg);
            }
        });
    }

}
