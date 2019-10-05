package com.competition.pdking.module_community.community.post_details;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.competition.pdking.lib_base.BaseActivity;
import com.competition.pdking.lib_common_resourse.loadingview.LoadingDialog;
import com.competition.pdking.lib_common_resourse.toast.ToastUtils;
import com.competition.pdking.module_community.R;
import com.competition.pdking.module_community.community.new_post.bean.Post;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostDetailActivity extends BaseActivity implements PostDetailContract.View {

    private String postId;
    private TextView tvTitle;
    private TextView tvUserName;
    private TextView tvTime;
    private TextView tvScan;
    private TextView tvTopic;
    private TextView tvContent;
    private CircleImageView civUserIcon;
    private LoadingDialog loading;
    private PostDetailContract.Presenter presenter;
    private Post post;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_post_detail);
        postId = getIntent().getStringExtra("postId");
        initView();
        initDate();
    }

    private void initDate() {
        presenter = new PostDetailPresenter(this);
        presenter.loadPostData(postId, this, (int) (((WindowManager) this.
                getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth() * 0.75));
    }

    private void initView() {
        tvTitle = findViewById(R.id.tv_title);
        tvUserName = findViewById(R.id.tv_user_name);
        tvTime = findViewById(R.id.tv_time);
        tvScan = findViewById(R.id.tv_scan);
        tvTopic = findViewById(R.id.tv_topic);
        tvContent = findViewById(R.id.tv_content);
        civUserIcon = findViewById(R.id.civ_icon);
        tvUserName.setTextColor(getIntent().getIntExtra("textColor", Color.GREEN));
    }

    public void onClick(View view) {
        if (view.getId() == R.id.rl_back) {
            finish();
        } else if (view.getId() == R.id.rl_back) {

        }
    }

    @Override
    public void loadDataSucceed(Post post, boolean isAuthor) {
        this.post = post;
        runOnUiThread(this::loadPage);
    }

    @SuppressLint("DefaultLocale")
    private void loadPage() {
        Glide.with(this).load(post.getAuthorIcon()).into(civUserIcon);
        tvTitle.setText(post.getTitle());
        tvUserName.setText(post.getAuthorName());
        tvTime.setText(post.getCreatedAt());
        tvScan.setText(String.format("%d浏览", post.getScan()));
        StringBuilder sb = new StringBuilder("#");
        for (String s : post.getTopic()) {
            sb.append(s).append(" ");
        }
        tvTopic.setText(sb.toString());
    }

    @Override
    public void loadDataFailure(String msg) {
        showToast("加载失败");
    }

    @Override
    public void loadContentText(CharSequence text) {
        if (text != null) {
            runOnUiThread(() -> tvContent.setText(text));
        }
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
