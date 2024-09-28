package com.gxx.supersmartrefreshlayoutlibrary.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.gxx.supersmartrefreshlayoutlibrary.R;
import com.gxx.supersmartrefreshlayoutlibrary.SuperSmartRefreshLayoutManager;
import com.gxx.supersmartrefreshlayoutlibrary.inter.OnMALoadMoreListener;
import com.gxx.supersmartrefreshlayoutlibrary.inter.OnMARefreshListener;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshHeader;

import java.lang.ref.WeakReference;


/**
 * @author : gaoxiaoxiong
 * @date :2019/10/29 0029
 * @description:抽象上拉刷新下拉加载
 **/
public abstract class AbsSuperRefreshRecyclerView extends FrameLayout {
    protected SmartRefreshLayout smartRefreshLayout = null;
    protected RefreshHeader superSmartRefreshHeaderView = null;//头部
    protected RecyclerView recyclerView = null;
    protected int pageCount = SuperSmartRefreshLayoutManager.getInstance().getPageCount();//默认条数
    protected volatile boolean isLoadingData = false;//是否正在请求数据，包含，上拉于下拉
    protected WeakReference<OnMALoadMoreListener> onMAFLoadMoreListenerWeakReference = null;
    protected WeakReference<OnMARefreshListener> onMAFRefreshListenerWeakReference = null;//下拉刷新
    protected View emptyView = null;

    public AbsSuperRefreshRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public AbsSuperRefreshRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AbsSuperRefreshRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = initLayoutView(getContext());
        smartRefreshLayout = view.findViewById(R.id.view_default_smart_refresh);
        recyclerView = view.findViewById(R.id.view_default_smart_recyclerview);
        superSmartRefreshHeaderView = view.findViewById(R.id.view_default_smart_refresh_header_view);
        smartRefreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        smartRefreshLayout.setBackground(getBackground());
    }


    /**
     * 设置背景色
     * R.color.white
     */
    public void setAbsBackGroundColor(int color) {
        if(smartRefreshLayout!=null)
             smartRefreshLayout.setBackgroundColor(getResources().getColor(color));
    }

    /**
     * 设置Drawable
     */
    public void setAbsBackDrawable(Drawable drawable) {
        if (smartRefreshLayout!=null)
              smartRefreshLayout.setBackground(drawable);
    }


    /**
     * 设置空视图,只有集合是空的时候才有效果
     */
    public void setEmptyView(int layoutResId,FrameLayout.LayoutParams layoutParams){
        if(emptyView == null){
            emptyView = LayoutInflater.from(getContext()).inflate(layoutResId,this,true);
            emptyView.setLayoutParams(layoutParams);
        }
    }


    /**
     * 滚动到指定位置，带动画，如果是有下拉加载数据，第一次会直接触发onUpFetch
     **/
    public void scrollToPositionHaveAnimal(int position) {
        if (recyclerView == null || position < 0) {
            return;
        }
        RecyclerView.LayoutManager manager1 = recyclerView.getLayoutManager();
        if (manager1 instanceof LinearLayoutManager) {
            TopSmoothScroller mScroller = new TopSmoothScroller(recyclerView.getContext());
            mScroller.setTargetPosition(position);
            manager1.startSmoothScroll(mScroller);
        }
    }

    /**
     * 滚动到底部（不带动画），如果你是需要下拉加载数据，不想第一次直接触发onUpFetch，请使用该方法。
     * 需要 position = headerViewCount + list.size()-1
     * 关于position的传递，是每次新的list集合数量 - 1
     **/
    public void scrollToPositionNoAnimal(int position) {
        if (recyclerView == null || position < 0) {
            return;
        }
        RecyclerView.LayoutManager manager1 = recyclerView.getLayoutManager();
        if (manager1 instanceof LinearLayoutManager) {
            LinearLayoutManager ll = (LinearLayoutManager) recyclerView.getLayoutManager();
            ll.scrollToPositionWithOffset(position, 0);
        }
    }

    /**
     * 滚动到第一项
     **/
    public void scrollToFirstItemNoAnimal() {
        if (recyclerView == null || recyclerView.getAdapter() == null || recyclerView.getAdapter().getItemCount() <= 0) {
            return;
        }
        RecyclerView.LayoutManager manager1 = recyclerView.getLayoutManager();
        if (manager1 instanceof LinearLayoutManager) {
            LinearLayoutManager ll = (LinearLayoutManager) recyclerView.getLayoutManager();
            ll.scrollToPositionWithOffset(0, 0);
        }
    }

    /**
     * 滚动到最后一项
     **/
    public void scrollToLastItemHaveAnimal() {
        if (recyclerView == null || recyclerView.getAdapter() == null || recyclerView.getAdapter().getItemCount() <= 0) {
            return;
        }
        RecyclerView.LayoutManager manager1 = recyclerView.getLayoutManager();
        if (manager1 instanceof LinearLayoutManager) {
            LinearLayoutManager ll = (LinearLayoutManager) recyclerView.getLayoutManager();
            ll.scrollToPositionWithOffset(recyclerView.getAdapter().getItemCount() - 1, 0);
        }
    }


    private static class TopSmoothScroller extends LinearSmoothScroller {
        TopSmoothScroller(Context context) {
            super(context);
        }

        @Override
        protected int getHorizontalSnapPreference() {
            return SNAP_TO_START;
        }

        @Override
        protected int getVerticalSnapPreference() {
            return SNAP_TO_START;
        }
    }


    /**
     * 判断是否正在刷新数据 return true 正在加载数据
     **/
    public boolean isLoadingData() {
        return isLoadingData;
    }

    /**
     * 设置正在刷新状态 true表示正在刷新
     **/
    public void setLoadingData(boolean loadingData) {
        isLoadingData = loadingData;
    }

    /**
     * 初始化视图
     **/
    public abstract View initLayoutView(Context context);


    /**
     * SmareRefreshLayout 是否开启下拉刷新功能
     **/
    public abstract void setEnableRefresh(boolean b);

    /**
     * @param b true 显示 false不显示
     *          是否显示加载更多布局
     **/
    public abstract void setEnableLoadMore(boolean b);


    public View getEmptyView() {
        return emptyView;
    }

    public SmartRefreshLayout getSmartRefreshLayout() {
        return smartRefreshLayout;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }


    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }



    public void setOnMAFRefreshListener(OnMARefreshListener onMARefreshListener) {
        this.onMAFRefreshListenerWeakReference = new WeakReference<OnMARefreshListener>(onMARefreshListener);
    }

    public void setOnMALoadMoreListener(OnMALoadMoreListener onMALoadMoreListener) {
        this.onMAFLoadMoreListenerWeakReference = new WeakReference<OnMALoadMoreListener>(onMALoadMoreListener);
    }


    public WeakReference<OnMALoadMoreListener> getOnMAFLoadMoreListenerWeakReference() {
        return onMAFLoadMoreListenerWeakReference;
    }

    public WeakReference<OnMARefreshListener> getOnMAFRefreshListenerWeakReference() {
        return onMAFRefreshListenerWeakReference;
    }
}
