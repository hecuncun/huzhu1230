package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import com.jzbn.huzhu1230.R
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by hecuncun on 2020-5-24
 */
class ScoreListActivity : BaseActivity() {
    override fun attachLayoutRes(): Int = R.layout.activity_score_list

    override fun initData() {

    }

    override fun initView() {
        toolbar_title.text = "积分明细"
    }

    override fun initListener() {

    }
}