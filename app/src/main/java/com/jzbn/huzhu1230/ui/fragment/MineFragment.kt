package com.jzbn.huzhu1230.ui.fragment

import android.content.Intent
import android.view.View
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.ui.activity.MyInfoActivity
import com.jzbn.huzhu1230.ui.activity.MyPublishActivity
import com.jzbn.huzhu1230.ui.activity.MyScoreActivity
import com.lhzw.bluetooth.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_mine.*

// Created by hesanwei on 2020/5/24.
class MineFragment: BaseFragment() {
    override fun attachLayoutRes(): Int = R.layout.fragment_mine

    override fun initView(view: View) {
    }

    override fun initListener() {
        iv_edit_info.setOnClickListener {
            startActivity(Intent(activity, MyInfoActivity::class.java))
        }
        ll_my_score.setOnClickListener {
            startActivity(Intent(activity, MyScoreActivity::class.java))
        }
        ll_my_publish.setOnClickListener {
            startActivity(Intent(activity, MyPublishActivity::class.java))
        }
    }

    override fun lazyLoad() {
    }

    companion object {
        fun getInstance(): MineFragment {
            return MineFragment()
        }
    }
}