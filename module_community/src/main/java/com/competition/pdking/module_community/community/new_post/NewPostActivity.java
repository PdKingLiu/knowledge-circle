package com.competition.pdking.module_community.community.new_post;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.competition.pdking.lib_base.BaseActivity;
import com.competition.pdking.module_community.R;
import com.competition.pdking.module_community.community.new_post.weight.InsertLinkDialog;
import com.competition.pdking.module_community.community.rich_edittext.RichEditText;
import com.competition.pdking.module_community.community.utils.FileUtils;

import java.io.File;
import java.util.Objects;

public class NewPostActivity extends BaseActivity {

    private RichEditText mRichEditText;

    final private int final_go_album = 1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_new_post);
        initView();
    }

    private void initView() {
        mRichEditText = findViewById(R.id.ret_content);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.rl_back) {
            finish();
        } else if (view.getId() == R.id.iv_picture) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, final_go_album);
        } else if (view.getId() == R.id.iv_link) {
            InsertLinkDialog dialog = new InsertLinkDialog(this, R.style.DialogTheme);
            dialog.setListener((name, link) -> mRichEditText.insertLink(name, link));
            dialog.setCancelable(true);
            dialog.show();
        } else if (view.getId() == R.id.rl_next) {
            Intent intent = new Intent(this, PostSettingActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case final_go_album:
                if (resultCode == RESULT_OK && data != null) {
                    mRichEditText.insertImage(data.getData(), "www.xxx.com");
                    File file = new FileUtils().uriToFile(this,
                            Objects.requireNonNull(data.getData()));
                }
                break;
        }
    }

}
