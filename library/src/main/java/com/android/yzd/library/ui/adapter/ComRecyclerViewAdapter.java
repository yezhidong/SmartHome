package com.android.yzd.library.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * <p>Title:        ComRecyclerViewAdapter
 * <p>Description:  通用的RecyclerView  adapter
 * <p>@author:      yzd
 * <p>Create Time:  2017/6/2 下午2:53
 */
public abstract class ComRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {
    protected final int mItemLayoutId;
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> list;
    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    public ComRecyclerViewAdapter(Context context, List<T> mDatas, int itemLayoutId) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.list = mDatas;
        this.mItemLayoutId = itemLayoutId;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Log.e ("ComRecyclerViewAdapter", "onCreateViewHolder");
        View itemView = mInflater.inflate(mItemLayoutId, parent, false);
        RecyclerViewHolder vh = new RecyclerViewHolder(itemView);
        return vh;
    }

    public T getItem(int position) {
        if (list == null) {
            return null;
        }

        return list.get(position);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder viewHolder, int position) {
        convert(viewHolder, getItem(position), position);
        if (mOnItemClickLitener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = viewHolder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(viewHolder.itemView, list.get(pos), pos);
                }
            });

            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = viewHolder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(viewHolder.itemView, list.get(pos), pos);

                    return false;
                }
            });
        }
    }

    public abstract void convert(RecyclerViewHolder viewHolder, T item, int position);

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    /**
     * 设置数据
     *
     * @param data
     */
    public void setData(List<T> data) {
        this.list = data;
    }

    public interface OnItemClickLitener<T> {
        void onItemClick(View view, T item, int position);

        void onItemLongClick(View view, T item, int position);
    }
}
