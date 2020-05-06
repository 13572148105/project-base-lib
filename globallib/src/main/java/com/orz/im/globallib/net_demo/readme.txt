#package name : net_demo
#instructions : 教会你如何轻松使用网络请求库
#涉及主要三方开源库如下：
1.Retrofit
2.OkHttp
3.kotlin 协程
4.kotlin Flow 类似rxJava流式调用
5.LiveData
6.ViewModel
7.使用样例
 private fun asyncBanner(){
        observe(mViewModel.getBanner()) {data->
            handlerResult(data,
                success = { result->
                    result?.let {
                    }
                },
                error = {
                    ToastUtils.showLong(it.errMsg)
                })
        }
    }
8.移动键值存储工具类PrefsUtil使用样例
  private var test1:String by PrefsUtil("t_1","")

