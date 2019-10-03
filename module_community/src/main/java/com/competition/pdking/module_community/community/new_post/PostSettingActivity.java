package com.competition.pdking.module_community.community.new_post;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.TextView;

import com.competition.pdking.lib_base.BaseActivity;
import com.competition.pdking.module_community.R;

public class PostSettingActivity extends BaseActivity {

    private TextView tvModule;
    private String[] modules = {"技术交流", "笔试面经", "猿生活", "资源分享", "我要提问", "招聘信息", "职业发展", "产品运营",
            "留学生", "工作以后"};
    private int moduleKind = -1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_post_setting);
        tvModule = findViewById(R.id.tv_module);
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
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode >= 10) {
                tvModule.setText(modules[resultCode % 10]);
            }
        }
    }
}
