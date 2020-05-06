package com.orz.im.globallib.util

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.LabeledIntent
import android.net.Uri
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.blankj.utilcode.util.ToastUtils
import java.io.File


/**
 * Created by Orz on .
 * 应用内分享帮助类
 *
 */
object InnerAppShareHelper {

    /**
     * 分享单张图片
     */
    fun shareImage(context: Context,filePath:String){
        val file = File(filePath)
        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            FileProvider.getUriForFile(context, "com.orz.common.file.provider", file)
        }else{
            Uri.fromFile(File(filePath))
        }
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.type = "image/*"
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        val pm = context.packageManager
        val resInfo = pm.queryIntentActivities(intent, 0)
        val targetIntents = arrayListOf<Intent>()
        for (resolveInfo in resInfo) {
            val activityInfo = resolveInfo.activityInfo
            if (activityInfo.packageName.contains("com.tencent.mm") || activityInfo.packageName.contains("com.tencent.mobileqq")) {
                //过滤掉qq收藏
                if (resolveInfo.loadLabel(pm).toString().contains("QQ收藏")) {
                    continue
                }
                val target = Intent()
                target.action = Intent.ACTION_SEND
                target.component = ComponentName(activityInfo.packageName, activityInfo.name)
                target.putExtra(Intent.EXTRA_STREAM, uri)
                target.type = "image/*"//必须设置，否则选定分享类型后不能跳转界面
                targetIntents.add(LabeledIntent(
                        target,
                        activityInfo.packageName,
                        resolveInfo.loadLabel(pm),
                        resolveInfo.icon
                    )
                )
            }
        }
        if (targetIntents.size <= 0) {
            ToastUtils.showLong("没有可以分享的应用")
            return
        }
        val chooser = Intent.createChooser(targetIntents.removeAt(targetIntents.size - 1), "选择分享") ?: return
        val labeledIntents = targetIntents.toTypedArray()
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, labeledIntents)
        ActivityCompat.startActivity(context,chooser,null)
    }

    /**
     * 分享文字
     */
    fun shareTxt(context: Context,desInfo:String){
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, desInfo)
        intent.type = "text/plain"
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val pm = context.packageManager
        val resInfo = pm.queryIntentActivities(intent, 0)
        val targetIntents = arrayListOf<Intent>()
        for (resolveInfo in resInfo) {
            val activityInfo = resolveInfo.activityInfo
            if (activityInfo.packageName.contains("com.tencent.mm") || activityInfo.packageName.contains("com.tencent.mobileqq")) {
                //过滤掉qq收藏
                if (resolveInfo.loadLabel(pm).toString().contains("QQ收藏")) {
                    continue
                }
                val target = Intent()
                target.action = Intent.ACTION_SEND
                target.component = ComponentName(activityInfo.packageName, activityInfo.name)
                target.putExtra(Intent.EXTRA_TEXT, desInfo)
                target.type = "text/plain"//必须设置，否则选定分享类型后不能跳转界面
                targetIntents.add(LabeledIntent(
                    target,
                    activityInfo.packageName,
                    resolveInfo.loadLabel(pm),
                    resolveInfo.icon
                )
                )
            }
        }
        if (targetIntents.size <= 0) {
            ToastUtils.showLong("没有可以分享的应用")
            return
        }
        val chooser = Intent.createChooser(targetIntents.removeAt(targetIntents.size - 1), "选择分享") ?: return
        val labeledIntents = targetIntents.toTypedArray()
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, labeledIntents)
        chooser.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        ContextCompat.startActivity(context,chooser,null)
    }
   /*
     注意：
     1.在Android 7.0及以上系统，限制了file域的访问，导致进行intent分享的时候，会报错甚至崩溃。我们需要在App启动的时候在Application的onCreate方法中添加如下代码，解除对file域访问的限制：
     if(VERSION.SDK_INT >= 24) {
     Builder builder = new Builder();
     StrictMode.setVmPolicy(builder.build());}
     */
     /*
     2.多张图片的分享
     target.setAction(Intent.ACTION_SEND_MULTIPLE);
     target.putParcelableArrayListExtra(Intent.EXTRA_STREAM, myList);//myList = ArrayList<Uri>
     3.startActivity(intent);
     利用这样的分享方式进行分享时，会出现一个设置默认的选择，选定后，系统默认此类型的分享内容一直使用选定的程序进行
     4.startActivity(Intent.createChooser(intent,"选择分享应用"));
     调用Intent.createChooser()这个方法，此时即使用户之前为这个intent设置了默认，选择界面还是会显示，并且我们可以指定选择界面的标题
    */


}