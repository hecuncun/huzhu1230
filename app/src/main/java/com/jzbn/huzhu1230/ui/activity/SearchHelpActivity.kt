package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.adapter.FirstHelpAdapter
import kotlinx.android.synthetic.main.activity_search_help.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by hecuncun on 2020-5-31
 */
class SearchHelpActivity:BaseActivity() {
    private val mAdapter: FirstHelpAdapter by lazy {
        FirstHelpAdapter()
    }
    override fun attachLayoutRes(): Int = R.layout.activity_search_help

    override fun initData() {

    }

    override fun initView() {
        toolbar_title.text="寻找互助内容"
        initDailyRecyclerView()
    }
    private fun initDailyRecyclerView(){
        rvDailyHelp.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            mAdapter.setNewData(mutableListOf("","","","","","","","","","","","","","",""))
            adapter = mAdapter
            isNestedScrollingEnabled = false
        }
}

    override fun initListener() {
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val intent =Intent(this@SearchHelpActivity,SecondHelpActivity::class.java)
            startActivity(intent)
        }
    }
}