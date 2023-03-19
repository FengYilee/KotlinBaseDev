package com.mvvm.fanny.test

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.mvvm.fanny.lib_base.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * @author: FengYi.Lee
 * @e-mail:fengyi.li@hotmail.com
 * @description:
 * @date: 2023/3/18
 */
class TestViewModel:BaseViewModel() {

    val testRepository = TestRepository()

    fun loadData(){
        launchRequest(true, request = {

            val response = testRepository.requestListData()

        })


    }


    private fun test1(){
        Thread.sleep(3000)
        Log.d("test1",Thread.currentThread().name)
    }
    private fun test2(){
        Thread.sleep(2000)
        Log.d("test2",Thread.currentThread().name)
    }
    private fun test3(){
        Thread.sleep(3000)
        Log.d("test3",Thread.currentThread().name)
    }

}