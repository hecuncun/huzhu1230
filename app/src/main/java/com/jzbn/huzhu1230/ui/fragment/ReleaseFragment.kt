package com.jzbn.huzhu1230.ui.fragment

import android.os.Bundle
import android.view.View
import com.amap.api.maps.AMap
import com.jzbn.huzhu1230.R
import com.lhzw.bluetooth.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_release.*

// Created by hesanwei on 2020/5/24.
class ReleaseFragment: BaseFragment() {
    override fun attachLayoutRes(): Int = R.layout.fragment_release
    var aMap: AMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        map.onCreate(savedInstanceState)
    }
    override fun initView(view: View) {

        //初始化地图控制器对象
        if (aMap == null) {
            aMap = map.getMap()
        }
    }

    override fun initListener() {
    }

    override fun lazyLoad() {
    }

    override fun onPause() {
        super.onPause()
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        map.onPause()
    }
    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState!!)
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        map.onSaveInstanceState(outState)
    }
    override fun onDestroy() {
        super.onDestroy()
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        map.onDestroy();
    }

    companion object {
        fun getInstance(): ReleaseFragment {
            return ReleaseFragment()
        }
    }
}