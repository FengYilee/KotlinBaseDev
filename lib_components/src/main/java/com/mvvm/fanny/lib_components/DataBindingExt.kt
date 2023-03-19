package com.mvvm.fanny.lib_components

import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.adapters.ListenerUtil
import com.mvvm.fanny.lib_components.view.TextInputView
import com.mvvm.fanny.lib_components.view.TextSelectView

/**
 * @author: FengYi.Lee
 * @e-mail:fengyi.li@hotmail.com
 * @description:
 * @date: 2023/3/18
 */

object DataBindingExt {
    @JvmStatic
    @BindingAdapter("text")
    fun setText(textSelectView: TextSelectView, text:String?){
        textSelectView.setValueText(text)
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "text", event = "textAttrChanged")
    fun getText(textSelectView: TextSelectView):String{
        return textSelectView.getValueText().toString()
    }

    @JvmStatic
    @BindingAdapter("text")
    fun setText(textInputView: TextInputView, text:String?){
        val lastLocation = textInputView.etContent?.selectionStart!!
        textInputView.etContent?.setText(text)
        val etable: Editable = textInputView.etContent?.text!!
        Selection.setSelection(etable,lastLocation)

    }

    @InverseBindingAdapter(attribute = "text",event = "textAttrChanged")
    @JvmStatic
    fun getText(textInputView: TextInputView):String{
        return textInputView.etContent!!.text.toString()
    }

    @BindingAdapter("textAttrChanged",requireAll = false)
    @JvmStatic
    fun setListener(textSelectView: TextSelectView, listener: InverseBindingListener?){
        textSelectView.listener = listener
    }

    @BindingAdapter("textAttrChanged")
    @JvmStatic
    fun setListener(textInputView: TextInputView, listener: InverseBindingListener?){
        if (listener != null){
            val newTextWatch = object: TextWatcher {

                var l = 0  //记录字符串被删除字符之前，字符串的长度
                var location = 0 //记录光标的位置

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                    l = s!!.length
                    location = textInputView.etContent!!.selectionStart
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (l != s.toString().length){
                        listener.onChange()
                    }

                }

                override fun afterTextChanged(s: Editable?) {
                }

            }

            val oldTextWatch = ListenerUtil.trackListener(textInputView,newTextWatch, androidx.databinding.library.baseAdapters.R.id.textWatcher)
            if (oldTextWatch != null){
                textInputView.removeTextWatch(oldTextWatch)
            }
            textInputView.addTextWatch(newTextWatch)
        }
    }
}



