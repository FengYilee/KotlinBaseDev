package com.mvvm.fanny.lib_base.base

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.mvvm.fanny.lib_base.http.HandlerException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author: FengYi.Lee
 * @e-mail:fengyi.li@hotmail.com
 * @description:
 * @date: 2023/3/18
 */

open class BaseViewModel:ViewModel(), ViewBehavior,DefaultLifecycleObserver {

    //loading视图显示Event
    var loadingEvent = SingleLiveEvent<Boolean>()
        private set

    //toast提示Event
    var toastEvent = SingleLiveEvent<Map<String, *>>()
        private set

    // 不带参数的页面跳转Event
    var pageNavigationEvent = SingleLiveEvent<Any?>()
        private set

    // 带参数的页面跳转Event
    var pageNavigationMapEvent = SingleLiveEvent<Map<String,*>>()
        private set

    // 点击系统返回键Event
    var backPressEvent = SingleLiveEvent<Any?>()
        private set

    // 关闭页面Event
    var finishPageEvent = SingleLiveEvent<Any?>()
        private set

    @SuppressLint("StaticFieldLeak")
    lateinit var application: Application
    private lateinit var lifecycleOwner: LifecycleOwner

    fun launchRequest(
        showLoading:Boolean = true,
        error:suspend (String) ->Unit = {
            errMsg->
            showToast(errMsg)
        },
        request:suspend () -> Unit
    ){
        showLoadingUI(showLoading)

        viewModelScope.launch (Dispatchers.IO){
            try {
                request()
            } catch (e:Exception){
                val exception = HandlerException.handlerException(e)
                error(exception.message ?: "")
            } finally {
                if(showLoading){
                    showLoadingUI(false)
                }
            }
        }
    }

    open fun showToast(str:String){
        showToast(str,null)
    }
    protected fun backPress() {
        backPress(null)
    }

    protected fun finishPage() {
        finishPage(null)
    }

    private fun showToast(str: String, duration: Int?) {
        val map = HashMap<String, Any>().apply {
            put(
                BaseConstants.TOAST_KEY_CONTENT_TYPE,
                BaseConstants.TOAST_CONTENT_TYPE_STR
            )
            put(BaseConstants.TOAST_KEY_CONTENT, str)
            if (duration != null) {
                put(BaseConstants.TOAST_KEY_DURATION, duration)
            }
        }

        showToast(map)
    }

    override fun showLoadingUI(isShow: Boolean) {
        loadingEvent.postValue(isShow)
    }

    override fun showToast(map: Map<String, *>) {
        toastEvent.postValue(map)
    }

    override fun navigate(page: Any?) {
        pageNavigationEvent.postValue(page)
    }

    override fun navigate(map: Map<String, *>) {
        pageNavigationMapEvent.postValue(map)
    }

    override fun backPress(arg: Any?) {
        backPressEvent.postValue(arg)
    }

    override fun finishPage(arg: Any?) {
        finishPageEvent.postValue(arg)
    }

    override fun onCreate(owner: LifecycleOwner) {
        this.lifecycleOwner = owner
    }

    companion object{
        @JvmStatic
        fun <T:BaseViewModel> createViewModelFactory(viewModel:T):ViewModelProvider.Factory{
            return ViewModelFactory(viewModel)
        }
    }

}

class ViewModelFactory(private val viewModel:BaseViewModel) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModel as T
    }

}