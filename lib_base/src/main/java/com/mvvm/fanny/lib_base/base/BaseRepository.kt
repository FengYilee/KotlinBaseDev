package com.mvvm.fanny.lib_base.base

import com.mvvm.fanny.lib_base.http.ApiService
import com.mvvm.fanny.lib_base.http.BaseResponse
import com.mvvm.fanny.lib_base.http.State
import com.mvvm.fanny.lib_base.http.retrofit

/**
 * @author: FengYi.Lee
 * @e-mail:fengyi.li@hotmail.com
 * @description:
 * @date: 2023/3/18
 */
open class BaseRepository {

    open val service:ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    suspend fun <T:Any> executeRequest(bloc:suspend() -> BaseResponse<T>):BaseResponse<T>{
        val baseData = bloc.invoke()
        baseData.state = if (baseData.code == RESPONSE_SUCCESS){
            State.SUCCESS
        } else {
            State.ERROR
        }
        return baseData
    }

}