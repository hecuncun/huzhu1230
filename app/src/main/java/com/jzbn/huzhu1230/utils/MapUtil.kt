package com.jzbn.huzhu1230.utils

import android.graphics.Bitmap
import com.amap.api.maps.AMap
import com.amap.api.maps.AMap.InfoWindowAdapter
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.services.geocoder.GeocodeQuery
import com.amap.api.services.geocoder.GeocodeSearch

/**
 * Created by hyy on 2020/5/25.
 */
object MapUtil {
    /**
     * @param aMap 地图对象
     * @param latLng 经纬度对象
     * @param bitmap 要显示的图片
     * @return 返回Marker对象 可以对Marker对象继续设置属性
     */
    fun addMarkerToMap(aMap: AMap, latLng: LatLng?, bitmap: Bitmap?): Marker {
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        if (bitmap != null) {
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap))
        }
        return aMap.addMarker(markerOptions)
    }

    fun getLatLonPointFromAddress(
        address: String?,
        geocoderSearch: GeocodeSearch
    ) {
        val geocodeQuery = GeocodeQuery(address, "")
        geocoderSearch.getFromLocationNameAsyn(geocodeQuery)
    }

    /**
     * @param aMap 地图对象
     * @param latLng 经纬度对象
     * @param bitmap 要显示的图片
     * @param infoWindowAdapter
     */
    fun addInfoWindowToMap(
        aMap: AMap,
        latLng: LatLng?,
        bitmap: Bitmap?,
        infoWindowAdapter: InfoWindowAdapter?
    ) {
        val marker = addMarkerToMap(aMap, latLng, bitmap)
        aMap.setInfoWindowAdapter(infoWindowAdapter)
        marker.isInfoWindowEnable = true
        marker.showInfoWindow()
    }
}