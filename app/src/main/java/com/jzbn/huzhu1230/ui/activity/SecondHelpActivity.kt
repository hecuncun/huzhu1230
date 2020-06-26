package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.content.Intent
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.adapter.SecondHelpAdapter
import com.jzbn.huzhu1230.bean.DailyRescueBean
import com.jzbn.huzhu1230.net.CallbackObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import kotlinx.android.synthetic.main.activity_message_list.*
import kotlinx.android.synthetic.main.activity_second_help.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by hecuncun on 2020-5-31
 */
class SecondHelpActivity:BaseActivity() {
    private val mAdapter: SecondHelpAdapter by lazy {
        SecondHelpAdapter()
    }
    override fun attachLayoutRes(): Int = R.layout.activity_second_help
    private var currentPage = 1
    private var total = 0
    private var dailyRescueList= mutableListOf<DailyRescueBean.RowsBean>()
    private  var pid=""
    private var title=""
    override fun initData() {
        title = intent.getStringExtra("title")
        pid = intent.getStringExtra("pid")
        tv_title.text=title
        //查询二级分类
        val dailyRescueCall = SLMRetrofit.getInstance().api.dailyRescueCall(currentPage, pid)
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
        initRecyclerView()
    }

    private fun initRecyclerView() {
        rvSecondHelp.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
            isNestedScrollingEnabled = false
        }
    }

    override fun initListener() {
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val intent = Intent(this,ThirdHelpActivity::class.java)
            val bean = adapter.data[position] as DailyRescueBean.RowsBean
            intent.putExtra("title","$title · ${bean.title}")
            intent.putExtra("pid",bean.magorid)
            startActivity(intent)
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
            val dailyRescueCall = SLMRetrofit.getInstance().api.dailyRescueCall(currentPage, pid)
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