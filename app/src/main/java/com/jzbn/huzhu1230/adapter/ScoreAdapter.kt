package com.jzbn.huzhu1230.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.MsgListBean
import com.jzbn.huzhu1230.bean.ScoreBean

/**
 * Created by hecuncun on 2020-5-25 56537287c027499eb05a406aa99d35c2
 */
class ScoreAdapter:BaseQuickAdapter<MsgListBean.RowsBean,BaseViewHolder>(R.layout.item_list_score) {
    override fun convert(helper: BaseViewHolder, item: MsgListBean.RowsBean?) {
        item?:return
        helper.setText(R.id.tv_remark1,item.remark1)
        helper.setText(R.id.tv_time,item.createtime)
        helper.setText(R.id.tv_score,"+${item.num} 积分")
    }
}