package chen.baselib.baseActivityModule;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.ArrayMap;

import java.util.ArrayList;

/**
 * Introduce :
 * Created by CHEN_ on 2019/3/29.
 * PACKAGE_NAME : chen.baselib
 **/
public class ModuleManager {
    private ArrayMap<String, ArrayList<Integer>> modules = new ArrayMap<>();   //模块名字
    protected ArrayMap<String, ActivityModule> allModules = new ArrayMap<>();    //模块实体

    public ArrayMap<String, ArrayList<Integer>> getModuleName() {
        return modules;
    }

    public void moduleConfig(ArrayMap<String, ArrayList<Integer>> modules) {
        this.modules = modules;
    }

    public ActivityModule getModulesByNames(String name) {
        if (!ModuleUtil.empty(allModules)) {
            return allModules.get(name);
        }
        return null;
    }

    public void onResume() {     //恢复周期
        for (ActivityModule module : allModules.values()) {
            if (module != null) {
                module.onResume();
            }
        }
    }

    public void onPause() {     //暂停周期
        for (ActivityModule module : allModules.values()) {
            if (module != null) {
                module.onPause();
            }
        }
    }

    public void onStop() {     //停止周期
        for (ActivityModule module : allModules.values()) {
            if (module != null) {
                module.onStop();
            }
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {     //配置变更周期
        for (ActivityModule module : allModules.values()) {
            if (module != null) {
                module.onOrientationChanges(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE);
            }
        }
    }

    public void onDestory() {     //销毁周期
        for (ActivityModule module : allModules.values()) {
            if (module != null) {
                module.onDestory();
            }
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        for (ActivityModule module : allModules.values()) {
            if (module != null) {
                module.onSaveInstanceState(outState);
            }
        }
    }

}
