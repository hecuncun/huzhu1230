package com.jzbn.huzhu1230.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.SearchPersonBean
import com.jzbn.huzhu1230.constants.Constant
import com.jzbn.huzhu1230.glide.GlideUtils

/**
 * Created by hecuncun on 2020-5-24
 */
class EmergencyFindAdapter:BaseQuickAdapter<SearchPersonBean.RowsBean,BaseViewHolder>(R.layout.item_emergency_list) {
    override fun convert(helper: BaseViewHolder, item: SearchPersonBean.RowsBean?) {
        item?:return
        helper.setText(R.id.tv_name,item.createUserName)
        helper.setText(R.id.tv_create_time,"发布于${item.createtime}")
        helper.setText(R.id.tv_duration,"历时${item.duration}")
        helper.setText(R.id.tv_address,"${item.area}${item.areaDetail}")
        helper.setText(R.id.tv_numView,"关注人数${item.numView}")

        val ivHead =helper.getView<ImageView>(R.id.iv_head_pic)
        val ivPhoto = helper.getView<ImageView>(R.id.iv_photo)
        GlideUtils.showCircle(ivHead,Constant.BASE_URL+item.createUserPhoto,R.mipmap.icon_head_def)
        GlideUtils.showAnimation(ivPhoto,Constant.BASE_URL+item.photo.split(",")[0],R.mipmap.icon_logo)

    }
}