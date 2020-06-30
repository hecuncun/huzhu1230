package com.jzbn.huzhu1230.ui.home

import android.graphics.BitmapFactory
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.amap.api.location.AMapLocationClient
import com.amap.api.maps.AMap
import com.amap.api.maps.MapView
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.NearAedBean
import com.jzbn.huzhu1230.constants.Constant
import com.jzbn.huzhu1230.net.CallbackListObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import com.jzbn.huzhu1230.ui.publish.BaseMapActivity
import com.jzbn.huzhu1230.utils.MapUtil
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_aed.*
import kotlinx.android.synthetic.main.toolbar.*

// Created by hesanwei on 2020/5/31.
class AedActivity : BaseMapActivity() {

    private val aedAdapter: AedAdapter by lazy {
        AedAdapter()
    }

    override fun getMapView(): MapView = map

    override fun attachLayoutRes(): Int = R.layout.activity_aed

    override fun initData() {
        toolbar_title.text = resources.getString(R.string.nearby_aed)
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

    private var aedList = mutableListOf<NearAedBean.DataBean>()
    var infoWindow: View? = null
    override fun setLocation() {
        //开始定位
        val mLocationClient = AMapLocationClient(applicationContext)
        mLocationClient.setLocationListener {
            if (it != null) {
                if (it.errorCode == 0) {
                    mLocationClient.stopLocation()
                    Logger.e("经纬度==${it.longitude},${it.latitude}")
                    //定位成功获取列表
                    val searchNearAedList = SLMRetrofit.getInstance().api.searchNearAedList(
                        it.longitude.toString(),
                        it.latitude.toString()
                    )
                    searchNearAedList.compose(ThreadSwitchTransformer())
                        .subscribe(object : CallbackListObserver<NearAedBean>() {
                            override fun onSucceed(t: NearAedBean) {
                                if (t.code == Constant.SUCCESSED_CODE) {
                                    aedList.addAll(t.data)
                                    aedAdapter.setNewData(aedList)
                                    for (item in aedList){
                                       // MapUtil.addMarkerToMap(aMap!!, LatLng(item.latitude.toDouble(),item.longitude.toDouble()), BitmapFactory.decodeResource(resources, R.mipmap.icon_location_yellow))
                                        MapUtil.addInfoWindowToMap(aMap!!, LatLng(item.latitude.toDouble(),item.longitude.toDouble()),BitmapFactory.decodeResource(resources, R.mipmap.icon_location_yellow),object :AMap.InfoWindowAdapter{
                                            override fun getInfoContents(p0: Marker?): View?{
                                                return null
                                            }

                                            override fun getInfoWindow(p0: Marker?): View {
                                                if (infoWindow==null){
                                                    infoWindow = LayoutInflater.from(this@AedActivity).inflate(R.layout.custom_info_window, null)
                                                    val tvInfo =
                                                        infoWindow!!.findViewById<TextView>(R.id.tv_info)
                                                    tvInfo.text="AED"
                                                }
                                                return infoWindow!!

                                            }
                                        })
                                    }
                                }
                            }

                            override fun onFailed() {

                            }
                        })
                } else {
                    Logger.e("${it.errorCode}，${it.errorInfo}")
                }
            }
        }
        mLocationClient.startLocation()
    }

    override fun initListener() {
    }
}