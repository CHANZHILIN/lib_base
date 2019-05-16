package chen.baselib.base;

/**
 * Introduce : 实体基类-用于Adapter填充数据
 * Created by CHEN_ on 2018/6/29.
 **/
public class BaseEntity<T> {

    private int type;
    private boolean isCheck;
    private boolean isEdit;
    private T data;

    public BaseEntity() {
    }

    public BaseEntity(T data) {
        this.data = data;
    }

    public BaseEntity(int type, boolean isCheck, boolean isEdit, T data) {
        this.type = type;
        this.isCheck = isCheck;
        this.isEdit = isEdit;
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "type=" + type +
                ", isCheck=" + isCheck +
                ", isEdit=" + isEdit +
                ", data=" + data +
                '}';
    }
}
