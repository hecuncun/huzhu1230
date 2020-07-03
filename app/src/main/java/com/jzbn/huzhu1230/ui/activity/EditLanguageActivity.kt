package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.graphics.Color
import com.flyco.dialog.widget.ActionSheetDialog
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.base.BaseNoDataBean
import com.jzbn.huzhu1230.bean.LanguageBean
import com.jzbn.huzhu1230.constants.Constant
import com.jzbn.huzhu1230.event.RefreshUserInfoEvent
import com.jzbn.huzhu1230.ext.showToast
import com.jzbn.huzhu1230.net.CallbackListObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import kotlinx.android.synthetic.main.activity_edit_certificate.tv_confirm
import kotlinx.android.synthetic.main.activity_edit_language.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by hecuncun on 2020-5-24
 */
class EditLanguageActivity : BaseActivity() {
    private var dialog: ActionSheetDialog? = null
    override fun attachLayoutRes(): Int = R.layout.activity_edit_language

    private var list = mutableListOf<LanguageBean.DataBean>()

    override fun initData() {
        //获取语言列表
        val languageBeanCall = SLMRetrofit.getInstance().api.languageBeanCall()
        languageBeanCall.compose(ThreadSwitchTransformer())
            .subscribe(object : CallbackListObserver<LanguageBean>() {
                override fun onSucceed(t: LanguageBean) {
                    if (t.code == Constant.SUCCESSED_CODE) {
                        list.addAll(t.data)
                        var array = arrayOfNulls<String>(list.size)
                        for (i in list.indices) {
                            array[i] = list[i].name
                        }
                        dialog = ActionSheetDialog(this@EditLanguageActivity, array, null)
                        dialog?.run {
                            isTitleShow(false)
                                .lvBgColor(Color.parseColor("#FFFFFF"))
                                .itemTextColor(resources.getColor(R.color.color_gray_323232))
                                .cancelText(resources.getColor(R.color.color_gray_323232))
                        }

                    }
                }

                override fun onFailed() {

                }
            })
    }

    override fun initView() {
        toolbar_title.text = "语言技能"
    }

    private var languageId = ""
    private var sb = StringBuilder()
    private var sbIds = StringBuilder()
    private var listId = mutableListOf<String>()
    private var map = mutableMapOf<String, String>()
    override fun initListener() {
        tv_language.setOnClickListener {
            dialog?.show()
            dialog?.setOnOperItemClickL { parent, view, position, id ->
               if ( list.isNotEmpty()){
                   if (!listId.contains(list[position].magorid)) {
                       listId.add(list[position].magorid)
                       sb.append(list[position].name)
                       sb.append(",")
                       val languageString = sb.toString()
                       tv_language.text = languageString.substring(0, languageString.length-1)
                       dialog?.dismiss()
                   } else {
                       showToast("该语言已选过")
                   }
               }

            }
        }

        tv_confirm.setOnClickListener {//修改证书
            for (item in listId) {
                sbIds.append(item)
                sbIds.append(",")
            }
            val s = sbIds.toString()
            languageId = s.substring(0, s.length-1)
            if (languageId.isNotEmpty()) {
                map["languageId"] = languageId
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
            } else {
                showToast("请把信息填完整")
            }

        }
    }


}