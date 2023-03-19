package com.mvvm.fanny.lib_base.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Created by FengYi.Lee@hotmail.com> on 2020/12/15.
 */
abstract class BaseDialog<T : ViewDataBinding> @JvmOverloads constructor(context: Context, themeResId: Int = 0) : Dialog(context, themeResId) {
    var binding: T? = null

    abstract fun getLayoutId(): Int
    abstract fun initData()

    init {
        if (getLayoutId() > 0) {
            val view = LayoutInflater.from(context).inflate(getLayoutId(), null)
            with(view){
                setContentView(this)
                binding = DataBindingUtil.bind(this)
                initData()
            }

        } else {
            throw NullPointerException("未设置layout")
        }
    }
}