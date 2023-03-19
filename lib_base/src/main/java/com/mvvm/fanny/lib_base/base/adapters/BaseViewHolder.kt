package com.mvvm.fanny.lib_base.base.adapters

import android.util.SparseArray
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

/**
 * 类描述：ViewHolder基类
 * 创建人： Fengyi.Li
 * 创建时间：2021/4/13 10:59
 * 版权： fengyi.li@hotmail.com
 */
@Suppress("UNCHECKED_CAST")
class BaseViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    private val views:SparseArray<View> = SparseArray()

    fun <T:View> getView(@IdRes resId:Int):T{
        var v:View? = views.get(resId)
        if (v == null){
            v = itemView.findViewById(resId)
            views.put(resId,v)
        }
        return v as T

    }
}