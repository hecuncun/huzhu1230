package com.jzbn.huzhu1230.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.adapter.AedAdapter
import com.jzbn.huzhu1230.bean.AedBean
import com.jzbn.huzhu1230.net.CallbackObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import com.lhzw.bluetooth.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_aed.*


class AedFragment : BaseFragment() {
    private var currentPage = 1
    private var total = 0
    private var list = mutableListOf<AedBean.RowsBean>()
    private val aedAdapter: AedAdapter by lazy {
        AedAdapter()
    }

    override fun attachLayoutRes(): Int = R.layout.fragment_aed

    override fun initView(view: View) {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = aedAdapter
        }
    }

    override fun initListener() {
        aedAdapter.disableLoadMoreIfNotFullPage(recyclerView)
        aedAdapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            if (total < 2) {
                aedAdapter.setEnableLoadMore(false)
            }
            //查下一页
            currentPage++
            if (currentPage > total) {
                return@RequestLoadMoreListener
            }
            val myAedCall = SLMRetrofit.getInstance().api.myAedCall(currentPage, uid)
            myAedCall.compose(ThreadSwitchTransformer())
                .subscribe(object : CallbackObserver<AedBean>() {
                    override fun onSucceed(t: AedBean, desc: String?) {
                        list.addAll(t.rows)
                        aedAdapter.setNewData(list)
                        if (currentPage == total) {
                            aedAdapter.loadMoreEnd()
                            aedAdapter.setEnableLoadMore(false)
                        } else {
                            aedAdapter.setEnableLoadMore(true)
                            aedAdapter.loadMoreComplete()
                        }
                    }

                    override fun onFailed() {

                    }
                })
        }, recyclerView)
    }

    override fun lazyLoad() {
        //获取数据
        val myAedCall = SLMRetrofit.getInstance().api.myAedCall(currentPage, uid)
        myAedCall.compose(ThreadSwitchTransformer())
            .subscribe(object : CallbackObserver<AedBean>() {
                override fun onSucceed(t: AedBean, desc: String?) {
                    total = t.total
                    list.addAll(t.rows)
                    aedAdapter.setNewData(list)
                }

                override fun onFailed() {

                }
            })

    }

    companion object {
        fun getInstance(): AedFragment {
            return AedFragment()
        }
    }
}