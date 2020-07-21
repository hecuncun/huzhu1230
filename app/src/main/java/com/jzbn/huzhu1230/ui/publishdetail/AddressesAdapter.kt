package com.jzbn.huzhu1230.ui.publishdetail

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.AddressBean
import com.jzbn.huzhu1230.bean.SearchDetailBean
import com.jzbn.huzhu1230.constants.Constant
import com.jzbn.huzhu1230.glide.GlideUtils

/**
 * Created by hecuncun on 2020-5-24
 */
class AddressesAdapter:BaseQuickAdapter<SearchDetailBean.FindClueListBean,BaseViewHolder>(R.layout.item_address_layout) {
    override fun convert(helper: BaseViewHolder, item: SearchDetailBean.FindClueListBean?) {
        item?:return
        helper.setGone(R.id.divider, helper.adapterPosition != (data.size-1))
        helper.setText(R.id.tv_time,item.updatetime.replace(" ","\n"))
        helper.setText(R.id.tv_address,item.area+item.areaDetail)
        helper.addOnClickListener(R.id.iv_img)
        val iv = helper.getView<ImageView>(R.id.iv_img)
        GlideUtils.showAnimation(iv,Constant.BASE_URL+item.photo,R.mipmap.icon_logo)
    }
}