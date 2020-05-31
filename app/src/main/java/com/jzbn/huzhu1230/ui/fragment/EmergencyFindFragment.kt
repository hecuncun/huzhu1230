package com.jzbn.huzhu1230.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.adapter.EmergencyFindAdapter
import com.jzbn.huzhu1230.bean.EmergencyFindBean
import com.lhzw.bluetooth.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_emergency_find.*


class EmergencyFindFragment : BaseFragment() {
    private var list= mutableListOf<EmergencyFindBean>()
    private val emergencyFindAdapter:EmergencyFindAdapter by lazy {
        EmergencyFindAdapter()
    }
    override fun attachLayoutRes(): Int = R.layout.fragment_emergency_find

    override fun initView(view: View) {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView.run {
            layoutManager = LinearLayoutManager(activity)
            adapter =emergencyFindAdapter
        }
    }

    override fun initListener() {
    }

    override fun lazyLoad() {

        initTestData()
    }

    private fun initTestData() {
        for (i in 1..20){
            list.add(EmergencyFindBean())
            emergencyFindAdapter.setNewData(list)
        }
    }

    companion object {
        fun getInstance(): EmergencyFindFragment {
            return EmergencyFindFragment()
        }
    }
}