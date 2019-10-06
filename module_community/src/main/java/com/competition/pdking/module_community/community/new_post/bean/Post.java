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

    private int comment;

    private int praise;

    private int collect;

    private int kind;

    private List<String> Topic;

    private BmobRelation scanList;

    private int scan;

    @Override
    public String toString() {
        return "Post{" +
                "author=" + author +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", comment=" + comment +
                ", praise=" + praise +
                ", collect=" + collect +
                ", kind=" + kind +
                ", Topic=" + Topic +
                ", scan=" + scan +
                '}';
    }

    public void setScanList(BmobRelation scanList) {
        this.scanList = scanList;
    }

    public void setScan(int scan) {
        this.scan = scan;
    }

    public BmobRelation getScanList() {
        return scanList;
    }

    public int getScan() {
        return scan;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public void setTopic(List<String> topic) {
        Topic = topic;
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

    public int getComment() {
        return comment;
    }

    public int getPraise() {
        return praise;
    }

    public int getCollect() {
        return collect;
    }

    public int getKind() {
        return kind;
    }

    public List<String> getTopic() {
        return Topic;
    }

}
