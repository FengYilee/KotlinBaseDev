package com.mvvm.fanny.lib_base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * @author: FengYi.Lee
 * @e-mail:fengyi.li@hotmail.com
 * @description:
 * @date: 2023/3/18
 */
abstract class BaseBindingFragment<B:ViewDataBinding>:Fragment() {
    lateinit var mBinding:B
        private set

    var rootView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView != null) return rootView

        injectDataBinding(inflater,container)
        initialize(savedInstanceState)

        return rootView
    }

    protected open fun injectDataBinding(inflater: LayoutInflater, container: ViewGroup?){
        mBinding = DataBindingUtil.inflate(inflater,getLayoutId(),container,false)
        mBinding.lifecycleOwner = this
        rootView = mBinding.root
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun initialize(savedInstanceState: Bundle?)

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }
}