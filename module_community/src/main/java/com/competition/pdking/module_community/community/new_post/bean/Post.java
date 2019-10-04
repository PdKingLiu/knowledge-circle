package com.competition.pdking.module_community.community.new_post.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * @author liupeidong
 * Created on 2019/10/4 17:56
 */
public class Post extends BmobObject {

    private String title = "";

    private String content = "";

    private String authorPhone = "";

    private String authorName = "";

    private String authorIcon = "";

    private String authorId = "";

    private Date createData = new Date();

    private int comment;

    private int praise;

    private int collect;

    private List<String> Topic = new ArrayList<>();

    private int kind;

    private int scan;

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreateData(Date createData) {
        this.createData = createData;
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

    public void setTopic(List<String> topic) {
        Topic = topic;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public void setScan(int scan) {
        this.scan = scan;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setAuthorPhone(String authorPhone) {
        this.authorPhone = authorPhone;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setAuthorIcon(String authorIcon) {
        this.authorIcon = authorIcon;
    }

    public String getAuthorPhone() {
        return authorPhone;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorIcon() {
        return authorIcon;
    }

    public Date getCreateData() {
        return createData;
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

    public List<String> getTopic() {
        return Topic;
    }

    public int getKind() {
        return kind;
    }

    public int getScan() {
        return scan;
    }
}
