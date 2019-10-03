package com.competition.pdking.module_community.community.rich_edittext;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.style.ImageSpan;

/**
 * @author liupeidong
 * Created on 2019/9/11 21:28
 */
class MyImageSpan extends ImageSpan {

    private Uri uri;

    public MyImageSpan(Context context, @NonNull Bitmap b, Uri uri) {
        super(context, b);
        this.uri = uri;
    }

    @Nullable
    @Override
    public String getSource() {
        return uri.toString();
    }
}
