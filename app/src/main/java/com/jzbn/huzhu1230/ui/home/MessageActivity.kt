package com.jzbn.huzhu1230.ui.home

import BaseActivity
import android.content.Intent
import android.view.View
import com.jzbn.huzhu1230.R
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.toolbar.*

// Created by hesanwei on 2020/5/31.
class MessageActivity : BaseActivity(), View.OnClickListener {
    override fun attachLayoutRes(): Int = R.layout.activity_message

    override fun initData() {
        toolbar_title.text = resources.getString(R.string.message)
    }

    override fun initView() {
    }

    override fun initListener() {
        clSystemMessage.setOnClickListener(this)
        clPlatformMessage.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        val intent = Intent(this, MessageListActivity::class.java)
        var type = TYPE_SYSTEM
        when (view?.id) {
            R.id.clSystemMessage -> {
                type = TYPE_SYSTEM
            }

            R.id.clPlatformMessage -> {
                type = TYPE_PLATFORM
            }
        }
        intent.putExtra(TYPE, type)
        startActivity(intent)
    }

    companion object {
        const val TYPE_SYSTEM = 0
        const val TYPE_PLATFORM = 1
        const val TYPE = "type"
    }
}