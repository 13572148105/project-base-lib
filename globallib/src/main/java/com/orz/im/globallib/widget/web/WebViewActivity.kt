package com.orz.im.globallib.widget.web

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.hjq.bar.OnTitleBarListener
import com.orz.im.globallib.databinding.ActivityWebViewBinding


/**
 * com.yj.lottery.widget.web_view.WebViewActivity
 * Created by cxd on 2020/4/17
 * Describe: 基础webview
 */
class WebViewActivity:AppCompatActivity(), OnTitleBarListener {

    private var mIntentTitle:String = ""
    private lateinit var mViewBinding:ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = ActivityWebViewBinding.inflate(LayoutInflater.from(this))
        setContentView(mViewBinding.root)
        initView()
        initData()
    }

    private fun initView() {
        initProgressbar()
        initWebSettings()
    }

    private fun initData() {
        mIntentTitle = intent?.getStringExtra(TITLE)?:""
        val url = intent?.getStringExtra(WEB_URL)
        title = mIntentTitle
        if(url.isNullOrEmpty()){
            return
        }
        if (url.startsWith("http")) {
            mViewBinding.webBase.loadUrl(url)
        } else {
            mViewBinding.webBase.loadData(url, "text/html", "UTF-8")
        }
    }

    private fun initProgressbar(){
        //设置加载进度最大值
        mViewBinding.progressBar.max = 100
    }

    private fun initWebSettings(){
        val webSettings: WebSettings = mViewBinding.webBase.settings
        if (Build.VERSION.SDK_INT >= 19) { //加载缓存否则网络
            webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        }
        webSettings.loadsImagesAutomatically = Build.VERSION.SDK_INT >= 19
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) { //软件解码
            mViewBinding.webBase.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }
        //硬件解码
        mViewBinding.webBase.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        // 设置支持javascript脚本
        webSettings.javaScriptEnabled = true
        // 设置可以支持缩放
        webSettings.setSupportZoom(true)
        // 设置出现缩放工具 是否使用WebView内置的缩放组件，由浮动在窗口上的缩放控制和手势缩放控制组成，默认false
        webSettings.builtInZoomControls = true
        //隐藏缩放工具
        webSettings.displayZoomControls = false
        // 扩大比例的缩放
        webSettings.useWideViewPort = true
        //自适应屏幕
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        webSettings.loadWithOverviewMode = true
        webSettings.databaseEnabled = true
        //保存密码
        //保存密码
        webSettings.savePassword = true
        //是否开启本地DOM存储  鉴于它的安全特性（任何人都能读取到它，尽管有相应的限制，将敏感数据存储在这里依然不是明智之举），Android 默认是关闭该功能的。
        webSettings.domStorageEnabled = true
        mViewBinding.webBase.isSaveEnabled = true
        mViewBinding.webBase.keepScreenOn = true
        // 设置setWebChromeClient对象
        mViewBinding.webBase.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)
                if(mIntentTitle.isNullOrEmpty()){
                    setTitle(title)
                }
            }

            override fun onProgressChanged(view: WebView, newProgress: Int) {
                mViewBinding.progressBar.progress = newProgress
                super.onProgressChanged(view, newProgress)
            }
        }

        //设置此方法可在WebView中打开链接，反之用浏览器打开
        mViewBinding.webBase.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                if (!mViewBinding.webBase.settings.loadsImagesAutomatically) {
                    mViewBinding.webBase.settings.loadsImagesAutomatically = true
                }
                mViewBinding.progressBar.visibility = View.GONE
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                mViewBinding.progressBar.visibility = View.VISIBLE
                super.onPageStarted(view, url, favicon)
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return if (url.startsWith("http:") || url.startsWith("https:")) {
                    view.loadUrl(url)
                    false
                } else { // Otherwise allow the OS to handle things like tel, mailto, etc.
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    true
                }
            }
        }
        mViewBinding.webBase.setDownloadListener(DownloadListener { paramAnonymousString1, paramAnonymousString2, paramAnonymousString3, paramAnonymousString4, paramAnonymousLong ->
            val intent = Intent()
            intent.action = "android.intent.action.VIEW"
            intent.data = Uri.parse(paramAnonymousString1)
            startActivity(intent)
        })
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        try {
            super.onConfigurationChanged(newConfig)
            if (this.resources.configuration.orientation === Configuration.ORIENTATION_LANDSCAPE) {
                Log.v("Himi", "onConfigurationChanged_ORIENTATION_LANDSCAPE")
            } else if (this.resources.configuration.orientation === Configuration.ORIENTATION_PORTRAIT) {
                Log.v("Himi", "onConfigurationChanged_ORIENTATION_PORTRAIT")
            }
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
    }

    override fun onLeftClick(v: View?) {
        onBackPressed()
    }

    override fun onRightClick(v: View?) {
    }

    override fun onTitleClick(v: View?) {
    }

    override fun onBackPressed() {
        if (mViewBinding.webBase.canGoBack()) {
            mViewBinding.webBase.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onPause() {
        super.onPause()
        mViewBinding.webBase.onPause()
    }

    override fun onDestroy() {
        mViewBinding.webBase.destroy()
        super.onDestroy()
    }

    companion object{
        private const val TITLE:String = "title"
        private const val WEB_URL:String = "web_url"
        fun start(context: Context? , title:String? , url:String){
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(TITLE, title)
            intent.putExtra(WEB_URL, url)
            if (context != null) {
                ContextCompat.startActivity(context, intent, null)
            }
        }
    }

}