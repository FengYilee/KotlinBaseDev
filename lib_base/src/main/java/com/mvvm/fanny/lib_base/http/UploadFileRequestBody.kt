package com.mvvm.fanny.lib_base.http

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.*
import java.io.File

/**
 * @author: FengYi.Lee
 * @e-mail:fengyi.li@hotmail.com
 * @description:
 * @date: 2023/3/19
 */
class UploadFileRequestBody(private val file: File,
                            private val uploadListener: UploadListener):RequestBody() {

    private val mRequestBody = create("application/octet-stream".toMediaTypeOrNull(), file)

    init {
        uploadListener.onStartUpload(contentLength())
    }

    override fun contentLength(): Long {
        return mRequestBody.contentLength()
    }

    override fun contentType(): MediaType? {
        return mRequestBody.contentType()
    }

    override fun writeTo(sink: BufferedSink) {
        val bufferedSink = object : ForwardingSink(sink) {
            var totalBytesWrite: Long = 0
            override fun write(source: Buffer, byteCount: Long) {
                super.write(source, byteCount)
                totalBytesWrite += byteCount
                uploadListener.onProgress(totalBytesWrite)
                if (totalBytesWrite == contentLength())
                {
                    uploadListener.onComplete()
                }
            }
        }.buffer()
        //写入
        mRequestBody.writeTo(bufferedSink)
        //刷新
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush()
    }

    //提供一个监听下载进度的接口
    interface UploadListener {
        fun onStartUpload(length: Long)
        fun onProgress(progress: Long)
        fun onComplete()
    }

}