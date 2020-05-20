package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.content.Intent
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.adapter.GuidePageAdapter

import kotlinx.android.synthetic.main.activity_guide.*
import kotlinx.android.synthetic.main.guide_3.view.*

/**
 * Created by hecuncun on 2020/4/26
 */
class GuideActivity : BaseActivity() {
    private var viewList = mutableListOf<View>()
    private val dotList = mutableListOf<ImageView>()
    override fun attachLayoutRes(): Int {
        return R.layout.activity_guide
    }

    override fun initData() {
    }

    override fun initView() {
        isFirst=false
        val view1 = LayoutInflater.from(this).inflate(R.layout.guide_1, null)
        val view2 = LayoutInflater.from(this).inflate(R.layout.guide_2, null)
        val view3 = LayoutInflater.from(this).inflate(R.layout.guide_3, null)
        viewList.add(view1)
        viewList.add(view2)
        viewList.add(view3)
        dotList.add(iv1)
        dotList.add(iv2)
        dotList.add(iv3)
        viewpager.adapter= GuidePageAdapter(viewList as ArrayList<View>)
        view3.btn_finish.setOnClickListener {

            if(isLogin){
             jumpToMain()
            }else{
             jumpToLogin()
            }
        }
        viewpager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                for (i in dotList.indices){
                    if (position==i){
                        dotList[i].setImageResource(R.mipmap.icon_dot_selected)
                    }else{
                       dotList[i].setImageResource(R.mipmap.icon_dot_normal)
                    }
                }

            }
        })
    }

    override fun initListener() {
    }

    private fun jumpToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun jumpToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}