package chen.baselib.baseActivityModule;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.util.SparseArrayCompat;
import android.util.ArrayMap;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Introduce :
 * Created by CHEN_ on 2019/3/29.
 * PACKAGE_NAME : chen.baselib
 **/
public class ActivityModuleManager extends ModuleManager {
    public void initModules(Bundle saveInstance, Activity activity, ArrayMap<String, ArrayList<Integer>> modules) {  //初始化业务模块
        if (activity == null || modules == null) return;
        moduleConfig(modules);
        initModules(saveInstance, activity);
    }

    public void initModules(Bundle saveInstance, Activity activity) {
        if (getModuleName() == null) return;
        //获取配置
        for (String moduleName : getModuleName().keySet()) {
            ActivityModule module = ModuleFactory.newModuleInstance(moduleName);
            if (module != null) {
                ModuleContext moduleContext = new ModuleContext();
                moduleContext.setContext(activity);
                moduleContext.setSaveInstance(saveInstance);
                //关联视图
                SparseArrayCompat<ViewGroup> viewGroups = new SparseArrayCompat<>();
                ArrayList<Integer> mViewIds = getModuleName().get(moduleName);
                if (mViewIds != null && mViewIds.size() > 0) {
                    for (int i = 0; i < mViewIds.size(); i++) {
                        viewGroups.put(i, (ViewGroup) activity.findViewById(mViewIds.get(i).intValue()));
                    }
                }
                moduleContext.setViewGroups(viewGroups);    //保存视图
                module.init(moduleContext, null);  //初始化每个module
                allModules.put(moduleName, module);      //记录module的名称和信息
            }
        }
    }
}
