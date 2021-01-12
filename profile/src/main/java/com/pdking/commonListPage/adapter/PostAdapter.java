package com.pdking.commonListPage.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.competition.pdking.lib_base.com.competition.pdking.bean.Post;
import com.competition.pdking.lib_common_resourse.constant.Constant;
import com.pdking.profile.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> implements View.OnClickListener {

    private final Random random = new Random(System.currentTimeMillis());
    private List<Post> list;
    private Context context;
    private OnClickListener listener;

    public PostAdapter(List<Post> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
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
            listener.onClick(v, (Integer) v.getTag());
        }
    }

    public interface OnClickListener {
        void onClick(View view, int i);
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
            Glide.with(context).load(post.getAuthor().getIconUrl()).into(civUserIcon);
            tvUserName.setText(post.getAuthor().getName());
            tvKind.setText(Constant.postModules[post.getKind()]);
            tvPostName.setText(post.getTitle());
            StringBuilder sb = new StringBuilder("#");
            for (String s : post.getTopic()) {
                sb.append(s).append(" ");
            }
            tvTopic.setText(sb.toString());
            tvComment.setText(String.valueOf(post.getComment()));
            tvPraise.setText(String.valueOf(post.getPraise()));
            tvScan.setText(String.valueOf(post.getScan()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            try {
                date = sdf.parse(post.getCreatedAt());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int d = (int) (System.currentTimeMillis() - date.getTime());
            d = d / 1000;
            if (d < 0) {
                tvTime.setText("刚刚");
            } else if (d < 60) {
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

            int ran =
                    Math.abs(post.getAuthor().getObjectId().hashCode()) % Constant.userColors.length;
            tvPostContent.setText(sb.toString().trim());
            tvUserName.setTextColor(Constant.userColors[ran]);
        }
    }
}
