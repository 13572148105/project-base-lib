package com.orz.im.globallib.network.remote

import com.orz.im.globallib.BuildConfig
import com.orz.im.globallib.network.interceptor.BaseInterceptor
import com.orz.im.globallib.network.interceptor.Level
import com.orz.im.globallib.network.interceptor.LoggingInterceptor
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *  Service 构建工厂
 */
object HttpServiceFactory {

    private fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
    }

    private fun provideOkHttpBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .connectTimeout(20L, TimeUnit.SECONDS)
            .addNetworkInterceptor(LoggingInterceptor().apply {
                isDebug = BuildConfig.DEBUG
                level = Level.BASIC
                type = Platform.INFO
                requestTag = "Request"
                requestTag = "Response"
            })
            .writeTimeout(20L, TimeUnit.SECONDS)
            .readTimeout(20L, TimeUnit.SECONDS)
            .connectionPool(ConnectionPool(8, 15, TimeUnit.SECONDS))
    }

    private fun configSimpleNetLoader(bUrl: String, headParams: Map<String, String>?): Retrofit {
        val okBuilder = provideOkHttpBuilder().apply {
            headParams?.let {
                addInterceptor(BaseInterceptor(headParams))
            }
        }
        val retrofitBuilder = provideRetrofitBuilder().apply { baseUrl(bUrl) }
        return retrofitBuilder.client(okBuilder.build())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    fun <T> create(baseUrl: String,service: Class<T>,headParams: Map<String, String>?=null): T =
        configSimpleNetLoader(baseUrl, headParams).create(service)
            ?: throw RuntimeException("Api service is null!")
}