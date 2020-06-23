package com.jzbn.huzhu1230.ui.home

import BaseActivity
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.MsgBean
import com.jzbn.huzhu1230.bean.ScoreBean
import com.jzbn.huzhu1230.net.CallbackObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import kotlinx.android.synthetic.main.activity_message_list.recyclerView
import kotlinx.android.synthetic.main.toolbar.*

// Created by hesanwei on 2020/5/31.
class MessageListActivity : BaseActivity() {
    private var currentPage = 1
    private var total = 0
    private var list = mutableListOf<MsgBean.RowsBean>()
    private val messageListAdapter: MessageListAdapter by lazy {
        MessageListAdapter()
    }
    private var type =MessageActivity.TYPE_SYSTEM
    override fun attachLayoutRes(): Int = R.layout.activity_message_list

    override fun initData() {
        type = intent.getIntExtra(MessageActivity.TYPE, MessageActivity.TYPE_SYSTEM)
        toolbar_title.text = resources.getString(if (type == MessageActivity.TYPE_SYSTEM) R.string.system_message else R.string.platform_message)
          if (type == MessageActivity.TYPE_SYSTEM){//系统消息
              messageListAdapter.setType(MessageActivity.TYPE_SYSTEM)
              val sysMsgCall = SLMRetrofit.getInstance().api.sysMsgCall(currentPage,uid)
              sysMsgCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackObserver<MsgBean>(){
                  override fun onSucceed(t: MsgBean, desc: String?) {
                      total = t.total
                      list.addAll(t.rows)
                      messageListAdapter.setNewData(list)
                  }

                  override fun onFailed() {

                  }
              })
          }else{//平台消息
              val platFormMsgCall = SLMRetrofit.getInstance().api.platFormMsgCall(currentPage)
              platFormMsgCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackObserver<MsgBean>(){
                  override fun onSucceed(t: MsgBean, desc: String?) {
                      total = t.total
                      list.addAll(t.rows)
                      messageListAdapter.setNewData(list)
                  }

                  override fun onFailed() {

                  }
              })

          }

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

        messageListAdapter.disableLoadMoreIfNotFullPage(recyclerView)
        messageListAdapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            if (total < 2) {
                messageListAdapter.setEnableLoadMore(false)
            }
            //查下一页
            currentPage++
            if (currentPage > total) {
                return@RequestLoadMoreListener
            }
            if (type == MessageActivity.TYPE_SYSTEM){//系统消息
                val sysMsgCall = SLMRetrofit.getInstance().api.sysMsgCall(currentPage,uid)
                sysMsgCall.compose(ThreadSwitchTransformer())
                    .subscribe(object : CallbackObserver<MsgBean>() {
                        override fun onSucceed(t: MsgBean, desc: String?) {
                            list.addAll(t.rows)
                            messageListAdapter.setNewData(list)
                            if (currentPage == total) {
                                messageListAdapter.loadMoreEnd()
                                messageListAdapter.setEnableLoadMore(false)
                            } else {
                                messageListAdapter.setEnableLoadMore(true)
                                messageListAdapter.loadMoreComplete()
                            }
                        }

                        override fun onFailed() {

                        }
                    })
            }else{
                val platFormMsgCall = SLMRetrofit.getInstance().api.platFormMsgCall(currentPage)
                platFormMsgCall.compose(ThreadSwitchTransformer())
                    .subscribe(object : CallbackObserver<MsgBean>() {
                        override fun onSucceed(t: MsgBean, desc: String?) {
                            list.addAll(t.rows)
                            messageListAdapter.setNewData(list)
                            if (currentPage == total) {
                                messageListAdapter.loadMoreEnd()
                                messageListAdapter.setEnableLoadMore(false)
                            } else {
                                messageListAdapter.setEnableLoadMore(true)
                                messageListAdapter.loadMoreComplete()
                            }
                        }

                        override fun onFailed() {

                        }
                    })
            }

        }, recyclerView)
    }
}