package com.competition.pdking.module_community.community.post_details.bean;

import com.competition.pdking.lib_base.com.competition.pdking.bean.User;
import com.competition.pdking.module_community.community.new_post.bean.Post;

import cn.bmob.v3.BmobObject;

/**
 * @author liupeidong
 * Created on 2019/10/5 23:44
 */
public class Comment extends BmobObject {

    private String Content;

    private User user;

    private Post post;

    public void setContent(String content) {
        Content = content;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getContent() {
        return Content;
    }

    public User getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }
}
