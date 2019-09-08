package com.competition.pdking.loginandregister.bean;

import java.util.List;

import cn.bmob.v3.BmobUser;

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

    private long praise;        //获赞

    private List<String> attentionList;     //关注

    private List<String> fansList;          //粉丝

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

    public void setPraise(long praise) {
        this.praise = praise;
    }

    public void setAttentionList(List<String> attentionList) {
        this.attentionList = attentionList;
    }

    public void setFansList(List<String> fansList) {
        this.fansList = fansList;
    }

    public String getName() {
        return name;
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

    public long getPraise() {
        return praise;
    }

    public List<String> getAttentionList() {
        return attentionList;
    }

    public List<String> getFansList() {
        return fansList;
    }
}
