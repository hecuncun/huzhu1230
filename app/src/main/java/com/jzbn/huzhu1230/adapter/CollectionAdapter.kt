package com.jzbn.huzhu1230.adapter

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.CollectionBean

/**
 * Created by hecuncun on 2020-5-25
 */
class CollectionAdapter :
    BaseQuickAdapter<CollectionBean, BaseViewHolder>(R.layout.item_collect_list) {
    private var show = false
    override fun convert(helper: BaseViewHolder, item: CollectionBean?) {
        item ?: return
        val ivCheck = helper.getView<ImageView>(R.id.iv_check)
        ivCheck.setImageResource(if (item.isChecked) R.mipmap.icon_collection_check_pre else R.mipmap.icon_collection_check)
        if (show) {
            ivCheck.visibility = View.VISIBLE
        } else {
            ivCheck.visibility = View.GONE
        }
        helper.addOnClickListener(R.id.iv_check)
    }

    fun setShowCheckIcon(showCheckIcon: Boolean) {
        show = showCheckIcon
        notifyDataSetChanged()
    }
}