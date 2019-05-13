package chen.baselib;

import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Introduce :  主Application
 * Created by CHEN_ on 2019/5/9.
 * PACKAGE_NAME : chen.baselib
 **/
public class BaseApplication extends MultiDexApplication {
    public synchronized static BaseApplication getInstance() {
        return instante;
    }

    private static BaseApplication instante;


    @Override
    public void onCreate() {
        super.onCreate();
        instante = this;

        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);//初始化

        //Module类的APP初始化
        modulesApplicationInit();

    }

    private void modulesApplicationInit() {
        for (String moduleImpl : ModuleConfig.MODULESLIST) {
            try {
                Class<?> clazz = Class.forName(moduleImpl);
                Object obj = clazz.newInstance();
                if (obj instanceof IComponentApplication) {
                    ((IComponentApplication) obj).onCreate(BaseApplication.getInstance());
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

}
