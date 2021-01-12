package com.competition.pdking.lib_base.com.competition.pdking.bean;

import cn.bmob.v3.BmobObject;


public class Comment extends BmobObject {

    private String Content;

    private User user;

    private Post post;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
