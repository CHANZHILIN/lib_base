package chen.baselib.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/**
 * Introduce :
 * <p>
 * 全屏模式下，即使将activity的windowSoftInputMode的属性设置为：adjustResize，
 * 在键盘显示时它未将Activity的Screen向上推动，所以你Activity的view的根树的尺寸是没有变化的。
 * 在这种情况下，你也就无法得知键盘的尺寸，对根view的作相应的推移。全屏下的键盘无法Resize的问题从2.1就已经存在了，直到现在google还未给予解决。
 * 有人已经封装好了该类，你只需引用就OK了。
 * 使用方法
 * 在你的Activity的oncreate()方法里调用AndroidBug5497Workaround.assistActivity(this);即可。
 * 注意：在setContentView(R.layout.xxx)之后调用。
 * <p>
 * 此类解决了，当activity设置了全屏，布局无法随着软键盘的弹起而向上推动
 * 坑1：全屏属性和属性adjustResize冲突
 * 坑2：如果你的APP设置了沉浸式状态栏（4.4及之后支持）的话这样使用还是会有问题，
 * 如果你的手机Android版本是4.4之前的话，你会发现布局显示不全，屏幕底部的控件有一半在屏幕外面，
 * 如果是4.4或之后的话也有问题，软键盘和上推后的布局之间有一个大概是状态栏高度的黑色区域，
 * 为了兼容这两种情况，修改上面的类：
 * 大神：https://blog.csdn.net/plq690816/article/details/51374883
 * <p>
 * <p>
 * 分割线
 * 为了适配底部虚拟按键导航栏遮盖页面问题，做出修改
 * //alter by CHEN_ on 2018/10/24
 * Created by CHEN_ on 2018/8/11.
 **/
public class AndroidBugWorkaround {

    // For more information, see https://code.google.com/p/android/issues/detail?id=5497
    // To use this class, simply invoke assistActivity() on an Activity that already has its content view set.

    public static void assistActivity(Activity activity) {
        new AndroidBugWorkaround(activity);
    }

    private Activity activity;
    private View mChildOfContent;//被监听的视图
    private int usableHeightPrevious;//视图变化前的可用高度
    private FrameLayout.LayoutParams frameLayoutParams;

    private AndroidBugWorkaround(Activity activity) {
        this.activity = activity;
        //获取根布局
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        //给View添加全局的布局监听器
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                possiblyResizeChildOfContent();
            }
        });
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {

            frameLayoutParams.height = usableHeightNow;
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
       /* int usableHeightNow = computeUsableHeight();
        //比较布局变化前后的View的可用高度
        if (usableHeightNow != usableHeightPrevious) {
            //如果两次高度不一致
            //将当前的View的可用高度设置成View的实际高度
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();

            //这个判断是为了解决19之前的版本不支持沉浸式状态栏导致布局显示不完全的问题
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
                Rect frame = new Rect();
                activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
                int statusBarHeight = frame.top;
                usableHeightSansKeyboard -= statusBarHeight;
            }
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard/4)) {
                // keyboard probably just became visible
                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
            } else {
                // keyboard probably just became hidden
                frameLayoutParams.height = usableHeightSansKeyboard;
            }
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }*/
    }

    /**
     * 可用的高度
     *
     * @return
     */
    private int computeUsableHeight() {
     /*   Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);

        //这个判断是为了解决19之后的版本在弹出软键盘时，键盘和推上去的布局（adjustResize）之间有黑色区域的问题
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            return (r.bottom - r.top)+statusBarHeight;
        }

        return (r.bottom - r.top);*/

        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom);
    }

   /* public static boolean checkDeviceHasNavigationBar(Context context) {  //获取是否存在底部导航栏
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;

    }*/


}