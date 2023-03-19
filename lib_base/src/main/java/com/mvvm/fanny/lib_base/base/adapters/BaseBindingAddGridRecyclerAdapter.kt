package com.mvvm.fanny.lib_base.base.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.mvvm.fanny.lib_base.base.adapters.BaseAddGridRecyclerAdapter

/**
 * 类描述：
 * 创建人： Fengyi.Li
 * 创建时间：2021/4/13 14:19
 * 版权： fengyi.li@hotmail.com
 */
abstract class BaseBindingAddGridRecyclerAdapter<VB_ADD: ViewDataBinding,VB_ITEM: ViewDataBinding,T>:
    BaseAddGridRecyclerAdapter<T>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val itemView: View = when(viewType){
            VIEW_TYPE_ADD->{
                bindingInflate<VB_ADD>(getAddViewLayoutId(),parent,false).root
            }
            VIEW_TYPE_ITEM->{
                bindingInflate<VB_ITEM>(getItemViewLayoutId(),parent,false).root
            }
            else -> {
                bindingInflate<VB_ITEM>(getItemViewLayoutId(),parent,false).root
            }
        }

        return BaseViewHolder(itemView)
    }

    protected open fun <VB:ViewDataBinding> bindingInflate(
            layoutId:Int,
            parent: ViewGroup,
            attachToParent:Boolean = false
    ):VB{
        return DataBindingUtil.inflate(LayoutInflater.from(parent.context),layoutId,parent,attachToParent)
    }

    override fun onBindAddItem(holder: BaseViewHolder, position: Int) {
        val binding = getBinding<VB_ADD>(holder)
        onBindAddItem(binding,position)
        binding.executePendingBindings()
    }

    override fun onBindItem(holder: BaseViewHolder, position: Int, item: T) {
        val binding = getBinding<VB_ITEM>(holder)
        onBindItem(binding,position,item)
        binding.executePendingBindings()
    }

    protected fun <VB : ViewDataBinding> getBinding(holder: BaseViewHolder): VB {
        return DataBindingUtil.getBinding(holder.itemView)!!
    }

    abstract fun onBindAddItem(binding: VB_ADD, position: Int)

    abstract fun onBindItem(binding: VB_ITEM, position: Int, item: T)

}