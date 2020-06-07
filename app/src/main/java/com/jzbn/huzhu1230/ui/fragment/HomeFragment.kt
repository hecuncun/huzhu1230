package com.jzbn.huzhu1230.ui.fragment

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.adapter.CommonHelpAdapter
import com.jzbn.huzhu1230.adapter.DailyHelpAdapter
import com.jzbn.huzhu1230.bean.SignBean
import com.jzbn.huzhu1230.ext.showToast
import com.jzbn.huzhu1230.net.CallbackListObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import com.jzbn.huzhu1230.ui.activity.SecondHelpActivity
import com.jzbn.huzhu1230.ui.home.AedActivity
import com.jzbn.huzhu1230.ui.home.MessageActivity
import com.jzbn.huzhu1230.ui.home.SearchActivity
import com.jzbn.huzhu1230.ui.home.SignDialog
import com.lhzw.bluetooth.base.BaseFragment
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
            commonHelpAdapter.setNewData(mutableListOf("", "", "", "", "", "", "", ""))
            adapter = commonHelpAdapter
            isNestedScrollingEnabled = false
        }
    }

    private fun initDailyRecyclerView() {
        rvDailyHelp.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            dailyHelpAdapter.setNewData(mutableListOf("", "", "", "", "", "", "", ""))
            adapter = dailyHelpAdapter
            isNestedScrollingEnabled = false
        }
    }

    override fun initListener() {
        commonHelpAdapter.setOnItemClickListener { adapter, view, position ->
            val intent = (Intent(activity, SecondHelpActivity::class.java))
            startActivity(intent)
        }
        dailyHelpAdapter.setOnItemClickListener { adapter, view, position ->
            val intent = (Intent(activity, SecondHelpActivity::class.java))
            startActivity(intent)
        }
    }

    override fun lazyLoad() {
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
                        if (t.code=="10001"){
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