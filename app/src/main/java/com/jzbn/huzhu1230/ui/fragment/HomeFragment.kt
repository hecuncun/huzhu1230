package com.jzbn.huzhu1230.ui.fragment

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.adapter.CommonHelpAdapter
import com.jzbn.huzhu1230.adapter.DailyHelpAdapter
import com.lhzw.bluetooth.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

// Created by hesanwei on 2020/5/24.
class HomeFragment : BaseFragment() {


    private val commonHelpAdapter: CommonHelpAdapter by lazy {
        CommonHelpAdapter()
    }

    private val dailyHelpAdapter: DailyHelpAdapter by lazy {
        DailyHelpAdapter()
    }

    override fun attachLayoutRes(): Int = R.layout.fragment_home

    override fun initView(view: View) {
        initCommonRecyclerView()
        initDailyRecyclerView()
    }

    private fun initCommonRecyclerView(){
        rvCommonHelp.run {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context,4)
            commonHelpAdapter.setNewData(mutableListOf("","","","","","","",""))
            adapter = commonHelpAdapter
            isNestedScrollingEnabled = false
        }
    }

    private fun initDailyRecyclerView(){
        rvDailyHelp.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            dailyHelpAdapter.setNewData(mutableListOf("","","","","","","",""))
            adapter = dailyHelpAdapter
            isNestedScrollingEnabled = false
        }
    }

    override fun initListener() {
    }

    override fun lazyLoad() {
    }

    companion object {
        fun getInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}