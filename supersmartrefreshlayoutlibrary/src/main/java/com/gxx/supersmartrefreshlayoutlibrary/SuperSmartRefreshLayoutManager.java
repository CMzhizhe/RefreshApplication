package com.gxx.supersmartrefreshlayoutlibrary;


public class SuperSmartRefreshLayoutManager {
    private String mAssetsRefreshHeader;//需要将xxx.json文件放到assets目录下
    private String pullUpToLoadMoreText;//上拉加载更多
    private String loadLoadingText;//正在加载中...
    private String loadCompleteText;//加载完成
    private String loadFailedText;//加载失败
    private String loadEndText;//没有更多数据
    private String iAmBottomLineText;//我是有底线的人
    private String refreshSuccessfulText;//刷新成功
    private String refreshFailText;//刷新失败
    private String pullDownToRefreshText;//下拉刷新
    private String refreshIngText;//正在刷新
    private String networkUnavailableText;//当前网络不可用
    private boolean isDeveloper = false;//是否开发者
    private int pageCount = 10;//默认条数

    private static SuperSmartRefreshLayoutManager mSuperSmartRefreshLayoutManager = null;

    public SuperSmartRefreshLayoutManager(Builder builder) {
        this.mAssetsRefreshHeader = builder.assetsRefreshHeader;
        this.pullUpToLoadMoreText = builder.pullUpToLoadMoreText;
        this.loadLoadingText = builder.loadLoadingText;
        this.loadCompleteText = builder.loadCompleteText;
        this.loadFailedText = builder.loadFailedText;
        this.loadEndText = builder.loadEndText;
        this.iAmBottomLineText = builder.iAmBottomLineText;
        this.refreshSuccessfulText = builder.refreshSuccessfulText;
        this.refreshFailText = builder.refreshFailText;
        this.pullDownToRefreshText = builder.pullDownToRefreshText;
        this.refreshIngText = builder.refreshIngText;
        this.networkUnavailableText = builder.networkUnavailableText;
        this.isDeveloper = builder.isDeveloper;
        this.pageCount = builder.pageCount;
    }

    private static void create(SuperSmartRefreshLayoutManager superSmartRefreshLayoutManager) {
        mSuperSmartRefreshLayoutManager = superSmartRefreshLayoutManager;
    }

    public static SuperSmartRefreshLayoutManager getInstance() {
        return mSuperSmartRefreshLayoutManager;
    }

    public static class Builder {
        private String assetsRefreshHeader;//需要将xxx.json文件放到assets目录下
        private String pullUpToLoadMoreText;//上拉加载更多
        private String loadLoadingText;//正在加载中...
        private String loadCompleteText;//加载完成
        private String loadFailedText;//加载失败
        private String loadEndText;//没有更多数据
        private String iAmBottomLineText;//我是有底线的人
        private String refreshSuccessfulText;//刷新成功
        private String refreshFailText;//刷新失败
        private String pullDownToRefreshText;//下拉刷新
        private String refreshIngText;//正在刷新
        private String networkUnavailableText;//当前网络不可用
        private boolean isDeveloper;//是否开发者
        private int pageCount = 10;//默认条数

        public Builder setIsDeveloper(boolean isDeveloper) {
            this.isDeveloper = isDeveloper;
            return this;
        }

        public Builder setPullUpToLoadMoreText(String pullUpToLoadMoreText) {
            this.pullUpToLoadMoreText = pullUpToLoadMoreText;
            return this;
        }

        public Builder setLoadLoadingText(String loadLoadingText) {
            this.loadLoadingText = loadLoadingText;
            return this;
        }

        public Builder setLoadCompleteText(String loadCompleteText) {
            this.loadCompleteText = loadCompleteText;
            return this;
        }

        public Builder setLoadFailedText(String loadFailedText) {
            this.loadFailedText = loadFailedText;
            return this;
        }

        public Builder setLoadEndText(String loadEndText) {
            this.loadEndText = loadEndText;
            return this;
        }

        public Builder setIAmBottomLineText(String iAmBottomLineText) {
            this.iAmBottomLineText = iAmBottomLineText;
            return this;
        }

        public Builder setRefreshSuccessfulText(String refreshSuccessfulText) {
            this.refreshSuccessfulText = refreshSuccessfulText;
            return this;
        }

        public Builder setRefreshFailText(String refreshFailText) {
            this.refreshFailText = refreshFailText;
            return this;
        }

        public Builder setPullDownToRefreshText(String pullDownToRefreshText) {
            this.pullDownToRefreshText = pullDownToRefreshText;
            return this;
        }

        public Builder setRefreshIngText(String refreshIngText) {
            this.refreshIngText = refreshIngText;
            return this;
        }

        /**
         * 需要将xxx.json文件放到assets目录下
         **/
        public Builder setAssetsRefreshHeader(String assetsRefreshHeader) {
            this.assetsRefreshHeader = assetsRefreshHeader;
            return this;
        }


        public Builder setNetworkUnavailableText(String networkUnavailableText) {
            this.networkUnavailableText = networkUnavailableText;
            return this;
        }

        public Builder setPageCount(int pageCount) {
            this.pageCount = pageCount;
            return this;
        }

        public SuperSmartRefreshLayoutManager builder() {
            SuperSmartRefreshLayoutManager superSmartRefreshLayoutManager = new SuperSmartRefreshLayoutManager(this);
            create(superSmartRefreshLayoutManager);
            return superSmartRefreshLayoutManager;
        }
    }


    public int getPageCount() {
        return pageCount;
    }

    public String getAssetsRefreshHeader() {
        return mAssetsRefreshHeader;
    }

    public String getPullUpToLoadMoreText() {
        return pullUpToLoadMoreText;
    }

    public String getLoadLoadingText() {
        return loadLoadingText;
    }

    public String getLoadCompleteText() {
        return loadCompleteText;
    }

    public String getLoadFailedText() {
        return loadFailedText;
    }

    public String getLoadEndText() {
        return loadEndText;
    }

    public String getiAmBottomLineText() {
        return iAmBottomLineText;
    }

    public String getRefreshSuccessfulText() {
        return refreshSuccessfulText;
    }

    public String getRefreshFailText() {
        return refreshFailText;
    }

    public String getPullDownToRefreshText() {
        return pullDownToRefreshText;
    }

    public String getRefreshIngText() {
        return refreshIngText;
    }

    public String getNetworkUnavailableText() {
        return networkUnavailableText;
    }

    public boolean isDeveloper() {
        return isDeveloper;
    }
}
