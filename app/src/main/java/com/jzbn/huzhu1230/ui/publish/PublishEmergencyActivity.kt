package com.jzbn.huzhu1230.ui.publish

import android.graphics.BitmapFactory
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.amap.api.maps.AMap.CancelableCallback
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.LatLngBounds
import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.RegeocodeResult
import com.blankj.utilcode.util.ToastUtils
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.utils.MapUtil
import kotlinx.android.synthetic.main.activity_publish_emergency_layout.*
import kotlinx.android.synthetic.main.toolbar.*


class PublishEmergencyActivity : BaseMapActivity() {
    val mLoseModeArray = arrayOf("步行","汽车","火车")
    val mLosReasonArray = arrayOf("精神疾病","离家出走","怀疑拐卖", "意外走失")
    override fun attachLayoutRes(): Int = R.layout.activity_publish_emergency_layout

    override fun getMapView(): MapView  = map
    override fun initData() {
    }

    override fun initView() {
        toolbar_title.text= resources.getString(R.string.publish_emergency_search)

        et_detail_address.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                MapUtil.getLatLonPointFromAddress("北京市房山区良乡大学城北地铁站", geocoderSearch)

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        ll_lose_mode.setOnClickListener {
            val loseModeDialog = LoseModeDialog()
            loseModeDialog.setOnDialogClickListener(object : LoseModeDialog.OnDialogBtnClickListener{
                override fun onClickReasonPosition(pos: Int) {
                    ToastUtils.showShort(mLoseModeArray[pos])
                }
            })
            loseModeDialog.show(supportFragmentManager, "LoseModeDialog")
        }
        ll_lose_reason.setOnClickListener {
            val loseModeDialog = LoseReasonDialog()
            loseModeDialog.setOnDialogClickListener(object : LoseReasonDialog.OnDialogBtnClickListener{
                override fun onClickReasonPosition(pos: Int) {
                    ToastUtils.showShort(mLosReasonArray[pos])
                }
            })
            loseModeDialog.show(supportFragmentManager, "LoseReasonDialog")

        }
    }

    override fun initListener() {
    }

    override fun onGeocodeSearched(geocodeResult: GeocodeResult?, p1: Int) {
        //根据地理位置描述 搜索出来的结果
        geocodeResult?.apply {
            if (geocodeAddressList.isNotEmpty()) {
                val geocodeAddress = geocodeAddressList[0]
                val latLonPoint = geocodeAddress.latLonPoint
                //经纬度
                val latLng = LatLng(latLonPoint.latitude, latLonPoint.longitude)

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
        }

    }

    override fun onRegeocodeSearched(regeocodeResul: RegeocodeResult?, p1: Int) {
    }
}