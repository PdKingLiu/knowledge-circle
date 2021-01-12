package com.competition.pdking.lib_base.com.competition.pdking.util;


import com.competition.pdking.lib_base.com.competition.pdking.bean.User;
import com.competition.pdking.lib_base.com.competition.pdking.bean.UserData;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class DataUtil {

    public static void getUserData(String userId, GetUserDataCallBack callBack) {

        User user = new User();
        user.setObjectId(userId);

        BmobQuery<UserData> query = new BmobQuery<>();
        query.addWhereEqualTo("user", user);
        //包含作者信息
        query.include("attentionList,fansList,praiseList,collectList,scanList");
        query.findObjects(new FindListener<UserData>() {
            @Override
            public void done(List<UserData> list, BmobException e) {
                if (e == null && list != null && list.size() == 1) {
                    callBack.callback(list.get(0));
                } else {
                    callBack.callback(null);
                }
            }
        });
    }

    public interface GetUserDataCallBack {
        void callback(UserData userData);
    }
}