# RefreshApplication
将SmartRefreshLayout 与 BaseQuickAdapter 相互结合

### 未来支持项
可以自定义头部，尾部

### 使用
```
dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}

 implementation 'com.github.CMzhizhe:RefreshApplication:v1.0.9'

 implementation  'io.github.scwang90:refresh-layout-kernel:2.0.6'      //核心必须依赖
 implementation  'io.github.scwang90:refresh-header-classics:2.0.6'    //经典刷新头
 implementation  'io.github.scwang90:refresh-header-material:2.0.6'    //谷歌刷新头
 implementation  'io.github.scwang90:refresh-footer-classics:2.0.6'    //经典加载
    
 implementation "com.airbnb.android:lottie:4.1.0"
 implementation "io.github.cymchad:BaseRecyclerViewAdapterHelper4:4.1.4"
```

```
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
        superRefreshRecyclerView.init(LinearLayoutManager(this),mPreLoadAdapter,this,null)

       /* superRefreshRecyclerView.postDelayed(object : Runnable{
            override fun run() {
                superRefreshRecyclerView.autoRefresh()
            }
        },2000)*/
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

```
