package com.jzbn.huzhu1230.ui.home

import BaseActivity
import android.content.Intent
import android.view.View
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.MessageUnReadBean
import com.jzbn.huzhu1230.bean.MsgBean
import com.jzbn.huzhu1230.bean.SysMsgBean
import com.jzbn.huzhu1230.constants.Constant
import com.jzbn.huzhu1230.ext.showToast
import com.jzbn.huzhu1230.net.CallbackListObserver
import com.jzbn.huzhu1230.net.CallbackObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.toolbar.*

// Created by hesanwei on 2020/5/31.
class MessageActivity : BaseActivity(), View.OnClickListener {
    override fun attachLayoutRes(): Int = R.layout.activity_message
    private var platFormMsgTotalNum = 0
    override fun initData() {
        toolbar_title.text = resources.getString(R.string.message)
        //系统未读
        val sysMsgUnreadNumCall = SLMRetrofit.getInstance().api.getSysMsgUnreadNumCall(uid)
        sysMsgUnreadNumCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackListObserver<MessageUnReadBean>(){
            override fun onSucceed(t: MessageUnReadBean) {
               if (t.code==Constant.SUCCESSED_CODE){
                   if (t.data>0){
                       viewRedSystemMessage.visibility=View.VISIBLE
                   }else{
                       viewRedSystemMessage.visibility=View.GONE
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
                   platFormMsgTotalNum=t.data
                   if (t.data>plantFormMsgReadNum){//有新消息
                       viewRedPlatformMessage.visibility=View.VISIBLE
                   }else{
                       viewRedPlatformMessage.visibility=View.GONE
                   }
               }else{
                   showToast(t.message)
               }
            }

            override fun onFailed() {

            }
        })

        //系统消息  默认第一条显示
        val sysMsgCall = SLMRetrofit.getInstance().api.sysMsgCall(1,uid)
        sysMsgCall.compose(ThreadSwitchTransformer()).subscribe(object :
            CallbackObserver<SysMsgBean>(){
            override fun onSucceed(t: SysMsgBean, desc: String?) {
                if (t.rows.isNotEmpty()){
                    val bean = t.rows[0]
                    tv_sys_create_time.text = bean.createtime.split(" ")[0]
                    tv_sys_content.text=bean.remark1
                }
            }

            override fun onFailed() {

            }
        })

        //平台消息  默认第一条
        val platFormMsgCall = SLMRetrofit.getInstance().api.platFormMsgCall(1)
        platFormMsgCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackObserver<MsgBean>(){
            override fun onSucceed(t: MsgBean, desc: String?) {
               if (t.rows.isNotEmpty()){
                   val bean = t.rows[0]
                   tv_platform_create_time.text = bean.createtime.split(" ")[0]
                   tv_platform_content.text =bean.content
               }
            }

            override fun onFailed() {

            }
        })
    }

    override fun initView() {
    }

    override fun initListener() {
        clSystemMessage.setOnClickListener(this)
        clPlatformMessage.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        val intent = Intent(this, MessageListActivity::class.java)
        var type = TYPE_SYSTEM
        when (view?.id) {
            R.id.clSystemMessage -> {
                viewRedSystemMessage.visibility=View.GONE
                type = TYPE_SYSTEM
            }

            R.id.clPlatformMessage -> {
                plantFormMsgReadNum=platFormMsgTotalNum
                viewRedPlatformMessage.visibility=View.GONE
                type = TYPE_PLATFORM
            }
        }
        intent.putExtra(TYPE, type)
        startActivity(intent)
    }

    companion object {
        const val TYPE_SYSTEM = 0
        const val TYPE_PLATFORM = 1
        const val TYPE = "type"
    }
}