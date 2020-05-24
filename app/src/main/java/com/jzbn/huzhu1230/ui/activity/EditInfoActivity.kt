package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import com.jzbn.huzhu1230.R
import kotlinx.android.synthetic.main.activity_edit_info.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by hecuncun on 2020-5-24
 */
class EditInfoActivity : BaseActivity() {
    override fun attachLayoutRes(): Int = R.layout.activity_edit_info

    override fun initData() {

    }

    override fun initView() {
        var title = ""
        when (intent.getIntExtra("type", -1)) {
            1 -> title = "姓名"
            2 -> title = "身份证"
            3 -> title = "性别"
            4 -> title = "语言技能"
            5 -> title = "疾病史"
            6 -> title = "过敏药物"
            7 -> title = "遗传病史"
            8 -> title = "所属公司"
            9 -> title = "救援经历"
            10 -> title = "个人救援技能"
        }
        toolbar_title.text = title
        et_content.hint="请输入您的$title"
    }

    override fun initListener() {

    }
}