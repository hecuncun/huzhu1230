package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.adapter.ScoreAdapter
import com.jzbn.huzhu1230.net.CallbackObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import kotlinx.android.synthetic.main.fragment_emergency_find.*
import kotlinx.android.synthetic.main.toolbar.*
import com.jzbn.huzhu1230.bean.ScoreBean

/**
 * Created by hecuncun on 2020-5-24
 */
class ScoreListActivity : BaseActivity() {

    private val scoreAdapter: ScoreAdapter by lazy {
        ScoreAdapter()
    }

    override fun attachLayoutRes(): Int = R.layout.activity_score_list
    private var currentPage = 1
    private var total = 0
    private var list = mutableListOf<ScoreBean.RowsBean>()
    override fun initData() {
        val msgListCall = SLMRetrofit.getInstance().api.scoreListCall(currentPage, uid)
        msgListCall.compose(ThreadSwitchTransformer())
            .subscribe(object : CallbackObserver<ScoreBean>() {
                override fun onSucceed(t: ScoreBean, desc: String?) {
                    total = t.total
                    list.addAll(t.rows)
                    scoreAdapter.setNewData(list)
                }

                override fun onFailed() {

                }
            })

    }

    override fun initView() {
        toolbar_title.text = "积分明细"
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView.run {
            layoutManager = LinearLayoutManager(this@ScoreListActivity)
            adapter = scoreAdapter
        }
    }

    override fun initListener() {
        scoreAdapter.disableLoadMoreIfNotFullPage(recyclerView)
        scoreAdapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            if (total < 2) {
                scoreAdapter.setEnableLoadMore(false)
            }
            //查下一页
            currentPage++
            if (currentPage > total) {
                return@RequestLoadMoreListener
            }
            val msgListCall = SLMRetrofit.getInstance().api.scoreListCall(currentPage, uid)
            msgListCall.compose(ThreadSwitchTransformer())
                .subscribe(object : CallbackObserver<ScoreBean>() {
                    override fun onSucceed(t: ScoreBean, desc: String?) {
                        list.addAll(t.rows)
                        scoreAdapter.setNewData(list)
                        if (currentPage == total) {
                            scoreAdapter.loadMoreEnd()
                            scoreAdapter.setEnableLoadMore(false)
                        } else {
                            scoreAdapter.setEnableLoadMore(true)
                            scoreAdapter.loadMoreComplete()
                        }
                    }

                    override fun onFailed() {

                    }
                })
        }, recyclerView)
    }
}