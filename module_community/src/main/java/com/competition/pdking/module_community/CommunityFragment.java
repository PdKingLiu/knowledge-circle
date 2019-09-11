package com.competition.pdking.module_community;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.competition.pdking.module_community.community.community_list_page.CommunityListPageActivity;

@Route(path = "/module_community/community_fragment")
public class CommunityFragment extends Fragment implements View.OnClickListener {

    public static CommunityFragment newInstance() {
        return new CommunityFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_community_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        LinearLayout linearLayout = view.findViewById(R.id.ll_community);
        linearLayout.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_community) {
            startActivity(new Intent(getContext(), CommunityListPageActivity.class));
        }
    }
}
