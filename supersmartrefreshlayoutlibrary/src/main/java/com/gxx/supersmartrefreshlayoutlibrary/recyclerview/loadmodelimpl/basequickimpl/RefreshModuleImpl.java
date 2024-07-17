package com.gxx.supersmartrefreshlayoutlibrary.recyclerview.loadmodelimpl.basequickimpl;

import androidx.annotation.NonNull;


import com.chad.library.adapter4.BaseQuickAdapter;
import com.gxx.supersmartrefreshlayoutlibrary.inter.OnRefreshCallListener;
import com.gxx.supersmartrefreshlayoutlibrary.recyclerview.SuperSmartRefreshPreLoadRecyclerView;
import com.gxx.supersmartrefreshlayoutlibrary.recyclerview.loadmodelimpl.abs.AbsModelImpl;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

public class RefreshModuleImpl extends AbsModelImpl implements OnRefreshListener {
    private OnRefreshCallListener mOnRefreshCallListener = null;
    private SmartRefreshLayout mSmartRefreshLayout = null;
    public RefreshModuleImpl(SuperSmartRefreshPreLoadRecyclerView superRecyclerView,SmartRefreshLayout smartRefreshLayout, BaseQuickAdapter baseQuickAdapter) {
        super(superRecyclerView, baseQuickAdapter);
        this.mSmartRefreshLayout = smartRefreshLayout;
        mSmartRefreshLayout.setOnRefreshListener(this);
    }

    public void setOnRefreshCallListener(OnRefreshCallListener mOnRefreshCallListener) {
        this.mOnRefreshCallListener = mOnRefreshCallListener;
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if(getSuperRecyclerView().isLoadingData()){
            mSmartRefreshLayout.finishRefresh();
            return;
        }

        getSuperRecyclerView().setLoadingData(true);
        if (mOnRefreshCallListener!=null){
            mOnRefreshCallListener.onRefreshCall();
        }

    }

    /**
     * @auther gaoxiaoxiong
     * 设置是否可以刷新
     **/
    public void setEnableRefresh(boolean b) {
        mSmartRefreshLayout.setEnableRefresh(b);
    }

}
