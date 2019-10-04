package com.competition.pdking.module_community.community.rich_edittext;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.util.AttributeSet;

import java.io.File;

/**
 * @author liupeidong
 * Created on 2019/10/3 11:22
 */
public class RichEditText extends AppCompatEditText {

    private Context context;

    public RichEditText(Context context) {
        super(context);
        this.context = context;
    }

    public RichEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public RichEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void insertImage(File file, String src) {
        new ImageInsert(context, this).insertImage(file, src);
    }

    public void insertLink(String name, String link) {
        SpannableString spannableString = new SpannableString(name);
        spannableString.setSpan(new URLSpan(link), 0, name.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getEditableText().insert(getSelectionStart(), spannableString);
    }
}
