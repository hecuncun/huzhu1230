package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.adapter.SecondHelpAdapter
import kotlinx.android.synthetic.main.activity_second_help.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by hecuncun on 2020-5-31
 */
class SecondHelpActivity:BaseActivity() {
    private val mAdapter: SecondHelpAdapter by lazy {
        SecondHelpAdapter()
    }
    override fun attachLayoutRes(): Int = R.layout.activity_second_help

    override fun initData() {

    }

    override fun initView() {
      toolbar_title.text="寻找互助内容"
        initRecyclerView()
    }

    private fun initRecyclerView() {
        rvSecondHelp.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            mAdapter.setNewData(mutableListOf("","","","","","","","","","","","","","",""))
            adapter = mAdapter
            isNestedScrollingEnabled = false
        }
    }

    override fun initListener() {
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val intent = Intent(this,ThirdHelpActivity::class.java)
            startActivity(intent)
        }
    }
}