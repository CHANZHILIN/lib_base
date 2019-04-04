package chen.baselib.modularization_distribute;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Introduce :如需使用fragment分发要继承这个ModuleManagerFragment
 * Created by CHEN_ on 2019/4/4.
 * PACKAGE_NAME : chen.baselib.modularization_distribute
 **/
public abstract class ModuleManagerFragment extends Fragment {
    private FragmentModuleManager moduleManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentViewId(),container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //模块管理初始化
        moduleManager = new FragmentModuleManager();
        moduleManager.initModules(savedInstanceState, getActivity(), view, moduleConfig());
    }

    //获取viewId
    protected abstract int getContentViewId();

    //设置module列表
    public abstract ArrayMap<String, ArrayList<Integer>> moduleConfig();


    @Override
    public void onResume() {
        super.onResume();
        moduleManager.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        moduleManager.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        moduleManager.onDestory();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        moduleManager.onConfigurationChanged(newConfig);
    }
}
