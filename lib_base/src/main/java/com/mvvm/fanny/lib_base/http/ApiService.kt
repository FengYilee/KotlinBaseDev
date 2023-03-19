package com.mvvm.fanny.lib_base.http

import com.mvvm.fanny.lib_base.TestModel
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author: FengYi.Lee
 * @e-mail:fengyi.li@hotmail.com
 * @description:
 * @date: 2023/3/18
 */
interface ApiService {

    @GET("api.php/provide/vod/?ac=list")
    suspend fun getList(@Query("pg")page:Int):BaseResponse<List<TestModel>>

}