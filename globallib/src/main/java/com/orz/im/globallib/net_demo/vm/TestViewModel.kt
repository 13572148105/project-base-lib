package com.orz.im.globallib.net_demo.vm

import androidx.lifecycle.MutableLiveData
import com.orz.im.globallib.base.BaseViewModel
import com.orz.im.globallib.net_demo.ApiTestService
import com.orz.im.globallib.net_demo.entity.BannerBean
import com.orz.im.globallib.net_demo.repository.TestRepository
import com.orz.im.globallib.network.Results
import com.orz.im.globallib.network.remote.HttpServiceFactory

/**
 * Created by Orz on .
 */
class TestViewModel:BaseViewModel() {
    private val mRepository by lazy { TestRepository.instance(HttpServiceFactory.create( "https://www.wanandroid.com/",ApiTestService::class.java)) }

    fun getBanner(): MutableLiveData<Results<List<BannerBean>>>? {
        val bannerResult = MutableLiveData<Results<List<BannerBean>>>()
        launchOnlyResult({ mRepository.getBannerData() }, success = {
            bannerResult.postValue(Results.success(it))
        }, error = {
            bannerResult.postValue(Results.failure(it))
        })
        return bannerResult

    }
}