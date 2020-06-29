package com.jzbn.huzhu1230.ui.home

import android.location.Location
import android.support.v7.widget.LinearLayoutManager
import com.amap.api.maps.MapView
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.ui.publish.BaseMapActivity
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_aed.*
import kotlinx.android.synthetic.main.toolbar.*

// Created by hesanwei on 2020/5/31.
class AedActivity : BaseMapActivity() {

    private val aedAdapter: AedAdapter by lazy {
        AedAdapter()
    }

    override fun getMapView(): MapView =map

    override fun attachLayoutRes(): Int = R.layout.activity_aed

    override fun initData() {
        toolbar_title.text = resources.getString(R.string.nearby_aed)
        val dataList = mutableListOf<String>()
        for (i in 0..20) {
            dataList.add("数据$i")
        }
        aedAdapter.setNewData(dataList)
    }

    override fun onMyLocationChange(location: Location?) {

        //拿到经纬度
        Logger.e("经纬度=${location?.longitude},${location?.latitude}")

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