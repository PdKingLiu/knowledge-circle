package com.competition.pdking.module_community.community.community_list_page;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.competition.pdking.lib_base.BaseActivity;
import com.competition.pdking.module_community.R;
import com.competition.pdking.module_community.community.new_post.view.NewPostActivity;

public class CommunityListPageActivity extends BaseActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_community_list_page);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.rl_back) {
            finish();
        } else if (view.getId() == R.id.new_post) {
            startActivity(new Intent(this, NewPostActivity.class));
        }
    }
}
