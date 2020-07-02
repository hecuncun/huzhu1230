package com.jzbn.huzhu1230.ui.fragment

import android.content.Intent
import android.view.View
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.MessageUnReadBean
import com.jzbn.huzhu1230.constants.Constant
import com.jzbn.huzhu1230.ext.showToast
import com.jzbn.huzhu1230.glide.GlideUtils
import com.jzbn.huzhu1230.net.CallbackListObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import com.jzbn.huzhu1230.ui.activity.*
import com.jzbn.huzhu1230.ui.home.MessageActivity
import com.lhzw.bluetooth.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.toolbar.*

// Created by hesanwei on 2020/5/24.
class MineFragment: BaseFragment() {
    override fun attachLayoutRes(): Int = R.layout.fragment_mine

    override fun initView(view: View) {
        tv_nick_name.text=nickname
        GlideUtils.showCircleWithBorder(iv_head_pic,Constant.BASE_URL+photoPath,R.mipmap.icon_head_def,resources.getColor(R.color.white))
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
        sysMsgUnreadNumCall.compose(ThreadSwitchTransformer()).subscribe(object :
            CallbackListObserver<MessageUnReadBean>(){
            override fun onSucceed(t: MessageUnReadBean) {
                if (t.code== Constant.SUCCESSED_CODE){
                    if (t.data>0){
                       red_dot.visibility=View.VISIBLE
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
        platFormMsgUnreadNumCall.compose(ThreadSwitchTransformer()).subscribe(object :
            CallbackListObserver<MessageUnReadBean>(){
            override fun onSucceed(t: MessageUnReadBean) {
                if (t.code== Constant.SUCCESSED_CODE){
                    if (t.data>plantFormMsgReadNum){//有新消息
                        red_dot.visibility=View.VISIBLE
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

    override fun initListener() {
        iv_msg.setOnClickListener {
            startActivity(Intent(activity, MessageActivity::class.java))
        }
        iv_edit_info.setOnClickListener {
            startActivity(Intent(activity, MyInfoActivity::class.java))
        }
        ll_my_info.setOnClickListener {
            startActivity(Intent(activity, MyInfoActivity::class.java))
        }
        ll_my_score.setOnClickListener {
            startActivity(Intent(activity, MyScoreActivity::class.java))
        }
        ll_my_publish.setOnClickListener {
            startActivity(Intent(activity, MyPublishActivity::class.java))
        }
        ll_setting.setOnClickListener {
            startActivity(Intent(activity, MySettingActivity::class.java))
        }
        rl_my_xin_yu.setOnClickListener {
            startActivity(Intent(activity, MyXinYuActivity::class.java))
        }
        ll_my_honor.setOnClickListener {
            startActivity(Intent(activity, MyHonorActivity::class.java))
        }
        ll_my_collection.setOnClickListener {
            startActivity(Intent(activity, MyCollectionActivity::class.java))
        }
    }

    override fun lazyLoad() {
    }

    companion object {
        fun getInstance(): MineFragment {
            return MineFragment()
        }
    }
}