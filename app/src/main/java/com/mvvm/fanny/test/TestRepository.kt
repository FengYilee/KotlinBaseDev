package com.mvvm.fanny.test

import com.mvvm.fanny.lib_base.TestModel
import com.mvvm.fanny.lib_base.base.BaseRepository
import com.mvvm.fanny.lib_base.http.BaseResponse

/**
 * @author: FengYi.Lee
 * @e-mail:fengyi.li@hotmail.com
 * @description:
 * @date: 2023/3/18
 */
class TestRepository:BaseRepository() {

    suspend fun requestListData():BaseResponse<List<TestModel>>{
        return executeRequest {
            service.getList(1)
        }
    }

}