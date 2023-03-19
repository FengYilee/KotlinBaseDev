package com.mvvm.fanny.lib_base.base

import androidx.lifecycle.MutableLiveData
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

/**
 * @author: FengYi.Lee
 * @e-mail:fengyi.li@hotmail.com
 * @description:
 * @date: 2023/3/18
 */
abstract class BaseListViewModel:BaseViewModel() {

    val refreshing = MutableLiveData<Boolean>()
    val moreLoading = MutableLiveData<Boolean>()
    val hasMore = MutableLiveData<Boolean>()
    val autoRefreshEvent = MutableLiveData<Boolean>()
    val onRefreshLoadMoreListener = OnViewModelRefreshLoadMoreListener()

    var page = 1
    var pageSize = 20

    val refreshOrLoadMoreEvent = MutableLiveData<Int>()
    var refreshOrLoadMore = 0       //0:刷新; 1:加载更多

    init {
        hasMore.value = true
    }

    fun refresh() {
        hasMore.value = true
        refreshOrLoadMore = 0
        refreshing.value = true
        page = 1
        autoRefresh()
        loadData()
    }

    fun loadMore(){
        refreshOrLoadMore = 1
        moreLoading.value = true
        page++
        loadData()
    }


    fun autoRefresh() {
        autoRefreshEvent.postValue(true)
    }

    abstract fun loadData()

    fun finishRefresh(){
        refreshOrLoadMoreEvent.postValue(refreshOrLoadMore)
        refreshing.value = false
        moreLoading.value = false
    }

    inner class OnViewModelRefreshLoadMoreListener: OnRefreshLoadMoreListener {
        override fun onRefresh(refreshLayout: RefreshLayout) {
            refresh()
        }

        override fun onLoadMore(refreshLayout: RefreshLayout) {
            loadMore()
        }

    }

}