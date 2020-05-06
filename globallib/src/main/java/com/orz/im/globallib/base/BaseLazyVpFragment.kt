package com.orz.im.globallib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
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
 * Created by Orz on .
 * 仅配合ViewPager2使用实现懒加载
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseLazyVpFragment<VB : ViewBinding> : Fragment(), TitleBarAction {

    protected open lateinit var mViewBinding: VB
    protected open lateinit var mActivity: AppCompatActivity
    protected open  var mLoadSir: LoadService<*>?=null
    private var isFirstLoad: Boolean = true
    private var mTitleBar: TitleBar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val clazz = type.actualTypeArguments[0] as Class<*>
            if (ViewBinding::class.java != clazz && ViewBinding::class.java.isAssignableFrom(clazz)) {
                val mInflate = clazz.getMethod("inflate", LayoutInflater::class.java,
                    ViewGroup::class.java, Boolean::class.java)
                mViewBinding = mInflate.invoke(null, layoutInflater, container, false) as VB
                return mViewBinding.root
            }
        }
        return inflater.inflate(layoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = context as AppCompatActivity
        configLoadSir()
        configTitleBar()
        initView(savedInstanceState)
        registerEvent()
    }

    override fun onStart() {
        super.onStart()
        initEventBus()
    }

    open fun initView(savedInstanceState: Bundle?) {}

    override fun onResume() {
        super.onResume()
            if (isFirstLoad) {
                processLogic()
                isFirstLoad = false
            }
    }

    abstract fun layoutId(): Int

    /**
     * 懒加载
     */
    open fun processLogic() {}

    /**
     * 注册 UI 事件
     */
    private fun registerEvent() {
        NetworkStateManager.getInstance().mNetworkStateCallback.observe(this, Observer {
            onNetworkStateChanged(it)
        })
    }


    protected open fun useEventBus():Boolean{
        return false
    }

    private fun initEventBus(){
        (useEventBus()).yes{
            EventBus.getDefault().register(this)
        }
    }

    protected open fun contentResId():Int{
        return 0
    }

    private fun configLoadSir(){
        if(contentResId() > 0){
            val loadView  = mViewBinding.root.findViewById<View>(contentResId())
            mLoadSir = LoadSir.getDefault().register(loadView) { v -> onReload(v) }
        }
    }

    protected open fun onReload(v: View?) {

    }

    private fun configTitleBar() {
        titleBar?.let {
            it.setOnTitleBarListener(this)
            ImmersionBar.setTitleBar(this, it)
        }
    }

    /**
     * 设置自定义titlebar，Click事件需自行定义
     */
    protected open fun setTitleBar(titleBar: View) {
        ImmersionBar.setTitleBar(this, titleBar)
    }

    @Nullable
    override fun getTitleBar(): TitleBar? {
        if (mTitleBar == null) {
            mTitleBar = mActivity.findViewById(R.id.common_toolbar)
        }
        return mTitleBar
    }


    /**
     *子类可以重写该方法，统一的网络状态通知和处理
     */
    protected open fun onNetworkStateChanged(netState: NetState) {
    }

    /**
     * 获取NavController
     */
    protected open fun nav(): NavController? {
        return NavHostFragment.findNavController(this)
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
        (useEventBus()).yes{
            if(EventBus.getDefault().isRegistered(this))
                EventBus.getDefault().unregister(this)
        }
    }

}