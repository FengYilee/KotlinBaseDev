package com.mvvm.fanny.lib_base.http

import com.mvvm.fanny.lib_base.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * @author: FengYi.Lee
 * @e-mail:fengyi.li@hotmail.com
 * @description:
 * @date: 2023/3/18
 */
val retrofit:Retrofit by lazy {
    Retrofit.Builder()
        .baseUrl("https://api.tiankongapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(getOkHttpClient())
        .build()
}


private fun getOkHttpClient():OkHttpClient{
    val builder:OkHttpClient.Builder = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS) //设置写的超时时间
        .connectTimeout(30, TimeUnit.SECONDS)
        .sslSocketFactory(SSLContextUtil.getSSLSocketFactory(),SSLContextUtil.getX509TrustManager())
        .hostnameVerifier(SSLContextUtil.getHostnameVerifier())
    if (BuildConfig.DEBUG){
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        builder.addInterceptor(httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        })
    }

    return builder.build()
}

