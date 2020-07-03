package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.adapter.CreditAdapter
import com.jzbn.huzhu1230.bean.CreditBean
import com.jzbn.huzhu1230.net.CallbackObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import kotlinx.android.synthetic.main.activity_credit_list.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by heCunCun on 2020/7/3
 */
class CreditListActivity:BaseActivity() {
    private val creditAdapter: CreditAdapter by lazy {
        CreditAdapter()
    }
    override fun attachLayoutRes(): Int = R.layout.activity_credit_list
    private var currentPage = 1
    private var total = 0
    private var list = mutableListOf<CreditBean.RowsBean>()
    override fun initData() {
        val creditListCall = SLMRetrofit.getInstance().api.creditListCall(currentPage, uid)
        creditListCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackObserver<CreditBean>(){
            override fun onSucceed(t: CreditBean, desc: String?) {
                total = t.total
                list.addAll(t.rows)
                creditAdapter.setNewData(list)
                if (list.isEmpty()){
                    llNoData.visibility= View.VISIBLE
                }else{
                    llNoData.visibility= View.GONE
                }
            }

            override fun onFailed() {

            }
        })
    }

    override fun initView() {
        toolbar_title.text = "信誉分明细"
        initRecyclerView()
    }
    private fun initRecyclerView() {
        recyclerView.run {
            layoutManager = LinearLayoutManager(this@CreditListActivity)
            adapter = creditAdapter
        }
    }
    override fun initListener() {
        creditAdapter.disableLoadMoreIfNotFullPage(recyclerView)
        creditAdapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            if (total < 2) {
                creditAdapter.setEnableLoadMore(false)
            }
            //查下一页
            currentPage++
            if (currentPage > total) {
                return@RequestLoadMoreListener
            }
            val creditListCall = SLMRetrofit.getInstance().api.creditListCall(currentPage, uid)
            creditListCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackObserver<CreditBean>(){
                override fun onSucceed(t: CreditBean, desc: String?) {
                    list.addAll(t.rows)
                    creditAdapter.setNewData(list)
                    if (currentPage == total) {
                        creditAdapter.loadMoreEnd()
                        creditAdapter.setEnableLoadMore(false)
                    } else {
                        creditAdapter.setEnableLoadMore(true)
                        creditAdapter.loadMoreComplete()
                    }
                }

                override fun onFailed() {

                }
            })

        }, recyclerView)
    }
}