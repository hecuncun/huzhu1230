package com.jzbn.huzhu1230.ui.fragment

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.adapter.CommonHelpAdapter
import com.jzbn.huzhu1230.adapter.DailyHelpAdapter
import com.jzbn.huzhu1230.ui.home.AedActivity
import com.jzbn.huzhu1230.ui.home.SignDialog
import com.jzbn.huzhu1230.ui.activity.SecondHelpActivity
import com.jzbn.huzhu1230.ui.home.MessageActivity
import com.lhzw.bluetooth.base.BaseFragment
import kotlinx.android.synthetic.main.dialog_sign.*
import kotlinx.android.synthetic.main.fragment_home.*

// Created by hesanwei on 2020/5/24.
class HomeFragment : BaseFragment(), View.OnClickListener {


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
        initViewClick()
    }

    private fun initViewClick() {
        ivSign.setOnClickListener(this)
        ivAed.setOnClickListener(this)
        ivMessage.setOnClickListener(this)
        tvSearchContent.setOnClickListener(this)
    }

    private fun initCommonRecyclerView() {
        rvCommonHelp.run {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 4)
            commonHelpAdapter.setNewData(mutableListOf("", "", "", "", "", "", "", ""))
            adapter = commonHelpAdapter
            isNestedScrollingEnabled = false
        }
    }

    private fun initDailyRecyclerView() {
        rvDailyHelp.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            dailyHelpAdapter.setNewData(mutableListOf("", "", "", "", "", "", "", ""))
            adapter = dailyHelpAdapter
            isNestedScrollingEnabled = false
        }
    }

    override fun initListener() {
        commonHelpAdapter.setOnItemClickListener { adapter, view, position ->
            val intent = (Intent(activity, SecondHelpActivity::class.java))
            startActivity(intent)
        }
        dailyHelpAdapter.setOnItemClickListener { adapter, view, position ->
            val intent = (Intent(activity, SecondHelpActivity::class.java))
            startActivity(intent)
        }
    }

    override fun lazyLoad() {
    }

    companion object {
        fun getInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.ivSign -> {
                SignDialog.newInstance(0).show(activity?.supportFragmentManager, "sign")
            }

            R.id.ivAed -> {
                startActivity(Intent(context, AedActivity::class.java))
            }

            R.id.ivMessage -> {
                startActivity(Intent(context, MessageActivity::class.java))
            }

            R.id.tvSearchContent -> {

            }
        }
    }
}