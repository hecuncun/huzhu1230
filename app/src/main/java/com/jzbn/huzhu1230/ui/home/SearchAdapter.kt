package com.jzbn.huzhu1230.ui.home

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.KnowledgeBean
import com.jzbn.huzhu1230.constants.Constant
import com.jzbn.huzhu1230.glide.GlideUtils

// Created by hesanwei on 2020/5/31.
class SearchAdapter: BaseQuickAdapter<KnowledgeBean.RowsBean,BaseViewHolder>(R.layout.item_search) {
    override fun convert(helper: BaseViewHolder, item: KnowledgeBean.RowsBean?) {
        item?:return
        helper.setGone(R.id.ivPlay,item.path.isNullOrEmpty())//没有视频地址就隐藏播放按钮
        helper.setText(R.id.tvTitle,item.title)
        helper.setText(R.id.tvTime,item.createtime.split(" ")[0])
        val iv = helper.getView<ImageView>(R.id.ivImg)
        GlideUtils.showAnimation(iv, Constant.BASE_URL+item.photo,R.mipmap.ic_launcher)
    }
}