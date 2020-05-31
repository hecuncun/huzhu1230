package com.jzbn.huzhu1230.ui.home

import BaseActivity
import android.support.v7.widget.LinearLayoutManager
import com.jzbn.huzhu1230.R
import kotlinx.android.synthetic.main.activity_message_list.*
import kotlinx.android.synthetic.main.toolbar.*

// Created by hesanwei on 2020/5/31.
class MessageListActivity : BaseActivity() {

    private val messageListAdapter: MessageListAdapter by lazy {
        MessageListAdapter()
    }

    override fun attachLayoutRes(): Int = R.layout.activity_message_list

    override fun initData() {
        val type = intent.getIntExtra(MessageActivity.TYPE, MessageActivity.TYPE_SYSTEM)
        toolbar_title.text = resources.getString(if (type == MessageActivity.TYPE_SYSTEM) R.string.system_message else R.string.platform_message)
        val dataList = mutableListOf<String>()
        for (i in 0..10) {
            dataList.add("测试$i")
        }
        messageListAdapter.setNewData(dataList)
    }

    override fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView?.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MessageListActivity)
            adapter = messageListAdapter
        }
    }

    override fun initListener() {
    }
}