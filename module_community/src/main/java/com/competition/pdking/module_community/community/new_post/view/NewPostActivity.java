package com.competition.pdking.module_community.community.new_post.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.competition.pdking.lib_base.BaseActivity;
import com.competition.pdking.lib_common_resourse.loadingview.LoadingDialog;
import com.competition.pdking.lib_common_resourse.toast.ToastUtils;
import com.competition.pdking.lib_common_resourse.utils.FileUtils;
import com.competition.pdking.module_community.R;
import com.competition.pdking.module_community.community.new_post.NewPostContract;
import com.competition.pdking.module_community.community.new_post.NewPostPresenter;
import com.competition.pdking.module_community.community.new_post.rich_edittext.RichEditText;
import com.competition.pdking.module_community.community.new_post.weight.InsertLinkDialog;

import java.io.File;
import java.util.Objects;

public class NewPostActivity extends BaseActivity implements NewPostContract.ViewOfNewPostPage {

    private RichEditText mRichEditText;
    private EditText etTitle;
    private String title;
    private String content;

    private NewPostContract.PresenterOfNewPostPage presenterOfNewPostPage;

    final private int final_go_album = 1;
    private LoadingDialog loading;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_new_post);
        setPresenter(new NewPostPresenter(this));
        initView();
    }

    private void initView() {
        mRichEditText = findViewById(R.id.ret_content);
        etTitle = findViewById(R.id.et_title);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClick(View view) {
        Log.d("Lpp", "onClick: " + view);
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
            title = etTitle.getText().toString();
            content = Html.toHtml(mRichEditText.getEditableText(),
                    Html.TO_HTML_PARAGRAPH_LINES_INDIVIDUAL);
            if (title.equals("") || content.equals("")) {
                ToastUtils.showToast(this, "请输入内容！");
                return;
            }
            Intent intent = new Intent(this, PostSettingActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("content", content);
            startActivityForResult(intent, 2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case final_go_album:
                if (resultCode == RESULT_OK && data != null) {
                    File file = new FileUtils().uriToFile(this,
                            Objects.requireNonNull(data.getData()));
                    if (file == null) {
                        ToastUtils.showToast(this, "未知错误");
                        return;
                    }
                    showLoading("上传中···");
                    new Handler().postDelayed(() -> presenterOfNewPostPage.
                            uploadFile(file, NewPostActivity.this), 1000);
                }
                break;
            case 2:
                if (resultCode == 10) {
                    finish();
                }
                break;
        }
    }

    @Override
    public void uploadFileSucceed(String url, File file) {
        hideLoading();
        showToast("上传成功");
        runOnUiThread(() -> mRichEditText.insertImage(file, url));
    }

    @Override
    public void uploadFileFailure(String msg) {
        hideLoading();
        showToast("上传失败");
    }

    @Override
    public void setPresenter(Object o) {
        presenterOfNewPostPage = (NewPostContract.PresenterOfNewPostPage) o;
    }


    @Override
    public void showLoading(String msg) {
        if (loading == null) {
            loading = new LoadingDialog(this, msg);
        }
        if (!loading.isShowing()) {
            runOnUiThread(() -> loading.show());
        }
    }

    @Override
    public void hideLoading() {
        if (loading != null && loading.isShowing()) {
            runOnUiThread(() -> loading.dismiss());
        }
    }

    @Override
    public void showToast(String msg) {
        runOnUiThread(() -> ToastUtils.showToast(this, msg));
    }
}
