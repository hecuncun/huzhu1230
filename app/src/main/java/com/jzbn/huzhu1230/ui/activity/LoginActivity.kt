package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.LoginBean
import com.jzbn.huzhu1230.ext.showToast
import com.jzbn.huzhu1230.net.CallbackObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import kotlinx.android.synthetic.main.activity_login.*


/**
 * Created by hecuncun on 2020-5-20
 */
class LoginActivity:BaseActivity() {
    private var showPwd=false
    override fun attachLayoutRes(): Int= R.layout.activity_login

    override fun initData() {

    }

    override fun initView() {

    }

    override fun initListener() {
        iv_delete_phone.setOnClickListener {
            et_phone.setText("")
        }

        iv_eye.setOnClickListener {
            showOrHidden()
        }
        tv_register.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
        tv_forget_pwd.setOnClickListener {
            val intent =Intent(this,ForgetPwdActivity::class.java)
            intent.putExtra("type","forget")
            startActivity(intent)
        }

        tv_login.setOnClickListener {
            if (et_phone.text.toString().trim().isEmpty()){
                showToast("请输入手机号")
                return@setOnClickListener
            }
            if (et_pwd.text.toString().trim().isEmpty()){
                showToast("请输入密码")
                return@setOnClickListener
            }
            val loginCall = SLMRetrofit.getInstance().api.loginCall(
                et_phone.text.toString().trim(),
                et_pwd.text.toString().trim()
            )
            loginCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackObserver<LoginBean>(){
                override fun onSucceed(t: LoginBean, desc: String?) {
                    showToast("登录成功")
                    isLogin=true
                    uid=t.uid
                    nickname=t.nickname
                    photoPath=t.path?:""
                    startActivity(Intent(this@LoginActivity,MainActivity::class.java))
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