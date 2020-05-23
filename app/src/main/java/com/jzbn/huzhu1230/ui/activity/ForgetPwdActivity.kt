package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.ext.showToast
import kotlinx.android.synthetic.main.activity_forget_pwd.*

/**
 * Created by hecuncun on 2020-5-23
 */
class ForgetPwdActivity:BaseActivity() {
    override fun attachLayoutRes(): Int= R.layout.activity_forget_pwd

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initListener() {
        tv_confirm.setOnClickListener {
            showToast("密码找回成功")
        }
    }
}