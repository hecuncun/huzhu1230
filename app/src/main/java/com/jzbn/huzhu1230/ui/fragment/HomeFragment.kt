package com.jzbn.huzhu1230.ui.fragment

import android.content.Intent
import android.os.Handler
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.adapter.CommonHelpAdapter
import com.jzbn.huzhu1230.adapter.DailyHelpAdapter
import com.jzbn.huzhu1230.bean.CommonRescueBean
import com.jzbn.huzhu1230.bean.DailyRescueBean
import com.jzbn.huzhu1230.bean.SignBean
import com.jzbn.huzhu1230.constants.Constant
import com.jzbn.huzhu1230.ext.showToast
import com.jzbn.huzhu1230.net.CallbackListObserver
import com.jzbn.huzhu1230.net.CallbackObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import com.jzbn.huzhu1230.ui.activity.SecondHelpActivity
import com.jzbn.huzhu1230.ui.home.AedActivity
import com.jzbn.huzhu1230.ui.home.MessageActivity
import com.jzbn.huzhu1230.ui.home.SearchActivity
import com.jzbn.huzhu1230.ui.home.SignDialog
import com.lhzw.bluetooth.base.BaseFragment
import kotlinx.android.synthetic.main.activity_message_list.*
import kotlinx.android.synthetic.main.fragment_home.*

// Created by hesanwei on 2020/5/24.
class HomeFragment : BaseFragment(), View.OnClickListener {


    private val commonHelpAdapter: CommonHelpAdapter by lazy {
        CommonHelpAdapter()
    }

    private val dailyHelpAdapter: DailyHelpAdapter by lazy {
        DailyHelpAdapter()
    }

    override fun attachLayoutRes(): Int = R.layout.fragment_home

    override fun initView(view: View) {
        initCommonRecyclerView()
        initDailyRecyclerView()
        initViewClick()
    }

    private fun initViewClick() {
        ivSign.setOnClickListener(this)
        ivAed.setOnClickListener(this)
        ivMessage.setOnClickListener(this)
        tvSearchContent.setOnClickListener(this)
    }

    private fun initCommonRecyclerView() {
        rvCommonHelp.run {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 4)
            adapter = commonHelpAdapter
            isNestedScrollingEnabled = false
        }
    }

    private fun initDailyRecyclerView() {
        rvDailyHelp.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = dailyHelpAdapter
            isNestedScrollingEnabled = false
        }
    }

    override fun initListener() {
        commonHelpAdapter.setOnItemClickListener { adapter, view, position ->
            val intent = (Intent(activity, SecondHelpActivity::class.java))
            val bean = adapter.data[position] as CommonRescueBean.DataBean
            intent.putExtra("title",bean.title)
            intent.putExtra("pid",bean.magorid)
            startActivity(intent)
        }
        dailyHelpAdapter.setOnItemClickListener { adapter, view, position ->
            val intent = Intent(activity, SecondHelpActivity::class.java)
            val bean = adapter.data[position] as DailyRescueBean.RowsBean
            intent.putExtra("title",bean.title)
            intent.putExtra("pid",bean.magorid)
            startActivity(intent)
        }

        dailyHelpAdapter.disableLoadMoreIfNotFullPage(recyclerView)
        dailyHelpAdapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            if (total < 2) {
                Handler().post{//解决崩溃
                    dailyHelpAdapter.setEnableLoadMore(false)
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
                    dailyHelpAdapter.setNewData(dailyRescueList)
                    if (currentPage == total) {
                        dailyHelpAdapter.loadMoreEnd()
                        dailyHelpAdapter.setEnableLoadMore(false)
                    } else {
                        dailyHelpAdapter.setEnableLoadMore(true)
                        dailyHelpAdapter.loadMoreComplete()
                    }
                }

                override fun onFailed() {

                }
            })
        }, recyclerView)
    }
    private var currentPage = 1
    private var total = 0
    private var dailyRescueList= mutableListOf<DailyRescueBean.RowsBean>()
    override fun lazyLoad() {
        //获取常用救援
        val commonRescueCall = SLMRetrofit.getInstance().api.commonRescueCall()
        commonRescueCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackListObserver<CommonRescueBean>(){
            override fun onSucceed(t: CommonRescueBean) {
                if (t.code==Constant.SUCCESSED_CODE){
                    commonHelpAdapter.setNewData(t.data)
                }else{
                    showToast(t.message)
                }
            }

            override fun onFailed() {

            }
        })

        //获取日常救援
        val dailyRescueCall = SLMRetrofit.getInstance().api.dailyRescueCall(currentPage, "0")
        dailyRescueCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackObserver<DailyRescueBean>(){
            override fun onSucceed(t: DailyRescueBean, desc: String?) {
                total = t.total
                dailyRescueList.addAll(t.rows)
                dailyHelpAdapter.setNewData(dailyRescueList)
            }

            override fun onFailed() {

            }
        })


    }

    companion object {
        fun getInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.ivSign -> {
                //签到
                val signCall = SLMRetrofit.getInstance().api.signCall(uid)
                signCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackListObserver<SignBean>(){
                    override fun onSucceed(t: SignBean) {
                        if (t.code==Constant.SUCCESSED_CODE){
                            SignDialog.newInstance(0).show(activity?.supportFragmentManager, "sign")
                        }else{
                            showToast(t.message)
                        }


                    }

                    override fun onFailed() {

                    }
                })

            }

            R.id.ivAed -> {
                startActivity(Intent(context, AedActivity::class.java))
            }

            R.id.ivMessage -> {
                startActivity(Intent(context, MessageActivity::class.java))
            }

            R.id.tvSearchContent -> {
                startActivity(Intent(context,SearchActivity::class.java))
            }
        }
    }
}