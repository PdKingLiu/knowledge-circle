package com.pdking.commonListPage.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.competition.pdking.lib_base.com.competition.pdking.bean.User;
import com.competition.pdking.lib_common_resourse.constant.Constant;
import com.pdking.profile.ProfileActivity;
import com.pdking.profile.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> userList;
    private Context context;

    public UserAdapter(ArrayList<User> commentList, Context context) {
        this.userList = commentList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_user_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setData(i);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView civUserIcon;
        TextView tvUserName;
        View dividerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            civUserIcon = itemView.findViewById(R.id.userIconIv);
            tvUserName = itemView.findViewById(R.id.userNameTv);
            dividerView = itemView.findViewById(R.id.dividerView);
        }

        @SuppressLint("DefaultLocale")
        public void setData(int i) {
            User user = userList.get(i);
            Glide.with(context).load(user.getIconUrl()).into(civUserIcon);
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), ProfileActivity.class);
                intent.putExtra("user_id", user.getObjectId());
                v.getContext().startActivity(intent);
            });
            if (i == userList.size() - 1) {
                dividerView.setVisibility(View.GONE);
            }
            tvUserName.setText(user.getName());
            tvUserName.setTextColor(Constant.userColors[Math.abs(user.getObjectId().hashCode()) % Constant.userColors.length]);
        }
    }
}
