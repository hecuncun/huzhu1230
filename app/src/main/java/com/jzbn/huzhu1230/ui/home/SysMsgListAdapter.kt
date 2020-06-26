package com.jzbn.huzhu1230.ui.home

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.MsgBean
import com.jzbn.huzhu1230.bean.SysMsgBean

// Created by hesanwei on 2020/5/31.
class SysMsgListAdapter: BaseQuickAdapter<SysMsgBean.RowsBean,BaseViewHolder>(R.layout.sys_message_list) {
    override fun convert(helper: BaseViewHolder, item: SysMsgBean.RowsBean?) {
          item?:return
          helper.setText(R.id.tvTime,item.createtime?:"")
          helper.setText(R.id.tv_content,item.remark1)
    }
}