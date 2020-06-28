package com.jzbn.huzhu1230.ui.fragment

import android.content.Intent
import android.support.v4.app.FragmentTransaction
import android.view.View
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.MessageUnReadBean
import com.jzbn.huzhu1230.constants.Constant
import com.jzbn.huzhu1230.ext.showToast
import com.jzbn.huzhu1230.net.CallbackListObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import com.jzbn.huzhu1230.ui.home.MessageActivity
import com.lhzw.bluetooth.base.BaseFragment
import kotlinx.android.synthetic.main.activity_my_publish.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar.*

// Created by hesanwei on 2020/5/24.
class LookupFragment : BaseFragment() {

    private var emergencyLookUpFragment:EmergencyLookUpFragment?=null
    private var commonLookUpFragment:CommonLookUpFragment?=null
    override fun attachLayoutRes(): Int = R.layout.fragment_lookup

    override fun initView(view: View) {
        toolbar_title.text="寻人专区"
        toolbar_right_img.setImageResource(R.mipmap.icon_look_msg)
        toolbar_right_img.visibility=View.VISIBLE
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
                        toolbar_red_dot.visibility=View.VISIBLE
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
                        toolbar_red_dot.visibility=View.VISIBLE
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
        toolbar_right_img.setOnClickListener {
            startActivity(Intent(context, MessageActivity::class.java))
        }
        rg.setOnCheckedChangeListener { group, checkedId ->
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            hideAllFragment(transaction)
            when (checkedId) {
                R.id.rg_emergency -> {
                    if (emergencyLookUpFragment == null) {
                        emergencyLookUpFragment = EmergencyLookUpFragment.getInstance()
                        transaction.add(R.id.fl_content, emergencyLookUpFragment!!)
                    }
                    transaction.show(emergencyLookUpFragment!!)
                }
                R.id.rg_common -> {
                    if (commonLookUpFragment == null) {
                        commonLookUpFragment = CommonLookUpFragment.getInstance()
                        transaction.add(R.id.fl_content, commonLookUpFragment!!)
                    }
                    transaction.show(commonLookUpFragment!!)

                }
            }
            transaction.commit()
        }

        rg.check(R.id.rg_emergency)
    }

    override fun lazyLoad() {
    }

    private fun hideAllFragment(transaction: FragmentTransaction) {
        emergencyLookUpFragment?.let {
            transaction.hide(it)
        }
        commonLookUpFragment?.let {
            transaction.hide(it)
        }
    }

    companion object {
        fun getInstance(): LookupFragment {
            return LookupFragment()
        }
    }
}