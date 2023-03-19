package com.mvvm.fanny.lib_base.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.launcher.ARouter
import com.mvvm.fanny.lib_base.dialog.LoadingDialog
import com.mvvm.fanny.lib_base.ktx.observe

/**
 * @author: FengYi.Lee
 * @e-mail:fengyi.li@hotmail.com
 * @description:
 * @date: 2023/3/18
 */
 abstract class BaseViewModelFragment<B : ViewDataBinding,VM: BaseViewModel> : BaseBindingFragment<B>(),
    ViewBehavior {

    lateinit var viewModel: VM
        private set
    private lateinit var mLoadingDialog: LoadingDialog

    abstract fun createViewModel(): VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView != null) return rootView

        injectDataBinding(inflater, container)
        injectViewModel()
        initialize(savedInstanceState)
        initInternalObserver()
        mLoadingDialog = LoadingDialog(requireContext())
        return rootView
    }

    fun getFragmentViewModel():VM{
        return viewModel
    }

    private fun injectViewModel(){
        val vm = createViewModel()
        viewModel = ViewModelProvider(
            this,
            BaseViewModel.createViewModelFactory(vm)
        )[vm::class.java]
        viewModel.application = requireActivity().application
        lifecycle.addObserver(viewModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(viewModel)
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
        if(isShow){
            mLoadingDialog.show()
        } else {
            mLoadingDialog.dismiss()
        }
    }

    override fun showToast(map: Map<String, *>) {
        Toast.makeText(activity,map[BaseConstants.TOAST_KEY_CONTENT].toString(), Toast.LENGTH_SHORT).show()
    }

    override fun navigate(page: Any?) {
        page?.let {
            ARouter.getInstance()
                .build(it.toString())
                .navigation()
        }
    }

    override fun backPress(arg: Any?) {
        activity?.finish()
    }

    override fun finishPage(arg: Any?) {
        if (arg is Intent){
            activity?.setResult(Activity.RESULT_OK,arg)
        }
        activity?.finish()
    }

    override fun navigate(map: Map<String, *>) {
        val page = map[PAGE_KEY]
        val bundle = map[BUNDLE_KEY] as Bundle
        ARouter.getInstance()
            .build(page.toString())
            .with(bundle)
            .navigation()
    }
}