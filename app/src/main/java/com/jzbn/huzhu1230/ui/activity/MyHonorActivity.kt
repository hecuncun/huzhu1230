package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.graphics.Paint
import com.jzbn.huzhu1230.R
import kotlinx.android.synthetic.main.activity_my_honor.*

/**
 * Created by hecuncun on 2020-5-25
 */
class MyHonorActivity:BaseActivity() {
    override fun attachLayoutRes(): Int = R.layout.activity_my_honor

    override fun initData() {

    }

    override fun initView() {
        tv_honor_ruler.paint.flags = Paint.UNDERLINE_TEXT_FLAG; //下划线
        tv_honor_ruler.paint.isAntiAlias = true;//抗锯齿
        tv_honor_ji_li.paint.flags = Paint.UNDERLINE_TEXT_FLAG; //下划线
        tv_honor_ji_li.paint.isAntiAlias = true;//抗锯齿
    }

    override fun initListener() {

    }
}