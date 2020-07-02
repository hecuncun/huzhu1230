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
import kotlinx.android.synthetic.main.fragment_emergency_find.*
/**
 *  紧急寻人列表
 */

class EmergencyLookUpFragment : BaseFragment() {
    private var list = mutableListOf<SearchPersonBean.RowsBean>()
    private val emergencyFindAdapter:EmergencyFindAdapter by lazy {
        EmergencyFindAdapter()
    }
    override fun attachLayoutRes(): Int = R.layout.fragment_emergency_find

    override fun initView(view: View) {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView.run {
            layoutManager = LinearLayoutManager(activity)
            adapter =emergencyFindAdapter
        }
    }

    override fun initListener() {
        emergencyFindAdapter.setOnItemClickListener { adapter, view, position ->
            val intent = Intent(activity, PublishEmergencyDetailActivity::class.java)
            intent.putExtra("publishType","emergency")
            intent.putExtra("magorid",list[position].magorid)
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
                SLMRetrofit.getInstance().api.getEmergencySearchPersonBeanCall(currentPage,"","")
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
        //获取紧急寻人信息
        val commonSearchPersonBeanCall =
            SLMRetrofit.getInstance().api.getEmergencySearchPersonBeanCall(currentPage,longitude,latitude)
        commonSearchPersonBeanCall.compose(ThreadSwitchTransformer())
            .subscribe(object : CallbackObserver<SearchPersonBean>() {
                override fun onSucceed(t: SearchPersonBean, desc: String?) {
                    total = t.total
                    list.addAll(t.rows)
                    emergencyFindAdapter.setNewData(list)
                }

                override fun onFailed() {

                }
            })
    }

    companion object {
        fun getInstance(): EmergencyLookUpFragment {
            return EmergencyLookUpFragment()
        }
    }
}