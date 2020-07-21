package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.constants.Constant
import com.jzbn.huzhu1230.glide.GlideUtils
import kotlinx.android.synthetic.main.activity_big_image.*

/**
 * Created by heCunCun on 2020/7/21
 */
class BigImageActivity:BaseActivity() {
    override fun attachLayoutRes(): Int= R.layout.activity_big_image

    override fun initData() {

    }

    override fun initView() {
        val path = intent.getStringExtra("path")
        GlideUtils.showPlaceholder(this,iv_pic,Constant.BASE_URL+path,R.mipmap.hz_logo)
    }

    override fun initListener() {

    }
}