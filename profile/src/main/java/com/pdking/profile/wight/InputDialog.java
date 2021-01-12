package com.pdking.profile.wight;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.competition.pdking.lib_common_resourse.toast.ToastUtils;
import com.pdking.profile.R;


public class InputDialog extends Dialog implements View.OnClickListener {

    private View view;
    private EditText edRoomName;
    private Button btnCancel;
    private Button btnAdd;
    private TextView tvTitle;
    private OnClickListener listener;

    public InputDialog(Context context, String title, String hint, OnClickListener cb) {
        super(context, R.style.DialogTheme);
        this.view = LayoutInflater.from(context).inflate(R.layout.layout_input_pop, null);
        listener = cb;
        setContentView(view);
        init(view);
        tvTitle.setText(title);
        edRoomName.setHint(hint);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    private void init(View view) {
        edRoomName = view.findViewById(R.id.ed_room_name);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnAdd = view.findViewById(R.id.btn_add);
        tvTitle = view.findViewById(R.id.title);
        btnCancel.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_cancel) {
            this.hide();
            this.dismiss();
        } else if (id == R.id.btn_add) {
            String input = edRoomName.getText().toString();
            if (input.isEmpty()) {
                ToastUtils.showToast(getContext(), "请输入正确的内容");
                return;
            }
            if (listener != null) {
                listener.onClick(input);
            }
            this.hide();
            this.dismiss();
        }
    }


    public interface OnClickListener {
        void onClick(String input);
    }
}
