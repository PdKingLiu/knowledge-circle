package com.competition.pdking.module_community.community.new_post.bean;

import com.competition.pdking.lib_base.com.competition.pdking.bean.User;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * @author liupeidong
 * Created on 2019/10/4 17:56
 */
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

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public void setPraise(Integer praise) {
        this.praise = praise;
    }

    public void setCollect(Integer collect) {
        this.collect = collect;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public void setTopic(List<String> topic) {
        Topic = topic;
    }

    public void setScanList(BmobRelation scanList) {
        this.scanList = scanList;
    }

    public void setPraiseList(BmobRelation praiseList) {
        this.praiseList = praiseList;
    }

    public void setCollectList(BmobRelation collectList) {
        this.collectList = collectList;
    }

    public void setScan(Integer scan) {
        this.scan = scan;
    }

    public User getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Integer getComment() {
        return comment;
    }

    public Integer getPraise() {
        return praise;
    }

    public Integer getCollect() {
        return collect;
    }

    public Integer getKind() {
        return kind;
    }

    public List<String> getTopic() {
        return Topic;
    }

    public BmobRelation getScanList() {
        return scanList;
    }

    public BmobRelation getPraiseList() {
        return praiseList;
    }

    public BmobRelation getCollectList() {
        return collectList;
    }

    public Integer getScan() {
        return scan;
    }
}
