package com.mvvm.fanny.lib_components.view

import android.content.Context
import android.text.*
import android.text.style.AbsoluteSizeSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.mvvm.fanny.lib_components.R

/**
 * Create by FengYi.Lee on 2021/10/14.
 * desc:
 */
class TextInputView : LinearLayout {

    private var mContext: Context? = null
    private var titleColor = 0
    private var titleSize = 0
    private var titleText:String ?= null
    private var hintColor = 0
    private var hintSize = 0
    private var hintText :String ?= null
    private var etContentText :String ?= null
    private var titleView:AppCompatTextView ?= null
    var etContent:AppCompatEditText ?= null

    private var mEtInputType: VCInputType? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        titleView?.setTextColor(titleColor)
        titleView?.setHintTextColor(titleColor)
        titleView?.textSize = titleSize.toFloat()

        if (titleText != null){
            titleView?.text = titleText
        }

//        etContent?.setTextColor(hintColor)
        etContent?.setHintTextColor(hintColor)
        etContent?.textSize = hintSize.toFloat()

        if (hintText != null){
            val ss = SpannableString(hintText)
            val ass = AbsoluteSizeSpan(hintSize,true)
            ss.setSpan(ass,0,ss.length,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            etContent?.hint = SpannedString(ss)
        }

        if (etContentText != null){
            etContent?.setText(etContentText)
        } else {
            etContent?.setText("")
        }

        when (mEtInputType) {
            VCInputType.NUMBER -> etContent?.inputType = InputType.TYPE_CLASS_NUMBER
            VCInputType.NUMBERPASSWORD -> {
                etContent?.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
                etContent?.transformationMethod = AsteriskPasswordTransformationMethod()
            }
            VCInputType.TEXT -> etContent?.inputType = InputType.TYPE_CLASS_TEXT
            VCInputType.TEXTPASSWORD -> {
                etContent?.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                etContent?.transformationMethod = AsteriskPasswordTransformationMethod()
            }
            else -> etContent?.inputType = InputType.TYPE_CLASS_NUMBER
        }

    }

    internal enum class VCInputType {
        /**
         * 数字类型
         */
        NUMBER,

        /**
         * 数字密码
         */
        NUMBERPASSWORD,

        /**
         * 文字
         */
        TEXT,

        /**
         * 文字密码
         */
        TEXTPASSWORD
    }

    /**
     * 初始化控件
     */
    fun init(attrs: AttributeSet?){
        LayoutInflater.from(context).inflate(R.layout.view_edittext_layout,this,true)
        titleView = findViewById(R.id.tv_title)
        etContent = findViewById(R.id.et_content)

        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.editTextFromView)
        titleColor = typedArray.getInteger(R.styleable.editTextFromView_tv_title_color,context.resources.getColor(
            com.mvvm.fanny.lib_base.R.color.color_666666))
        titleSize = typedArray.getDimensionPixelSize(R.styleable.editTextFromView_tv_title_size,35)
        titleSize = px2sp(context,titleSize.toFloat())
        titleText = typedArray.getString(R.styleable.editTextFromView_tv_title_text)
        hintColor = typedArray.getInteger(R.styleable.editTextFromView_et_hint_color,context.resources.getColor(
            com.mvvm.fanny.lib_base.R.color.color_8D8D8D))
        hintSize = typedArray.getDimensionPixelSize(R.styleable.editTextFromView_et_hint_size,35)
        hintSize = px2sp(context,hintSize.toFloat())
        hintText = typedArray.getString(R.styleable.editTextFromView_et_hint_text)
        etContentText = typedArray.getString(R.styleable.editTextFromView_et_content_text)
        val inputType = typedArray.getInt(
            R.styleable.editTextFromView_et_text_inputType,
            VCInputType.NUMBER.ordinal
        )
        mEtInputType = VCInputType.values()[inputType]
        typedArray.recycle()
    }

    fun px2sp(context: Context, pxValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    fun removeTextWatch(textWatcher: TextWatcher){
        etContent?.removeTextChangedListener(textWatcher)
    }

    fun addTextWatch(textWatcher: TextWatcher){
        etContent?.addTextChangedListener(textWatcher)
    }

    fun setTitleText(str:String?){
        titleView?.text = str
    }

    fun setContentText(str:String?){
        etContent?.setText(str)
    }

    fun setContentHint(str:String?){
        etContent?.hint = str
    }

    fun getContentText():String? = etContent?.text?.toString()

    fun getTitleText():String? = titleView?.text?.toString()


}





