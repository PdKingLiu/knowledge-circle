package com.competition.pdking.module_community.community.new_post;

import android.content.Context;

import com.competition.pdking.module_community.community.new_post.bean.PictureUploadBean;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

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
                try {
                    bean = new Gson().fromJson(response.body().string(), PictureUploadBean.class);
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

    interface UploadFileCallBack {

        void succeed(String url, File file);

        void failure(String msg);

    }

}
