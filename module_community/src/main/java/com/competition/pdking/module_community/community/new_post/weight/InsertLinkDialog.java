package com.competition.pdking.module_community.community.new_post.weight;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.competition.pdking.lib_common_resourse.toast.ToastUtils;
import com.competition.pdking.module_community.R;

/**
 * @author liupeidong
 * Created on 2019/10/3 18:00
 */
public class InsertLinkDialog extends Dialog implements View.OnClickListener {

    private EditText etName;
    private EditText etLink;
    private OnClickListener listener;
    private Context context;

    public InsertLinkDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_insert_link_dialog, null);
        setContentView(view);
        etName = view.findViewById(R.id.et_name);
        etLink = view.findViewById(R.id.et_link);
        view.findViewById(R.id.btn_ok).setOnClickListener(this);
        view.findViewById(R.id.btn_cancel).setOnClickListener(this);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_cancel) {
            this.hide();
            this.dismiss();
        } else if (i == R.id.btn_ok) {
            if (listener != null) {
                String name = etName.getText().toString();
                String link = etLink.getText().toString();
                if (name.equals("") || link.equals("")) {
                    ToastUtils.showToast(context, "输入有误");
                    return;
                }
                listener.onClick(name, link);
            }
            this.hide();
            this.dismiss();
        }
    }

    public interface OnClickListener {
        void onClick(String name, String link);
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }
}
