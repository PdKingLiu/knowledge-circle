package com.competition.pdking.lib_base.com.competition.pdking.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

public class UserData extends BmobObject {

    private User user;

    private BmobRelation attentionList;     //关注

    private BmobRelation fansList;

    private Integer post;

    private Integer collect;

    private Integer attention;

    private Integer fans;

    private Integer praise;

    private BmobRelation praiseList;

    private BmobRelation collectList;

    private BmobRelation scanList;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BmobRelation getAttentionList() {
        return attentionList;
    }

    public void setAttentionList(BmobRelation attentionList) {
        this.attentionList = attentionList;
    }

    public BmobRelation getFansList() {
        return fansList;
    }

    public void setFansList(BmobRelation fansList) {
        this.fansList = fansList;
    }

    public Integer getPost() {
        return post;
    }

    public void setPost(Integer post) {
        this.post = post;
    }

    public Integer getCollect() {
        return collect;
    }

    public void setCollect(Integer collect) {
        this.collect = collect;
    }

    public Integer getAttention() {
        return attention;
    }

    public void setAttention(Integer attention) {
        this.attention = attention;
    }

    public Integer getFans() {
        return fans;
    }

    public void setFans(Integer fans) {
        this.fans = fans;
    }

    public Integer getPraise() {
        return praise;
    }

    public void setPraise(Integer praise) {
        this.praise = praise;
    }

    public BmobRelation getPraiseList() {
        return praiseList;
    }

    public void setPraiseList(BmobRelation praiseList) {
        this.praiseList = praiseList;
    }

    public BmobRelation getCollectList() {
        return collectList;
    }

    public void setCollectList(BmobRelation collectList) {
        this.collectList = collectList;
    }

    public BmobRelation getScanList() {
        return scanList;
    }

    public void setScanList(BmobRelation scanList) {
        this.scanList = scanList;
    }
}