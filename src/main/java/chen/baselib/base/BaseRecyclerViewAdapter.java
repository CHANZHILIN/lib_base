package chen.baselib.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Introduce :  RecyclerView的封装
 * Created by CHEN_ on 2019/5/15.
 * PACKAGE_NAME : chen.baselib.base
 **/
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    protected List<T> mData;
    private int resource;
    protected OnItemListener mItemListener;
    protected Context mContext;

    public BaseRecyclerViewAdapter(List<T> data, int resource) {
        super();
        this.mData = data;
        this.resource = resource;
    }

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        this.mContext = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                resource, viewGroup, false);
        BaseRecyclerViewHolder baseHolder = holder(view, viewType);
        return baseHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerViewHolder baseHolder, int position) {
        baseHolder.setOnItemClickListener(mItemListener);
        baseHolder.onBindView(mData.get(position));
    }

    public abstract BaseRecyclerViewHolder holder(View view, int viewType);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnItemClickListener(OnItemListener itemClickListener) {
        this.mItemListener = itemClickListener;
    }

    public void addDataList(List<T> list, boolean isClear) {
        if (isClear) {
            mData.clear();
        }
        int position = mData.size();
        mData.addAll(list);
        if (isClear) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeInserted(position, list.size());
        }
    }
}
