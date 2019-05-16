package chen.baselib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import chen.baselib.utils.ToastUtil;

/**
 * Created by CHEN_ on 2019/4/11.
 *
 * @param <P> 具体的presenter
 */

public abstract class MVPFragment<P extends BasePresenter> extends BaseFragment implements BaseView {
    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresent();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    protected abstract P createPresent();

    @Override
    public void onError(int code, String msg) {
        hideLoading();
        ToastUtil.show(msg);
    }

    @Override
    public void onSuccess(String msg) {
        hideLoading();
    }
}
