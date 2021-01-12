package com.competition.pdking.module_my;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.competition.pdking.lib_base.com.competition.pdking.bean.Post;
import com.competition.pdking.lib_base.com.competition.pdking.bean.User;
import com.competition.pdking.lib_base.com.competition.pdking.bean.UserData;
import com.competition.pdking.lib_base.com.competition.pdking.util.DataUtil;
import com.competition.pdking.lib_common_resourse.constant.Constant;
import com.competition.pdking.lib_common_resourse.utils.ARouterUtils;
import com.pdking.commonListPage.PostListActivity;
import com.pdking.commonListPage.UserListActivity;
import com.pdking.profile.ProfileEditActivity;
import com.pdking.profile.wight.ProfileLikeCountDialog;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

@Route(path = "/module_my/my_fragment")
public class MyFragment extends Fragment {

    private CircleImageView userIconIv;
    private TextView userNameTv;
    private TextView userIntroductionTv;
    private SwipeRefreshLayout refreshLayout;
    private TextView postCountTv;
    private TextView favorCount;
    private TextView followingCountTv;
    private TextView followedCountTv;
    private TextView likeCountTv;
    private ViewGroup likeVG;
    private ViewGroup postVG;
    private ViewGroup favorVG;
    private ViewGroup logoutVG;
    private ViewGroup scanHistory;
    private ViewGroup followedVG;
    private ViewGroup followingVG;
    private int loadResponse = 0;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.userProfile).setOnClickListener((View v) -> startActivityForResult(new Intent(getContext(), ProfileEditActivity.class), 111));
        userIconIv = view.findViewById(R.id.userIconIv);
        userNameTv = view.findViewById(R.id.userNameTv);
        userIntroductionTv = view.findViewById(R.id.userIntroductionTv);
        refreshLayout = view.findViewById(R.id.srl_refresh);
        postCountTv = view.findViewById(R.id.postCountTv);
        favorCount = view.findViewById(R.id.favorCount);
        followingCountTv = view.findViewById(R.id.followingCountTv);
        followedCountTv = view.findViewById(R.id.followedCountTv);
        likeCountTv = view.findViewById(R.id.likeCountTv);
        likeVG = view.findViewById(R.id.likeVG);
        postVG = view.findViewById(R.id.postVG);
        favorVG = view.findViewById(R.id.favorVG);
        logoutVG = view.findViewById(R.id.logoutVG);
        scanHistory = view.findViewById(R.id.scanHistory);
        followedVG = view.findViewById(R.id.followedVG);
        followingVG = view.findViewById(R.id.followingVG);
        refreshLayout.setColorSchemeColors(0xfffea419);
        refreshLayout.setOnRefreshListener(() -> {
            loadResponse = 0;
            loadProfile();
        });
        loadResponse = 0;
        loadProfile();
        refreshLayout.setRefreshing(true);
        logoutVG.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setPositiveButton("确定", (dialog, which) -> {
                BmobUser.logOut();
                ARouter.getInstance().build(ARouterUtils.MainActivity).navigation();
                getActivity().finish();
            });
            builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
            builder.setTitle("提示");
            builder.setMessage("确定要退出吗？");
            builder.show();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 111 && data != null && data.getIntExtra("is_modify", -1) == 1) {
            loadResponse = 0;
            loadProfile();
            refreshLayout.setRefreshing(true);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.layout_my_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void loadProfile() {

        DataUtil.getUserData(BmobUser.getCurrentUser(User.class).getObjectId(), new DataUtil.GetUserDataCallBack() {
            @Override
            public void callback(UserData userData) {
                if (userData != null) {

                    String userDataId = userData.getObjectId();

                    // 获赞 + 帖子数
                    BmobQuery<Post> query = new BmobQuery<>();
                    User user = BmobUser.getCurrentUser(User.class);
                    query.addWhereEqualTo("author", new BmobPointer(user));
                    query.findObjects(new FindListener<Post>() {
                        @Override
                        public void done(List<Post> list, BmobException e) {
                            if (e == null && list != null) {
                                UserData userD = new UserData();
                                userD.setObjectId(userDataId);
                                int praise = 0;
                                for (Post post : list) {
                                    praise += post.getPraise();
                                }
                                userD.setPraise(praise);
                                userD.setPost(list.size());
                                userD.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            loadResponse++;
                                            syncProfile();
                                        } else {
                                            refreshLayout.setRefreshing(false);
                                        }
                                    }
                                });
                            } else {
                                refreshLayout.setRefreshing(false);
                            }
                        }
                    });

                    // 收藏
                    UserData userData1 = new UserData();
                    userData1.setObjectId(userDataId);
                    BmobQuery<Post> query2 = new BmobQuery<>();
                    query2.addWhereRelatedTo("collectList", new BmobPointer(userData1));
                    query2.findObjects(new FindListener<Post>() {
                        @Override
                        public void done(List<Post> object, BmobException e) {
                            if (e == null && object != null) {
                                UserData userD = new UserData();
                                userD.setObjectId(userDataId);
                                userD.setCollect(object.size());
                                userD.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            loadResponse++;
                                            syncProfile();
                                        } else {
                                            refreshLayout.setRefreshing(false);
                                        }
                                    }
                                });
                            } else {
                                refreshLayout.setRefreshing(false);
                            }
                        }
                    });


                    // 关注
                    userData1 = new UserData();
                    userData1.setObjectId(userDataId);
                    BmobQuery<User> query3 = new BmobQuery<>();
                    query3.addWhereRelatedTo("attentionList", new BmobPointer(userData1));
                    query3.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            if (e == null && list != null) {
                                UserData userD = new UserData();
                                userD.setObjectId(userDataId);
                                userD.setAttention(list.size());
                                userD.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            loadResponse++;
                                            syncProfile();
                                        } else {
                                            refreshLayout.setRefreshing(false);
                                        }
                                    }
                                });
                            } else {
                                refreshLayout.setRefreshing(false);
                            }
                        }
                    });

                    // 被关注
                    userData1 = new UserData();
                    userData1.setObjectId(userDataId);
                    BmobQuery<User> query4 = new BmobQuery<>();
                    query4.addWhereRelatedTo("fansList", new BmobPointer(userData1));
                    query4.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            if (e == null && list != null) {
                                UserData userD = new UserData();
                                userD.setObjectId(userDataId);
                                userD.setFans(list.size());
                                userD.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            loadResponse++;
                                            syncProfile();
                                        } else {
                                            refreshLayout.setRefreshing(false);
                                        }
                                    }
                                });
                            } else {
                                refreshLayout.setRefreshing(false);
                            }
                        }
                    });
                }
            }
        });


    }

    @SuppressLint("SetTextI18n")
    private void syncProfile() {
        if (loadResponse != 4) {
            return;
        }
        BmobUser.fetchUserInfo(new FetchUserInfoListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    loadResponse = 0;
                    User user = BmobUser.getCurrentUser(User.class);
                    RequestOptions options = new RequestOptions()
                            .placeholder(R.drawable.icon_user_default)//图片加载出来前，显示的图片
                            .fallback(R.drawable.icon_user_default) //url为空的时候,显示的图片
                            .error(R.drawable.icon_user_default);//图片加载失败后，显示的图片
                    Glide.with(MyFragment.this).load(user.getIconUrl()).apply(options).into(userIconIv);
                    userNameTv.setText(user.getName());
                    userNameTv.setTextColor(Constant.userColors[Math.abs(user.getObjectId().hashCode()) % Constant.userColors.length]);
                    if (user.getSex() != null && user.getSex().equals("女")) {
                        userNameTv.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_female), null);
                    } else if (user.getSex() != null && user.getSex().equals("男")) {
                        userNameTv.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_male), null);
                    } else {
                        userNameTv.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    }
                    if (user.getIntroduction() != null && !user.getIntroduction().isEmpty()) {
                        userIntroductionTv.setText("个人简介：" + user.getIntroduction());
                    } else {
                        userIntroductionTv.setText("个人简介：暂无");
                    }
                    postVG.setOnClickListener(v -> {
                        Intent intent = new Intent(getContext(), PostListActivity.class);
                        intent.putExtra("userId", BmobUser.getCurrentUser(User.class).getObjectId());
                        intent.putExtra("title", "帖子列表");
                        startActivity(intent);
                    });
                    favorVG.setOnClickListener(v -> {
                        Intent intent = new Intent(getContext(), PostListActivity.class);
                        intent.putExtra("userId", BmobUser.getCurrentUser(User.class).getObjectId());
                        intent.putExtra("title", "收藏列表");
                        startActivity(intent);
                    });
                    scanHistory.setOnClickListener(v -> {
                        Intent intent = new Intent(getContext(), PostListActivity.class);
                        intent.putExtra("userId", BmobUser.getCurrentUser(User.class).getObjectId());
                        intent.putExtra("title", "浏览历史");
                        startActivity(intent);
                    });
                    followedVG.setOnClickListener(v -> {
                        Intent intent = new Intent(getContext(), UserListActivity.class);
                        intent.putExtra("userId", BmobUser.getCurrentUser(User.class).getObjectId());
                        intent.putExtra("title", "关注列表");
                        startActivity(intent);
                    });
                    followingVG.setOnClickListener(v -> {
                        Intent intent = new Intent(getContext(), UserListActivity.class);
                        intent.putExtra("userId", BmobUser.getCurrentUser(User.class).getObjectId());
                        intent.putExtra("title", "粉丝列表");
                        startActivity(intent);
                    });
                }
                DataUtil.getUserData(BmobUser.getCurrentUser(User.class).getObjectId(), userData -> {
                    if (userData != null) {
                        postCountTv.setText(String.valueOf(userData.getPost()));
                        favorCount.setText(String.valueOf(userData.getCollect()));
                        followingCountTv.setText(String.valueOf(userData.getAttention()));
                        followedCountTv.setText(String.valueOf(userData.getFans()));
                        likeCountTv.setText(String.valueOf(userData.getPraise()));
                        likeVG.setOnClickListener(v -> new ProfileLikeCountDialog(getContext(), userData.getPraise()).show());
                    }
                });
                refreshLayout.setRefreshing(false);
            }
        });
    }
}
