package com.competition.pdking.module_community.community.new_post;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter = new ChatAdapter(list);
        adapter.setListener(i -> {
            list.remove(i);
            sum--;
            tvSum.setText(String.format("已选：%d/5", sum));
            adapter.notifyDataSetChanged();
        });
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("DefaultLocale")
    public void onClick(View view) {
        if (view.getId() == R.id.rl_back) {
            finish();
        } else if (view.getId() == R.id.btn_add) {
            if (sum == 5) {
                ToastUtils.showToast(this, "最多添加5个！");
                return;
            }
            String s = editText.getText().toString();
            if (list.contains(s)) {
                ToastUtils.showToast(this, "已经添加过啦！");
                return;
            }
            list.add(s);
            editText.setText("");
            sum++;
            tvSum.setText(String.format("已选：%d/5", sum));
            adapter.notifyDataSetChanged();
        }
    }
}
