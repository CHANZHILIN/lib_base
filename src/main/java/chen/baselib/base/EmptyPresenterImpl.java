package chen.baselib.base;

/**
 * Introduce : 空的Presenter
 * Created by CHEN_ on 2018/6/22.
 **/
public class EmptyPresenterImpl extends BasePresenter<EmptyView, EmptyModelImpl> {
    public EmptyPresenterImpl(EmptyView mvpView) {
        super(mvpView);
    }

    @Override
    protected EmptyModelImpl createModel() {
        return new EmptyModelImpl();
    }

}
