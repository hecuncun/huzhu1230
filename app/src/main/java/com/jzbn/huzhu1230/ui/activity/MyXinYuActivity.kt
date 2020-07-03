package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.content.Intent
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.PersonalInfoBean
import com.jzbn.huzhu1230.net.CallbackObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import kotlinx.android.synthetic.main.activity_my_xin_yu.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by hecuncun on 2020-5-25
 */
class MyXinYuActivity:BaseActivity() {
    override fun attachLayoutRes(): Int = R.layout.activity_my_xin_yu

    override fun initData() {
        getUserInfo()
    }
    private fun getUserInfo() {
        val personalInfoCall = SLMRetrofit.getInstance().api.personalInfoCall(uid)
        personalInfoCall.compose(ThreadSwitchTransformer())
            .subscribe(object : CallbackObserver<PersonalInfoBean>() {
                override fun onSucceed(t: PersonalInfoBean, desc: String?) {
                    tv_credit_num.text=t.score.toString()
                }

                override fun onFailed() {

                }
            })
    }
    override fun initView() {
       toolbar_title.text="信誉值"
    }

    override fun initListener() {
        tv_credit_detail.setOnClickListener {
            startActivity(Intent(this,CreditListActivity::class.java))
        }
    }
}