package com.jzbn.huzhu1230.ui.fragment

import android.view.View
import com.jzbn.huzhu1230.R
import com.lhzw.bluetooth.base.BaseFragment

// Created by hesanwei on 2020/5/24.
class MineFragment: BaseFragment() {
    override fun attachLayoutRes(): Int = R.layout.fragment_mine

    override fun initView(view: View) {
    }

    override fun initListener() {
    }

    override fun lazyLoad() {
    }

    companion object {
        fun getInstance(): MineFragment {
            return MineFragment()
        }
    }
}