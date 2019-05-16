package chen.baselib.base;

/**
 * Introduce :  用于联网返回时  Observable的实体类
 * 实例：Observable<BaseWebEntity<UserEntity>> doLogin(@FieldMap Map<String, String> data);
 * Created by CHEN_ on 2018/7/16.
 **/
public class BaseWebEntity<T> {
    /**
     * code : 200
     * message : ok
     * data : data里面的数据实体类
     */

    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
