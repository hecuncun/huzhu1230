package com.jzbn.huzhu1230.ui.publishdetail

import BaseActivity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.AddressBean
import com.jzbn.huzhu1230.bean.CollectionBean
import com.stx.xhb.xbanner.entity.SimpleBannerInfo
import kotlinx.android.synthetic.main.activity_publish_emergency_detail_layout.*
import kotlinx.android.synthetic.main.toolbar.*

//长期寻人/紧急寻人详情页
class PublishEmergencyDetailActivity : BaseActivity() {

    val bannerBeans = listOf<MyBannerInfo>(MyBannerInfo(), MyBannerInfo(), MyBannerInfo())
    private var list = mutableListOf<AddressBean>()

    val mAddressAdapter: AddressesAdapter by lazy {
        AddressesAdapter()
    }

    override fun attachLayoutRes(): Int = R.layout.activity_publish_emergency_detail_layout

    override fun initData() {
        banner.setBannerData(bannerBeans)
        initTestData()
    }

    private fun initTestData() {
        for (i in 1..3) {
            list.add(AddressBean())
            mAddressAdapter.setNewData(list)
        }
    }
    override fun initView() {
        toolbar_title.text= "紧急寻人详情"
        btn_provide_something.setOnClickListener {
            startActivity(Intent(this, ProvideClueActivity::class.java))
        }
        rv_addresses.apply {
            layoutManager = LinearLayoutManager(this@PublishEmergencyDetailActivity)
            adapter = mAddressAdapter
        }
    }

    override fun initListener() {
    }

    class MyBannerInfo : SimpleBannerInfo() {
        override fun getXBannerUrl(): Any {
            return R.mipmap.ic_launcher
        }

    }
}