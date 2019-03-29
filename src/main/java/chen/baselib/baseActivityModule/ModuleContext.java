package chen.baselib.baseActivityModule;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.util.SparseArrayCompat;
import android.view.ViewGroup;

/**
 * Introduce :  保存Activity分发的三个参数
 * Created by CHEN_ on 2019/3/28.
 * PACKAGE_NAME : chen.baselib
 **/
public class ModuleContext {
    private Activity context;
    private Bundle saveInstance;
    private SparseArrayCompat<ViewGroup> viewGroups = new SparseArrayCompat<>();

    public Activity getContext() {
        return context;
    }

    public void setContext(Activity context) {
        this.context = context;
    }

    public Bundle getSaveInstance() {
        return saveInstance;
    }

    public void setSaveInstance(Bundle saveInstance) {
        this.saveInstance = saveInstance;
    }

    public SparseArrayCompat<ViewGroup> getViewGroups() {
        return viewGroups;
    }

    public void setViewGroups(SparseArrayCompat<ViewGroup> viewGroups) {
        this.viewGroups = viewGroups;
    }
}
