package chen.baselib;

/**
 * Introduce :  ARouter跳转的链接
 * Created by CHEN_ on 2019/3/26.
 **/
public interface Constants {

    String REN_MIN_BI = String.valueOf(((char) 165));      //￥ 人民币符号
    String DEBUG_TAG = "CHEN";// LogCat的标记
    boolean DEBUG_ENABLE = false;// 是否调试模式

    /*app*/
    String MAIN_ACTIVITY_PATH =  "/app/MainActivity";     //MainActivity
    /*module_login*/
    String LOGIN_ACTIVITY_PATH = "/module_login/loginActivity";      //登录页面
    String SECOND_ACTIVITY_PATH = "/module_login/secondActivity";      //fragment的页面
}
