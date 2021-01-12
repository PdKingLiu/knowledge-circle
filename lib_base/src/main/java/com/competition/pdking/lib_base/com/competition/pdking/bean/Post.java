package com.competition.pdking.lib_base.com.competition.pdking.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;


public class Post extends BmobObject {

    private User author;

    private String title;

    private String content;

    private Integer comment;

    private Integer praise;

    private Integer collect;

    private Integer kind;

    private List<String> Topic;

    private BmobRelation scanList;

    private BmobRelation praiseList;

    private BmobRelation collectList;

    private Integer scan;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public Integer getPraise() {
        return praise;
    }

    public void setPraise(Integer praise) {
        this.praise = praise;
    }

    public Integer getCollect() {
        return collect;
    }

    public void setCollect(Integer collect) {
        this.collect = collect;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public List<String> getTopic() {
        return Topic;
    }

    public void setTopic(List<String> topic) {
        Topic = topic;
    }

    public BmobRelation getScanList() {
        return scanList;
    }

    public void setScanList(BmobRelation scanList) {
        this.scanList = scanList;
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

    public Integer getScan() {
        return scan;
    }

    public void setScan(Integer scan) {
        this.scan = scan;
    }
}
