package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.content.Intent
import android.graphics.Paint
import android.view.View
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.PersonalInfoBean
import com.jzbn.huzhu1230.net.CallbackObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import kotlinx.android.synthetic.main.activity_my_score.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by hecuncun on 2020-5-24
 */
class MyScoreActivity:BaseActivity() {
    override fun attachLayoutRes(): Int = R.layout.activity_my_score

    override fun initData() {
        getUserInfo()
    }
    private fun getUserInfo() {
        val personalInfoCall = SLMRetrofit.getInstance().api.personalInfoCall(uid)
        personalInfoCall.compose(ThreadSwitchTransformer())
            .subscribe(object : CallbackObserver<PersonalInfoBean>() {
                override fun onSucceed(t: PersonalInfoBean, desc: String?) {
                    tv_score.text=t.integral.toString()
                }

                override fun onFailed() {

                }
            })
    }
    override fun initView() {
        toolbar_title.text="我的积分"
        toolbar_right_tv.text="明细"
        toolbar_right_tv.visibility= View.VISIBLE
        tv_score_ruler.paint.flags = Paint.UNDERLINE_TEXT_FLAG; //下划线
        tv_score_ruler.paint.isAntiAlias = true;//抗锯齿
        tv_score_des.paint.flags = Paint.UNDERLINE_TEXT_FLAG; //下划线
        tv_score_des.paint.isAntiAlias = true;//抗锯齿
    }

    override fun initListener() {
        toolbar_right_tv.setOnClickListener {
            startActivity(Intent(this,ScoreListActivity::class.java))
        }
    }
}