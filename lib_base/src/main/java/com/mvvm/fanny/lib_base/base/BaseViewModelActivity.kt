package com.mvvm.fanny.lib_base.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.launcher.ARouter
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.mvvm.fanny.lib_base.dialog.LoadingDialog
import com.mvvm.fanny.lib_base.ktx.observe
import com.mvvm.fanny.lib_base.ktx.showToastLong

/**
 * @author: FengYi.Lee
 * @e-mail:fengyi.li@hotmail.com
 * @description:
 * @date: 2023/3/18
 */
 abstract class BaseViewModelActivity<B : ViewDataBinding,VM: BaseViewModel>: BaseBindingActivity<B>(){

    private lateinit var viewModel:VM
    private lateinit var mLoadingDialog:LoadingDialog
    /**
     * 创建ViewModel
     */
    protected abstract fun createViewModel():VM

    /**
     * 初始化操作
     */
    protected abstract fun initialize(savedInstanceState: Bundle?)


    override fun init(savedInstanceState: Bundle?) {
        injectViewModel()
        initialize(savedInstanceState)
        initInternalObserver()
        mLoadingDialog = LoadingDialog(this)
    }


    private fun injectViewModel(){
        val vm = createViewModel()
        viewModel = ViewModelProvider(this,BaseViewModel.createViewModelFactory(vm))[vm::class.java]
        viewModel.application = application
        lifecycle.addObserver(viewModel)
    }

    fun getActivityViewModel():VM{
        return viewModel
    }

    protected open fun initInternalObserver(){

        observe(viewModel.loadingEvent) {
            showLoadingUI(it)
        }

        observe(viewModel.toastEvent){
            showToast(it)
        }

        observe(viewModel.pageNavigationEvent){
            navigate(it)
        }

        observe(viewModel.pageNavigationMapEvent){
            navigate(it)
        }

        observe(viewModel.backPressEvent){
            backPress(it)
        }

        observe(viewModel.finishPageEvent){
            finishPage(it)
        }

    }

    override fun showLoadingUI(isShow: Boolean) {
        if (isShow){
            mLoadingDialog.show()
        } else {
            mLoadingDialog.dismiss()
        }
    }

    override fun showToast(map: Map<String, *>) {
        showToastLong(this, map[BaseConstants.TOAST_KEY_CONTENT].toString())
    }

    override fun navigate(page: Any?) {
        page?.let {
            ARouter.getInstance()
                .build(it.toString())
                .navigation()
        }
    }

    override fun navigate(map: Map<String, *>) {
        val page = map[PAGE_KEY]
        val bundle = map[BUNDLE_KEY] as Bundle?
        val requestCode:Any ?= map[REQUEST_CODE_KEY]

        val postcard = ARouter.getInstance().build(page.toString())
        if (bundle != null){
            postcard.with(bundle)
        }
        if (requestCode != null && requestCode is Int){
            postcard.navigation(this,requestCode)
        } else{
            postcard.navigation()
        }
    }


    override fun backPress(arg: Any?) {
        finish()
    }

    override fun finishPage(arg: Any?) {
        if (arg is Intent){
            setResult(RESULT_OK,arg)
        }
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(viewModel)
    }

    open fun requestPermissions(permission: String?, callback: OnPermissionCallback?) {
        XXPermissions.with(this)
            .permission(permission)
            .request(callback)
    }

    open fun requestPermissions(permissions: Array<String>, callback: OnPermissionCallback?) {
        XXPermissions.with(this)
            .permission(permissions)
            .request(callback)
    }
}