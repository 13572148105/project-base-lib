package com.orz.im.globallib.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 *Created by Orz on 2019/10/22.
 *Describe:TabFragmentAdapter
 * 配合ViewPager2使用
 */
class BaseVpFragmentAdapter(fragmentActivity:FragmentActivity, private val fragments:List<Fragment>): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}