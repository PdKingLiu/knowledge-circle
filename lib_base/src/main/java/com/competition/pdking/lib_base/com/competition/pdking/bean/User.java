package com.competition.pdking.lib_base.com.competition.pdking.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * @author liupeidong
 * Created on 2019/9/8 17:40
 */
public class User extends BmobUser {

    private String name;        //昵称

    private String iconUrl;     //头像url

    private String sex;         //性别

    private String education;   //学历

    private String school;      //毕业学校

    private String wantGoCompany; //想去的公司

    private String work;        //从事的工作

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setWantGoCompany(String wantGoCompany) {
        this.wantGoCompany = wantGoCompany;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public void setAttentionList(BmobRelation attentionList) {
        this.attentionList = attentionList;
    }

    public void setFansList(BmobRelation fansList) {
        this.fansList = fansList;
    }

    public void setPost(Integer post) {
        this.post = post;
    }

    public void setCollect(Integer collect) {
        this.collect = collect;
    }

    public void setAttention(Integer attention) {
        this.attention = attention;
    }

    public void setFans(Integer fans) {
        this.fans = fans;
    }

    public void setPraise(Integer praise) {
        this.praise = praise;
    }

    public void setPraiseList(BmobRelation praiseList) {
        this.praiseList = praiseList;
    }

    public void setCollectList(BmobRelation collectList) {
        this.collectList = collectList;
    }

    public void setScanList(BmobRelation scanList) {
        this.scanList = scanList;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getSex() {
        return sex;
    }

    public String getEducation() {
        return education;
    }

    public String getSchool() {
        return school;
    }

    public String getWantGoCompany() {
        return wantGoCompany;
    }

    public String getWork() {
        return work;
    }

    public BmobRelation getAttentionList() {
        return attentionList;
    }

    public BmobRelation getFansList() {
        return fansList;
    }

    public Integer getPost() {
        return post == null ? 0 : post;
    }

    public Integer getCollect() {
        return collect == null ? 0 : collect;
    }

    public Integer getAttention() {
        return attention == null ? 0 : attention;
    }

    public Integer getFans() {
        return fans == null ? 0 : fans;
    }

    public Integer getPraise() {
        return praise == null ? 0 : praise;
    }

    public BmobRelation getPraiseList() {
        return praiseList;
    }

    public BmobRelation getCollectList() {
        return collectList;
    }

    public BmobRelation getScanList() {
        return scanList;
    }
}
