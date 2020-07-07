package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Environment
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.HonorInfoBean
import com.jzbn.huzhu1230.ext.showToast
import com.jzbn.huzhu1230.net.CallbackListObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import com.jzbn.huzhu1230.utils.BitmapUtil
import com.jzbn.huzhu1230.utils.StatusBarUtil
import com.jzbn.huzhu1230.utils.ThreadUtils
import com.jzbn.huzhu1230.utils.UriUtils
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_my_honor.*
import kotlinx.android.synthetic.main.toolbar.*
import java.io.File

/**
 * Created by hecuncun on 2020-5-25
 */
class MyHonorActivity:BaseActivity() {
    override fun attachLayoutRes(): Int = R.layout.activity_my_honor

    override fun initData() {

        //获取荣誉积分
        val honorInfoCall = SLMRetrofit.getInstance().api.honorInfoCall(uid)
        honorInfoCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackListObserver<HonorInfoBean>(){
            override fun onSucceed(t: HonorInfoBean) {
                if (t.code=="10001"){
                    Logger.e(t.data)
                    tv_name.text=t.data
                }

            }

            override fun onFailed() {

            }
        })

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
        tv_save.setOnClickListener {
            //保存到本地
             //val linearLayoutBitmap = BitmapUtil.getLinearLayoutBitmap(ll_container)
             val linearLayoutBitmap = BitmapUtil.screenShot(this@MyHonorActivity)
             val picPath= getDir(this)+ System.currentTimeMillis().toString() + "huzhu.jpg"
             BitmapUtil.saveBitmap(linearLayoutBitmap, picPath)
            if (Build.VERSION.SDK_INT >= 29) {
                //适配android Q
                ThreadUtils.runOnSubThread {
                    UriUtils.saveImgToMediaStore(this@MyHonorActivity,picPath)
                }
            } else {
                MediaScannerConnection.scanFile(
                    this@MyHonorActivity.applicationContext,
                    arrayOf(picPath),
                    arrayOf("image/jpeg"),
                    null
                )

            }
            showToast("保存相册成功")

        }
    }

    fun getDir(context: Context): String? {
        val dir: String
        dir = if (Build.VERSION.SDK_INT >= 29) {
            context.getExternalFilesDir("").toString() + File.separator + "Media" + File.separator
        } else {
            (Environment.getExternalStorageDirectory()
                .toString() + File.separator + "DCIM"
                    + File.separator + "Camera" + File.separator)
        }
        val file = File(dir)
        if (!file.exists()) {
            file.mkdirs()
        }
        return dir
    }
}