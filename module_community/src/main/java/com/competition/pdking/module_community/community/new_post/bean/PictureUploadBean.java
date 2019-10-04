package com.competition.pdking.module_community.community.new_post.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author liupeidong
 * Created on 2019/10/4 14:56
 */
public class PictureUploadBean {
    @SerializedName("status")
    public int status;
    @SerializedName("data")
    public String data;

    @Override
    public String toString() {
        return "PictureUploadBean{" +
                "status=" + status +
                ", data='" + data + '\'' +
                '}';
    }
}
