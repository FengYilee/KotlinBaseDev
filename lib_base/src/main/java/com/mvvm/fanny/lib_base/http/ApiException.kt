package com.mvvm.fanny.lib_base.http

/**
 * @author: FengYi.Lee
 * @e-mail:fengyi.li@hotmail.com
 * @description:
 * @date: 2023/3/18
 */
class ApiException(val code:Int?, private val msg:String):Exception(msg) {
}