package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.content.Intent
import android.view.View
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.event.LogoutEvent
import com.jzbn.huzhu1230.widget.LogoutDialog
import kotlinx.android.synthetic.main.activity_my_setting.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.EventBus

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
                isLogin=false
                uid=""
                photoPath=""
                EventBus.getDefault().post(LogoutEvent())
                startActivity(Intent(this@MySettingActivity,LoginActivity::class.java))
            })
        }

        rl_about_us.setOnClickListener {
            val intent = Intent(this,WebViewActivity::class.java)
            intent.putExtra("url","")
            intent.putExtra("type",0)
            startActivity(intent)
        }

        rl_change_pwd.setOnClickListener {
            val intent =Intent(this,ForgetPwdActivity::class.java)
            intent.putExtra("type","fix")
            startActivity(intent)
        }
    }
}