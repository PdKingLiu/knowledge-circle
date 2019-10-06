package com.competition.pdking.module_community.community.post_details.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.competition.pdking.lib_base.BaseActivity;
import com.competition.pdking.lib_common_resourse.loadingview.LoadingDialog;
import com.competition.pdking.lib_common_resourse.toast.ToastUtils;
import com.competition.pdking.module_community.R;
import com.competition.pdking.module_community.community.new_post.bean.Post;
import com.competition.pdking.module_community.community.post_details.PostDetailContract;
import com.competition.pdking.module_community.community.post_details.PostDetailPresenter;
import com.competition.pdking.module_community.community.post_details.adapter.CommentAdapter;
import com.competition.pdking.module_community.community.post_details.bean.Comment;

import java.util.ArrayList;
import java.util.List;

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

    private EditText etComment;
    private RelativeLayout rlComment;
    private RecyclerView rvCommentList;
    private LinearLayout llComment;
    private CommentAdapter adapter;
    private List<Comment> commentList;
    private TextView tvListHeader;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_post_detail);
        postId = getIntent().getStringExtra("postId");
        initView();
        initVar();
        initDate();
    }

    private void initVar() {
        commentList = new ArrayList<>();
        adapter = new CommentAdapter(commentList, this);
        rvCommentList.setLayoutManager(new LinearLayoutManager(this));
        rvCommentList.setAdapter(adapter);
    }

    private void initDate() {
        presenter = new PostDetailPresenter(this);
        presenter.loadPostData(postId, this, (int) (((WindowManager) this.
                getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth() * 0.75));
        presenter.loadComment(postId);
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

        etComment = findViewById(R.id.et_comment);
        rlComment = findViewById(R.id.rl_comment);
        rvCommentList = findViewById(R.id.rv_comment_list);
        llComment = findViewById(R.id.ll_comment);
        tvListHeader = findViewById(R.id.tv_list_header);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.rl_back) {
            finish();
        } else if (view.getId() == R.id.rl_comment) {
            if (llComment.getVisibility() == View.VISIBLE) {
                llComment.setVisibility(View.GONE);
            } else {
                llComment.setVisibility(View.VISIBLE);
            }
        } else if (view.getId() == R.id.btn_send_comment) {
            String comment = etComment.getText().toString();
            if (comment.equals("")) {
                showToast("再写点东西吧！");
                return;
            }
            presenter.sendComment(comment, postId);
        }
    }

    @Override
    public void onBackPressed() {
        if (llComment != null && llComment.getVisibility() == View.VISIBLE) {
            llComment.setVisibility(View.GONE);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void loadDataSucceed(Post post, boolean isAuthor) {
        this.post = post;
        runOnUiThread(this::loadPage);
    }

    @SuppressLint("DefaultLocale")
    private void loadPage() {
        Glide.with(this).load(post.getAuthor().getIconUrl()).into(civUserIcon);
        tvTitle.setText(post.getTitle());
        tvUserName.setText(post.getAuthor().getName());
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
    public void sendCommentSucceed(Comment comment) {
        showToast("发表成功");
        commentList.add(comment);
        runOnUiThread(() -> {
            etComment.setText("");
            notifyChanged();
        });
    }

    private boolean mShouldScroll;
    private int mToPosition;

    private void smoothMoveToPosition(final int position) {
        int firstItem = rvCommentList.getChildLayoutPosition(rvCommentList.getChildAt(0));
        int lastItem =
                rvCommentList.getChildLayoutPosition(rvCommentList.getChildAt(rvCommentList.getChildCount() - 1));
        if (position < firstItem) {
            // 如果要跳转的位置在第一个可见项之前，则smoothScrollToPosition可以直接跳转
            rvCommentList.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 如果要跳转的位置在第一个可见项之后，且在最后一个可见项之前
            // smoothScrollToPosition根本不会动，此时调用smoothScrollBy来滑动到指定位置
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < rvCommentList.getChildCount()) {
                int top = rvCommentList.getChildAt(movePosition).getTop();
                rvCommentList.smoothScrollBy(0, top);
            }
        } else {
            // 如果要跳转的位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，进入上一个控制语句
            rvCommentList.smoothScrollToPosition(position);
            mShouldScroll = true;
            mToPosition = position;
        }
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                if (mShouldScroll) {
                    mShouldScroll = false;
                    smoothMoveToPosition(mToPosition);
                }
            }
        }
    };

    @Override
    public void sendCommentFailure(String msg) {
        showToast("发表失败");
    }

    @Override
    public void loadCommentSucceed(List<Comment> commentList) {
        showToast("评论获取成功");
        this.commentList.clear();
        this.commentList.addAll(commentList);
        runOnUiThread(this::notifyChanged);
    }

    @SuppressLint("DefaultLocale")
    private void notifyChanged() {
        if (commentList.size() == 0) {
            tvListHeader.setText("暂时没有回帖，快来抢沙发！");
        } else {
            tvListHeader.setText(String.format("%d条回帖", commentList.size()));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadCommentFailure(String msg) {
        showToast("评论获取失败");
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
