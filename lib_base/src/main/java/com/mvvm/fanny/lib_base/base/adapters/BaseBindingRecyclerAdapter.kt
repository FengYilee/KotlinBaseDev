package com.mvvm.fanny.lib_base.base.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * 类描述：Recycler.Adapter结合DataBinding
 * 创建人： Fengyi.Li
 * 创建时间：2021/4/13 10:47
 * 版权： fengyi.li@hotmail.com
 */
abstract class BaseBindingRecyclerAdapter<B:ViewDataBinding,T>:BaseRecyclerAdapter<T>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding:B = DataBindingUtil.inflate(LayoutInflater.from(parent.context)
                ,getLayoutId(viewType),parent,false)
        return BaseViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        onBindItem(holder,getItems()!![position],position)
    }

    override fun onBindItem(holder: BaseViewHolder, item: T, position: Int) {
        val binding:B = DataBindingUtil.getBinding(holder.itemView)!!
        onBindItem(binding,item,position)
        binding.executePendingBindings()
        getItemClickListener()?.let {
            listener->
            holder.itemView.setOnClickListener {
                listener.onItemClick(binding,item,position)
            }
        }
    }

    abstract fun onBindItem(binding:B,item:T,position:Int)
}