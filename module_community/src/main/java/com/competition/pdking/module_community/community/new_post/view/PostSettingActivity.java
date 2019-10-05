package com.competition.pdking.module_community.community.new_post.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.TextView;

import com.competition.pdking.lib_base.BaseActivity;
import com.competition.pdking.lib_common_resourse.loadingview.LoadingDialog;
import com.competition.pdking.lib_common_resourse.toast.ToastUtils;
import com.competition.pdking.module_community.R;
import com.competition.pdking.module_community.community.new_post.NewPostContract;
import com.competition.pdking.module_community.community.new_post.NewPostPresenter;
import com.competition.pdking.module_community.community.new_post.bean.Post;

import java.util.ArrayList;
import java.util.Arrays;

public class PostSettingActivity extends BaseActivity implements NewPostContract.ViewOfReleasePost {

    private TextView tvModule;
    private TextView tvChat;
    private String[] modules = {"技术交流", "笔试面经", "猿生活", "资源分享", "我要提问", "招聘信息", "职业发展", "产品运营",
            "留学生", "工作以后"};
    private int moduleKind = -1;
    private String[] strings;

    private String title;
    private String content;
    private LoadingDialog loading;

    private NewPostContract.PresenterOfNewPostPage presenterOfNewPostPage;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_post_setting);
        presenterOfNewPostPage = new NewPostPresenter(this);
        tvModule = findViewById(R.id.tv_module);
        tvChat = findViewById(R.id.tv_chat);
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
    }


    public void onClick(View view) {
        if (view.getId() == R.id.ll_module) {
            Intent intent = new Intent(this, ModuleSettingActivity.class);
            startActivityForResult(intent, 1);
        } else if (view.getId() == R.id.rl_back) {
            finish();
        } else if (view.getId() == R.id.ll_chat) {
            Intent intent = new Intent(this, ChatSettingActivity.class);
            startActivityForResult(intent, 2);
        } else if (view.getId() == R.id.rl_post) {
            if (moduleKind == -1) {
                ToastUtils.showToast(this, "选择一个版块吧~");
                return;
            }
            if (strings == null || strings.length == 0) {
                ToastUtils.showToast(this, "选择一个主题吧~");
                return;
            }
            showLoading("发布中···");
            Post post = new Post();
            post.setTitle(title);
            post.setContent(content);
            post.setKind(moduleKind);
            post.setTopic(new ArrayList<>(Arrays.asList(strings)));
            new Handler().postDelayed(() -> presenterOfNewPostPage.releasePost(post), 1000);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode >= 10) {
                tvModule.setText(modules[resultCode % 10]);
                moduleKind = resultCode % 10;
            }
        } else if (requestCode == 2) {
            if (resultCode == 10 && data != null) {
                Object[] objects = (Object[]) data.getSerializableExtra("result");
                strings = new String[objects.length];
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < objects.length; i++) {
                    strings[i] = (String) objects[i];
                    sb.append("#").append(strings[i]).append(" ");
                }
                sb.append("等").append(strings.length).append("个");
                tvChat.setText(sb.toString());
            }
        }
    }

    @Override
    public void releaseSucceed(String postId) {
        hideLoading();
        showToast("发布成功");
    }

    @Override
    public void releaseFailure(String msg) {
        hideLoading();
        showToast("发布失败");
    }

    @Override
    public void setPresenter(Object o) {

    }

    @Override
    public void showLoading(String msg) {
        if (loading == null) {
            loading = new LoadingDialog(this, msg);
        }
        if (!loading.isShowing()) {
            runOnUiThread(() -> loading.show());
        }
    }

    @Override
    public void hideLoading() {
        if (loading != null && loading.isShowing()) {
            runOnUiThread(() -> loading.dismiss());
        }
    }

    @Override
    public void showToast(String msg) {
        runOnUiThread(() -> ToastUtils.showToast(this, msg));
    }
}
