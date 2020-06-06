package com.jzbn.huzhu1230.ui.publishdetail

import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v7.widget.LinearLayoutManager
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MyLocationStyle
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.AddressBean
import com.jzbn.huzhu1230.ui.publish.BaseMapActivity
import com.jzbn.huzhu1230.utils.MapUtil
import com.stx.xhb.xbanner.entity.SimpleBannerInfo
import kotlinx.android.synthetic.main.activity_publish_emergency_detail_layout.*
import kotlinx.android.synthetic.main.toolbar.*

//长期寻人/紧急寻人详情页
class PublishEmergencyDetailActivity : BaseMapActivity() {

    val bannerBeans = listOf<MyBannerInfo>(MyBannerInfo(), MyBannerInfo(), MyBannerInfo())
    private var list = mutableListOf<AddressBean>()

    val mAddressAdapter: AddressesAdapter by lazy {
        AddressesAdapter()
    }

    override fun getMapView(): MapView = map

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
    private var publishType =""
    override fun initView() {
        publishType = intent.getStringExtra("publishType")
        if (publishType=="common"){
            toolbar_title.text= "长期寻人详情"
        }else{
            toolbar_title.text= "紧急寻人详情"
        }

        btn_provide_something.setOnClickListener {
            startActivity(Intent(this, ProvideClueActivity::class.java))
        }
        rv_addresses.apply {
            layoutManager = LinearLayoutManager(this@PublishEmergencyDetailActivity)
            adapter = mAddressAdapter
        }

    }

    //不显示地图的中心  保证能直接定位到对应的经纬度
    override fun getLocationType(): Int  = MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER

    //根据经纬度 设置marker
    override fun setLocation() {
        val latLng = LatLng(39.729906, 116.18348)

        //添加系统默认 marker
        MapUtil.addMarkerToMap(
            aMap!!,
            latLng,
            BitmapFactory.decodeResource(resources, R.mipmap.icon_home_test)
        )
        //将中心移到自己的位置
        val mCameraUpdate = CameraUpdateFactory.newCameraPosition(
            CameraPosition(latLng, 15f, 0f, 0f)
        )

        aMap!!.moveCamera(mCameraUpdate)
    }


    override fun initListener() {
    }

    class MyBannerInfo : SimpleBannerInfo() {
        override fun getXBannerUrl(): Any {
            return R.mipmap.ic_launcher
        }

    }
}