package chen.baselib.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import chen.baselib.R;
import chen.baselib.utils.FragmentUserVisibleController;


/**
 * Created by Reims
 * init:初始化数据
 * initListenerEvent:设置监听事件
 */

public abstract class BaseFragment extends Fragment implements FragmentUserVisibleController.UserVisibleCallback {

    protected BaseActivity mContext;
    protected View mRootView;

    Unbinder mUnbinder;
    private FragmentUserVisibleController userVisibleController;
    private AlertDialog loadingDialog;


    public BaseFragment() {
        userVisibleController = new FragmentUserVisibleController(this, this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = (BaseActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        view = inflater.inflate(getResId(), container, false);
        View view = setContentView(inflater, getResId());
        mUnbinder = ButterKnife.bind(this, view);
        loadingDialog = new AlertDialog.Builder(getActivity(), R.style.CustomDialog)
                .setView(R.layout.layout_loading_view)
                .setCancelable(true)
                .create();
        return view;
    }

    /**
     * 调用该办法可避免重复加载UI
     */
    public View setContentView(LayoutInflater inflater, int resId) {
        if (mRootView == null) {
            mRootView = inflater.inflate(resId, null);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    public View getContentView() {
        return mRootView;
    }

    public abstract int getResId();


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userVisibleController.activityCreated();
        init();
        initListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        userVisibleController.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        hideLoading();
        userVisibleController.pause();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        userVisibleController.setUserVisibleHint(isVisibleToUser);
    }


    protected abstract void init();

    protected abstract void initListener();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null)
            mUnbinder.unbind();
    }

    @Override
    public boolean isWaitingShowToUser() {
        return userVisibleController.isWaitingShowToUser();
    }

    @Override
    public void setWaitingShowToUser(boolean waitingShowToUser) {
        userVisibleController.setWaitingShowToUser(waitingShowToUser);
    }

    @Override
    public boolean isVisibleToUser() {
        return userVisibleController.isVisibleToUser();
    }

    @Override
    public void callSuperSetUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onVisibleToUserChanged(boolean isVisibleToUser, boolean invokeInResumeOrPause) {

    }

    protected void showLoading() {
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    protected void hideLoading() {
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    protected void finish() {
        mContext.finish();
    }

    protected void finishSimple() {
        mContext.finishSimple();
    }

    public void finishResult(Intent intent) {
        mContext.finishResult(intent);
    }

    public void finishResult() {
        mContext.finishResult();
    }

/*    public void startActivity(Bundle bundle, Class<?> target) {
        mContext.startActivity(bundle, target);
    }

    public void startForResult(Bundle bundle, int requestCode, Class<?> target) {
        Intent intent = new Intent(mContext, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        mContext.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }*/
}
