package com.mvvm.fanny.lib_components.view

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.databinding.InverseBindingListener
import com.mvvm.fanny.lib_components.R

/**
 * Create by FengYi.Lee on 2021/10/26.
 * desc:
 */
class TextSelectView:LinearLayoutCompat {
    var listener: InverseBindingListener ?= null
    private var tvTitleView:AppCompatTextView ?= null       //标题控件
    private var tvContentView:AppCompatTextView ?= null     //内容控件

    private var titleStr:String ?= null
    private var contentStr:String ?= null
    private var hintStr:String ?= null
    private var showDateImage = false
    private var showSelectImage = false

    var onItemClickListener: ItemOnClickListener?= null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_textview_frame_layout,this,true)
        tvTitleView = findViewById(R.id.tv_title)
        tvContentView = findViewById(R.id.tv_content)

        attrs?.apply {
            val typedArray = context.obtainStyledAttributes(this,R.styleable.textInfoFrameView)
            titleStr = typedArray.getString(R.styleable.textInfoFrameView_textview_frame_title)
            contentStr = typedArray.getString(R.styleable.textInfoFrameView_textview_frame_content)
            hintStr = typedArray.getString(R.styleable.textInfoFrameView_textview_frame_hint)
            showDateImage = typedArray.getBoolean(R.styleable.textInfoFrameView_textview_frame_show_date,false)
            showSelectImage = typedArray.getBoolean(R.styleable.textInfoFrameView_textview_frame_show_select,false)

            typedArray.recycle()
        }
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (!TextUtils.isEmpty(titleStr)){
            tvTitleView?.text = titleStr
        }
        if (!TextUtils.isEmpty(contentStr)){
            tvContentView?.text = contentStr
        }

        if (!TextUtils.isEmpty(hintStr)){
            tvContentView?.hint = hintStr
        }

        if (showDateImage){
            val drawableImg = context.resources.getDrawable(R.mipmap.image_date_select,context.theme)
            drawableImg.setBounds(0,0,drawableImg.minimumWidth,drawableImg.minimumHeight)
            tvContentView?.setCompoundDrawables(null,null,drawableImg,null)
        }

        if (showSelectImage){
            val drawableImg = context.resources.getDrawable(R.mipmap.icon_picker,context.theme)
            drawableImg.setBounds(0,0,drawableImg.minimumWidth,drawableImg.minimumHeight)
            tvContentView?.setCompoundDrawables(null,null,drawableImg,null)
        }

        setOnClickListener {
            onItemClickListener?.onItemClick()
        }

    }

    /**
     * 设置控件标题
     */
    fun setTitleText(str:String?){
        if (!TextUtils.isEmpty(str)){
            tvTitleView?.text = str
        }
    }

    fun getTitleText():String?{
        return tvTitleView?.text?.toString()
    }

    /**
     * 设置控件文本内容
     */
    fun setValueText(str:String?){
        if (!TextUtils.isEmpty(str)) {
            tvContentView?.text = str
        }
        listener?.onChange()
    }

    fun getValueText():String?{
        return tvContentView?.text?.toString()
    }

    fun setHintText(hintStr:String?){
        tvContentView?.hint = hintStr
    }

    fun showDateImageView(){
        val drawableImg = context.resources.getDrawable(R.mipmap.image_date_select,context.theme)
        drawableImg.setBounds(0,0,drawableImg.minimumWidth,drawableImg.minimumHeight)
        tvContentView?.setCompoundDrawables(null,null,drawableImg,null)
    }

    fun showSelectImageView(){
        val drawableImg = context.resources.getDrawable(R.mipmap.icon_picker,context.theme)
        drawableImg.setBounds(0,0,drawableImg.minimumWidth,drawableImg.minimumHeight)
        tvContentView?.setCompoundDrawables(null,null,drawableImg,null)
    }

    fun setOnItemOnClickListener(itemClick: ItemOnClickListener){
        this.onItemClickListener = itemClick
    }

    interface ItemOnClickListener{
        fun onItemClick()
    }
}