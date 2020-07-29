package com.jzbn.huzhu1230.adapter

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.CollectionBean
import com.jzbn.huzhu1230.bean.CollectionResponseBean
import com.jzbn.huzhu1230.constants.Constant
import com.jzbn.huzhu1230.glide.GlideUtils

/**
 * Created by hecuncun on 2020-5-25
 */
class CollectionAdapter :
    BaseQuickAdapter<CollectionResponseBean.RowsBean, BaseViewHolder>(R.layout.item_collect_list) {
    private var show = false
    override fun convert(helper: BaseViewHolder, item: CollectionResponseBean.RowsBean?) {
        item ?: return
        val ivCheck = helper.getView<ImageView>(R.id.iv_check)
        ivCheck.setImageResource(if (item.isChecked) R.mipmap.icon_collection_check_pre else R.mipmap.icon_collection_check)
        if (show) {
            ivCheck.visibility = View.VISIBLE
        } else {
            ivCheck.visibility = View.GONE
        }
        helper.addOnClickListener(R.id.iv_check)
        helper.setText(R.id.tv_title,item.remark2)
        helper.setText(R.id.tv_time,item.createtime.split(" ")[0])
        val ivPic = helper.getView<ImageView>(R.id.iv_pic)
        GlideUtils.showAnimation(ivPic, Constant.BASE_URL+item.picture,R.mipmap.icon_logo)
    }

    fun setShowCheckIcon(showCheckIcon: Boolean) {
        show = showCheckIcon
        notifyDataSetChanged()
    }
}