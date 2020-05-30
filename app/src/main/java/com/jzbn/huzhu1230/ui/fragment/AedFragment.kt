package com.jzbn.huzhu1230.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.adapter.AedAdapter
import com.jzbn.huzhu1230.bean.AedBean
import com.lhzw.bluetooth.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_aed.*


class AedFragment : BaseFragment() {
    private var list = mutableListOf<AedBean>()
    private val aedAdapter:AedAdapter by lazy {
        AedAdapter()
    }
    override fun attachLayoutRes(): Int = R.layout.fragment_aed

    override fun initView(view: View) {
        initRecyclerView()
    }
    private fun initRecyclerView() {
        recyclerView.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = aedAdapter
        }
    }
    override fun initListener() {
    }

    override fun lazyLoad() {
        initTestData()
    }
    private fun initTestData() {
        for (i in 1..20) {
            list.add(AedBean())
            aedAdapter.setNewData(list)
        }
    }
    companion object {
        fun getInstance(): AedFragment {
            return AedFragment()
        }
    }
}