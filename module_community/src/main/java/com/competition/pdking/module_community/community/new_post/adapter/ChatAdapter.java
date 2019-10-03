package com.competition.pdking.module_community.community.new_post.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.competition.pdking.module_community.R;

import java.util.List;

/**
 * @author liupeidong
 * Created on 2019/10/3 22:18
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> implements View.OnClickListener {

    private List<String> list;

    private OnClickListener listener;

    public ChatAdapter(List<String> list) {
        this.list = list;
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_chat_adapter, viewGroup, false);
        view.setOnClickListener(this);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
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

        private TextView textView;
        private View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            textView = itemView.findViewById(R.id.tv);
        }

        public void setData(String s, int i) {
            textView.setText(s);
            view.setTag(i);
        }
    }

    public interface OnClickListener {
        void onClick(int i);
    }

}
