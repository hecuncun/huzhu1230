package com.jzbn.huzhu1230.ui.home

import BaseActivity
import com.jzbn.huzhu1230.R
import kotlinx.android.synthetic.main.toolbar.*

// Created by hesanwei on 2020/5/31.
class VideoDetailActivity : BaseActivity() {
    override fun attachLayoutRes(): Int = R.layout.activity_video_detail

    override fun initData() {
        toolbar_title.text = resources.getString(R.string.video_content)
    }

    override fun initView() {
    }

    override fun initListener() {
    }
}