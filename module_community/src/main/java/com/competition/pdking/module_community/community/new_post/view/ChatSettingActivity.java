package com.competition.pdking.module_community.community.new_post.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.competition.pdking.lib_base.BaseActivity;
import com.competition.pdking.lib_common_resourse.toast.ToastUtils;
import com.competition.pdking.module_community.R;
import com.competition.pdking.module_community.community.new_post.adapter.ChatAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatSettingActivity extends BaseActivity {

    private ChatAdapter adapter;
    private RecyclerView recyclerView;
    private List<String> list;
    private EditText editText;
    private TextView tvSum;
    private int sum = 0;

    @SuppressLint("DefaultLocale")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_chat_setting);
        recyclerView = findViewById(R.id.rv);
        editText = findViewById(R.id.et_chat);
        tvSum = findViewById(R.id.tv_sum);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        list = new ArrayList<>();
        adapter = new ChatAdapter(list);
        adapter.setListener(i -> {
            list.remove(i);
            sum--;
            tvSum.setText(String.format("已选：%d/10", sum));
            adapter.notifyDataSetChanged();
        });
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("DefaultLocale")
    public void onClick(View view) {
        if (view.getId() == R.id.rl_back) {
            finish();
        } else if (view.getId() == R.id.btn_add) {
            if (sum == 10) {
                ToastUtils.showToast(this, "最多添加10个！");
                return;
            }
            String s = editText.getText().toString();
            if (s.equals("")) {
                return;
            }
            if (list.contains(s)) {
                ToastUtils.showToast(this, "已经添加过啦！");
                return;
            }
            list.add(s);
            editText.setText("");
            sum++;
            tvSum.setText(String.format("已选：%d/10", sum));
            adapter.notifyDataSetChanged();
        } else if (view.getId() == R.id.rl_next) {
            if (list.size() == 0) {
                ToastUtils.showToast(this, "添加一个话题吧!");
            } else {
                Object[] strings = list.toArray();
                Intent intent = new Intent();
                intent.putExtra("result", strings);
                setResult(10, intent);
                finish();
            }
        }
    }
}
