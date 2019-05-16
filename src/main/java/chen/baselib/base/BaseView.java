package chen.baselib.base;

/**
 * Created by Administrator on 2017/9/20.
 */

public interface BaseView {
    void onSuccess(String msg);

    void onError(int code, String msg);
}
