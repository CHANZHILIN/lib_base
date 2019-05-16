package chen.baselib.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import chen.baselib.R;
import chen.baselib.application.BaseApplication;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by Administrator on 2017/9/21.
 * modify by CHEN_ on 2018/7/16.
 */

public abstract class BaseObserver<T> implements Observer<BaseWebEntity<T>> {
    //对应HTTP的状态码
    private static final int SUCCESS = 200;
    private static final int NOT_FOUND = 404;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int UNSATISFIABLE_REQUEST = 504;
    private static final int SERVICE_TEMPORARILY_UNAVAILABLE = 503;
    private static final int OTHER_ERROR = 506;     //其他错误
    // message
    private static final String SOCKETTIMEOUTEXCEPTION = "网络连接超时，请检查您的网络状态，稍后重试";
    private static final String CONNECTEXCEPTION = "网络连接异常，请检查您的网络状态";
    private static final String UNKNOWNHOST_EXCEPTION_MSG = "网络异常，请检查您的网络状态";
    private static final String NOT_FOUND_EXCEPTION_MSG = "资源未找到";
    private static final String INTERNAL_SERVER_EXCEPTION_MSG = "服务器内部错误";
    private static final String UNSATISFIABLE_REQUEST_MSG = "网关超时，服务器未响应";
    private static final String SERVICE_TEMPORARILY_UNAVAILABLE_MSG = "服务器错误";
    private final BasePresenter mPresenter;

    private Context context;

    public BaseObserver(BasePresenter presenter) {
        this.context = BaseApplication.getInstance().getApplicationContext();
        mPresenter = presenter;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        mPresenter.addDisposable(d);
        onStart();
    }

    @Override
    public void onNext(@NonNull BaseWebEntity<T> o) {
        if (o.getCode() == SUCCESS) {    //返回的code 为200 代表成功，其余的code值统一定为失败
            onSuccess(o.getData());
        } else {
            onError(o.getCode(), o.getMessage());
        }
    }

    /**
     * 开始,需要时重写
     */
    protected void onStart() {
    }

    /**
     * 结束,需要时重写
     */
    protected void onEnd() {
    }

    protected abstract void onSuccess(T data);

    protected abstract void onError(int code, String msg);

    @Override
    public void onError(@NonNull Throwable e) {
            /*    e.printStackTrace();
                        String msg;
                        if (e instanceof HttpException) {
                            HttpException httpException = (HttpException) e;
                            //httpException.response().errorBody().string()
                            int code = httpException.code();
                            msg = httpException.getMessage();
                            if (code == 504) {
                                msg = "网络不给力";
                            }
                            if (code == 502 || code == 404) {
                                msg = "服务器异常，请稍后再试";
                            }
                        } else {
                            System.out.println("=============" + e.getMessage() + "===============");
                            msg = "服务器出错";
                        }
                        onError(msg);*/

        String msg = "";
        if (e instanceof HttpException) {
            int code = ((HttpException) e).code();
            switch (code) {
                case NOT_FOUND:
                    // 404
                    msg = context.getString(R.string.network_error);
                    break;
                case INTERNAL_SERVER_ERROR:
                    // 500
                    msg = context.getString(R.string.network_error);
                    break;
                case UNSATISFIABLE_REQUEST:
                    // 504
                    msg = context.getString(R.string.network_error);
                    break;
                case SERVICE_TEMPORARILY_UNAVAILABLE:
                    // 503
                    msg = context.getString(R.string.network_error);
                default:
                    break;
            }
            onError(code, msg);
        } else if (e instanceof UnknownHostException) {
            //没有网络
            msg = context.getString(R.string.network_error);
            onError(OTHER_ERROR, msg);
        } else if (e instanceof SocketTimeoutException) {
            // 连接超时
            msg = context.getString(R.string.network_error);
            onError(OTHER_ERROR, msg);
        } else if (e instanceof ConnectException) {
            msg = context.getString(R.string.network_error);
            onError(OTHER_ERROR, msg);
        } else if (e instanceof ParseException) {
            msg = context.getString(R.string.network_error);
            onError(OTHER_ERROR, msg);
        } else {
            msg = context.getString(R.string.network_error);
            onError(OTHER_ERROR, msg);
        }
        if (e != null) {
            e.printStackTrace();
            Log.d("BaseObserver", "onError: " + e.getCause() + "  " + e.getMessage() + "  " + e.toString());
        }
//        ToastUtil.show(msg);
    }

    @Override
    public void onComplete() {
        onEnd();
    }
}
