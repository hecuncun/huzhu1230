package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.support.v7.widget.LinearLayoutManager
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.adapter.ScoreAdapter
import com.jzbn.huzhu1230.bean.ScoreBean
import kotlinx.android.synthetic.main.fragment_emergency_find.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by hecuncun on 2020-5-24
 */
class ScoreListActivity : BaseActivity() {
    private var list= mutableListOf<ScoreBean>()
    private val scoreAdapter:ScoreAdapter by lazy {
        ScoreAdapter()
    }
    override fun attachLayoutRes(): Int = R.layout.activity_score_list

    override fun initData() {
        initTestData()
    }
    private fun initTestData() {
        for (i in 1..20) {
            list.add(ScoreBean())
            scoreAdapter.setNewData(list)
        }
    }
    override fun initView() {
        toolbar_title.text = "积分明细"
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView.run {
            layoutManager = LinearLayoutManager(this@ScoreListActivity)
            adapter = scoreAdapter
        }
    }

    override fun initListener() {

    }
}