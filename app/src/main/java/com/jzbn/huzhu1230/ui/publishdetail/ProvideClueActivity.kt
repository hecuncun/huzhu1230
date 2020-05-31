package com.jzbn.huzhu1230.ui.publishdetail

import BaseActivity
import com.jzbn.huzhu1230.R
import kotlinx.android.synthetic.main.toolbar.*

//提供线索 页面
class ProvideClueActivity : BaseActivity(){
    override fun attachLayoutRes(): Int = R.layout.activity_provide_clue_layout

    override fun initData() {
    }

    override fun initView() {
        toolbar_title.text = "提供线索"
    }

    override fun initListener() {
    }
}