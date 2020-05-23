package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.application.App
import com.jzbn.huzhu1230.utils.StatusBarUtil

import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_splash.*


/**
 * Created by hecuncun on 2019/11/12
 */
class SplashActivity : BaseActivity() {
    private var alphaAnimation: AlphaAnimation? = null


    override fun attachLayoutRes(): Int = R.layout.activity_splash
    override fun initData() {
        //获取启动页图片
//        val updateAppCall = SLMRetrofit.instance.api.updateAppCall()
//        updateAppCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackObserver<UpdateAppBean>(){
//            override fun onSucceed(t: UpdateAppBean, desc: String?) {
//               GlideUtils.showPlaceholder(this@SplashActivity,iv_splash,t.androidPic3,R.mipmap.splash)
//            }
//
//            override fun onFailed() {
//
//            }
//        })
    }

    override fun initStateBarColor() {
        val mThemeColor = App.context.resources.getColor(R.color.transparent)//设置状态栏颜色
        StatusBarUtil.setColor(this, mThemeColor, 100)
        if (this.supportActionBar != null) {
            this.supportActionBar?.setBackgroundDrawable(ColorDrawable(mThemeColor))
        }
    }

    override fun initView() {
        Logger.e("isFirst==$isFirst,isLogin==$isLogin")
        alphaAnimation = AlphaAnimation(0.3F, 1.0F)
        alphaAnimation?.run {
            duration = 2000
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {
                }

                override fun onAnimationEnd(p0: Animation?) {
                    if (isFirst){
                        jumpToGuide()
                    }else{
                        if (isLogin){
                            jumpToMain()
                        }else{
                            jumpToLogin()
                        }

                    }
                 overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }

                override fun onAnimationStart(p0: Animation?) {
                }
            })
        }

        splash_view.startAnimation(alphaAnimation)
    }

    private fun jumpToGuide() {
        val intent = Intent(this, GuideActivity::class.java)
        startActivity(intent)
        finish()

    }
    private fun jumpToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun jumpToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun initListener() {
    }

}