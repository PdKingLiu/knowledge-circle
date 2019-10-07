package com.competition.pdking.module_community.community.new_post;

import android.content.Context;

import com.competition.pdking.lib_base.com.competition.pdking.bean.User;
import com.competition.pdking.module_community.community.new_post.bean.PictureUploadBean;
import com.competition.pdking.module_community.community.new_post.bean.Post;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import id.zelory.compressor.Compressor;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author liupeidong
 * Created on 2019/10/4 13:17
 */
public class NewPostTasks {

    private NewPostContract.PresenterOfNewPostPage presenterOfNewPostPage;

    public NewPostTasks(NewPostContract.PresenterOfNewPostPage presenterOfNewPostPage) {
        this.presenterOfNewPostPage = presenterOfNewPostPage;
    }

    public void uploadFile(File fileSource, UploadFileCallBack callBack, Context context) {
        File file = null;
        try {
            file = new Compressor(context).compressToFile(fileSource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file == null) {
            callBack.failure("失败");
            return;
        }
        OkHttpClient client = new OkHttpClient();
        MultipartBody body = new MultipartBody.Builder()
                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse(
                        "image/jpeg"), file))
                .build();
        Request request = new Request.Builder()
                .addHeader("token", "liupeidong")
                .post(body)
                .url("http://www.shidongxuan.top/smartMeeting_Web/liuUpload/file")
                .build();
        File finalFile = file;
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.failure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                PictureUploadBean bean;
                String s = response.body().string();
                try {
                    bean = new Gson().fromJson(s, PictureUploadBean.class);
                } catch (Exception e) {
                    callBack.failure("上传失败");
                    return;
                }
                if (bean.status == 0) {
                    callBack.succeed(bean.data, finalFile);
                } else {
                    callBack.failure("上传失败");
                }
            }
        });
    }

    public void releasePost(Post post, ReleaseCallBack callBack) {
        post.setCollect(0);
        post.setPraise(0);
        post.setComment(0);
        post.setScan(0);
        post.setAuthor(BmobUser.getCurrentUser(User.class));
        post.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    callBack.succeed(s);
                } else {
                    callBack.failure(e.getMessage());
                }
            }
        });
    }

    interface UploadFileCallBack {

        void succeed(String url, File file);

        void failure(String msg);

    }

    interface ReleaseCallBack {

        void succeed(String postId);

        void failure(String msg);

    }

}
