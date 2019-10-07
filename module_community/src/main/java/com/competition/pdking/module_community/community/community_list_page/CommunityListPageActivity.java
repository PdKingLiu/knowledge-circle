package com.competition.pdking.module_community.community.community_list_page;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.competition.pdking.lib_base.BaseActivity;
import com.competition.pdking.lib_common_resourse.toast.ToastUtils;
import com.competition.pdking.module_community.R;
import com.competition.pdking.module_community.community.community_list_page.adapter.PostAdapter;
import com.competition.pdking.module_community.community.new_post.bean.Post;
import com.competition.pdking.module_community.community.new_post.view.NewPostActivity;
import com.competition.pdking.module_community.community.post_details.view.PostDetailActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class CommunityListPageActivity extends BaseActivity {

    private RecyclerView rvPostList;
    private PostAdapter postAdapter;
    private List<Post> postList;

    private SwipeRefreshLayout srlRefresh;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_community_list_page);
        initView();
        initList();
        initListener();
        srlRefresh.setRefreshing(true);
        new Handler().postDelayed(this::requestRefresh, 1000);
    }

    private void initListener() {
        srlRefresh.setOnRefreshListener(() -> new Handler().postDelayed(this::requestRefresh,
                1000));
    }

    private void requestRefresh() {
        BmobQuery<Post> postBmobQuery = new BmobQuery<>();
        postBmobQuery.order("-createdAt");
        postBmobQuery.include("author,scan");
        postBmobQuery.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                srlRefresh.setRefreshing(false);
                if (e == null || list != null) {
                    postList.clear();
                    postList.addAll(list);
                    runOnUiThread(() -> postAdapter.notifyDataSetChanged());
                } else {
                    ToastUtils.showToast(CommunityListPageActivity.this, "刷新失败");
                }
            }
        });
    }

    private void initList() {
        postList = new ArrayList<>();
        postAdapter = new PostAdapter(postList, this);
        postAdapter.setListener((view, i) -> {
            Intent intent = new Intent(this, PostDetailActivity.class);
            intent.putExtra("postId", postList.get(i).getObjectId());
            startActivity(intent);
        });
        rvPostList.setLayoutManager(new LinearLayoutManager(this));
        rvPostList.setAdapter(postAdapter);
    }

    private void initView() {
        rvPostList = findViewById(R.id.rv_post_list);
        srlRefresh = findViewById(R.id.srl_refresh);
        srlRefresh.setColorSchemeColors(0xfffea419);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.rl_back) {
            finish();
        } else if (view.getId() == R.id.new_post) {
            startActivity(new Intent(this, NewPostActivity.class));
        }
    }
}
