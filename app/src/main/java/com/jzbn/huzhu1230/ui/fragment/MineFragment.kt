package com.jzbn.huzhu1230.ui.fragment

import android.content.Intent
import android.view.View
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.glide.GlideUtils
import com.jzbn.huzhu1230.ui.activity.*
import com.jzbn.huzhu1230.ui.home.MessageActivity
import com.lhzw.bluetooth.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_mine.*

// Created by hesanwei on 2020/5/24.
class MineFragment: BaseFragment() {
    override fun attachLayoutRes(): Int = R.layout.fragment_mine

    override fun initView(view: View) {
        tv_nick_name.text=nickname
        GlideUtils.showCircleWithBorder(iv_head_pic,photoPath,R.mipmap.icon_head_def,resources.getColor(R.color.white))

    }

    override fun initListener() {
        iv_msg.setOnClickListener {
            startActivity(Intent(activity, MessageActivity::class.java))
        }
        iv_edit_info.setOnClickListener {
            startActivity(Intent(activity, MyInfoActivity::class.java))
        }
        ll_my_info.setOnClickListener {
            startActivity(Intent(activity, MyInfoActivity::class.java))
        }
        ll_my_score.setOnClickListener {
            startActivity(Intent(activity, MyScoreActivity::class.java))
        }
        ll_my_publish.setOnClickListener {
            startActivity(Intent(activity, MyPublishActivity::class.java))
        }
        ll_setting.setOnClickListener {
            startActivity(Intent(activity, MySettingActivity::class.java))
        }
        rl_my_xin_yu.setOnClickListener {
            startActivity(Intent(activity, MyXinYuActivity::class.java))
        }
        ll_my_honor.setOnClickListener {
            startActivity(Intent(activity, MyHonorActivity::class.java))
        }
        ll_my_collection.setOnClickListener {
            startActivity(Intent(activity, MyCollectionActivity::class.java))
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