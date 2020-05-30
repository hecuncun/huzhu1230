package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import com.jzbn.huzhu1230.R
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by hecuncun on 2020-5-25
 */
class MyXinYuActivity:BaseActivity() {
    override fun attachLayoutRes(): Int = R.layout.activity_my_xin_yu

    override fun initData() {

    }

    override fun initView() {
       toolbar_title.text="信誉值"
    }

    override fun initListener() {

    }
}