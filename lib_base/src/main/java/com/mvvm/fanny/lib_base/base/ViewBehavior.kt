package com.mvvm.fanny.lib_base.base

import android.os.Bundle

/**
 * @author: FengYi.Lee
 * @e-mail:fengyi.li@hotmail.com
 * @description:
 * @date: 2023/3/18
 */
interface ViewBehavior {
    /**
     * 是否显示Loading视图
     */
    fun showLoadingUI(isShow:Boolean)

    /**
     * 弹出toast提示
     */
    fun showToast(map: Map<String, *>)


    /**
     * 不带参数得页面跳转
     */
    fun navigate(page:Any?)

    /**
     * 带参数得页面跳转
     */
    fun navigate(map: Map<String, *>)

    /**
     * 返回键点击
     */
    fun backPress(arg: Any?)


    /**
     * 关闭页面
     */
    fun finishPage(arg: Any?)
}