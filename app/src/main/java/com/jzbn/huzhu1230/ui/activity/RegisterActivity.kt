package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.PhoneCodeBean
import com.jzbn.huzhu1230.bean.UserInfoBean
import com.jzbn.huzhu1230.ext.showToast
import com.jzbn.huzhu1230.net.CallbackObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import com.jzbn.huzhu1230.utils.RegexUtil
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by hecuncun on 2020-5-23
 */
class RegisterActivity:BaseActivity() {
    private var showPwd=false
    private var isAgree =false
    override fun attachLayoutRes(): Int= R.layout.activity_register
    override fun initData() {
    }

    override fun initView() {
        toolbar_title.text="注册"
    }

    override fun initListener() {
        tv_get_code.setOnClickListener { //获取验证码
          val phoneNum  =  et_phone.text.toString().trim()
            if (RegexUtil.checkMobile(phoneNum)){
                val phoneCodeCall = SLMRetrofit.getInstance().api.phoneCodeCall(phoneNum, "1")
                phoneCodeCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackObserver<PhoneCodeBean>(){
                    override fun onSucceed(t: PhoneCodeBean?, desc: String?) {
                       showToast("获取验证码成功")
                    }

                    override fun onFailed() {

                    }
                })
            }else{
                showToast("请输入正确的手机号")
            }
        }
        iv_eye.setOnClickListener {
            showOrHidden()
        }
        iv_check.setOnClickListener {
            isAgree=!isAgree
            if (isAgree){
                iv_check.setImageResource(R.mipmap.icon_chenk_pre)
            }else{
                iv_check.setImageResource(R.mipmap.icon_uncheck)
            }
        }


        tv_confirm.setOnClickListener {
            //确认注册
            if (!isAgree){
                showToast("请勾选同意《用户注册协议》")
                return@setOnClickListener
            }
            if (et_phone.text.toString().trim().isEmpty()){
                showToast("请输入手机号")
                return@setOnClickListener
            }
            if (et_pwd.text.toString().trim().isEmpty()){
                showToast("请输入密码")
                return@setOnClickListener
            }
            if (et_code.text.toString().trim().isEmpty()){
                showToast("请输入验证码")
                return@setOnClickListener
            }
            val registerCall = SLMRetrofit.getInstance().api.registerCall(
                et_phone.text.toString().trim(),
                et_code.text.toString().trim(),
                et_pwd.text.toString().trim()
            )
            registerCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackObserver<UserInfoBean>(){
                override fun onSucceed(t: UserInfoBean?, desc: String?) {
                    showToast("注册成功")
                    finish()
                }

                override fun onFailed() {

                }
            })

        }
    }

    /**
     * 显示密码操作
     */
    private fun showOrHidden() {
        if (et_pwd.text.toString().isEmpty()) {
            return
        }
        if (showPwd) {
            showPwd = false
            //否则隐藏密码、
            iv_eye.setImageResource(R.mipmap.icon_login_look)
            et_pwd.transformationMethod = PasswordTransformationMethod.getInstance()
            //光标最后
            et_pwd.setSelection(et_pwd.text.toString().length)
        } else {
            showPwd = true
            //如果选中，显示密码
            iv_eye.setImageResource(R.mipmap.icon_login_look_pre)
            et_pwd.transformationMethod = HideReturnsTransformationMethod.getInstance()
            //光标最后
            et_pwd.setSelection(et_pwd.text.toString().length)
        }
    }
}