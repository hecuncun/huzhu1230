package com.jzbn.huzhu1230.ui.publish

import android.graphics.BitmapFactory
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import cn.qqtheme.framework.entity.City
import cn.qqtheme.framework.entity.County
import cn.qqtheme.framework.entity.Province
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker
import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.RegeocodeResult
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.base.BaseNoDataBean
import com.jzbn.huzhu1230.bean.MyAedDetailBean
import com.jzbn.huzhu1230.bean.PublishResponseBean
import com.jzbn.huzhu1230.constants.Constant
import com.jzbn.huzhu1230.event.RefreshAedEvent
import com.jzbn.huzhu1230.ext.showToast
import com.jzbn.huzhu1230.net.CallbackListObserver
import com.jzbn.huzhu1230.net.CallbackObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import com.jzbn.huzhu1230.picker.AddressPickTask
import com.jzbn.huzhu1230.utils.MapUtil
import com.jzbn.huzhu1230.widget.LoadingView
import kotlinx.android.synthetic.main.activity_publish_aed_layout.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.EventBus

class PublishAedActivity :BaseMapActivity() {
    override fun getMapView(): MapView= map

    override fun attachLayoutRes(): Int = R.layout.activity_publish_aed_layout

    override fun initData() {

    }
    private var loadingView:LoadingView?=null
    private var from=""//从那个页面来
    private var magorid=""
    override fun initView() {
        loadingView= LoadingView(this)
        from = intent.getStringExtra("from")
        if (from=="my"){
            toolbar_title.text="编辑Aed"
            btn_publish.text="编辑信息"
            magorid = intent.getStringExtra("magorid")
            val myAedDetailCall = SLMRetrofit.getInstance().api.myAedDetailCall(magorid)
            loadingView?.show()
            myAedDetailCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackObserver<MyAedDetailBean>(){
                override fun onSucceed(t: MyAedDetailBean, desc: String?) {
                    //设置数据
                    et_name.setText(t.name)
                    et_phone_number.setText(t.phone)
                    area=t.area
                    areaDetail=t.areaDetail
                    longitude=t.longitude
                    latitude=t.latitude

                    tv_address.text=t.area
                    et_detail_address.setText(t.areaDetail)

                    loadingView?.dismiss()
                }

                override fun onFailed() {
                    loadingView?.dismiss()
                }
            })


        }else{
            btn_publish.text="发布信息"
            toolbar_title.text= resources.getString(R.string.publish_aed)
        }

    }

    override fun initListener() {
        ll_select_address.setOnClickListener {
            showCityPickerDialog()
        }
        et_detail_address.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                areaDetail=s.toString().trim()
                val address = area+areaDetail
                MapUtil.getLatLonPointFromAddress(address, geocoderSearch)

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        btn_publish.setOnClickListener {
            //发布Aed
            val name = et_name.text.toString().trim()
            val phone= et_phone_number.text.toString().trim()
            if (from=="my"){//编辑
                loadingView?.setLoadingTitle("修改中...")
                val updateMyAedCall = SLMRetrofit.getInstance().api.updateMyAedCall(
                    uid,
                    magorid,
                    name,
                    area,
                    areaDetail,
                    longitude,
                    latitude,
                    phone
                )
                updateMyAedCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackListObserver<BaseNoDataBean>(){
                    override fun onSucceed(t: BaseNoDataBean?) {
                        loadingView?.dismiss()
                        finish()
                        showToast("修改成功")
                        EventBus.getDefault().post(RefreshAedEvent())
                    }

                    override fun onFailed() {
                        loadingView?.dismiss()
                    }
                })

            }else{//发布Aed

                if (name.isNotEmpty() && area.isNotEmpty() && areaDetail.isNotEmpty() && longitude.isNotEmpty() && latitude.isNotEmpty() && phone.isNotEmpty()){
                    //发布
                    loadingView?.setLoadingTitle("发布中...")
                    val publishAedCall = SLMRetrofit.getInstance().api.publishAedCall(
                        uid,
                        name,
                        area,
                        areaDetail,
                        longitude,
                        latitude,
                        phone
                    )

                    publishAedCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackListObserver<PublishResponseBean>(){
                        override fun onSucceed(t: PublishResponseBean) {
                            if(t.code==Constant.SUCCESSED_CODE){
                                loadingView?.dismiss()
                                showToast("发布成功")
                                finish()
                            }else{
                                loadingView?.dismiss()
                                showToast(t.message)
                            }
                        }

                        override fun onFailed() {
                            loadingView?.dismiss()
                        }
                    })
                }else{
                    showToast("请把信息填写完整")
                }

            }


        }
    }
    /**
     * 显示城市选择器
     */
    private var area =""
    private var areaDetail=""
    private var longitude=""
    private var latitude=""
    private fun showCityPickerDialog() {
        val task = AddressPickTask(this)
        task.setHideProvince(false)
        task.setHideCounty(false)
        task.setCallback(object : AddressPickTask.Callback {
            override fun onAddressInitFailed() {
                showToast("数据初始化失败")
            }

            override fun onAddressPicked(province: Province, city: City, county: County?) {
                if (county == null) {
                    showToast(province.areaName + city.areaName)
                    tv_address.text=province.areaName + city.areaName
                    area=province.areaName + city.areaName
                } else {
                    showToast(province.areaName + city.areaName + county.areaName)
                    tv_address.text=province.areaName + city.areaName + county.areaName
                    area=province.areaName + city.areaName+county.areaName
                }
            }

        })
        task.execute("北京", "北京", "昌平")
    }
    var infoWindow: View? = null
    override fun onGeocodeSearched(geocodeResult: GeocodeResult?, p1: Int) {
        //根据地理位置描述 搜索出来的结果
        geocodeResult?.apply {
            if (geocodeAddressList.isNotEmpty()) {
                val geocodeAddress = geocodeAddressList[0]
                val latLonPoint = geocodeAddress.latLonPoint
                //经纬度
                val latLng = LatLng(latLonPoint.latitude, latLonPoint.longitude)
                latitude=latLonPoint.latitude.toString()
                longitude=latLonPoint.longitude.toString()
                Log.d(
                    "HAHAHHAHA",
                    "onRegeocodeSearched: City" + geocodeAddress.city
                )
                Log.d(
                    "HAHAHHAHA",
                    "onRegeocodeSearched: latitude" + latLonPoint.latitude
                )
                Log.d(
                    "HAHAHHAHA",
                    "onRegeocodeSearched: longitude" +  latLonPoint.longitude
                )
                MapUtil.addInfoWindowToMap(aMap!!, LatLng( latLonPoint.latitude,latLonPoint.longitude),BitmapFactory.decodeResource(resources, R.mipmap.icon_location_yellow),object :
                    AMap.InfoWindowAdapter{
                    override fun getInfoContents(p0: Marker?): View?{
                        return null
                    }

                    override fun getInfoWindow(p0: Marker?): View {
                        if (infoWindow==null){
                            infoWindow = LayoutInflater.from(this@PublishAedActivity).inflate(R.layout.custom_info_window, null)
                            val tvInfo =
                                infoWindow!!.findViewById<TextView>(R.id.tv_info)
                            tvInfo.text="标记这里"
                        }
                        return infoWindow!!

                    }
                })
                //添加系统默认 marker
//                MapUtil.addMarkerToMap(
//                    aMap!!,
//                    latLng,
//                    BitmapFactory.decodeResource(resources, R.mipmap.icon_location_yellow)
//                )
                //将中心移到自己的位置
                val mCameraUpdate = CameraUpdateFactory.newCameraPosition(
                    CameraPosition(latLng, 15f, 0f, 0f)
                )

                aMap!!.moveCamera(mCameraUpdate)
            }
        }

    }

    override fun onRegeocodeSearched(regeocodeResul: RegeocodeResult?, p1: Int) {
    }

}