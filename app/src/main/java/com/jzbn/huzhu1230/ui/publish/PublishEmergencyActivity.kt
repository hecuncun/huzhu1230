package com.jzbn.huzhu1230.ui.publish

import BaseActivity
import com.jzbn.huzhu1230.R
import kotlinx.android.synthetic.main.toolbar.*

class PublishEmergencyActivity : BaseActivity() {
    override fun attachLayoutRes(): Int = R.layout.activity_publish_emergency_layout

    override fun initData() {
    }

    override fun initView() {
        toolbar_title.text= resources.getString(R.string.publish_emergency_search)
    }

    override fun initListener() {
    }

}