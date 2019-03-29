package chen.baselib.baseActivityModule;

/**
 * Introduce :
 * Created by CHEN_ on 2019/3/29.
 * PACKAGE_NAME : chen.baselib
 **/
public class ModuleFactory {
    public static ActivityModule newModuleInstance(String name) {
        if (name == null || name.equals("")) {
            return null;
        }
        try {
            Class<? extends ActivityModule> moduleClzz = (Class<? extends ActivityModule>) Class.forName(name);
            if (moduleClzz != null) {
                ActivityModule instance = (ActivityModule) moduleClzz.newInstance();
                return instance;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
