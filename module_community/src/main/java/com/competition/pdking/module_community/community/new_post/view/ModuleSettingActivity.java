package com.competition.pdking.module_community.community.new_post.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.competition.pdking.lib_base.BaseActivity;
import com.competition.pdking.module_community.R;

public class ModuleSettingActivity extends BaseActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_module_setting);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.rl_back) {
            finish();
        } else if (view.getId() == R.id.tv_0) {
            setResult(10);
            finish();
        } else if (view.getId() == R.id.tv_1) {
            setResult(11);
            finish();
        } else if (view.getId() == R.id.tv_2) {
            setResult(12);
            finish();
        } else if (view.getId() == R.id.tv_3) {
            setResult(13);
            finish();
        } else if (view.getId() == R.id.tv_4) {
            setResult(14);
            finish();
        } else if (view.getId() == R.id.tv_5) {
            setResult(15);
            finish();
        } else if (view.getId() == R.id.tv_6) {
            setResult(16);
            finish();
        } else if (view.getId() == R.id.tv_6) {
            setResult(16);
            finish();
        } else if (view.getId() == R.id.tv_7) {
            setResult(17);
            finish();
        } else if (view.getId() == R.id.tv_8) {
            setResult(18);
            finish();
        } else if (view.getId() == R.id.tv_9) {
            setResult(19);
            finish();
        }
    }
}
