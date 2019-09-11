package com.competition.pdking.module_community.community.community_list_page;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.competition.pdking.lib_base.BaseActivity;
import com.competition.pdking.module_community.R;

public class CommunityListPageActivity extends BaseActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_community_list_page);
    }
}
