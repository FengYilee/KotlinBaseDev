package com.mvvm.fanny.lib_components.view

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.mvvm.fanny.lib_components.R

/**
 * Create by FengYi.Lee on 2021/10/19.
 * desc:
 */
class TextInfoFormView: ConstraintLayout {

    var titleView: AppCompatTextView?= null
    var contentView: AppCompatTextView?= null
    var titleStr:String?= null
    var contentStr:String?= null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_textview_layout,this,true)
        titleView = findViewById(R.id.tv_title)
        contentView = findViewById(R.id.tv_content)

        attrs?.apply {
            val typedArray = context.obtainStyledAttributes(this,R.styleable.textInfoFormView)
            titleStr = typedArray.getString(R.styleable.textInfoFormView_textview_form_title)
            contentStr = typedArray.getString(R.styleable.textInfoFormView_textview_form_content)

            typedArray.recycle()
        }
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun setKeyText(str:String?){
        if (!TextUtils.isEmpty(str)){
            titleView?.text = str
        }
    }

    fun setValueText(str:String?){
        if (!TextUtils.isEmpty(str)) {
            contentView?.text = str
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (!TextUtils.isEmpty(titleStr)){
            titleView?.text = titleStr
        }

        if (!TextUtils.isEmpty(contentStr)){
            contentView?.text = contentStr
        }
    }
}