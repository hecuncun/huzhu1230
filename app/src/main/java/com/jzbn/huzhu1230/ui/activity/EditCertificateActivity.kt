package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.widget.Toast
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.CertificateBean
import com.jzbn.huzhu1230.constants.Constant
import com.jzbn.huzhu1230.ext.showToast
import com.jzbn.huzhu1230.glide.GlideUtils
import com.jzbn.huzhu1230.net.CallbackListObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import kotlinx.android.synthetic.main.activity_edit_certificate.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by hecuncun on 2020-5-24
 */
class EditCertificateActivity:BaseActivity() {
    override fun attachLayoutRes(): Int = R.layout.activity_edit_certificate

    private var list = mutableListOf<CertificateBean.DataBean>()

    override fun initData() {
        //获取证书列表
        val certificateBeanCall = SLMRetrofit.getInstance().api.certificateBeanCall()
        certificateBeanCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackListObserver<CertificateBean>(){
            override fun onSucceed(t: CertificateBean) {
                if (t.code==Constant.SUCCESSED_CODE){
                    list.addAll(t.data)
                }

            }

            override fun onFailed() {

            }
        })

    }

    override fun initView() {
       toolbar_title.text="获得证书"
    }

    override fun initListener() {
        iv_pic.setOnClickListener {
            showList()
        }
    }

    private fun showList() {
        val items = arrayOf("拍照", "从相册中选择")
        val  builder = AlertDialog.Builder(this)
            .setItems(items) { _, i ->
                Toast.makeText(this@EditCertificateActivity, "你点击的内容为： " + items[i], Toast.LENGTH_LONG).show()
                selectImage(i)
            }
        builder.create().show()
    }
    private fun selectImage(i: Int) {
        if (i == 0) {
            PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .withAspectRatio(3, 2)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .minimumCompressSize(200)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST)
        } else {
            PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage()) //全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(1)// 最大图片选择数量 int
                .imageSpanCount(3)
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .withAspectRatio(3, 2)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .minimumCompressSize(200)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true).// 裁剪是否可放大缩小图片 true or false
                    isDragFrame(false).// 是否可拖动裁剪框(固定)
                    forResult(PictureConfig.CHOOSE_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    val selectList = PictureSelector.obtainMultipleResult(data)
                    if (selectList.size > 0) {
                        GlideUtils.showAnimation(iv_pic,selectList[0].compressPath,R.drawable.ic_launcher_background)
                        //上传文件
//                        val file = File(selectList[0].compressPath)
//                        Logger.e("图片地址==${selectList[0].compressPath}")
//                        val requestFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
//                        //retrofit 上传文件api加上 @Multipart注解,然后下面这是个重点 参数1：上传文件的key，参数2：上传的文件名，参数3 请求头
//                        val body: MultipartBody.Part = MultipartBody.Part.createFormData("upload", file.name, requestFile)
//                        val uploadCall = SLMRetrofit.getInstance().api.uploadCall(body)
//                        uploadCall.compose(ThreadSwitchTransformer()).subscribe(object:CallbackObserver<ImgBean>(){
//                            override fun onSucceed(t: ImgBean?, desc: String?) {
//                                Logger.e("成功")
//                                Logger.e("网络图片地址==${t?.fileUrl}")
//                                photo=t?.fileUrl?:photo
//                                //调用修改头像接口
//                                val updateInfoCall = SLMRetrofit.getInstance().api.updateInfoCall(uid, null, photo)
//                                updateInfoCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackListObserver<BaseNoDataBean>(){
//                                    override fun onSucceed(t: BaseNoDataBean?) {
//                                        if (t?.code== Constant.SUCCESSED_CODE){
//                                            showToast("头像修改成功")
//                                            EventBus.getDefault().post(UpdateInfoEvent())
//                                        }else{
//                                            showToast("头像修改失败")
//                                        }
//                                    }
//
//                                    override fun onFailed() {
//                                    }
//                                })
//
//                            }
//
//                            override fun onFailed() {
//                                Logger.e("失败")
//                            }
//                        } )
                    } else {
                        showToast("图片出现问题")
                    }
                }
            }
        }
    }
}