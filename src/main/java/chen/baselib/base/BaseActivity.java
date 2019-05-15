package chen.baselib.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import chen.baselib.R;
import chen.baselib.utils.AndroidBugWorkaround;


/**
 * Created by Reims
 * BaseActivity,做大部分Activity都需要的功能，例如加载动画，@BindView等操作
 * 至于为什么在BaseActivity做getResId操作，因为ButterKnife必须在setContentView操作后面
 * 所以onCreate方法必须抽取在这里实现，然后通过initData初始化数据,initEvent设置点击监听
 * init方法执行于initData方法前,用于获取数据new对象等
 *
 * @date 2017/8/29.
 */

public abstract class BaseActivity extends AppCompatActivity {
//    Unbinder unbinder;
/*    //    @BindView(R.id.tv_toolbar_title)
    protected TextView toolbarTitle;
    //    @BindView(R.id.iv_toolbar_back)
    protected ImageView toolbarBack;
    //    @BindView(R.id.iv_toolbar_delete)
    protected ImageView toolbarDelete;
    //    @BindView(R.id.tv_toolbar_righttitle)
    protected TextView toolbarRightTitle;*/

//    private AlertDialog loadingDialog;

    protected ImageView mIvBack;
    protected TextView mTitle;
    protected TextView mRightTitle;

    protected BaseActivity mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        mContext = this;
        setContentView(getResId());
        AndroidBugWorkaround.assistActivity(mContext);  //适配输入法弹出和底部导航栏遮盖问题

     /*   if (ChangeStatusTextColorUtil.isSetBar()) { //更改状态栏颜色
            ChangeStatusTextColorUtil.changeTextColor(mContext);
        }*/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        unbinder = ButterKnife.bind(this);
//        loadingDialog = new AlertDialog.Builder(this, R.style.CustomDialog)
//                .setView(R.layout.layout_loading_view)
//                .setCancelable(true)
//                .create();
//
        mIvBack = findViewById(R.id.iv_back);
        mTitle = findViewById(R.id.title);
        mRightTitle = findViewById(R.id.right_title);
        // 这里把标题栏回退点击事件放在init之前，是为了可以在子页面根据需要重写他的点击事件
        setBackClick();
        init();
        initListener();
    }

    /*
        返回点击结束
     */
    private void setBackClick() {
        if (mIvBack != null) {
            mIvBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    public void setTitle(int res) {
        setTitle(getString(res));
    }

    /**
     * 设置xml资源id
     *
     * @return
     */
    protected abstract int getResId();

    /**
     * 初始化
     */
    protected void init() {}

    /**
     * 初始化点击事件
     */
    protected abstract void initListener();

    @Override
    protected void onPause() {
        super.onPause();
//        hideLoading();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*if (unbinder != null) {
            unbinder.unbind();
        }
        if (loadingDialog != null)
            loadingDialog = null;*/
    }

    protected void setTitle(final String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTitle != null)
                    mTitle.setText(title);
            }
        });

    }

  /*  protected void showLoading() {
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }*/

/*    protected void hideLoading() {
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }*/

    /**
     * 覆写startActivity方法，加入切换动画
     */
 /*   public void startActivity(Bundle bundle, Class<?> target) {
        Intent intent = new Intent(this, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }*/

    /**
     * 带回调的跳转
     *
     * @param bundle
     * @param requestCode
     * @param target
     */
 /*   public void startForResult(Bundle bundle, int requestCode, Class<?> target) {
        Intent intent = new Intent(this, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }*/

    /**
     * 覆写finish方法，覆盖默认方法，加入切换动画
     */
/*    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }*/

    /**
     * 不带切换动画
     */
    public void finishSimple() {
        super.finish();
    }

    public void finishResult(Intent intent) {
        setResult(RESULT_OK, intent);
        this.finish();
    }

    public void finishResult() {
        setResult(RESULT_OK);
        this.finish();
    }

    @Override
    public boolean moveTaskToBack(boolean nonRoot) {
        return super.moveTaskToBack(nonRoot);
    }
}
