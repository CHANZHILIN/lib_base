package chen.baselib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import chen.baselib.utils.ToastUtil;


/**
 * @param <P>具体的presenter
 */
public abstract class MVPActivity<P extends BasePresenter> extends BaseActivity implements BaseView {

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    /**
     * @return 返回具体的Persenter
     */
    protected abstract P createPresenter();

    @Override
    public void onSuccess(String msg) {
        hideLoading();
    }

    @Override
    public void onError(int code, String msg) {
        hideLoading();
        ToastUtil.show(msg);
    }
}
