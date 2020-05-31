package com.jzbn.huzhu1230.ui.home

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jzbn.huzhu1230.R

// Created by hesanwei on 2020/5/31.
class AedAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_aed_list) {
    override fun convert(helper: BaseViewHolder, item: String?) {
        helper.setVisible(R.id.tvDistance, true)
    }
}