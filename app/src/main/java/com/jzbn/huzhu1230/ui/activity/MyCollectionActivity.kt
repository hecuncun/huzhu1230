package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.adapter.CollectionAdapter
import com.jzbn.huzhu1230.bean.CollectionBean
import kotlinx.android.synthetic.main.activity_my_collection.*
import kotlinx.android.synthetic.main.fragment_emergency_find.recyclerView
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by hecuncun on 2020-5-25
 */
class MyCollectionActivity : BaseActivity() {
    private var list = mutableListOf<CollectionBean>()
    private val collectionAdapter: CollectionAdapter by lazy {
        CollectionAdapter()
    }

    override fun attachLayoutRes(): Int = R.layout.activity_my_collection

    override fun initData() {
        initTestData()
    }

    private fun initTestData() {
        for (i in 1..20) {
            list.add(CollectionBean())
            collectionAdapter.setNewData(list)
        }
    }

    override fun initView() {
        toolbar_title.text="我的收藏"
        toolbar_right_tv.text="编辑"
        toolbar_right_tv.visibility= View.VISIBLE
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView.run {
            layoutManager = LinearLayoutManager(this@MyCollectionActivity)
            adapter = collectionAdapter
        }
    }

    override fun initListener() {
        toolbar_right_tv.setOnClickListener {
            if (list.isEmpty()){
                return@setOnClickListener
            }
            if (toolbar_right_tv.text == "编辑") {
                collectionAdapter.setShowCheckIcon(true)
                toolbar_right_tv.text = "完成"
                tv_delete.visibility = View.VISIBLE
            } else if (toolbar_right_tv.text == "完成") {
                collectionAdapter.setShowCheckIcon(false)
                toolbar_right_tv.text = "编辑"
                tv_delete.visibility = View.GONE
            }
        }


        collectionAdapter.setOnItemChildClickListener { adapter, view, position ->
            when(view.id){
                R.id.iv_check->{
                    val bean = (adapter.getItem(position) as CollectionBean)
                    bean.isChecked = !bean.isChecked
                    collectionAdapter.notifyItemChanged(position)
                }
            }
        }
    }
}