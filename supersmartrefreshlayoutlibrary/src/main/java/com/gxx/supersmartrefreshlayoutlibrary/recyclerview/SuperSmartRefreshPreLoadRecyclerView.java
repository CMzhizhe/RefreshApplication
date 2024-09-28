package com.gxx.supersmartrefreshlayoutlibrary.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter4.BaseQuickAdapter;
import com.gxx.supersmartrefreshlayoutlibrary.R;
import com.gxx.supersmartrefreshlayoutlibrary.base.AbsSuperRefreshRecyclerView;
import com.gxx.supersmartrefreshlayoutlibrary.inter.OnLoadMoreLoadListener;
import com.gxx.supersmartrefreshlayoutlibrary.inter.OnMALoadMoreListener;
import com.gxx.supersmartrefreshlayoutlibrary.inter.OnMARefreshListener;
import com.gxx.supersmartrefreshlayoutlibrary.inter.OnRefreshCallListener;
import com.gxx.supersmartrefreshlayoutlibrary.recyclerview.loadmodelimpl.basequickimpl.LoadMoreModuleImpl;
import com.gxx.supersmartrefreshlayoutlibrary.recyclerview.loadmodelimpl.basequickimpl.RefreshModuleImpl;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author gaoxiaoxiong
 * @date 创建时间 2019/8/13
 * @desc 预加载
 **/
public class SuperSmartRefreshPreLoadRecyclerView extends AbsSuperRefreshRecyclerView implements OnLoadMoreLoadListener, OnRefreshCallListener {
    private LoadMoreModuleImpl mLoadMoreModuleImpl = null;//出来上拉加载更多
    private RefreshModuleImpl mRefreshModuleImpl = null;
    private BaseQuickAdapter mBaseQuickAdapter = null;
    public SuperSmartRefreshPreLoadRecyclerView(Context context) {
        this(context,null);
    }

    public SuperSmartRefreshPreLoadRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SuperSmartRefreshPreLoadRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化视图
     * @param context
     * @return
     */
    public View initLayoutView(Context context) {
       return LayoutInflater.from(context).inflate(R.layout.su_view_default_smartrefresh_preload_recyclerview, this);
    }


    /**
     * @author: gaoxiaoxiong
     * @descripion: 初始化加载框架
     **/
    public void init(RecyclerView.LayoutManager layoutManager, BaseQuickAdapter adapter) {
        this.init(layoutManager, adapter, null, null);
    }

    /**
     * @author: gaoxiaoxiong
     * @descripion: 初始化加载框架
     **/
    public void init(RecyclerView.LayoutManager layoutManager, BaseQuickAdapter adapter, final OnMARefreshListener onMARefreshListener, final OnMALoadMoreListener onMALoadMoreListener) {
        this.init(layoutManager, adapter, 1, onMARefreshListener, onMALoadMoreListener);
    }



    /**
     * @date: 创建时间:2019/9/19
     * @author: gaoxiaoxiong
     * @descripion: 初始化加载框架
     * @preLoadNumber 预加载个数， > 0才会开启预加载
     **/
    public void init(RecyclerView.LayoutManager layoutManager, BaseQuickAdapter adapter, int preLoadNumber, final OnMARefreshListener onMARefreshListener, final OnMALoadMoreListener onMALoadMoreListener) {
       if(layoutManager == null){
           throw new IllegalArgumentException("未配置 layoutManager");
       }

        if(adapter == null){
            throw new IllegalArgumentException("未配置 baseQuickAdapter");
        }

        this.recyclerView.setLayoutManager(layoutManager);
        this.smartRefreshLayout.setEnableLoadMore(false);
        this.mBaseQuickAdapter = adapter;

        if (onMARefreshListener!=null){
            this.onMAFRefreshListenerWeakReference = new WeakReference<OnMARefreshListener>(onMARefreshListener);
            smartRefreshLayout.setEnableRefresh(true);
            mRefreshModuleImpl = new RefreshModuleImpl(this,adapter,smartRefreshLayout);
            mRefreshModuleImpl.setOnRefreshCallListener(this);
        }else {
            smartRefreshLayout.setEnableRefresh(false);
        }


        mLoadMoreModuleImpl = new LoadMoreModuleImpl(this,adapter);
        if(onMALoadMoreListener!=null){
            this.onMAFLoadMoreListenerWeakReference = new WeakReference<OnMALoadMoreListener>(onMALoadMoreListener);
            mLoadMoreModuleImpl.setOnLoadMoreLoadListener(this);
            mLoadMoreModuleImpl.setPreLoadSize(preLoadNumber);
        }
    }


