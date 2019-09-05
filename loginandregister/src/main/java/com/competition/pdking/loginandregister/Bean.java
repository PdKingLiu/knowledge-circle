package com.competition.pdking.loginandregister;

import cn.bmob.v3.BmobObject;

/**
 * @author liupeidong
 * Created on 2019/9/5 23:15
 */
public class Bean extends BmobObject {

    private int age;

    private String name;

    private String address;

    public Bean(int age, String name, String address ) {
        this.age = age;
        this.name = name;
        this.address = address;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

}
