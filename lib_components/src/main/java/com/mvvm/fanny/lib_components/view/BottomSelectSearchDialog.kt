package com.mvvm.fanny.lib_components.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.mvvm.fanny.lib_base.base.adapters.BaseRecyclerAdapter
import com.mvvm.fanny.lib_components.R
import com.mvvm.fanny.lib_components.adapters.BottomSelectSearchAdapter
import com.mvvm.fanny.lib_components.bean.BottomSearchBean

/**
 * Create by FengYi.Lee on 2021/10/27.
 * desc: 带搜索功能的BottomDialog(底部弹出窗口)
 */
class BottomSelectSearchDialog: Dialog{

    private var recyclerView:RecyclerView ?= null
    private var tvTitleView:AppCompatTextView ?= null
    private var tvCancel:AppCompatTextView ?= null
    private var etFilterView:AppCompatEditText ?= null
    private var ivSearchCancel: AppCompatImageView?= null
    private var mAdapter = BottomSelectSearchAdapter()
    private var onSelectListener: BottomSelectClickListener?= null

    constructor(context: Context) : super(context)
    constructor(context: Context, themeResId: Int) : super(context, R.style.bottom_dialog)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val contentView = LayoutInflater.from(context).inflate(R.layout.dialog_bottom_list_search, null)
        setContentView(contentView)
        recyclerView = findViewById(R.id.recycler_view)
        tvTitleView = findViewById(R.id.tv_title)
        tvCancel = findViewById(R.id.tv_cancel)
        etFilterView = findViewById(R.id.et_filter_content)
        ivSearchCancel = findViewById(R.id.iv_search_cancel)

        etFilterView?.addTextChangedListener(textWatcher)
        ivSearchCancel?.setOnClickListener {
            etFilterView?.setText("")
        }

        tvCancel?.setOnClickListener {
            dismiss()
        }

        mAdapter.setOnItemClickListener(object: BaseRecyclerAdapter.OnItemClickListener<BottomSearchBean>{
            override fun onItemClick(holder: Any, item: BottomSearchBean, position: Int) {
                onSelectListener?.onSelectResult(item.name!!)
                dismiss()
            }
        })

        recyclerView?.apply{
            this.adapter = mAdapter
        }


        window?.apply {
            val attr = attributes
            attr.width = ViewGroup.LayoutParams.MATCH_PARENT
            attr.height = (context.resources.displayMetrics?.heightPixels!! * 0.8).toInt()
            attr.gravity = Gravity.BOTTOM
            attributes = attr
            setBackgroundDrawableResource(android.R.color.white)
            setWindowAnimations(R.style.BottomDialog_Animation)
        }

    }

    /**
     * 必须用在showDialog之后，不然无法View为null
     */
    fun setTitle(title:String){
        tvTitleView?.text = title
        etFilterView?.hint = title
    }

    fun showDialog(){
        show()
    }

    fun dismissDialog(){
        dismiss()
    }

    fun setDataList(list:ArrayList<BottomSearchBean>){
        mAdapter.refreshItems(list)
        mAdapter.mFilterList = list
    }

    fun setOnBottomSelectClickListener(listener: BottomSelectClickListener){
        this.onSelectListener = listener
    }


    interface BottomSelectClickListener{
        fun onSelectResult(item:String)
    }


    private var textWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            mAdapter.filter.filter(s.toString())
        }

    }
}