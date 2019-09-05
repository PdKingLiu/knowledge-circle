package com.competition.pdking.loginandregister;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

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

    public void click(View view) {
        Bean bean = new Bean(20, "刘沛栋", "安徽" );
        bean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(LayoutActivityMainActivity.this, s, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LayoutActivityMainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
