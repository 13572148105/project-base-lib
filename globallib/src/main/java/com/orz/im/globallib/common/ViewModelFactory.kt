package com.orz.im.globallib.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 *  ViewModelFactory
 */
class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.newInstance()
    }
}