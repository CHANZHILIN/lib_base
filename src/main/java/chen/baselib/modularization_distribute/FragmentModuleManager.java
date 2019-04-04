package chen.baselib.modularization_distribute;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.util.SparseArrayCompat;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Introduce :获取Fragment的module信息,与activity分发不同的是这里需要rootView
 * Created by CHEN_ on 2019/4/4.
 * PACKAGE_NAME : chen.baselib.modularization_distribute
 **/
public class FragmentModuleManager extends ModuleManager {
    public void initModules(Bundle saveInstance, Activity activity, View rootView, ArrayMap<String, ArrayList<Integer>> modules) {
        if (activity == null || modules == null) return;
        moduleConfig(modules);
        initModules(saveInstance, activity, rootView);
    }

    private void initModules(Bundle saveInstance, Activity activity, View rootView) {
        //获取配置
        for (String moduleName : getModule().keySet()) {
            if (TextUtils.isEmpty(moduleName)) return;
            //创建模块
            ActivityModule module = ModuleFactory.newModuleInstance(moduleName);
            if (module!=null){
                ModuleContext context = new ModuleContext();
                //关联Activity
                context.setContext(activity);
                context.setSaveInstance(saveInstance);
                //关联视图
                SparseArrayCompat<ViewGroup> sVerticalViews = new SparseArrayCompat<>();
                ArrayList<Integer> viewIds = getModule().get(moduleName);
                if (viewIds != null && viewIds.size() > 0){
                    for (int i = 0; i < viewIds.size(); i++) {
                        //添加视图到视图列表
                        sVerticalViews.put(i, (ViewGroup) rootView.findViewById(viewIds.get(i)));
                    }
                }
                context.setViewGroups(sVerticalViews);  //保存视图
                module.init(context,saveInstance);  //初始化各个module
                allModules.put(moduleName,module);
            }
        }
    }
}
