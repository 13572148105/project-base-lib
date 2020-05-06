package com.orz.im.globallib.helper

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.orz.im.globallib.base.BaseViewModel
import com.orz.im.globallib.common.ViewModelFactory


/**
 * Created by Orz on .
 * ViewModelProvider 提供帮助类
 */
object ViewModelProviderHelper {

    private val viewModelFactory: ViewModelProvider.NewInstanceFactory by lazy { ViewModelFactory() }

     fun<T: BaseViewModel> buildViewModel(owner: ViewModelStoreOwner, modelClass:Class<T>): T{
        return ViewModelProvider(owner, viewModelFactory).get(modelClass)
    }
}