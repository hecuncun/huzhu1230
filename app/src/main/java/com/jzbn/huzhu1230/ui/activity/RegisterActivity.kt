package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.ext.showToast
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by hecuncun on 2020-5-23
 */
class RegisterActivity:BaseActivity() {
    private var showPwd=false
    override fun attachLayoutRes(): Int= R.layout.activity_register
    override fun initData() {
    }

    override fun initView() {
        toolbar_title.text="注册"
    }

    override fun initListener() {
        iv_eye.setOnClickListener {
            showOrHidden()
        }
        tv_confirm.setOnClickListener {
            //确认注册
            showToast("注册成功")
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