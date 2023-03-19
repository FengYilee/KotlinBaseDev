package com.mvvm.fanny.lib_base.base.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * 类描述： Recycler.Adapter基类
 * 创建人： Fengyi.Li
 * 创建时间：2021/4/13 10:33
 * 版权： fengyi.li@hotmail.com
 */
abstract class BaseRecyclerAdapter<T>:RecyclerView.Adapter<BaseViewHolder>() {

    private var items: MutableList<T>? = null
    private var itemClickListener: OnItemClickListener<T>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val itemView =
                LayoutInflater.from(parent.context).inflate(getLayoutId(viewType),parent,false)
        return BaseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        onBindItem(holder,items!![position],position)
        if (itemClickListener != null){
            holder.itemView.setOnClickListener {
                itemClickListener!!.onItemClick(holder,items!![position],position)
            }
        }
    }

    override fun getItemCount(): Int = items?.size ?: 0

    /**
     * 数据绑定
     */
    abstract fun onBindItem(holder: BaseViewHolder, item: T, position: Int)

    /**
     * 获取布局资源Id
     */
    @LayoutRes
    abstract fun getLayoutId(viewType: Int): Int


    fun getItems(): MutableList<T>? {
        return items
    }

    fun addItems(items: MutableList<T>?) {
        this.items?.addAll(items!!)
        notifyDataSetChanged()
    }

    fun addItem(item: T) {
        if (!items.isNullOrEmpty()) {
            items!!.add(item)
        } else {
            items = ArrayList<T>()
            items!!.add(item)
        }
        notifyDataSetChanged()
    }

    fun refreshItems(items: MutableList<T>?) {
        this.items = items
        notifyDataSetChanged()
    }

    fun replaceItems(items: MutableList<T>?){
        if (!this.items.isNullOrEmpty()){
            this.items!!.clear()
            this.addItems(items)
        } else {
            this.items = ArrayList<T>()
            this.addItems(items)
        }
        notifyDataSetChanged()
    }

    fun refreshItem(position: Int, item: T) {
        if (!items.isNullOrEmpty() && itemCount > position) {
            items!!.set(position, item)
            notifyItemChanged(position)
        }
    }

    fun removeItem(position: Int) {
        if (itemCount > position) {
            items!!.removeAt(position)
            notifyItemRemoved(position)
            // 使用notifyItemRemoved()方法时此句一定要加，用于通知item的变化，否则item的position会错乱
            notifyItemRangeChanged(0, items!!.size)
        }
    }

    fun clear() {
        if (items != null) {
            items!!.clear()
            items = null
        }
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemClickListener<T>?) {
        this.itemClickListener = listener
    }

    fun getItemClickListener(): OnItemClickListener<T>? {
        return this.itemClickListener
    }

    interface OnItemClickListener<T> {
        fun onItemClick(holder: Any, item: T, position: Int)
    }

}