package com.competition.pdking.module_community.community.post_details;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.competition.pdking.lib_base.BaseActivity;
import com.competition.pdking.module_community.R;

public class PostDetailActivity extends BaseActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_post_detail);
    }
}
