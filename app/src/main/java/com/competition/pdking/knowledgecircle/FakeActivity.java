package com.competition.pdking.knowledgecircle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.competition.pdking.module_circle.framework.Constants;
import com.competition.pdking.module_circle.framework.PluginManager;
import com.competition.pdking.module_circle.framework.ReflectUtil;

public class FakeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_fake);
        initPlugin();
    }


    private PluginManager mPluginManager;

    private void initPlugin() {
        ReflectUtil.init();
        mPluginManager = PluginManager.getInstance(getApplicationContext());
        mPluginManager.hookInstrumentation();
        mPluginManager.hookCurrentActivityInstrumentation(this);
    }

    public void onClick(View view) {
        if (mPluginManager.loadPlugin(Constants.PLUGIN_PATH)) {
            Intent intent = new Intent();
            intent.setClassName("com.plugin.demo.plugindemo2", "com.plugin.demo.plugindemo2.HotPostActivity");
            getApplicationContext().startActivity(intent);
        }
    }
}

