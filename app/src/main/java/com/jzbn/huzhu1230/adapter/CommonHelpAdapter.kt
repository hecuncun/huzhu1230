package com.jzbn.huzhu1230.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.CommonRescueBean
import com.jzbn.huzhu1230.constants.Constant
import com.jzbn.huzhu1230.glide.GlideUtils

// Created by hesanwei on 2020/5/25.
class CommonHelpAdapter: BaseQuickAdapter<CommonRescueBean.DataBean,BaseViewHolder>(R.layout.item_common_help) {
    override fun convert(helper: BaseViewHolder, item: CommonRescueBean.DataBean?) {
       item?:return
        helper.setText(R.id.tvName,item.title)
        val iv = helper.getView<ImageView>(R.id.ivImg)
        GlideUtils.showAnimation(iv,Constant.BASE_URL+item.photo,R.mipmap.ic_launcher)

    }
}