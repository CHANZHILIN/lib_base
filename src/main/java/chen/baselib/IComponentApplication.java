package chen.baselib;

import android.app.Application;

/**
 * Introduce :  moduleApplication需要实现这个接口
 * Created by CHEN_ on 2019/5/9.
 * PACKAGE_NAME : chen.baselib
 **/
public interface IComponentApplication {

    void onCreate(BaseApplication application);

    Application getAppliaction();
}
