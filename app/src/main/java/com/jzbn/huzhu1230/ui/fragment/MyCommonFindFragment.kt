package com.jzbn.huzhu1230.ui.fragment

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.adapter.EmergencyFindAdapter
import com.jzbn.huzhu1230.bean.SearchPersonBean
import com.jzbn.huzhu1230.net.CallbackObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import com.jzbn.huzhu1230.ui.publishdetail.PublishEmergencyDetailActivity
import com.lhzw.bluetooth.base.BaseFragment
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_emergency_find.recyclerView

/**
 * 我的发布  常年寻人
 */

class MyCommonFindFragment : BaseFragment() {
    private var list = mutableListOf<SearchPersonBean.RowsBean>()
    private val emergencyFindAdapter: EmergencyFindAdapter by lazy {
        EmergencyFindAdapter()
    }

    override fun attachLayoutRes(): Int = R.layout.fragment_common_find

    override fun initView(view: View) {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = emergencyFindAdapter
        }
    }

    override fun initListener() {
        emergencyFindAdapter.setOnItemClickListener { adapter, view, position ->
            val intent =Intent(activity,PublishEmergencyDetailActivity::class.java)
            intent.putExtra("publishType","common")
            intent.putExtra("magorid",list[position].magorid)
            intent.putExtra("type","my")
            startActivity(intent)
        }

        emergencyFindAdapter.disableLoadMoreIfNotFullPage(recyclerView)
        emergencyFindAdapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            if (total < 2) {
                emergencyFindAdapter.setEnableLoadMore(false)
            }
            //查下一页
            currentPage++
            if (currentPage > total) {
                return@RequestLoadMoreListener
            }
            val commonSearchPersonBeanCall =
                SLMRetrofit.getInstance().api.getMyCommonSearchPersonCall(currentPage,uid)
            commonSearchPersonBeanCall.compose(ThreadSwitchTransformer())
                .subscribe(object : CallbackObserver<SearchPersonBean>() {
                    override fun onSucceed(t: SearchPersonBean, desc: String?) {
                        list.addAll(t.rows)
                        emergencyFindAdapter.setNewData(list)
                        if (currentPage == total) {
                            emergencyFindAdapter.loadMoreEnd()
                            emergencyFindAdapter.setEnableLoadMore(false)
                        } else {
                            emergencyFindAdapter.setEnableLoadMore(true)
                            emergencyFindAdapter.loadMoreComplete()
                        }
                    }

                    override fun onFailed() {

                    }
                })
        }, recyclerView)
    }
    private var currentPage = 1
    private var total = 0
    override fun lazyLoad() {
        //获取常年寻人信息
        val commonSearchPersonBeanCall =
            SLMRetrofit.getInstance().api.getMyCommonSearchPersonCall(currentPage,uid)
        commonSearchPersonBeanCall.compose(ThreadSwitchTransformer())
            .subscribe(object : CallbackObserver<SearchPersonBean>() {
                override fun onSucceed(t: SearchPersonBean, desc: String?) {
                    total = t.total
                    list.addAll(t.rows)
                    emergencyFindAdapter.setNewData(list)
                    if (list.isEmpty()){
                        llNoData.visibility=View.VISIBLE
                    }else{
                        llNoData.visibility=View.GONE
                    }
                }

                override fun onFailed() {

                }
            })
    }

    companion object {
        fun getInstance(): MyCommonFindFragment {
            return MyCommonFindFragment()
        }
    }
}