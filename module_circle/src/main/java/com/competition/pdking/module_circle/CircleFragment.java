package com.competition.pdking.module_circle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.competition.pdking.lib_common_resourse.toast.ToastUtils;
import com.competition.pdking.module_circle.framework.Constants;
import com.competition.pdking.module_circle.framework.PluginManager;
import com.competition.pdking.module_circle.framework.ReflectUtil;

@Route(path = "/module_circle/circle_fragment")
public class CircleFragment extends Fragment {

    public static CircleFragment newInstance() {
        return new CircleFragment();
    }

    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_circle_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.ll_find).setOnClickListener(v -> {
            if (mPluginManager.loadPlugin(Constants.PLUGIN_PATH)) {
                try {
                    Intent intent = new Intent();
                    intent.setClassName("com.plugin.demo.plugindemo2", "com.plugin.demo" +
                            ".plugindemo2" +
                            ".HotPostActivity");
                    getActivity().getApplicationContext().startActivity(intent);
                } catch (Exception e) {
                    ToastUtils.showToast(getContext(), "进入插件模块失败！");
                }
            } else {
                ToastUtils.showToast(getContext(), "插件apk不存在！");
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_STORAGE, 1);
            }
        }
        initPlugin();
    }

    private PluginManager mPluginManager;

    private void initPlugin() {
        ReflectUtil.init();
        mPluginManager = PluginManager.getInstance(getActivity().getApplicationContext());
        mPluginManager.hookInstrumentation();
    }

}
