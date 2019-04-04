package chen.baselib.modularization_distribute;

import android.os.Bundle;

/**
 * Introduce :
 * Created by CHEN_ on 2019/3/29.
 * PACKAGE_NAME : chen.baselib
 **/
public abstract class ActivityModule {

    public abstract void init(ModuleContext moduleContext, Bundle bundle);

    public abstract void onSaveInstanceState(Bundle outState);

    public abstract void onResume();

    public abstract void onPause();

    public abstract void onStop();

    public abstract void onOrientationChanges(boolean isLandscape);

    public abstract void onDestory();
}
