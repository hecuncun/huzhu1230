package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_my_honor.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by hecuncun on 2020-5-25
 */
class MyHonorActivity:BaseActivity() {
    override fun attachLayoutRes(): Int = R.layout.activity_my_honor

    override fun initData() {

    }

    override fun initStateBarColor() {
        val mThemeColor =Color.parseColor("#FF431FC3")//设置状态栏颜色
        StatusBarUtil.setColor(this, mThemeColor, 0)
        if (this.supportActionBar != null) {
            this.supportActionBar?.setBackgroundDrawable(ColorDrawable(mThemeColor))
        }
    }

    override fun initView() {
        toolbar_title.text="个人荣誉"
        tv_honor_ruler.paint.flags = Paint.UNDERLINE_TEXT_FLAG; //下划线
        tv_honor_ruler.paint.isAntiAlias = true;//抗锯齿
        tv_honor_ji_li.paint.flags = Paint.UNDERLINE_TEXT_FLAG; //下划线
        tv_honor_ji_li.paint.isAntiAlias = true;//抗锯齿
    }

    override fun initListener() {

    }
}