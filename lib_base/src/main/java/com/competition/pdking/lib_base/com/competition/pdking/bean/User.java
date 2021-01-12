package com.competition.pdking.lib_base.com.competition.pdking.bean;

import cn.bmob.v3.BmobUser;


public class User extends BmobUser {

    private String name;        //昵称

    private String iconUrl;     //头像url

    private String sex;         //性别

    private String education;   //学历

    private String school;      //毕业学校

    private String wantGoCompany; //想去的公司

    private String work;        //从事的工作

    private String location;        // 住址

    private String introduction;    // 简介

    private String graduation;      // 毕业年份

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

    public String getGraduation() {
        return graduation;
    }

    public void setGraduation(String graduation) {
        this.graduation = graduation;
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
