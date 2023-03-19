package com.mvvm.fanny.lib_base.base

import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @author: FengYi.Lee
 * @e-mail:fengyi.li@hotmail.com
 * @description:
 * @date: 2023/3/18
 */
abstract class BaseBindingActivity<B: ViewDataBinding>: AppCompatActivity(), ViewBehavior {

    lateinit var mBinding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDataBinding()
        init(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }

    protected open fun injectDataBinding(){
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        mBinding = DataBindingUtil.setContentView(this,getLayoutId())
        mBinding.lifecycleOwner = this
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int


    protected abstract fun init(savedInstanceState: Bundle?)
}