package com.gxx.refreshapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gxx.refreshapplication.utils.DataUtils
import com.gxx.supersmartrefreshlayoutlibrary.SuperSmartRefreshLayoutManager
import com.gxx.supersmartrefreshlayoutlibrary.inter.OnMALoadMoreListener
import com.gxx.supersmartrefreshlayoutlibrary.inter.OnMARefreshListener
import com.gxx.supersmartrefreshlayoutlibrary.recyclerview.SuperSmartRefreshPreLoadRecyclerView


class MainActivity : ComponentActivity(), OnMARefreshListener, OnMALoadMoreListener {
    val mPreLoadAdapter:PreLoadAdapter = PreLoadAdapter()
    lateinit var superRefreshRecyclerView:SuperSmartRefreshPreLoadRecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_content)

        SuperSmartRefreshLayoutManager.Builder()
            .setPullUpToLoadMoreText("上拉加载更多---gxx")
            .setLoadLoadingText("正在加载中...---gxx")
            .setLoadCompleteText("加载完成---gxx")
            .setLoadFailedText("加载失败，点我重新加载---gxx")
            .setLoadEndText("没有更多数据---gxx")
            .setIAmBottomLineText("---我是有底线的人--- ---gxx")
            .setRefreshSuccessfulText("刷新成功---gxx")
            .setRefreshFailText("刷新失败---gxx")
            .setPullDownToRefreshText("下拉刷新---gxx")
            .setIsDeveloper(true)
            .setRefreshIngText("正在刷新---gxx")
            .setNetworkUnavailableText("当前网络不可用---gxx")
            .builder();

        superRefreshRecyclerView = this.findViewById<SuperSmartRefreshPreLoadRecyclerView>(R.id.su_recyclerview)
        superRefreshRecyclerView.init(LinearLayoutManager(this),mPreLoadAdapter,this,this)
    }

    override fun onRefresh() {
        Log.d("TAG","onRefresh")
        Thread(object :Runnable{
            override fun run() {
                Thread.sleep(3 * 1000)
                runOnUiThread{
                    superRefreshRecyclerView.finishLoadAndUpdateData(DataUtils.createDatas(20,"RefreshLoadMoreActivity"))
                }
            }
        }).start()
    }

    override fun onLoadMore() {
        Log.d("TAG","onLoadMore")
        Thread(object : Runnable{
            override fun run() {
                Thread.sleep(5 * 1000)
                runOnUiThread{
                    if (superRefreshRecyclerView.loadMorePageIndex<=5){
                        superRefreshRecyclerView.finishLoadAndUpdateData(DataUtils.createDatas(20,"RefreshLoadMoreActivity---onLoadMore"))
                    }else{
                        superRefreshRecyclerView.finishLoadAndUpdateData(DataUtils.createDatas(2,"RefreshLoadMoreActivity---onLoadMore"))
                    }
                }
            }
        }).start()
    }




}

