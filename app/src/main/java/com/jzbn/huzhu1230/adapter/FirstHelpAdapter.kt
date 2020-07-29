package com.jzbn.huzhu1230.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.application.App.Companion.context
import com.jzbn.huzhu1230.bean.DailyRescueBean
import com.jzbn.huzhu1230.constants.Constant
import com.jzbn.huzhu1230.glide.GlideUtils

// Created by hesanwei on 2020/5/25.
class FirstHelpAdapter: BaseQuickAdapter<DailyRescueBean.RowsBean,BaseViewHolder>(R.layout.item_first_help) {
    override fun convert(helper: BaseViewHolder, item: DailyRescueBean.RowsBean?) {
        item?:return
        helper.setText(R.id.tvName,item.title)
        val iv =helper.getView<ImageView>(R.id.ivImg)
        GlideUtils.showPlaceholder(context,iv, Constant.BASE_URL+item.photo,R.mipmap.icon_logo)
    }
}