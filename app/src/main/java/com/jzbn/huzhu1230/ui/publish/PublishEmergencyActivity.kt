package com.jzbn.huzhu1230.ui.publish

import BaseActivity
import android.widget.Toast
import com.blankj.utilcode.util.ToastUtils
import com.jzbn.huzhu1230.R
import kotlinx.android.synthetic.main.activity_publish_emergency_layout.*
import kotlinx.android.synthetic.main.toolbar.*

class PublishEmergencyActivity : BaseActivity() {
    val mLoseModeArray = arrayOf("步行","汽车","火车")
    val mLosReasonArray = arrayOf("精神疾病","离家出走","怀疑拐卖", "意外走失")
    override fun attachLayoutRes(): Int = R.layout.activity_publish_emergency_layout

    override fun initData() {
    }

    override fun initView() {
        toolbar_title.text= resources.getString(R.string.publish_emergency_search)

        ll_lose_mode.setOnClickListener {
            val loseModeDialog = LoseModeDialog()
            loseModeDialog.setOnDialogClickListener(object : LoseModeDialog.OnDialogBtnClickListener{
                override fun onClickReasonPosition(pos: Int) {
                    ToastUtils.showShort(mLoseModeArray[pos])
                }
            })
            loseModeDialog.show(supportFragmentManager, "LoseModeDialog")
        }
        ll_lose_reason.setOnClickListener {
            val loseModeDialog = LoseReasonDialog()
            loseModeDialog.setOnDialogClickListener(object : LoseReasonDialog.OnDialogBtnClickListener{
                override fun onClickReasonPosition(pos: Int) {
                    ToastUtils.showShort(mLosReasonArray[pos])
                }
            })
            loseModeDialog.show(supportFragmentManager, "LoseReasonDialog")

        }
    }

    override fun initListener() {
    }

}