package com.gxx.supersmartrefreshlayoutlibrary.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.loadState.LoadState
import com.chad.library.adapter4.loadState.trailing.TrailingLoadStateAdapter
import com.gxx.supersmartrefreshlayoutlibrary.R
import com.gxx.supersmartrefreshlayoutlibrary.SuperSmartRefreshLayoutManager

/**
  * 构建默认的adapter
  */
class DefaultTrailingLoadStateAdapter(isLoadEndDisplay: Boolean = true): TrailingLoadStateAdapter<DefaultTrailingLoadStateAdapter.TrailingLoadStateVH>(isLoadEndDisplay) {

    /**
     * Default implementation of "load more" ViewHolder
     *
     * 默认实现的"加载更多" ViewHolder
     */
    class TrailingLoadStateVH(
        parent: ViewGroup,
        view: View = LayoutInflater.from(parent.context).inflate(R.layout.su_view_trailing_load_more, parent, false)
    ) : RecyclerView.ViewHolder(view) {
        val loadCompleteView: TextView = itemView.findViewById(R.id.su_load_more_load_complete_view)
        val loadingView: LinearLayout = itemView.findViewById(R.id.su_load_more_loading_view)
        val loadFailView: TextView = itemView.findViewById(R.id.su_load_more_load_fail_view)
        val loadEndView: TextView = itemView.findViewById(R.id.su_load_more_load_end_view)
        val loadingTextView = itemView.findViewById<TextView>(R.id.loading_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): TrailingLoadStateVH {
        return TrailingLoadStateVH(parent).apply {
            loadFailView.setOnClickListener {
                // 失败重试点击事件
                // retry when loaded failed.
                invokeFailRetry()
            }
            loadCompleteView.setOnClickListener {
                // 加载更多，手动点击事件
                // manual click to load more.
                invokeLoadMore()
            }
        }
    }

    /**
     * bind LoadState
     *
     * 绑定加载状态
     */
    override fun onBindViewHolder(holder: TrailingLoadStateVH, loadState: LoadState) {
        SuperSmartRefreshLayoutManager.getInstance().loadLoadingText?.apply {
            holder.loadingTextView.text = this
        }

        SuperSmartRefreshLayoutManager.getInstance().loadFailedText.apply {
            holder.loadFailView.text = this
        }

        SuperSmartRefreshLayoutManager.getInstance().loadCompleteText.apply {
            holder.loadCompleteView.text = this
        }

        SuperSmartRefreshLayoutManager.getInstance().loadEndText.apply {
            holder.loadEndView.text = this
        }

        when (loadState) {
            is LoadState.NotLoading -> {
                if (loadState.endOfPaginationReached) {
                    holder.loadCompleteView.visibility = View.GONE
                    holder.loadingView.visibility = View.GONE
                    holder.loadFailView.visibility = View.GONE
                    holder.loadEndView.visibility = View.VISIBLE
                } else {
                    holder.loadCompleteView.visibility = View.VISIBLE
                    holder.loadingView.visibility = View.GONE
                    holder.loadFailView.visibility = View.GONE
                    holder.loadEndView.visibility = View.GONE
                }
            }
            is LoadState.Loading -> {
                holder.loadCompleteView.visibility = View.GONE
                holder.loadingView.visibility = View.VISIBLE
                holder.loadFailView.visibility = View.GONE
                holder.loadEndView.visibility = View.GONE
            }
            is LoadState.Error -> {
                holder.loadCompleteView.visibility = View.GONE
                holder.loadingView.visibility = View.GONE
                holder.loadFailView.visibility = View.VISIBLE
                holder.loadEndView.visibility = View.GONE
            }
            is LoadState.None -> {
                holder.loadCompleteView.visibility = View.GONE
                holder.loadingView.visibility = View.GONE
                holder.loadFailView.visibility = View.GONE
                holder.loadEndView.visibility = View.GONE
            }
        }
    }

    override fun getStateViewType(loadState: LoadState): Int = R.layout.su_view_trailing_load_more
}