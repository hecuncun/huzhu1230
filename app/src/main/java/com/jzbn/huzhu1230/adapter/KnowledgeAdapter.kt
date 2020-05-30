package com.jzbn.huzhu1230.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.KnowledgeBean

/**
 * Created by hecuncun on 2020-5-30
 */
class KnowledgeAdapter :BaseQuickAdapter<KnowledgeBean,BaseViewHolder>(R.layout.item_knowledge_list){
    override fun convert(helper: BaseViewHolder, item: KnowledgeBean?) {
    }
}