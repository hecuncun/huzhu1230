package com.jzbn.huzhu1230.ui.home

import BaseActivity
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.MsgBean
import com.jzbn.huzhu1230.bean.SysMsgBean
import com.jzbn.huzhu1230.net.CallbackObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import kotlinx.android.synthetic.main.activity_message_list.recyclerView
import kotlinx.android.synthetic.main.toolbar.*

// Created by hesanwei on 2020/5/31.
class MessageListActivity : BaseActivity() {
    private var currentPage = 1
    private var total = 0
    private var listPlat = mutableListOf<MsgBean.RowsBean>()
    private var listSys = mutableListOf<SysMsgBean.RowsBean>()
    private val platFromMsgListAdapter: PlatFormMsgListAdapter by lazy {
        PlatFormMsgListAdapter()
    }
    private val sysMsgListAdapter:SysMsgListAdapter by lazy {
        SysMsgListAdapter()
    }
    private var type =MessageActivity.TYPE_SYSTEM
    override fun attachLayoutRes(): Int = R.layout.activity_message_list

    override fun initData() {

          if (type == MessageActivity.TYPE_SYSTEM){//系统消息
              val sysMsgCall = SLMRetrofit.getInstance().api.sysMsgCall(currentPage,uid)
              sysMsgCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackObserver<SysMsgBean>(){
                  override fun onSucceed(t: SysMsgBean, desc: String?) {
                      total = t.total
                      listSys.addAll(t.rows)
                      sysMsgListAdapter.setNewData(listSys)
                  }

                  override fun onFailed() {

                  }
              })
          }else{//平台消息
              val platFormMsgCall = SLMRetrofit.getInstance().api.platFormMsgCall(currentPage)
              platFormMsgCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackObserver<MsgBean>(){
                  override fun onSucceed(t: MsgBean, desc: String?) {
                      total = t.total
                      listPlat.addAll(t.rows)
                      platFromMsgListAdapter.setNewData(listPlat)
                  }

                  override fun onFailed() {

                  }
              })

          }

    }

    override fun initView() {
        type = intent.getIntExtra(MessageActivity.TYPE, MessageActivity.TYPE_SYSTEM)
        toolbar_title.text = resources.getString(if (type == MessageActivity.TYPE_SYSTEM) R.string.system_message else R.string.platform_message)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView?.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MessageListActivity)
            if (type==MessageActivity.TYPE_SYSTEM){
                adapter=sysMsgListAdapter
            }else{
                adapter = platFromMsgListAdapter
            }

        }
    }

    override fun initListener() {
        if (type ==MessageActivity.TYPE_SYSTEM){
            sysMsgListAdapter.disableLoadMoreIfNotFullPage(recyclerView)
            sysMsgListAdapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
                if (total < 2) {
                    sysMsgListAdapter.setEnableLoadMore(false)
                }
                //查下一页
                currentPage++
                if (currentPage > total) {
                    return@RequestLoadMoreListener
                }
                val sysMsgCall = SLMRetrofit.getInstance().api.sysMsgCall(currentPage,uid)
                sysMsgCall.compose(ThreadSwitchTransformer())
                    .subscribe(object : CallbackObserver<SysMsgBean>() {
                        override fun onSucceed(t: SysMsgBean, desc: String?) {
                            listSys.addAll(t.rows)
                            sysMsgListAdapter.setNewData(listSys)
                            if (currentPage == total) {
                                sysMsgListAdapter.loadMoreEnd()
                                sysMsgListAdapter.setEnableLoadMore(false)
                            } else {
                                sysMsgListAdapter.setEnableLoadMore(true)
                                sysMsgListAdapter.loadMoreComplete()
                            }
                        }

                        override fun onFailed() {

                        }
                    })


            }, recyclerView)
        }else{
            platFromMsgListAdapter.disableLoadMoreIfNotFullPage(recyclerView)
            platFromMsgListAdapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
                if (total < 2) {
                    platFromMsgListAdapter.setEnableLoadMore(false)
                }
                //查下一页
                currentPage++
                if (currentPage > total) {
                    return@RequestLoadMoreListener
                }
                    val platFormMsgCall = SLMRetrofit.getInstance().api.platFormMsgCall(currentPage)
                    platFormMsgCall.compose(ThreadSwitchTransformer())
                        .subscribe(object : CallbackObserver<MsgBean>() {
                            override fun onSucceed(t: MsgBean, desc: String?) {
                                listPlat.addAll(t.rows)
                                platFromMsgListAdapter.setNewData(listPlat)
                                if (currentPage == total) {
                                    platFromMsgListAdapter.loadMoreEnd()
                                    platFromMsgListAdapter.setEnableLoadMore(false)
                                } else {
                                    platFromMsgListAdapter.setEnableLoadMore(true)
                                    platFromMsgListAdapter.loadMoreComplete()
                                }
                            }

                            override fun onFailed() {

                            }
                        })


            }, recyclerView)
        }

    }
}