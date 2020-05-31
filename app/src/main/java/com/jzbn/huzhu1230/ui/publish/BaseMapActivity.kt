package com.jzbn.huzhu1230.ui.publish

import BaseActivity
import android.location.Location
import android.os.Bundle
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.GeocodeSearch
import com.amap.api.services.geocoder.RegeocodeResult


abstract class BaseMapActivity : BaseActivity(), AMap.OnMyLocationChangeListener,
    GeocodeSearch.OnGeocodeSearchListener {

    private val mMapView: MapView by lazy {
        getMapView()
    }

    protected var aMap: AMap? = null
    protected lateinit var myLocationStyle: MyLocationStyle
    protected lateinit var geocoderSearch: GeocodeSearch

    abstract fun getMapView(): MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMapView.onCreate(savedInstanceState)
        initMapView()
    }

    private fun initMapView() {
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mMapView.map
        }
        geocoderSearch = GeocodeSearch(this)
        geocoderSearch.setOnGeocodeSearchListener(this)
        aMap!!.setOnMyLocationChangeListener(this)
        myLocationStyle =
            MyLocationStyle() //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。

        //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.interval(2000)
        myLocationStyle.showMyLocation(true)
        //连续定位、蓝点移动到地图中心点，并且蓝点会跟随设备移动。
        myLocationStyle.myLocationType(getLocationType())

        aMap!!.myLocationStyle = myLocationStyle //设置定位蓝点的Style
        //设置地图缩放界别
        //设置我的位置 现实的图标
        //myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_round)));
        //设置地图缩放界别
        aMap!!.moveCamera(CameraUpdateFactory.zoomTo(14f))
        //设置默认定位按钮是否显示，非必需设置。
//        aMap!!.uiSettings.isMyLocationButtonEnabled = true
        aMap!!.isMyLocationEnabled = true // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        setLocation()
    }

    open fun setLocation() {

    }

    open fun getLocationType() =
        MyLocationStyle.LOCATION_TYPE_LOCATE

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState)
    }

    override fun onMyLocationChange(location: Location?) {

    }

    //通过地址描述得来的结果
    override fun onGeocodeSearched(geocodeResult: GeocodeResult?, p1: Int) {

    }

    override fun onRegeocodeSearched(regeocodeResul: RegeocodeResult?, p1: Int) {

    }
}