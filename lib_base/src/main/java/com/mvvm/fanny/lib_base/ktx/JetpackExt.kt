package com.mvvm.fanny.lib_base.ktx

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

/**
 * @author: FengYi.Lee
 * @e-mail:fengyi.li@hotmail.com
 * @description:
 * @date: 2023/3/18
 */
fun <T> LifecycleOwner.observe(liveData: LiveData<T>, observer: (t: T) -> Unit){
    liveData.observe(this) { observer(it) }
}

fun Context.showToastLong(context: Context, str: String){
    Toast.makeText(context,str,Toast.LENGTH_LONG).show()
}

fun Context.showToastShort(context: Context, str:String){
    Toast.makeText(context,str,Toast.LENGTH_SHORT).show()
}