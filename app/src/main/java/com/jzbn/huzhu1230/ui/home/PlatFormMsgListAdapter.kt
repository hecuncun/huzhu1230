package com.jzbn.huzhu1230.ui.home

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.MsgBean

// Created by hesanwei on 2020/5/31.
class PlatFormMsgListAdapter: BaseQuickAdapter<MsgBean.RowsBean,BaseViewHolder>(R.layout.platform_message_list) {
    override fun convert(helper: BaseViewHolder, item: MsgBean.RowsBean?) {
          item?:return
          helper.setText(R.id.tvTime,item.createtime?:"")
          helper.setText(R.id.tv_content,item.content)
    }
}