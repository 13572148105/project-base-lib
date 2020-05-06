package com.orz.im.globallib.net_demo

import com.orz.im.globallib.net_demo.entity.BannerBean
import com.orz.im.globallib.network.HttpResponseBody
import retrofit2.http.GET

/**
 * Created by Orz on .
 */
interface ApiTestService {
    /**
     * 玩安卓轮播图
     */
    @GET("banner/json")
    suspend fun getTestBanner(): HttpResponseBody<List<BannerBean>>
}