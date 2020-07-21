package com.jzbn.huzhu1230.ui.publishdetail

import android.view.View
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
        helper.addOnClickListener(R.id.iv_img1)
        helper.addOnClickListener(R.id.iv_img2)
        helper.addOnClickListener(R.id.iv_img3)
        val iv1 = helper.getView<ImageView>(R.id.iv_img1)
        val iv2 = helper.getView<ImageView>(R.id.iv_img2)
        val iv3 = helper.getView<ImageView>(R.id.iv_img3)
        val split = item.photo.split(",")
        if (split.size==1){
            GlideUtils.showAnimation(iv1,Constant.BASE_URL+ split[0],R.mipmap.icon_logo)
            iv1.visibility=View.VISIBLE
            iv2.visibility= View.GONE
            iv3.visibility=View.GONE
        }else if (split.size==2){
            GlideUtils.showAnimation(iv1,Constant.BASE_URL+ split[0],R.mipmap.icon_logo)
            GlideUtils.showAnimation(iv2,Constant.BASE_URL+ split[1],R.mipmap.icon_logo)
            iv1.visibility=View.VISIBLE
            iv2.visibility= View.VISIBLE
            iv3.visibility=View.GONE
        }else{
            iv1.visibility=View.VISIBLE
            iv2.visibility= View.VISIBLE
            iv3.visibility=View.VISIBLE
            GlideUtils.showAnimation(iv1,Constant.BASE_URL+ split[0],R.mipmap.icon_logo)
            GlideUtils.showAnimation(iv2,Constant.BASE_URL+ split[1],R.mipmap.icon_logo)
            GlideUtils.showAnimation(iv3,Constant.BASE_URL+ split[2],R.mipmap.icon_logo)
        }

    }
}