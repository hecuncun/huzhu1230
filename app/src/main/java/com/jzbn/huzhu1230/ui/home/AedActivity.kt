package com.jzbn.huzhu1230.ui.home

import BaseActivity
import android.support.v7.widget.LinearLayoutManager
import com.jzbn.huzhu1230.R
import kotlinx.android.synthetic.main.activity_aed.*

// Created by hesanwei on 2020/5/31.
class AedActivity : BaseActivity() {

    private val aedAdapter: AedAdapter by lazy {
        AedAdapter()
    }

    override fun attachLayoutRes(): Int = R.layout.activity_aed

    override fun initData() {
        val dataList = mutableListOf<String>()
        for (i in 0..20) {
            dataList.add("数据$i")
        }
        aedAdapter.setNewData(dataList)
    }

    override fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView?.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@AedActivity)
            adapter = aedAdapter
        }
    }

    override fun initListener() {
    }
}