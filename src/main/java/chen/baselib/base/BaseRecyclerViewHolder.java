package chen.baselib.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Introduce : Adapter的holder基类
 * Created by CHEN_ on 2018/6/29.
 **/
public abstract class BaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder {
    protected Context mContext;
    private OnItemListener itemListener;

    public BaseRecyclerViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        mContext = context;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemListener != null) {
                    itemListener.onItem(view, getLayoutPosition());
                }
            }
        });
    }

    public abstract void onBindView(T baseEntity);

    public void setOnItemClickListener(OnItemListener itemClickListener) {
        this.itemListener = itemClickListener;
    }
}
