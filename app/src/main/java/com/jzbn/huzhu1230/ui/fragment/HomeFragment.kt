package com.jzbn.huzhu1230.ui.fragment

import android.content.Intent
import android.os.Handler
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.amap.api.location.AMapLocationClient
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.adapter.CommonHelpAdapter
import com.jzbn.huzhu1230.adapter.DailyHelpAdapter
import com.jzbn.huzhu1230.base.BaseNoDataBean
import com.jzbn.huzhu1230.bean.CommonRescueBean
import com.jzbn.huzhu1230.bean.DailyRescueBean
import com.jzbn.huzhu1230.bean.MessageUnReadBean
import com.jzbn.huzhu1230.bean.SignBean
import com.jzbn.huzhu1230.constants.Constant
import com.jzbn.huzhu1230.ext.showToast
import com.jzbn.huzhu1230.net.CallbackListObserver
import com.jzbn.huzhu1230.net.CallbackObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import com.jzbn.huzhu1230.ui.activity.SecondHelpActivity
import com.jzbn.huzhu1230.ui.home.*
import com.lhzw.bluetooth.base.BaseFragment
import com.orhanobut.logger.Logger
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
        startLocationAddress()
        initCommonRecyclerView()
        initDailyRecyclerView()
        initViewClick()
        getUnReadMsg()
    }
//可见就刷新下消息
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!isHidden){
            getUnReadMsg()
        }
    }

    private fun getUnReadMsg() {
        //系统未读
        val sysMsgUnreadNumCall = SLMRetrofit.getInstance().api.getSysMsgUnreadNumCall(uid)
        sysMsgUnreadNumCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackListObserver<MessageUnReadBean>(){
            override fun onSucceed(t: MessageUnReadBean) {
                if (t.code==Constant.SUCCESSED_CODE){
                    if (t.data>0){
                        unReadMsgRedDot.visibility=View.VISIBLE
                    }else{

                    }
                }else{
                    showToast(t.message)
                }
            }

            override fun onFailed() {

            }
        })
        //平台未读
        val platFormMsgUnreadNumCall = SLMRetrofit.getInstance().api.platFormMsgUnreadNumCall
        platFormMsgUnreadNumCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackListObserver<MessageUnReadBean>(){
            override fun onSucceed(t: MessageUnReadBean) {
                if (t.code==Constant.SUCCESSED_CODE){
                    if (t.data>plantFormMsgReadNum){//有新消息
                        unReadMsgRedDot.visibility=View.VISIBLE
                    }else{

                    }
                }else{
                    showToast(t.message)
                }
            }

            override fun onFailed() {

            }
        })
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

            val bean = adapter.data[position] as CommonRescueBean.DataBean
            if (bean.remark1=="1"){//如果值为1直接跳转到播放视频详情页面，否则继续跳转救援项目列表
                val intent = Intent(activity, VideoDetailActivity::class.java)
                val bean2 = adapter.data[position] as CommonRescueBean.DataBean
                intent.putExtra("pid",bean2.magorid)
                startActivity(intent)
            }else{
                val intent = (Intent(activity, SecondHelpActivity::class.java))
                intent.putExtra("title",bean.title)
                intent.putExtra("pid",bean.magorid)
                startActivity(intent)
            }

        }
        dailyHelpAdapter.setOnItemClickListener { adapter, view, position ->
            val bean = adapter.data[position] as DailyRescueBean.RowsBean
            if (bean.remark1=="1"){//如果值为1直接跳转到播放视频详情页面，否则继续跳转救援项目列表
                val intent = Intent(activity, VideoDetailActivity::class.java)
                val bean2 = adapter.data[position] as DailyRescueBean.RowsBean
                intent.putExtra("pid",bean2.magorid)
                startActivity(intent)
            }else{
                val intent = Intent(activity, SecondHelpActivity::class.java)
                intent.putExtra("title",bean.title)
                intent.putExtra("pid",bean.magorid)
                startActivity(intent)
            }

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
 private var mLocationClient:AMapLocationClient?=null
    private fun startLocationAddress() {
        //开始定位
        mLocationClient = AMapLocationClient(requireActivity())
        mLocationClient?.setLocationListener {
            if (it != null) {
                if (it.errorCode == 0) {
                    mLocationClient?.stopLocation()
                    tvLocation.text=it.address
                    longitudeMy=it.longitude.toString()
                    latitudeMy=it.latitude.toString()
                    Logger.e(it.toStr())
                    Logger.e("longitude==${it.longitude},latitude==${it.latitude}")
                    gpsAddressMy=it.address
                    val updateUserLocation =
                        SLMRetrofit.getInstance().api.updateUserLocation(uid, longitudeMy, latitudeMy)
                    updateUserLocation.compose(ThreadSwitchTransformer()).subscribe(object :CallbackListObserver<BaseNoDataBean>(){
                        override fun onSucceed(t: BaseNoDataBean?) {

                        }

                        override fun onFailed() {

                        }
                    })
                } else {
                    Logger.e("定位信息:${it.errorCode}，${it.errorInfo}")
                }
             }else{
                Logger.e("定位信息为null")
            }
        }
        mLocationClient?.startLocation()
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

    override fun onDestroy() {
        super.onDestroy()
        mLocationClient=null
    }
}