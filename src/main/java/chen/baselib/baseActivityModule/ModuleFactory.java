package chen.baselib.baseActivityModule;

/**
 * Introduce :  利用发射获取Activity中不同的小Module
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
            return (ActivityModule) moduleClzz.newInstance();
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
