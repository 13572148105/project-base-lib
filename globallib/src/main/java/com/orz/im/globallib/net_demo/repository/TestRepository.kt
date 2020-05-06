package com.orz.im.globallib.net_demo.repository

import com.orz.im.globallib.base.BaseModel
import com.orz.im.globallib.net_demo.ApiTestService
import com.orz.im.globallib.net_demo.entity.BannerBean
import com.orz.im.globallib.network.HttpResponseBody
import com.orz.im.globallib.util.SingletonHolderSingleArg

/**
 * Created by Orz on .
 */
class TestRepository private constructor(private val mService:ApiTestService):BaseModel(){
     suspend fun getBannerData(): HttpResponseBody<List<BannerBean>> {
        return mService.getTestBanner()
    }
    companion object : SingletonHolderSingleArg<TestRepository, ApiTestService>(::TestRepository)
}