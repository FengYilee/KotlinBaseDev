package com.mvvm.fanny.lib_base.http

/**
 * @author: FengYi.Lee
 * @e-mail:fengyi.li@hotmail.com
 * @description:
 * @date: 2023/3/18
 */
class BaseResponse<T> {
    val data: T? = null
    val code: Int? = null
    val message: String? = null
    var state: State = State.ERROR
}
enum class State {
    SUCCESS, ERROR
}