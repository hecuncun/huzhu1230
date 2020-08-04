package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.base.BaseNoDataBean
import com.jzbn.huzhu1230.constants.Constant
import com.jzbn.huzhu1230.event.RefreshUserInfoEvent
import com.jzbn.huzhu1230.ext.showToast
import com.jzbn.huzhu1230.net.CallbackListObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import kotlinx.android.synthetic.main.activity_edit_info.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by hecuncun on 2020-5-24
 */
class EditInfoActivity : BaseActivity() {
    override fun attachLayoutRes(): Int = R.layout.activity_edit_info

    override fun initData() {

    }
    private var map = mutableMapOf<String, String>()
    private var type=-1
    private var key=""
    override fun initView() {
        var title = ""
        type=intent.getIntExtra("type", -1)
        when (type) {
            1 -> {title = "姓名"
                key="name"}
            2 -> {title = "身份证"
                key="card"
                  }
            5 -> {title = "疾病史"
                key="caseHistory"
            }
            6 -> {title = "过敏药物"
                  key="allergy"
            }
            7 -> {title = "遗传病史"
                key="inherit"
            }
            8 -> {
                title = "所属公司"
                key="company"
            }
            9 ->{ title = "救援经历"
                key="experience"
            }
            10 -> {
                title = "个人救援技能"
                key="skill"
            }
        }
        toolbar_title.text = title
        et_content.hint="请输入您的$title"
    }

    override fun initListener() {

        tv_confirm.setOnClickListener {
            val value = et_content.text.toString().trim()
            if (value.isNotEmpty()){
                //修改
                map[key]=value
                if (key=="card" && value.length != 18){
                    showToast("请检查身份证号是否正确")
                    return@setOnClickListener
                }
                val updateUserInfo = SLMRetrofit.getInstance().api.updateUserInfo(map, uid)
                updateUserInfo.compose(ThreadSwitchTransformer())
                    .subscribe(object : CallbackListObserver<BaseNoDataBean>() {
                        override fun onSucceed(t: BaseNoDataBean) {
                            if (t.code == Constant.SUCCESSED_CODE) {
                                showToast("保存成功")
                                EventBus.getDefault().post(RefreshUserInfoEvent())
                                finish()
                            } else {
                                showToast(t.message)
                            }
                        }

                        override fun onFailed() {

                        }
                    })
            }else{
                showToast("请填写信息")
            }
        }
    }
}