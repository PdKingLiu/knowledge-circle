package com.competition.pdking.module_community.community.post_details;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Html;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.competition.pdking.lib_base.com.competition.pdking.bean.User;
import com.competition.pdking.module_community.community.new_post.bean.Post;

import java.util.List;
import java.util.concurrent.ExecutionException;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

import static android.text.Html.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH;

/**
 * @author liupeidong
 * Created on 2019/10/5 15:04
 */
public class PostDetailTasks {

    public void loadPostData(String postId, Activity activity, int width, CallBack callBack) {
        BmobQuery<Post> query = new BmobQuery<>();
        query.include("author");
        query.getObject(postId, new QueryListener<Post>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void done(Post post, BmobException e) {
                if (e == null) {
                    if (post == null) {
                        callBack.failure("加载失败");
                    } else {
                        callBack.succeed(post,
                                post.getAuthor().getObjectId().equals(BmobUser.getCurrentUser(User.class).getObjectId()));
                        updatePostScanList(post);
                        loadContentText(post.getContent(), activity, width, callBack);
                    }
                } else {
                    callBack.failure("加载失败");
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadContentText(String content, Activity activity, int width, CallBack callBack) {
        final CharSequence[] charSequence = new CharSequence[1];
        new Thread(() -> {
            charSequence[0] = Html.fromHtml(content, FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH,
                    source -> {
                        FutureTarget<Drawable> futureBitmap = Glide.with(activity)
                                .asDrawable()
                                .load(source)
                                .submit();
                        Drawable drawable = null;
                        try {
                            drawable = futureBitmap.get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (drawable == null) {
                            return null;
                        }
                        drawable = zoomDrawable(drawable, width, activity);
                        drawable.setBounds(0, 0,
                                drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                        return drawable;
                    }, null);
            callBack.contentText(charSequence[0]);
        }).start();
    }

    public Bitmap drawableToBitmap(Drawable drawable) {// drawable 转换成 bitmap
        int width = drawable.getIntrinsicWidth(); // 取 drawable 的长宽
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ?
                Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565; // 取 drawable 的颜色格式
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);// 建立对应 bitmap
        Canvas canvas = new Canvas(bitmap); // 建立对应 bitmap 的画布
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas); // 把 drawable 内容画到画布中
        return bitmap;
    }

    public Drawable zoomDrawable(Drawable drawable, int w, Activity activity) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        int h = w * height / width;
        Bitmap oldbmp = drawableToBitmap(drawable); // drawable 转换成 bitmap
        Matrix matrix = new Matrix(); // 创建操作图片用的 Matrix 对象
        float scaleWidth = ((float) w / width); // 计算缩放比例
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);// 设置缩放比例
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true); // 建立新的
        // bitmap ，其内容是对原 bitmap 的缩放后的图
        return new BitmapDrawable(activity.getResources(), newbmp); // 把 bitmap 转换成 drawable 并返回
    }

    /*
     * 更新访问过的用户
     * */
    private void updatePostScanList(Post post) {
        BmobRelation relation = new BmobRelation();
        User user = new User();
        user.setObjectId(BmobUser.getCurrentUser(User.class).getObjectId());
        relation.add(user);
        post.setScanList(relation);
        post.update(post.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    updatePostScan(post);
                }
            }
        });
    }


    /*
     * 更新scan的数值
     * */
    private void updatePostScan(Post post) {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereRelatedTo("scanList", new BmobPointer(post));
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                post.setScan(list.size());
                post.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {

                    }
                });
            }
        });
    }

    interface CallBack {

        void contentText(CharSequence text);

        void succeed(Post post, boolean isAuthor);

        void failure(String msg);

    }


}
