package com.competition.pdking.module_community.community.post_details.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.competition.pdking.lib_base.com.competition.pdking.bean.User;
import com.competition.pdking.lib_common_resourse.constant.Constant;
import com.competition.pdking.module_community.R;
import com.competition.pdking.module_community.community.post_details.bean.Comment;

import java.util.List;

import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author liupeidong
 * Created on 2019/10/6 19:30
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<Comment> commentList;
    private Context context;
    private String userId;

    public CommentAdapter(List<Comment> commentList, Context context) {
        this.commentList = commentList;
        this.context = context;
        userId = BmobUser.getCurrentUser(User.class).getObjectId();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_comment_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setData(i);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView civUserIcon;
        TextView tvUserName;
        TextView tvTime;
        TextView tvLevel;
        TextView tvContent;
        TextView tvIsMaster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            civUserIcon = itemView.findViewById(R.id.civ_icon);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvLevel = itemView.findViewById(R.id.tv_level);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvIsMaster = itemView.findViewById(R.id.tv_is_master);
        }

        @SuppressLint("DefaultLocale")
        public void setData(int i) {
            Comment comment = commentList.get(i);
            Glide.with(context).load(comment.getUser().getIconUrl()).into(civUserIcon);
            tvUserName.setText(comment.getUser().getName());
            tvUserName.setTextColor(Constant.userColors[Math.abs(comment.getUser().getObjectId().hashCode()) % Constant.userColors.length]);
            tvLevel.setText(String.format("%d#", i + 1));
            tvTime.setText(comment.getCreatedAt());
            tvContent.setText(comment.getContent());
            if (comment.getUser().getObjectId().equals(userId)) {
                tvIsMaster.setText("楼主");
            } else {
                tvIsMaster.setVisibility(View.GONE);
            }
        }

    }


}
