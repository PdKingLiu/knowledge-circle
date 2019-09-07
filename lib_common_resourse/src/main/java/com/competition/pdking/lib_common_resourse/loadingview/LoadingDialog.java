package com.competition.pdking.lib_common_resourse.loadingview;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.competition.pdking.lib_common_resourse.R;
import com.mingle.widget.LoadingView;

/**
 * @author liupeidong
 * Created on 2019/9/7 18:13
 */
public class LoadingDialog extends Dialog {

    private Context mContext;

    public LoadingDialog(Context context, String msg) {
        this(context, R.style.DialogTheme, msg);
    }

    public LoadingDialog(Context context, int themeResId, String msg) {
        super(context, themeResId);
        if (mContext != null) {
            this.mContext = context;
            View view =
                    LayoutInflater.from(context).inflate(R.layout.layout_popwindow_loading_dialog
                    , null);
            ((LoadingView) view.findViewById(R.id.loadView)).setLoadingText(msg);
            setContentView(view);
            Window window = getWindow();
            if (window != null) {
                WindowManager.LayoutParams params = window.getAttributes();
                params.gravity = Gravity.CENTER;
                window.setAttributes(params);
            }
        }
    }
}
