package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.jzbn.huzhu1230.R
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
       //  loadingView?.show()
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
            startActivity(Intent(this,ForgetPwdActivity::class.java))
        }

        tv_login.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
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