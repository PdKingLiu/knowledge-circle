package com.competition.pdking.module_community.community.community_list_page.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.competition.pdking.module_community.R;
import com.competition.pdking.module_community.community.new_post.bean.Post;

import java.util.Date;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author liupeidong
 * Created on 2019/10/5 10:43
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> implements View.OnClickListener {

    private List<Post> list;
    private Context context;
    private String[] modules = {"技术交流", "笔试面经", "猿生活", "资源分享", "我要提问",
            "招聘信息", "职业发展", "产品运营", "留学生", "工作以后"};
    private int[] colors = {0xFFC278E7, 0xFFA1A1A1, 0xFFFC7123, 0xFFF34C54,
            0xFFE8CA2D, 0xFF418AC3, 0xFF10B4D2, 0xFF42C1A2};

    private OnClickListener listener;

    private final Random random = new Random(System.currentTimeMillis());

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    public PostAdapter(List<Post> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.layout_post_list_item, viewGroup, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setData(list.get(i), i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick((Integer) v.getTag());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private CircleImageView civUserIcon;
        private TextView tvUserName;
        private TextView tvTime;
        private TextView tvKind;
        private TextView tvPostName;
        private TextView tvPostContent;
        private TextView tvTopic;
        private TextView tvComment;
        private TextView tvPraise;
        private TextView tvScan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            civUserIcon = itemView.findViewById(R.id.civ_user_icon);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvKind = itemView.findViewById(R.id.tv_kind);
            tvPostName = itemView.findViewById(R.id.tv_post_name);
            tvPostContent = itemView.findViewById(R.id.tv_post_content);
            tvTopic = itemView.findViewById(R.id.tv_topic);
            tvComment = itemView.findViewById(R.id.tv_comment);
            tvPraise = itemView.findViewById(R.id.tv_praise);
            tvScan = itemView.findViewById(R.id.tv_scan);
        }

        @SuppressLint("DefaultLocale")
        public void setData(Post post, int i) {
            view.setTag(i);
            Glide.with(context).load(post.getAuthorIcon()).into(civUserIcon);
            tvUserName.setText(post.getAuthorName());
            tvKind.setText(modules[post.getKind()]);
            tvPostName.setText(post.getTitle());
            StringBuilder sb = new StringBuilder("#");
            for (String s : post.getTopic()) {
                sb.append(s).append(" ");
            }
            tvTopic.setText(sb.toString());
            tvComment.setText(String.valueOf(post.getComment()));
            tvPraise.setText(String.valueOf(post.getPraise()));
            tvScan.setText(String.valueOf(post.getScan()));
            Date date = post.getCreateData();
            Date now = new Date();
            int d = (int) (now.getTime() - date.getTime());
            d = d / 1000;
            if (d < 60) {
                tvTime.setText(String.format("%d秒前", d));
            } else if (d < 60 * 60) {
                tvTime.setText(String.format("%d分钟前", d / 60));
            } else if (d < 24 * 60 * 60) {
                tvTime.setText(String.format("%d小时前", d / 60 / 60));
            } else {
                tvTime.setText(String.format("%d天前", d / 60 / 60 / 24));
            }
            sb.delete(0, sb.length());
            String s = Html.fromHtml(post.getContent()).toString();
            for (int j = 0; j < s.length(); j++) {
                char c = s.charAt(j);
                if (c == '\n') {
                    sb.append(' ');
                } else if (c != '￼') {
                    sb.append(c);
                }
            }

            int ran = random.nextInt(colors.length);
            Log.d("Lpp", "setData: " + ran + "-" + colors[ran]);
            tvPostContent.setText(sb.toString().trim());
            tvUserName.setTextColor(colors[ran]);
        }
    }

    interface OnClickListener {
        void onClick(int i);
    }
}
