package com.jzbn.huzhu1230.ui.publishdetail

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.AddressBean
import com.jzbn.huzhu1230.bean.AedBean

/**
 * Created by hecuncun on 2020-5-24
 */
class AddressesAdapter:BaseQuickAdapter<AddressBean,BaseViewHolder>(R.layout.item_address_layout) {
    override fun convert(helper: BaseViewHolder, item: AddressBean?) {
        helper.setGone(R.id.divider, helper.adapterPosition != (data.size-1))
    }
}