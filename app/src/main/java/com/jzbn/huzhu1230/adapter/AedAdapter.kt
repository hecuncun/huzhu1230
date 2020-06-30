package com.jzbn.huzhu1230.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.AedBean

/**
 * Created by hecuncun on 2020-5-24
 */
class AedAdapter:BaseQuickAdapter<AedBean.RowsBean,BaseViewHolder>(R.layout.item_aed_list) {
    override fun convert(helper: BaseViewHolder, item: AedBean.RowsBean?) {
        item?:return
        helper.setGone(R.id.tvDistance,false)
        helper.setText(R.id.tvAddress,item.area+item.areaDetail)
        helper.setText(R.id.tv_create_time,"发布于${item.createtime}")
    }
}