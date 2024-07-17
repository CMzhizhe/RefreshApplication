package com.gxx.supersmartrefreshlayoutlibrary.recyclerview.loadmodelimpl.abs;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter4.BaseQuickAdapter;
import com.gxx.supersmartrefreshlayoutlibrary.base.AbsSuperRefreshRecyclerView;

import java.util.List;

public abstract class AbsModelImpl {
    private AbsSuperRefreshRecyclerView mSuperRecyclerView = null;
    private BaseQuickAdapter mBaseQuickAdapter = null;
    private RecyclerView mRecyclerView = null;

    public AbsModelImpl(AbsSuperRefreshRecyclerView superRefreshRecyclerView, BaseQuickAdapter baseQuickAdapter) {
        this.mSuperRecyclerView = superRefreshRecyclerView;
        this.mRecyclerView = mSuperRecyclerView.getRecyclerView();
        this.mBaseQuickAdapter = baseQuickAdapter;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public BaseQuickAdapter getBaseQuickAdapter() {
        return mBaseQuickAdapter;
    }

    public AbsSuperRefreshRecyclerView getSuperRecyclerView() {
        return mSuperRecyclerView;
    }
}
