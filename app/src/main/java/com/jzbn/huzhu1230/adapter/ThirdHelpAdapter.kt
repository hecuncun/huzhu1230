package com.jzbn.huzhu1230.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.DailyRescueBean

// Created by hesanwei on 2020/5/25.
class ThirdHelpAdapter: BaseQuickAdapter<DailyRescueBean.RowsBean,BaseViewHolder>(R.layout.item_third_help) {
    override fun convert(helper: BaseViewHolder, item: DailyRescueBean.RowsBean?) {
        item?:return
        helper.setText(R.id.tvName,item.title)
    }
}