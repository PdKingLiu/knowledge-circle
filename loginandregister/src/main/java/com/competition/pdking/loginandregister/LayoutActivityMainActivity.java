package com.competition.pdking.loginandregister;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

@Route(path = "/login/activity")
public class LayoutActivityMainActivity extends AppCompatActivity {

    @Autowired
    String key3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_main);
        ARouter.getInstance().inject(this);
        Toast.makeText(this, "key3" + key3, Toast.LENGTH_SHORT).show();
    }
}
