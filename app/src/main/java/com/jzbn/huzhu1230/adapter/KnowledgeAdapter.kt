package com.jzbn.huzhu1230.adapter

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.KnowledgeBean
import com.jzbn.huzhu1230.constants.Constant
import com.jzbn.huzhu1230.glide.GlideUtils

/**
 * Created by hecuncun on 2020-5-30
 */
class KnowledgeAdapter :BaseQuickAdapter<KnowledgeBean.RowsBean,BaseViewHolder>(R.layout.item_knowledge_list){
    override fun convert(helper: BaseViewHolder, item: KnowledgeBean.RowsBean?) {
        item?:return
        helper.setText(R.id.tv_title,item.title)
        helper.setText(R.id.tv_time,item.createtime.split(" ")[0])
        val ivPic = helper.getView<ImageView>(R.id.iv_pic)
        GlideUtils.showAnimation(ivPic,Constant.BASE_URL+item.photo,R.mipmap.icon_logo)
        val ivPlay = helper.getView<ImageView>(R.id.iv_play)
        ivPlay.visibility=if (item.path.isNullOrEmpty()) View.GONE else View.VISIBLE
    }
}