package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.base.BaseNoDataBean
import com.jzbn.huzhu1230.bean.ImgBean
import com.jzbn.huzhu1230.bean.PersonalInfoBean
import com.jzbn.huzhu1230.constants.Constant
import com.jzbn.huzhu1230.event.RefreshUserInfoEvent
import com.jzbn.huzhu1230.ext.showToast
import com.jzbn.huzhu1230.glide.GlideUtils
import com.jzbn.huzhu1230.net.CallbackListObserver
import com.jzbn.huzhu1230.net.CallbackObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_my_info.*
import kotlinx.android.synthetic.main.toolbar.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File

/**
 * Created by hecuncun on 2020-5-24
 */
class MyInfoActivity : BaseActivity(), View.OnClickListener {

    override fun useEventBus(): Boolean=true
    override fun attachLayoutRes(): Int = R.layout.activity_my_info

    override fun initData() {
        //获取个人信息
        getUserInfo()
    }
//获取服务器数据
    private fun getUserInfo() {
        val personalInfoCall = SLMRetrofit.getInstance().api.personalInfoCall(uid)
        personalInfoCall.compose(ThreadSwitchTransformer())
            .subscribe(object : CallbackObserver<PersonalInfoBean>() {
                override fun onSucceed(t: PersonalInfoBean, desc: String?) {
                    initPersonData(t)
                }

                override fun onFailed() {

                }
            })
    }

    //初始化 信息
    private fun initPersonData(t: PersonalInfoBean) {
        GlideUtils.showCircle(iv_head_pic, Constant.BASE_URL+t.path, R.mipmap.icon_head_def)
        tv_name.text = t.name ?: ""
        tv_id_card.text = t.card ?: ""
        tv_sex.text = if (t.sex == 0) "女" else "男"
        tv_language.text =t.languageName
        tv_medical_history.text = t.caseHistory ?: ""
        tv_allergic_drugs.text = t.allergy ?: ""
        tv_genetic_history.text = t.inherit ?: ""
        tv_company.text = t.company ?: ""
        tv_rescue_history.text = t.experience ?: ""
        tv_certificate.text = t.zsCardName
        GlideUtils.showPlaceholder(this, iv_certificate, Constant.BASE_URL+t.zsCardPhoto, R.mipmap.icon_logo)//还有个证书状态
        tv_rescue_skill.text =t.skill
    }

    override fun initView() {
        toolbar_title.text = "我的资料"

    }

    override fun initListener() {
        rl_head_pic.setOnClickListener {
            showList()
        }
        rl_name.setOnClickListener(this)
        rl_id_card.setOnClickListener(this)
        rl_medical_history.setOnClickListener(this)
        rl_allergic_drugs.setOnClickListener(this)
        rl_genetic_history.setOnClickListener(this)
        rl_company.setOnClickListener(this)
        rl_rescue_history.setOnClickListener(this)
        rl_rescue_skill.setOnClickListener(this)
        rl_certificate.setOnClickListener {
            startActivity(Intent(this@MyInfoActivity, EditCertificateActivity::class.java))
        }
        rl_language.setOnClickListener{
            startActivity(Intent(this@MyInfoActivity, EditLanguageActivity::class.java))
        }
        rl_sex.setOnClickListener{
            startActivity(Intent(this@MyInfoActivity, EditSexActivity::class.java))
        }
    }

    private fun showList() {
        val items = arrayOf("拍照", "从相册中选择")
        val builder = AlertDialog.Builder(this)
            .setItems(items) { _, i ->
                Toast.makeText(this@MyInfoActivity, "你点击的内容为： " + items[i], Toast.LENGTH_LONG)
                    .show()
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
    private var map = mutableMapOf<String, String>()
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    val selectList = PictureSelector.obtainMultipleResult(data)
                    if (selectList.size > 0) {
                        GlideUtils.showCircle(
                            iv_head_pic,
                            selectList[0].compressPath,
                            R.drawable.ic_launcher_background
                        )
                        //上传文件
                        val file = File(selectList[0].compressPath)
                        Logger.e("图片地址==${selectList[0].compressPath}")
                        val requestFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                        //retrofit 上传文件api加上 @Multipart注解,然后下面这是个重点 参数1：上传文件的key，参数2：上传的文件名，参数3 请求头
                        val body: MultipartBody.Part = MultipartBody.Part.createFormData("upload", file.name, requestFile)
                        val uploadCall = SLMRetrofit.getInstance().api.uploadCall(body)
                        uploadCall.compose(ThreadSwitchTransformer()).subscribe(object:CallbackObserver<ImgBean>(){
                            override fun onSucceed(t: ImgBean, desc: String?) {
                                Logger.e("网络图片地址==${t.fileUrl}")
                                map["picture"]=t.fileUrl
                                //调用修改头像接口
                                val updateUserInfo = SLMRetrofit.getInstance().api.updateUserInfo(map, uid)
                                updateUserInfo.compose(ThreadSwitchTransformer())
                                    .subscribe(object : CallbackListObserver<BaseNoDataBean>() {
                                        override fun onSucceed(t: BaseNoDataBean) {
                                            if (t.code == Constant.SUCCESSED_CODE) {
                                                showToast("保存成功")
                                            } else {
                                                showToast(t.message)
                                            }
                                        }

                                        override fun onFailed() {

                                        }
                                    })

                            }

                            override fun onFailed() {
                                Logger.e("失败")
                            }
                        } )
                    } else {
                        showToast("图片出现问题")
                    }
                }
            }
        }
    }

    override fun onClick(v: View) {
        val intent = Intent(this@MyInfoActivity, EditInfoActivity::class.java)
        var type = -1

        when (v.id) {
            R.id.rl_name -> {
                type = 1
            }
            R.id.rl_id_card -> {
                type = 2
            }
            R.id.rl_medical_history -> {
                type = 5
            }
            R.id.rl_allergic_drugs -> {
                type = 6
            }
            R.id.rl_genetic_history -> {
                type = 7
            }
            R.id.rl_company -> {
                type = 8
            }
            R.id.rl_rescue_history -> {
                type = 9
            }
            R.id.rl_rescue_skill -> {
                type = 10
            }
            else -> {
                return
            }
        }
        intent.putExtra("type", type)
        startActivity(intent)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshUserInfo(event:RefreshUserInfoEvent){
        getUserInfo()
    }
}