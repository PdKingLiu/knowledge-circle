package com.competition.pdking.module_community.community.rich_edittext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.competition.pdking.module_community.R;

import java.io.File;

/**
 * @author liupeidong
 * Created on 2019/10/3 11:16
 */
public class ImageInsert {

    private Context context;

    private RichEditText richEditText;

    public ImageInsert(Context context, RichEditText richEditText) {
        this.context = context;
        this.richEditText = richEditText;
    }

    public void insertImage(File file, String src) {
        final int maxWidth =
                richEditText.getMeasuredWidth() - richEditText.getPaddingLeft() - richEditText.getPaddingRight() - 200;
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher);
        Glide.with(context).asBitmap()
                .load(file)
                .apply(options)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource,
                                                Transition<? super Bitmap> transition) {
                        Bitmap bitmap = zoomBitmapToFixWidth(resource, maxWidth);
                        image(src, bitmap);
                    }
                });
    }

    public void image(String src, Bitmap pic) {
        String img_str = "img";
        int start = richEditText.getSelectionStart();
        SpannableString ss = new SpannableString("\nimg\n");
        MyImageSpan myImgSpan = new MyImageSpan(context, pic, Uri.parse(src));
        ss.setSpan(myImgSpan, 1, img_str.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        richEditText.getEditableText().insert(start, ss);// 设置ss要添加的位置
        richEditText.requestLayout();
        richEditText.requestFocus();
    }

    public static Bitmap zoomBitmapToFixWidth(Bitmap bitmap, int maxWidth) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int newHight = maxWidth * h / w;
        return zoomBitmap(bitmap, maxWidth, newHight);
    }

    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
    }

}
