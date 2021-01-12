package com.pdking.detail.post_details;

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
import com.competition.pdking.lib_base.com.competition.pdking.bean.Comment;
import com.competition.pdking.lib_base.com.competition.pdking.bean.Post;
import com.competition.pdking.lib_base.com.competition.pdking.bean.User;
import com.competition.pdking.lib_base.com.competition.pdking.bean.UserData;
import com.competition.pdking.lib_base.com.competition.pdking.util.DataUtil;

import java.util.List;
import java.util.concurrent.ExecutionException;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static android.text.Html.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH;


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
                        updatePostScanSum(post);
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

    public void sendComment(String comment, String postId, SendCommentCallBack callBack) {
        Post post = new Post();
        post.setObjectId(postId);
        Comment com = new Comment();
        com.setPost(post);
        com.setUser(BmobUser.getCurrentUser(User.class));
        com.setContent(comment);
        com.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    com.setObjectId(s);
                    callBack.succeed(com);
                    updateCommentSum(postId);
                } else {
                    callBack.failure(e.getMessage());
                }
            }
        });
    }

    public void loadComment(String postId, LoadCommentCallBack callBack) {
        BmobQuery<Comment> query = new BmobQuery<>();
        Post post = new Post();
        post.setObjectId(postId);
        query.addWhereEqualTo("post", new BmobPointer(post));
        query.include("user");
        query.order("updatedAt");
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if (e == null) {
                    callBack.succeed(list);
                } else {
                    callBack.failure(e.getMessage());
                }
            }
        });
    }

    public void getPraiseSumAndIsPraise(String postId, PraiseCollectCallBack callBack) {
        BmobQuery<User> query = new BmobQuery<>();
        Post post = new Post();
        post.setObjectId(postId);
        query.addWhereRelatedTo("praiseList", new BmobPointer(post));
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    User u = BmobUser.getCurrentUser(User.class);
                    for (User user : list) {
                        if (user.getObjectId().equals(u.getObjectId())) {
                            callBack.succeed(list.size(), true);
                            return;
                        }
                    }
                    callBack.succeed(list.size(), false);
                }
            }
        });
    }

    public void getCollectSumAndIsCollect(String postId, PraiseCollectCallBack callBack) {
        BmobQuery<User> query = new BmobQuery<>();
        Post post = new Post();
        post.setObjectId(postId);
        query.addWhereRelatedTo("collectList", new BmobPointer(post));
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    User u = BmobUser.getCurrentUser(User.class);
                    for (User user : list) {
                        if (user.getObjectId().equals(u.getObjectId())) {
                            callBack.succeed(list.size(), true);
                            return;
                        }
                    }
                    callBack.succeed(list.size(), false);
                }
            }
        });
    }

    public void sendPraiseMessage(String postId) {
        Post post = new Post();
        post.setObjectId(postId);
        BmobRelation relation = new BmobRelation();
        relation.add(BmobUser.getCurrentUser(User.class));
        post.setPraiseList(relation);
        post.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    updatePraiseSum(postId);
                    BmobRelation relation1 = new BmobRelation();
                    Post post1 = new Post();
                    post1.setObjectId(postId);
                    relation1.add(post1);
                    DataUtil.getUserData(BmobUser.getCurrentUser(User.class).getObjectId(), userData -> {
                        if (userData != null) {
                            UserData u = new UserData();
                            u.setObjectId(userData.getObjectId());
                            u.setPraiseList(relation1);
                            u.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e1) {
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    public void sendCancelPraise(String postId) {
        Post post = new Post();
        post.setObjectId(postId);
        BmobRelation relation = new BmobRelation();
        relation.remove(BmobUser.getCurrentUser(User.class));
        post.setPraiseList(relation);
        post.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    updatePraiseSum(postId);
                    BmobRelation relation1 = new BmobRelation();
                    Post post1 = new Post();
                    post1.setObjectId(postId);
                    relation1.remove(post1);
                    DataUtil.getUserData(BmobUser.getCurrentUser(User.class).getObjectId(), userData -> {
                        if (userData != null) {
                            UserData u = new UserData();
                            u.setObjectId(userData.getObjectId());
                            u.setPraiseList(relation1);
                            u.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    public void sendCollectMessage(String postId) {
        Post post = new Post();
        post.setObjectId(postId);
        BmobRelation relation = new BmobRelation();
        relation.add(BmobUser.getCurrentUser(User.class));
        post.setCollectList(relation);
        post.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    updateCollectSum(postId);
                    BmobRelation relation1 = new BmobRelation();
                    Post post1 = new Post();
                    post1.setObjectId(postId);
                    relation1.add(post1);
                    DataUtil.getUserData(BmobUser.getCurrentUser(User.class).getObjectId(), userData -> {
                        if (userData != null) {
                            UserData u = new UserData();
                            u.setObjectId(userData.getObjectId());
                            u.setCollectList(relation1);
                            u.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    public void sendCancelCollect(String postId) {
        Post post = new Post();
        post.setObjectId(postId);
        BmobRelation relation = new BmobRelation();
        relation.remove(BmobUser.getCurrentUser(User.class));
        post.setCollectList(relation);
        post.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    updateCollectSum(postId);
                    BmobRelation relation1 = new BmobRelation();
                    Post post1 = new Post();
                    post1.setObjectId(postId);
                    relation1.remove(post1);
                    DataUtil.getUserData(BmobUser.getCurrentUser(User.class).getObjectId(), userData -> {
                        if (userData != null) {
                            UserData u = new UserData();
                            u.setObjectId(userData.getObjectId());
                            u.setCollectList(relation1);
                            u.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    private void updatePostScanSum(Post post) {
        BmobRelation relation = new BmobRelation();
        User user = new User();
        user.setObjectId(BmobUser.getCurrentUser(User.class).getObjectId());
        relation.add(user);
        post.setScanList(relation);
        post.update(post.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    BmobQuery<User> query = new BmobQuery<>();
                    query.addWhereRelatedTo("scanList", new BmobPointer(post));
                    query.count(User.class, new CountListener() {
                        @Override
                        public void done(Integer integer, BmobException e) {
                            if (e == null) {
                                post.setScan(integer);
                                post.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

        // 更新user scan的列表
        Post post1 = new Post();
        post1.setObjectId(post.getObjectId());
        BmobRelation relation2 = new BmobRelation();
        relation2.add(post1);
        DataUtil.getUserData(user.getObjectId(), userData -> {
            if (userData != null) {
                UserData u = new UserData();
                u.setObjectId(userData.getObjectId());
                u.setScanList(relation2);
                u.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                    }
                });
            }
        });

    }

    private void updateCommentSum(String postId) {
        BmobQuery<Comment> query = new BmobQuery<>();
        Post post = new Post();
        post.setObjectId(postId);
        query.addWhereEqualTo("post", new BmobPointer(post));
        query.count(Comment.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    post.setComment(integer);
                    post.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                        }
                    });
                }
            }
        });
    }

    private void updatePraiseSum(String postId) {
        BmobQuery<User> query = new BmobQuery<>();
        Post post = new Post();
        post.setObjectId(postId);
        query.addWhereRelatedTo("praiseList", new BmobPointer(post));
        query.count(User.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    Post p = new Post();
                    p.setObjectId(postId);
                    p.setPraise(integer);
                    p.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                        }
                    });
                }
            }
        });
    }

    private void updateCollectSum(String postId) {
        BmobQuery<User> query = new BmobQuery<>();
        Post post = new Post();
        post.setObjectId(postId);
        query.addWhereRelatedTo("collectList", new BmobPointer(post));
        query.count(User.class, new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    Post p = new Post();
                    p.setObjectId(postId);
                    p.setCollect(integer);
                    p.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                        }
                    });
                }
            }
        });
    }

    interface CallBack {

        void contentText(CharSequence text);

        void succeed(Post post, boolean isAuthor);

        void failure(String msg);

    }

    interface SendCommentCallBack {

        void succeed(Comment comment);

        void failure(String msg);

    }

    interface LoadCommentCallBack {

        void succeed(List<Comment> commentList);

        void failure(String msg);

    }

    interface PraiseCollectCallBack {

        void succeed(int sum, boolean is);

    }

}
