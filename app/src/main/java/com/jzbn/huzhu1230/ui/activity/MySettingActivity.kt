package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.view.View
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.ext.showToast
import com.jzbn.huzhu1230.widget.LogoutDialog
import kotlinx.android.synthetic.main.activity_my_setting.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by hecuncun on 2020-5-25
 */
class MySettingActivity : BaseActivity() {
    private  var dialog:LogoutDialog?=null
    override fun attachLayoutRes(): Int = R.layout.activity_my_setting

    override fun initData() {

    }

    override fun initView() {
        toolbar_title.text = "设置"
        dialog = LogoutDialog(this)
    }

    override fun initListener() {
        rl_logout.setOnClickListener { //退出登录
            dialog!!.show()
            dialog!!.setConfirmListener(View.OnClickListener {
                dialog!!.dismiss()
                 showToast("确定")
            })
        }
    }
}