package com.mvvm.fanny.lib_base.dialog

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.mvvm.fanny.lib_base.R
import com.mvvm.fanny.lib_base.databinding.ViewDialogLoadingBinding
import com.mvvm.fanny.lib_base.dialog.BaseDialog

/**
 * Created by FengYi.Lee<fengyi.li></fengyi.li>@hotmail.com> on 2020/12/15.
 */
class LoadingDialog(context: Context) : BaseDialog<ViewDialogLoadingBinding>(context, R.style.LoadingDialog) {

    override fun getLayoutId(): Int {
        return R.layout.view_dialog_loading
    }

    override fun initData() {
        window!!.setGravity(Gravity.CENTER)
        window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }

    fun show(text: String?) {
        binding!!.tvTips.text = if (TextUtils.isEmpty(text)) "加载中..." else text
        binding!!.tvTips.visibility = View.VISIBLE
        super.show()
    }

    override fun show() {
        this.show("加载中...")
    }
}