    /**
      * 第一次触发刷新
      */
    public void autoRefresh(){
        if(mRefreshModuleImpl!=null && smartRefreshLayout.isEnabled()){
            smartRefreshLayout.autoRefresh();
        }
    }

    /**
     * @param list 是每次的新集合
     * 完成加载
     **/
    public void finishLoadAndUpdateData(List list) {
        this.finishLoadAndUpdateData(list,null);
    }

    /**
      * @param isHasMore 可选参数
      */
    public void finishLoadAndUpdateData(List list,Boolean isHasMore) {
        if(mRefreshModuleImpl!=null && smartRefreshLayout.isRefreshing()){
            smartRefreshLayout.finishRefresh();
        }

        if(mLoadMoreModuleImpl!=null){
            mLoadMoreModuleImpl.finishLoadAndUpdateData(list,isHasMore);
        }

        setLoadingData(false);


        if(getEmptyView()!=null && getLoadMorePageIndex() == 1){
           if ((list == null || list.isEmpty())){
               getEmptyView().setVisibility(View.VISIBLE);
           }else {
               getEmptyView().setVisibility(View.GONE);
           }
        }

    }


    /**
     * 是否开启下拉刷新功能
     * true 开启  false 关闭
     **/
    @Override
    public void setEnableRefresh(boolean b) {
        smartRefreshLayout.setEnableRefresh(b);
    }

    @Override
    public void setEnableLoadMore(boolean b) {
        if(mLoadMoreModuleImpl!=null){
            mLoadMoreModuleImpl.setAutoLoadMore(b);
        }
    }


    public BaseQuickAdapter getBaseQuickAdapter() {
        return mBaseQuickAdapter;
    }

    /**
     * @date :2019/12/5 0005
     * @author : gaoxiaoxiong
     * @description:获取所有的集合
     **/
    public List getAllList() {
        return mBaseQuickAdapter.getItems();
    }


    public LoadMoreModuleImpl getLoadMoreModuleImpl() {
        return mLoadMoreModuleImpl;
    }


    public int getLoadMorePageIndex(){
        if (mLoadMoreModuleImpl!=null){
           return mLoadMoreModuleImpl.getLoadMorePageIndex();
        }
        return 1;
    }


    /**
     * @date 创建时间:2022/5/13 0013
     * @auther gaoxiaoxiong
     * @Descriptiion 设置上拉加载更多页码数量
     **/
    public void setLoadMorePageIndex(int pageIndex){
        if (mLoadMoreModuleImpl!=null){
            mLoadMoreModuleImpl.setLoadMorePageIndex(pageIndex);
        }
    }

    /**
     * 不满一屏幕，是否不去自动触发loadMore操作
     */
    public void setNotFullToAutoMore(boolean isNotFullToAutoMore){
        if (mLoadMoreModuleImpl!=null){
            mLoadMoreModuleImpl.setNotFullToAutoMore(isNotFullToAutoMore);
        }
    }

    /**
     * @date 创建时间: 2022/4/9
     * @auther gaoxiaoxiong
     * 上拉加载更多的回调
     **/
    @Override
    public void onPullUpLoadMoreCall() {
        if(onMAFLoadMoreListenerWeakReference!=null && onMAFLoadMoreListenerWeakReference.get()!=null){
            onMAFLoadMoreListenerWeakReference.get().onLoadMore();
        }
    }


    /**
     * @date 创建时间: 2022/4/10
     * @auther gaoxiaoxiong
     * 下拉刷新
     **/
    @Override
    public void onRefreshCall() {
        if(onMAFRefreshListenerWeakReference!=null && onMAFRefreshListenerWeakReference.get()!=null){
            setLoadMorePageIndex(1);
            onMAFRefreshListenerWeakReference.get().onRefresh();
        }
    }
}
