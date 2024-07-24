package com.gxx.supersmartrefreshlayoutlibrary.recyclerview.loadmodelimpl.basequickimpl;
import com.chad.library.adapter4.BaseQuickAdapter;
import com.chad.library.adapter4.QuickAdapterHelper;
import com.chad.library.adapter4.loadState.LoadState;
import com.chad.library.adapter4.loadState.trailing.TrailingLoadStateAdapter;
import com.gxx.supersmartrefreshlayoutlibrary.inter.OnLoadMoreLoadListener;
import com.gxx.supersmartrefreshlayoutlibrary.recyclerview.SuperSmartRefreshPreLoadRecyclerView;
import com.gxx.supersmartrefreshlayoutlibrary.recyclerview.loadmodelimpl.abs.AbsModelImpl;
import com.gxx.supersmartrefreshlayoutlibrary.utils.DefaultTrailingLoadStateAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 创建时间: 2022/4/9
 * @auther gaoxiaoxiong
 * @description 加载更多
 **/
public class LoadMoreModuleImpl extends AbsModelImpl implements TrailingLoadStateAdapter.OnTrailingListener {
    private OnLoadMoreLoadListener onLoadMoreLoadListener = null;
    private int loadMorePageIndex = 1;//页码数
    private QuickAdapterHelper quickAdapterHelper;
    private boolean isNotFullToAutoMore = false;

    public LoadMoreModuleImpl(SuperSmartRefreshPreLoadRecyclerView superSmartRefreshPreLoadRecyclerView, BaseQuickAdapter baseQuickAdapter) {
        super(superSmartRefreshPreLoadRecyclerView, baseQuickAdapter);
        quickAdapterHelper = new QuickAdapterHelper.Builder(baseQuickAdapter).setTrailingLoadStateAdapter(new DefaultTrailingLoadStateAdapter().setOnLoadMoreListener(this)).build();
        quickAdapterHelper.getTrailingLoadStateAdapter().setPreloadSize(1);
        quickAdapterHelper.getTrailingLoadStateAdapter().setAutoLoadMore(getSuperRecyclerView().getOnMAFLoadMoreListenerWeakReference() != null && getSuperRecyclerView().getOnMAFLoadMoreListenerWeakReference().get()!=null);
        superSmartRefreshPreLoadRecyclerView.getRecyclerView().setAdapter(quickAdapterHelper.getAdapter());
    }

    /**
      * 设置预加载个数
      */
    public void setPreLoadSize(int preLoadSize){
        if(quickAdapterHelper!=null && quickAdapterHelper.getTrailingLoadStateAdapter()!=null){
            quickAdapterHelper.getTrailingLoadStateAdapter().setPreloadSize(preLoadSize);
        }
    }

    /**
     * @date 创建时间: 2022/4/9
     * @auther gaoxiaoxiong
     * @description 设置加载更多的接口
     **/
    public void setOnLoadMoreLoadListener(OnLoadMoreLoadListener onLoadMoreLoadListener) {
        this.onLoadMoreLoadListener = onLoadMoreLoadListener;
    }


    /**
      * 不满一屏幕，是否不去自动触发loadMore操作
      */
    public void setNotFullToAutoMore(boolean isNotFullToAutoMore){
        this.isNotFullToAutoMore = isNotFullToAutoMore;
    }

    /**
      * @param isHasMore 如果不是null，将以isHasMore为准，不会以数量为准
      */
    public void finishLoadAndUpdateData(List list,Boolean isHasMore) {
        //按数量来计算
        if (list != null) {
            if (loadMorePageIndex == 1) {
                getBaseQuickAdapter().submitList(list);
            } else {
                if (list.size() <= 0 && loadMorePageIndex > 1) {
                    loadMorePageIndex = loadMorePageIndex - 1;
                }
                getBaseQuickAdapter().addAll(list);
            }
        } else {
            if (loadMorePageIndex == 1) {
                getBaseQuickAdapter().submitList(new ArrayList());
            }
            if (loadMorePageIndex > 1) {
                loadMorePageIndex = loadMorePageIndex - 1;
            }
        }

        //不满一屏幕，不要去自动触发加载更多
        if(isNotFullToAutoMore){
            quickAdapterHelper.getTrailingLoadStateAdapter().checkDisableLoadMoreIfNotFullPage();
        }

        if(isHasMore == null){
            if(quickAdapterHelper.getTrailingLoadStateAdapter().isAutoLoadMore()){
                //开启了分页的功能
                quickAdapterHelper.setTrailingLoadState(new LoadState.NotLoading(list == null || list.size() < getSuperRecyclerView().getPageCount()));
            }
        }else{
            //按提供的 isHasMore 来判断是否还有数据
            if(quickAdapterHelper.getTrailingLoadStateAdapter().isAutoLoadMore()){
                //开启了分页的功能
                quickAdapterHelper.setTrailingLoadState(new LoadState.NotLoading(isHasMore));
            }

        }
    }


    public int getLoadMorePageIndex() {
        return loadMorePageIndex;
    }

    public void setLoadMorePageIndex(int loadMorePageIndex) {
        this.loadMorePageIndex = loadMorePageIndex;
        if(loadMorePageIndex == 1 && quickAdapterHelper.getTrailingLoadStateAdapter()!=null && quickAdapterHelper.getTrailingLoadStateAdapter().isAutoLoadMore()){
            //重置状态
            quickAdapterHelper.setTrailingLoadState(LoadState.None.INSTANCE);
        }
    }

    /**
      * 是否可以加载更多
      */
    public void setAutoLoadMore(boolean b){
        quickAdapterHelper.getTrailingLoadStateAdapter().setAutoLoadMore(b);
    }


    /**
      * 执行加载更多的操作，
      */
    @Override
    public void onLoad() {
        if(getSuperRecyclerView().isLoadingData()){
            return;
        }

        getSuperRecyclerView().setLoadingData(true);

        loadMorePageIndex++;
        if(onLoadMoreLoadListener!=null){
            onLoadMoreLoadListener.onPullUpLoadMoreCall();
        }
    }

    /**
      * 网络失败，重试
      */
    @Override
    public void onFailRetry() {
        if(onLoadMoreLoadListener!=null){
            onLoadMoreLoadListener.onPullUpLoadMoreCall();
        }
    }

    /**
      * 是否允许触发“加载更多”
      */
    @Override
    public boolean isAllowLoading() {
        if(getSuperRecyclerView().isLoadingData()){
            //如果正在加载数据，是不可以触发加载更多的
            return false;
        }
        return true;
    }
}
