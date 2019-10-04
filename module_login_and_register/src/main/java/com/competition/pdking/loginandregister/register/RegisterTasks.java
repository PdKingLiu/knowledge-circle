package com.competition.pdking.loginandregister.register;

import com.competition.pdking.lib_base.com.competition.pdking.bean.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author liupeidong
 * Created on 2019/9/8 16:05
 */
public class RegisterTasks {

    private RegisterContract.Presenter presenter;

    private long lastSendTime = 0;

    public RegisterTasks(RegisterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public interface GetSMSCallBack {

        void succeed();

        void failure(String msg);

    }

    public interface RegisterCallBack {

        void succeed();

        void failure(String msg);

    }

    public void sendSMS(String phone, GetSMSCallBack callBack) {
        BmobQuery<User> userQuery = new BmobQuery<>();
        userQuery.addWhereEqualTo("username", phone);
        userQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    if (object != null && object.size() != 0) {
                        callBack.failure("账号已存在");
                    } else {
                        if (System.currentTimeMillis() - lastSendTime < 60000) {
                            callBack.failure("请" + (60000 - System.currentTimeMillis() + lastSendTime) / 1000 + "秒后重试");
                        } else {
                            BmobSMS.requestSMSCode(phone, "circle", new QueryListener<Integer>() {
                                @Override
                                public void done(Integer smsId, BmobException e) {
                                    if (e == null) {
                                        callBack.succeed();
                                        lastSendTime = System.currentTimeMillis();
                                    } else {
                                        callBack.failure(e.getMessage());
                                    }
                                }
                            });
                        }
                    }
                } else {
                    callBack.failure(e.getErrorCode() + "-" + e.getMessage());
                }
            }
        });
    }

    public void register(String phone, String password, String SMS, RegisterCallBack callBack) {
        BmobSMS.verifySmsCode(phone, SMS, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    register(phone, password, callBack);
                } else {
                    callBack.failure(e.getMessage());
                }
            }
        });
    }

    private void register(String phone, String password, RegisterCallBack callBack) {
        User user = new User();
        user.setName("用户" + phone);
        user.setUsername(phone);
        user.setPassword(password);
        user.setIconUrl("http://www.shidongxuan.top/image/61c57028-2ce2-416a-abd1-991da2126f55" +
                ".jpg");
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    callBack.succeed();
                } else {
                    callBack.failure(e.getMessage());
                }
            }
        });
    }

}
