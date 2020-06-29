package com.jzbn.huzhu1230.ui.home

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.NearAedBean

// Created by hesanwei on 2020/5/31.
class AedAdapter : BaseQuickAdapter<NearAedBean.DataBean, BaseViewHolder>(R.layout.item_aed_list) {
    override fun convert(helper: BaseViewHolder, item: NearAedBean.DataBean?) {
        item?:return
        helper.setVisible(R.id.tvDistance, true)
        helper.setText(R.id.tvAddress,item.area+item.areaDetail)
        helper.setText(R.id.tv_create_time,"发布于${item.createtime}")
        helper.setText(R.id.tvDistance,if (item.distance.toDouble()<1000) item.distance+"m" else (item.distance.toDouble()/1000).toString() +"km")
    }
}