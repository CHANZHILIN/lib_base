package chen.baselib.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * @param <V>具体的View
 * @param <M>具体的model   Created by CHEN_ on 2019/4/11.
 */
public abstract class BasePresenter<V extends BaseView, M extends BaseModel> {
    protected V mvpView;
    protected CompositeDisposable mDisposable;
    protected M mModel;

    public BasePresenter(V mvpView) {
        this.mvpView = mvpView;
        mModel = createModel();
    }

    protected abstract M createModel();

    public void detachView() {
        this.mvpView = null;
        clearDisposable();
    }


    //RXjava取消注册，以避免内存泄露
    public void clearDisposable() {
        if (mDisposable != null && mDisposable.size() != 0) {
            mDisposable.clear();
        }
    }


    public void addDisposable(Disposable disposable) {
        if (mDisposable == null) {
            mDisposable = new CompositeDisposable();
        }
        mDisposable.add(disposable);
    }
}
