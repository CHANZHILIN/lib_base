package chen.baselib.baseActivityModule;

import android.util.ArrayMap;

/**
 * Introduce :
 * Created by CHEN_ on 2019/3/29.
 * PACKAGE_NAME : chen.baselib
 **/
public class ModuleUtil {
    public static boolean empty(ArrayMap moduleArrayMap) {
        if (moduleArrayMap != null && moduleArrayMap.size() > 0) {
            return false;
        }
        return true;
    }
}
