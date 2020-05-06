package com.orz.im.joincall

import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.orz.im.globallib.base.BaseActivity
import com.orz.im.globallib.common.ext.handlerResult
import com.orz.im.globallib.common.ext.observe
import com.orz.im.globallib.helper.ViewModelProviderHelper
import com.orz.im.globallib.net_demo.vm.TestViewModel
import com.orz.im.joincall.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun layoutId(): Int = R.layout.activity_main
    private val mViewModel:TestViewModel by lazy { ViewModelProviderHelper.buildViewModel(this,TestViewModel::class.java) }
    override fun contentResId(): Int {
        return R.id.mTV
    }

    override fun onNetReload(v: View?) {
        super.onNetReload(v)
        showHintView(R.drawable.ic_no_network,"网络异常，请稍后重试！")
    }
    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun processLogic() {
        asyncBanner()
    }

    private fun asyncBanner(){
        observe(mViewModel.getBanner()) {data->
            handlerResult(data,
                success = { result->
                    result?.let {
                      LogUtils.dTag("uuu",GsonUtils.toJson(it))
                    }
                },
                error = {
                    ToastUtils.showLong(it.errMsg)
                })
        }
    }
}

