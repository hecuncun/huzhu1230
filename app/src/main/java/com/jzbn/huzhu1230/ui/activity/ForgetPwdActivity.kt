package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.base.BaseNoDataBean
import com.jzbn.huzhu1230.bean.PhoneCodeBean
import com.jzbn.huzhu1230.ext.showToast
import com.jzbn.huzhu1230.net.CallbackListObserver
import com.jzbn.huzhu1230.net.CallbackObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import com.jzbn.huzhu1230.utils.RegexUtil
import kotlinx.android.synthetic.main.activity_forget_pwd.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by hecuncun on 2020-5-23
 */
class ForgetPwdActivity : BaseActivity() {
    override fun attachLayoutRes(): Int = R.layout.activity_forget_pwd

    override fun initData() {
    }

    override fun initView() {
        val type = intent.getStringExtra("type")
        toolbar_title.text = if (type=="fix") "修改密码" else "忘记密码"
    }

    override fun initListener() {
        tv_get_code.setOnClickListener {
            //获取验证码
            val phoneNum = et_phone.text.toString().trim()
            if (RegexUtil.checkMobile(phoneNum)) {
                val phoneCodeCall = SLMRetrofit.getInstance().api.phoneCodeCall(phoneNum, "2")
                phoneCodeCall.compose(ThreadSwitchTransformer()).subscribe(object :
                    CallbackObserver<PhoneCodeBean>() {
                    override fun onSucceed(t: PhoneCodeBean?, desc: String?) {
                        showToast("获取验证码成功")
                    }

                    override fun onFailed() {

                    }
                })
            } else {
                showToast("请输入正确的手机号")
            }
        }
        tv_confirm.setOnClickListener {

            if (et_phone.text.toString().trim().isEmpty()) {
                showToast("请输入手机号")
                return@setOnClickListener
            }
            if (et_pwd.text.toString().trim().isEmpty()) {
                showToast("请输入密码")
                return@setOnClickListener
            }
            if (et_pwd2.text.toString().trim().isEmpty()) {
                showToast("请输入密码")
                return@setOnClickListener
            }
            if (et_pwd.text.toString().trim()!=et_pwd2.text.toString().trim()){
                showToast("两次输入的密码不一致")
                return@setOnClickListener
            }
            if (et_code.text.toString().trim().isEmpty()) {
                showToast("请输入验证码")
                return@setOnClickListener
            }

            val resetPwdCall = SLMRetrofit.getInstance().api.resetPwdCall(
                et_phone.text.toString().trim(),
                et_code.text.toString().trim(),
                et_pwd.text.toString().trim()
            )
            resetPwdCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackListObserver<BaseNoDataBean>(){
                override fun onSucceed(t: BaseNoDataBean?) {
                    showToast("成功")
                    finish()
                }

                override fun onFailed() {
                }
            })

        }
    }
}