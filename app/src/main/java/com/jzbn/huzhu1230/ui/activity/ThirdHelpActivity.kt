package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.support.v7.widget.LinearLayoutManager
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.adapter.ThirdHelpAdapter
import kotlinx.android.synthetic.main.activity_third_help.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by hecuncun on 2020-5-31
 */
class ThirdHelpActivity :BaseActivity() {
    private val mAdapter: ThirdHelpAdapter by lazy {
        ThirdHelpAdapter()
    }
    override fun attachLayoutRes(): Int = R.layout.activity_third_help
    override fun initData() {

    }

    override fun initView() {
        toolbar_title.text="寻找互助内容"
        initRecyclerView()
    }

    private fun initRecyclerView() {
        rvThirdHelp.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            mAdapter.setNewData(mutableListOf("","","","","","","","","","","","","","",""))
            adapter = mAdapter
            isNestedScrollingEnabled = false
        }
    }

    override fun initListener() {
        mAdapter.setOnItemClickListener { adapter, view, position ->
          //  val intent = Intent(this,ThirdHelpActivity::class.java)
         //   startActivity(intent)
        }
    }
}