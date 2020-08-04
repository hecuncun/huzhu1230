package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.content.Context
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Environment
import android.view.View
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.constants.Constant
import com.jzbn.huzhu1230.ext.showToast
import com.jzbn.huzhu1230.glide.GlideUtils
import com.jzbn.huzhu1230.utils.BitmapUtil
import com.jzbn.huzhu1230.utils.ThreadUtils
import com.jzbn.huzhu1230.utils.UriUtils
import kotlinx.android.synthetic.main.activity_big_image.*
import java.io.File

/**
 * Created by heCunCun on 2020/7/21
 */
class BigImageActivity:BaseActivity() {
    override fun attachLayoutRes(): Int= R.layout.activity_big_image

    override fun initData() {

    }

    override fun initView() {
        val path = intent.getStringExtra("path")
        GlideUtils.showPlaceholder(this,iv_pic,Constant.BASE_URL+path,R.mipmap.hz_logo)


    }

    override fun initListener() {
        iv_pic.setOnLongClickListener {
            val linearLayoutBitmap = BitmapUtil.screenShot(this@BigImageActivity)
            val picPath= getDir(this) + System.currentTimeMillis().toString() + "huzhu.jpg"
            BitmapUtil.saveBitmap(linearLayoutBitmap, picPath)
            if (Build.VERSION.SDK_INT >= 29) {
                //适配android Q
                ThreadUtils.runOnSubThread {
                    UriUtils.saveImgToMediaStore(this@BigImageActivity,picPath)
                }
            } else {
                MediaScannerConnection.scanFile(
                    this@BigImageActivity.applicationContext,
                    arrayOf(picPath),
                    arrayOf("image/jpeg"),
                    null
                )

            }
            showToast("保存相册成功")
            true
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