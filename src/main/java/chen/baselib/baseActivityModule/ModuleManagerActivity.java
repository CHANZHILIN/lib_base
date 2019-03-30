package chen.baselib.baseActivityModule;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.util.Log;

import java.util.ArrayList;

/**
 * Introduce : 如需使用activity分发要继承这个ModuleManagerActivity
 * Created by CHEN_ on 2019/3/29.
 * PACKAGE_NAME : chen.baselib
 **/
public abstract class ModuleManagerActivity extends AppCompatActivity {
    private ActivityModuleManager moduleManager;
    private static final String TAG = "ModuleManagerActivity";

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        if (moduleManager == null) {
            long ti = System.currentTimeMillis();
            moduleManager = new ActivityModuleManager();    //初始化管理者
            moduleManager.initModules(savedInstanceState, ModuleManagerActivity.this, moduleConfig());
            Log.v(TAG, "init use time = " + (System.currentTimeMillis() - ti));
        }
    }

    //获取viewId
    protected abstract int getContentViewId();

    //设置module列表
    public abstract ArrayMap<String, ArrayList<Integer>> moduleConfig();

    @Override
    protected void onResume() {
        super.onResume();
        moduleManager.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        moduleManager.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        moduleManager.onDestory();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        moduleManager.onConfigurationChanged(newConfig);
    }
}
