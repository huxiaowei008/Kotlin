package com.hxw.frame.integration

import retrofit2.Retrofit

/**
 * 用来管理网络请求层,以及数据缓存层,以后可以添加数据库请求层
 * Created by hxw on 2017/8/30.
 */

class RepositoryManager(private val mRetrofit: Retrofit) {
    private val mRetrofitService = HashMap<String, Any>()

    fun <T> getRetrofitService(service: Class<T>): T =
            synchronized(mRetrofitService) {
                var retrofitService = mRetrofitService[service.name] as T
                if (retrofitService == null) {
                    retrofitService = mRetrofit.create(service)
                    mRetrofitService.put(service.name, retrofitService!!)
                }
                retrofitService
            }


}