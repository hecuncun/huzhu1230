package com.jzbn.huzhu1230.ui.home

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.amap.api.maps.AMap
import com.amap.api.maps.MapView
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.NearAedBean
import com.jzbn.huzhu1230.ui.publish.BaseMapActivity
import com.jzbn.huzhu1230.utils.MapUtil
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_aed_detail.*
import kotlinx.android.synthetic.main.toolbar.*


/**
 * Created by hecuncun on 2020/7/12
 */
class AedDetailActivity :BaseMapActivity() {
    override fun getMapView(): MapView=map
    override fun attachLayoutRes(): Int = R.layout.activity_aed_detail
    var infoWindow: View? = null
    private var aedBean:NearAedBean.DataBean?=null
    override fun initData() {
        aedBean = intent.getParcelableExtra<NearAedBean.DataBean>("bean")
        et_name.text=aedBean?.name
        tv_address.text=aedBean?.area
        et_detail_address.text=aedBean?.areaDetail
        et_phone_number.text=aedBean?.phone
        Logger.e("aedBean==$aedBean")
        Logger.e("aMap==$aMap")

    }

    override fun setLocation() {
        // MapUtil.addMarkerToMap(aMap!!, LatLng(item.latitude.toDouble(),item.longitude.toDouble()), BitmapFactory.decodeResource(resources, R.mipmap.icon_location_yellow))
            MapUtil.addInfoWindowToMap(aMap!!, LatLng(aedBean?.latitude!!.toDouble(),aedBean?.longitude!!.toDouble()),
                BitmapFactory.decodeResource(resources, R.mipmap.icon_location_yellow),object :
                    AMap.InfoWindowAdapter{
                override fun getInfoContents(p0: Marker?): View?{
                    return null
                }

                override fun getInfoWindow(p0: Marker?): View {
                    if (infoWindow==null){
                        infoWindow = LayoutInflater.from(this@AedDetailActivity).inflate(R.layout.custom_info_window, null)
                        val tvInfo =
                            infoWindow!!.findViewById<TextView>(R.id.tv_info)
                        tvInfo.text="AED"
                    }
                    return infoWindow!!

                }
            })
    }

    override fun initView() {
       toolbar_title.text="AED详情"
    }

    override fun initListener() {

    }
}