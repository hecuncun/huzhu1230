package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.content.Intent
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.adapter.FirstHelpAdapter
import com.jzbn.huzhu1230.bean.DailyRescueBean
import com.jzbn.huzhu1230.net.CallbackObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import com.jzbn.huzhu1230.ui.home.VideoDetailActivity
import kotlinx.android.synthetic.main.activity_message_list.*
import kotlinx.android.synthetic.main.activity_search_help.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by hecuncun on 2020-5-31
 */
class SearchHelpActivity:BaseActivity() {
    private val mAdapter: FirstHelpAdapter by lazy {
        FirstHelpAdapter()
    }
    override fun attachLayoutRes(): Int = R.layout.activity_search_help

    private var currentPage = 1
    private var total = 0
    private var dailyRescueList= mutableListOf<DailyRescueBean.RowsBean>()
    override fun initData() {
        //获取日常救援
        val dailyRescueCall = SLMRetrofit.getInstance().api.dailyRescueCall(currentPage, "0")
        dailyRescueCall.compose(ThreadSwitchTransformer()).subscribe(object :
            CallbackObserver<DailyRescueBean>(){
            override fun onSucceed(t: DailyRescueBean, desc: String?) {
                total = t.total
                dailyRescueList.addAll(t.rows)
                mAdapter.setNewData(dailyRescueList)
            }

            override fun onFailed() {

            }
        })

    }

    override fun initView() {
        toolbar_title.text="寻找互助内容"
        initDailyRecyclerView()
    }
    private fun initDailyRecyclerView(){
        rvDailyHelp.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
            isNestedScrollingEnabled = false
        }
}

    override fun initListener() {
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val bean = adapter.data[position] as DailyRescueBean.RowsBean
            if (bean.remark1=="1"){//如果值为1直接跳转到播放视频详情页面，否则继续跳转救援项目列表
                val intent = Intent(this, VideoDetailActivity::class.java)
                val bean2 = adapter.data[position] as DailyRescueBean.RowsBean
                intent.putExtra("pid",bean2.magorid)
                startActivity(intent)
            }else{
                val intent =Intent(this@SearchHelpActivity,SecondHelpActivity::class.java)
                intent.putExtra("title",bean.title)
                intent.putExtra("pid",bean.magorid)
                startActivity(intent)
            }

        }

        mAdapter.disableLoadMoreIfNotFullPage(recyclerView)
        mAdapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            if (total < 2) {
                Handler().post{//解决崩溃
                    mAdapter.setEnableLoadMore(false)
                }
            }
            //查下一页
            currentPage++
            if (currentPage > total) {
                return@RequestLoadMoreListener
            }
            //获取日常救援
            val dailyRescueCall = SLMRetrofit.getInstance().api.dailyRescueCall(currentPage, "0")
            dailyRescueCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackObserver<DailyRescueBean>(){
                override fun onSucceed(t: DailyRescueBean, desc: String?) {
                    dailyRescueList.addAll(t.rows)
                    mAdapter.setNewData(dailyRescueList)
                    if (currentPage == total) {
                        mAdapter.loadMoreEnd()
                        mAdapter.setEnableLoadMore(false)
                    } else {
                        mAdapter.setEnableLoadMore(true)
                        mAdapter.loadMoreComplete()
                    }
                }

                override fun onFailed() {

                }
            })
        }, recyclerView)
    }
}