package com.pdking.detail.post_details.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.competition.pdking.lib_base.BaseActivity;
import com.competition.pdking.lib_base.com.competition.pdking.bean.Comment;
import com.competition.pdking.lib_base.com.competition.pdking.bean.Post;
import com.competition.pdking.lib_common_resourse.constant.Constant;
import com.competition.pdking.lib_common_resourse.loadingview.LoadingDialog;
import com.competition.pdking.lib_common_resourse.toast.ToastUtils;
import com.pdking.detail.post_details.PostDetailContract;
import com.pdking.detail.post_details.PostDetailPresenter;
import com.pdking.detail.post_details.adapter.CommentAdapter;
import com.pdking.profile.ProfileActivity;
import com.pdking.profile.R;

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
    private RecyclerView rvCommentList;
    private LinearLayout llComment;
    private CommentAdapter adapter;
    private List<Comment> commentList;
    private TextView tvListHeader;
    private TextView tvCommentSum;

    private ImageView ivPraise;
    private ImageView ivCollect;
    private TextView tvPraise;
    private TextView tvCollect;
    private SwipeRefreshLayout srlRefresh;


    private boolean isPraise = false;
    private boolean isCollect = false;
    private int praiseSum = 0;
    private int collectSum = 0;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_post_detail);
        postId = getIntent().getStringExtra("postId");
        presenter = new PostDetailPresenter(this);
        initView();
        initVar();
        initDate();
    }

    private void initVar() {
        commentList = new ArrayList<>();
        adapter = new CommentAdapter(commentList, this);
        rvCommentList.setLayoutManager(new LinearLayoutManager(this));
        rvCommentList.setAdapter(adapter);
        srlRefresh.setOnRefreshListener(() -> new Handler().postDelayed(this::initDate, 1500));
    }


    private void initDate() {
        presenter.loadPostData(postId, this, (int) (((WindowManager) this.
                getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth() * 0.75));
        presenter.loadComment(postId);
        presenter.getIsCollectAndSum(postId);
        presenter.getIsPraiseAndSum(postId);
    }

    private void initView() {
        tvTitle = findViewById(R.id.tv_title);
        tvUserName = findViewById(R.id.tv_user_name);
        tvTime = findViewById(R.id.tv_time);
        tvScan = findViewById(R.id.tv_scan);
        tvTopic = findViewById(R.id.tv_topic);
        tvContent = findViewById(R.id.tv_content);
        civUserIcon = findViewById(R.id.civ_icon);

        etComment = findViewById(R.id.et_comment);
        rvCommentList = findViewById(R.id.rv_comment_list);
        llComment = findViewById(R.id.ll_comment);
        tvListHeader = findViewById(R.id.tv_list_header);
        tvCommentSum = findViewById(R.id.tv_comment_sum);

        ivPraise = findViewById(R.id.iv_praise);
        ivCollect = findViewById(R.id.iv_collect);
        tvPraise = findViewById(R.id.tv_praise);
        tvCollect = findViewById(R.id.tv_collect);

        srlRefresh = findViewById(R.id.srl_refresh);
        srlRefresh.setColorSchemeColors(0xfffea419);

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
        } else if (view.getId() == R.id.rl_praise) {
            if (isPraise) {
                isPraise = false;
                praiseSum--;
                presenter.sendCancelPraise(postId);
                ivPraise.setImageResource(R.mipmap.icon_praise_light);
                tvPraise.setText(String.format("点赞(%d)", praiseSum));
            } else {
                isPraise = true;
                praiseSum++;
                presenter.sendPraiseMessage(postId);
                ivPraise.setImageResource(R.mipmap.icon_praise_dark);
                tvPraise.setText(String.format("点赞(%d)", praiseSum));
            }
        } else if (view.getId() == R.id.rl_collect) {
            if (isCollect) {
                isCollect = false;
                collectSum--;
                presenter.sendCancelCollect(postId);
                ivCollect.setImageResource(R.mipmap.icon_collect_light);
                tvCollect.setText(String.format("收藏(%d)", collectSum));
            } else {
                isCollect = true;
                collectSum++;
                presenter.sendCollectMessage(postId);
                ivCollect.setImageResource(R.mipmap.icon_collect_dark);
                tvCollect.setText(String.format("收藏(%d)", collectSum));
            }
        } else if (view.getId() == R.id.rl_share) {
            showToast("暂未完成");
        } else if (view.getId() == R.id.civ_icon) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("user_id", post.getAuthor().getObjectId());
            startActivity(intent);
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
        if (srlRefresh.isRefreshing()) {
            srlRefresh.setRefreshing(false);
        }
        this.post = post;
        runOnUiThread(this::loadPage);
    }

    @SuppressLint("DefaultLocale")
    private void loadPage() {
        Glide.with(this).load(post.getAuthor().getIconUrl()).into(civUserIcon);
        tvTitle.setText(post.getTitle());
        tvUserName.setText(post.getAuthor().getName());
        tvUserName.setTextColor(Constant.userColors[Math.abs(post.getAuthor().getObjectId().hashCode()) % Constant.userColors.length]);
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
        if (srlRefresh.isRefreshing()) {
            srlRefresh.setRefreshing(false);
        }
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

    @Override
    public void sendCommentFailure(String msg) {
        showToast("发表失败");
    }

    @Override
    public void loadCommentSucceed(List<Comment> commentList) {
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
        tvCommentSum.setText(String.format("评论(%d)", commentList.size()));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadCommentFailure(String msg) {
        showToast("评论获取失败");
    }

    @Override
    public void loadPraiseSumAndIsPraise(int sum, boolean isPraise) {
        this.isPraise = isPraise;
        praiseSum = sum;
        runOnUiThread(() -> {
            if (isPraise) {
                ivPraise.setImageResource(R.mipmap.icon_praise_dark);
            } else {
                ivPraise.setImageResource(R.mipmap.icon_praise_light);
            }
            tvPraise.setText(String.format("点赞(%d)", sum));
        });
    }

    @Override
    public void loadCollectSumAndIsCollect(int sum, boolean isCollect) {
        this.isCollect = isCollect;
        collectSum = sum;
        runOnUiThread(() -> {
            if (isCollect) {
                ivCollect.setImageResource(R.mipmap.icon_collect_dark);
            } else {
                ivCollect.setImageResource(R.mipmap.icon_collect_light);
            }
            tvCollect.setText(String.format("收藏(%d)", sum));
        });
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
