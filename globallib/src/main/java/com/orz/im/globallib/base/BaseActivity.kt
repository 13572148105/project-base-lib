package com.orz.im.globallib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar
import com.hjq.bar.TitleBar
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.orz.im.globallib.R
import com.orz.im.globallib.common.ext.yes
import com.orz.im.globallib.common.loadsir.EmptyCallback
import com.orz.im.globallib.common.loadsir.ErrorCallback
import com.orz.im.globallib.common.loadsir.LoadingCallback
import com.orz.im.globallib.common.netstate.NetState
import com.orz.im.globallib.common.netstate.NetworkStateManager
import com.orz.im.globallib.widget.title.TitleBarAction
import org.greenrobot.eventbus.EventBus
import java.lang.reflect.ParameterizedType


/**
 *   @auther : Aleyn
 *   time   : 2019/11/01
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() , TitleBarAction {

    protected open lateinit var mViewBinding: VB

    protected open  var mLoadSir: LoadService<*>?=null

    private var mTitleBar: TitleBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        configLoadSir()
        configTitleBar()
        configStatusBar()
        initView(savedInstanceState)
        registerEvent()
        processLogic()
    }

    override fun onStart() {
        super.onStart()
        initEventBus()
    }

    abstract fun layoutId(): Int
    protected open fun contentResId():Int=0
    abstract fun initView(savedInstanceState: Bundle?)
    abstract fun processLogic()

    protected open fun configStatusBar(){
        ImmersionBar.with(this)
            .keyboardEnable(true)
            .init()
    }

    private fun configTitleBar(){
        titleBar?.let {
            it.setOnTitleBarListener(this)
            ImmersionBar.setTitleBar(this, it)
        }
    }

    /**
     * DataBinding
     */
    private fun initViewBinding() {
        //利用反射，调用指定ViewBinding中的inflate方法填充视图
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val clazz = type.actualTypeArguments[0] as Class<*>
            if (ViewBinding::class.java.isAssignableFrom(clazz)) {
                val method = clazz.getMethod("inflate", LayoutInflater::class.java)
                mViewBinding = method.invoke(null, layoutInflater) as VB
                setContentView(mViewBinding.root)
            }
        }else{
            setContentView(layoutId())
        }
    }


    /**
     * 注册 UI 事件
     */
    private fun registerEvent() {
        lifecycle.addObserver(NetworkStateManager.getInstance())
        NetworkStateManager.getInstance().mNetworkStateCallback.observe(this, Observer {
            onNetworkStateChanged(it)
        })
    }

    protected open fun useEventBus():Boolean{
        return false
    }

    private fun initEventBus(){
        if(useEventBus()){
            EventBus.getDefault().register(this)
        }
    }

    private fun configLoadSir(){
        if(contentResId() > 0){
            val loadView  = this.findViewById<View>(contentResId())
            mLoadSir = LoadSir.getDefault().register(loadView) { v -> onNetReload(v) }
        }
    }

    protected open fun onNetReload(v: View?) {

    }

    /**
     * 设置自定义titlebar，Click事件需自行定义
     */
    protected open fun setTitleBar(titleBar: View) {
        ImmersionBar.setTitleBar(this, titleBar)
    }

    @Override
    @Nullable
    override fun getTitleBar(): TitleBar? {
       (mTitleBar == null).yes {
            mTitleBar = findViewById(R.id.common_toolbar)
        }
        return mTitleBar
    }

    private fun getContentView(): ViewGroup? {
        return findViewById(Window.ID_ANDROID_CONTENT)
    }

    override fun setTitle(title: CharSequence?) {
         mTitleBar?.title = title
    }

    override fun setTitle(id: Int) {
         mTitleBar?.title = getString(id)
    }

    override fun onLeftClick(v: View?) {
        onBackPressed()
    }

    /**
     *子类可以重写该方法，统一的网络状态通知和处理
     */
    protected open fun onNetworkStateChanged(netState: NetState) {
    }


    protected open fun showLoadingView(){
        (contentResId() > 0).yes {
            mLoadSir?.showCallback(LoadingCallback::class.java)
        }
    }

    protected open fun showEmptyView(){
        (contentResId() > 0).yes {
            mLoadSir?.showCallback(EmptyCallback::class.java)
        }
    }

    protected open fun showErrorView(){
        (contentResId() > 0).yes {
            mLoadSir?.showCallback(ErrorCallback::class.java)
        }
    }

    /**
     * 可以动态更改View显示内容
     */
    protected open fun showHintView(imgResId:Int,describe:String){
        (contentResId() > 0).yes {
            mLoadSir?.setCallBack(EmptyCallback::class.java){ _, v->
            val icon = v.findViewById<ImageView>(R.id.iv_error_icon)
            val desc = v.findViewById<TextView>(R.id.tv_error_desc)
                icon.setImageResource(imgResId)
                desc.text = describe
            }
            showEmptyView()
        }
    }

    protected open fun showSuccess(){
        (contentResId() > 0).yes {
            mLoadSir?.showSuccess()
        }
    }

    override fun onStop() {
        super.onStop()
        if(useEventBus()){
            if(EventBus.getDefault().isRegistered(this))
                EventBus.getDefault().unregister(this)
        }
    }

}