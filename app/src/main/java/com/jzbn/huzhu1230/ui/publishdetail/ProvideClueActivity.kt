package com.jzbn.huzhu1230.ui.publishdetail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Point
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import cn.qqtheme.framework.entity.City
import cn.qqtheme.framework.entity.County
import cn.qqtheme.framework.entity.Province
import cn.qqtheme.framework.picker.DateTimePicker
import cn.qqtheme.framework.picker.WheelPicker
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker
import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.RegeocodeResult
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.application.App
import com.jzbn.huzhu1230.base.BaseNoDataBean
import com.jzbn.huzhu1230.bean.ImgBean
import com.jzbn.huzhu1230.constants.Constant
import com.jzbn.huzhu1230.event.RefreshDetailEvent
import com.jzbn.huzhu1230.ext.showToast
import com.jzbn.huzhu1230.glide.GlideUtils
import com.jzbn.huzhu1230.net.CallbackListObserver
import com.jzbn.huzhu1230.net.CallbackObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import com.jzbn.huzhu1230.picker.AddressPickTask
import com.jzbn.huzhu1230.ui.publish.BaseMapActivity
import com.jzbn.huzhu1230.utils.CommonUtil
import com.jzbn.huzhu1230.utils.MapUtil
import com.jzbn.huzhu1230.widget.LoadingView
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_provide_clue_layout.*
import kotlinx.android.synthetic.main.toolbar.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.greenrobot.eventbus.EventBus
import java.io.File
import java.util.*

//提供线索 页面
class ProvideClueActivity : BaseMapActivity(), View.OnClickListener {
    private var loadingView: LoadingView? = null
    override fun getMapView(): MapView = map

    override fun attachLayoutRes(): Int = R.layout.activity_provide_clue_layout

    override fun initData() {
    }

    private var findId = ""
    private var area = ""
    private var areaDetail = ""
    private var longitude = ""
    private var latitude = ""
    private var ddate = ""
    private var photo = ""
    private var picUrl1 = ""
    private var picUrl2 = ""
    private var picUrl3 = ""
    override fun initView() {
        toolbar_title.text = "提供线索"
        findId = intent.getStringExtra("magorid")
        val photoMain = intent.getStringExtra("photoMain")
        val loseTime = intent.getStringExtra("ddate")
        val loseDuration = intent.getStringExtra("duration")
        val loseAddress = intent.getStringExtra("address")
        GlideUtils.showPlaceholder(this,iv_photo,Constant.BASE_URL+photoMain,R.mipmap.icon_logo)
        tv_lose_time.text="走失于$loseTime"
        tv_lose_duration.text="已走失$loseDuration"
        tv_lose_address.text=loseAddress


    }

    override fun initListener() {
        ll_select_time.setOnClickListener {
            showTimePickerDialog()
        }
        ll_select_address.setOnClickListener {
            showCityPickerDialog()
        }
        //选择图片
        pic_one.setOnClickListener(this)
        pic_two.setOnClickListener(this)
        pic_three.setOnClickListener(this)

        et_detail_address.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                areaDetail = s.toString().trim()
                val address = area + areaDetail
                MapUtil.getLatLonPointFromAddress(address, geocoderSearch)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        btn_provide.setOnClickListener {
            photo = CommonUtil.getAppendString(picUrl1, picUrl2, picUrl3)
            val content = et_remark.text.toString().trim()
            if (photo.isNotEmpty() && ddate.isNotEmpty() && area.isNotEmpty()
                && areaDetail.isNotEmpty() && longitude.isNotEmpty() && latitude.isNotEmpty()
            ) {
                loadingView = LoadingView(this)
                loadingView?.setLoadingTitle("提交中...")
                val provideClueCall = SLMRetrofit.getInstance().api.provideClueCall(
                    uid,
                    findId,
                    photo,
                    ddate,
                    area,
                    areaDetail,
                    longitude,
                    latitude,
                    content
                )
                provideClueCall.compose(ThreadSwitchTransformer()).subscribe(object :
                    CallbackListObserver<BaseNoDataBean>() {
                    override fun onSucceed(t: BaseNoDataBean?) {
                        showToast("提供线索成功")
                      EventBus.getDefault().post(RefreshDetailEvent())
                        finish()
                    }

                    override fun onFailed() {

                    }

                })
            }else{
                showToast("请把信息填写完整")
            }
        }
    }

    /**
     * 显示城市选择器
     */
    private fun showCityPickerDialog() {
        val task = AddressPickTask(this)
        task.setHideProvince(false)
        task.setHideCounty(false)
        task.setCallback(object : AddressPickTask.Callback {
            override fun onAddressInitFailed() {
                showToast("数据初始化失败")
            }

            override fun onAddressPicked(province: Province, city: City, county: County?) {
                if (county == null) {
                    showToast(province.areaName + city.areaName)
                    tv_address.text = province.areaName + city.areaName
                    area = province.areaName + city.areaName
                } else {
                    showToast(province.areaName + city.areaName + county.areaName)
                    tv_address.text = province.areaName + city.areaName + county.areaName
                    area = province.areaName + city.areaName + county.areaName
                }
            }

        })
        task.execute("北京", "北京", "昌平")
    }


    /**
     * 显示时间选择器
     */
    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH] + 1
        val day = calendar[Calendar.DAY_OF_MONTH]
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]

        val picker = DateTimePicker(this, DateTimePicker.HOUR_24)
        picker.setDateRangeStart(2000, 1, 1)
        picker.setDateRangeEnd(year, month, day)
        picker.setTimeRangeStart(0, 0)
        picker.setTimeRangeEnd(hour, minute)
        picker.setSelectedItem(year,month,day,hour,minute)
        picker.setTextColor(Color.parseColor("#333333"))
        picker.setTextSize(18)
        picker.setDividerColor(Color.parseColor("#F6B900"))
        picker.setCancelTextSize(18)
        picker.setCancelTextColor(Color.parseColor("#F6B900"))
        picker.setSubmitTextSize(18)
        picker.setSubmitTextColor(Color.parseColor("#F6B900"))
        picker.setTopLineColor(-0x66010000)
        picker.setLabelTextColor(-0x10000)
        picker.setDividerColor(-0x10000)
        picker.setOnDateTimePickListener(DateTimePicker.OnYearMonthDayTimePickListener { year, month, day, hour, minute ->
            showToast("$year-$month-$day $hour:$minute")
            tv_time.text = "$year-$month-$day $hour:$minute"
            ddate = "$year-$month-$day $hour:$minute:00"
        })
        fitScreenWidth(picker)
        picker.show()
    }

    //兼容今日头条适配方案
    private fun fitScreenWidth(picker: WheelPicker) {
        val wm = App.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        wm.defaultDisplay.getSize(point)
        val params = picker.window.attributes
        params.width = point.x
    }

    var infoWindow: View? = null
    override fun onGeocodeSearched(geocodeResult: GeocodeResult?, p1: Int) {
        //根据地理位置描述 搜索出来的结果
        geocodeResult?.apply {
            if (geocodeAddressList.isNotEmpty()) {
                val geocodeAddress = geocodeAddressList[0]
                val latLonPoint = geocodeAddress.latLonPoint
                //经纬度
                val latLng = LatLng(latLonPoint.latitude, latLonPoint.longitude)
                longitude = latLonPoint.longitude.toString()
                latitude = latLonPoint.latitude.toString()
                Log.d(
                    "HAHAHHAHA",
                    "onRegeocodeSearched: City" + geocodeAddress.city
                )
                Log.d(
                    "HAHAHHAHA",
                    "onRegeocodeSearched: latitude" + latLonPoint.latitude
                )
                Log.d(
                    "HAHAHHAHA",
                    "onRegeocodeSearched: longitude" + latLonPoint.longitude
                )

                //添加系统默认 marker
//                MapUtil.addMarkerToMap(
//                    aMap!!,
//                    latLng,
//                    BitmapFactory.decodeResource(resources, R.mipmap.icon_location_yellow)
//                )
                MapUtil.addInfoWindowToMap(aMap!!,
                    LatLng(latLonPoint.latitude, latLonPoint.longitude),
                    BitmapFactory.decodeResource(resources, R.mipmap.icon_location_yellow),
                    object :
                        AMap.InfoWindowAdapter {
                        override fun getInfoContents(p0: Marker?): View? {
                            return null
                        }

                        override fun getInfoWindow(p0: Marker?): View {
                            if (infoWindow == null) {
                                infoWindow = LayoutInflater.from(this@ProvideClueActivity)
                                    .inflate(R.layout.custom_info_window, null)
                                val tvInfo =
                                    infoWindow!!.findViewById<TextView>(R.id.tv_info)
                                tvInfo.text = "标记这里"
                            }
                            return infoWindow!!

                        }
                    })
                //将中心移到自己的位置
                val mCameraUpdate = CameraUpdateFactory.newCameraPosition(
                    CameraPosition(latLng, 15f, 0f, 0f)
                )

                aMap!!.moveCamera(mCameraUpdate)
            }
        }

    }

    override fun onRegeocodeSearched(regeocodeResul: RegeocodeResult?, p1: Int) {
    }


    private var type = -1// 1 ， 2 ，3代表选的图片
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.pic_one -> {
                type = 1
                selectImage(1)
            }
            R.id.pic_two -> {
                type = 2
                selectImage(2)
            }
            R.id.pic_three -> {
                type = 3
                selectImage(3)
            }
        }
    }

    private fun selectImage(type: Int) {
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage()) //全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
            .maxSelectNum(1)// 最大图片选择数量 int
            .imageSpanCount(3)
            .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
            .previewImage(true)// 是否可预览图片 true or false
            .isCamera(true)// 是否显示拍照按钮 true or false
            .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
            .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
            .enableCrop(false)// 是否裁剪 true or false
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    var pic: ImageView? = null
                    val selectList = PictureSelector.obtainMultipleResult(data)
                    if (selectList.size > 0) {
                        when (type) {
                            1 -> {
                                pic = pic_one
                            }
                            2 -> {
                                pic = pic_two
                            }
                            3 -> {
                                pic = pic_three
                            }

                        }
                        GlideUtils.loadRoundImg(
                            pic,
                            selectList[0].compressPath,
                            R.drawable.ic_launcher_background,
                            6
                        )
                        //上传文件
                        val file = File(selectList[0].compressPath)
                        Logger.e("图片地址==${selectList[0].compressPath}")
                        val requestFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                        //retrofit 上传文件api加上 @Multipart注解,然后下面这是个重点 参数1：上传文件的key，参数2：上传的文件名，参数3 请求头
                        val body: MultipartBody.Part = MultipartBody.Part.createFormData("upload", file.name, requestFile)
                        val uploadCall = SLMRetrofit.getInstance().api.uploadCall(body)
                        uploadCall.compose(ThreadSwitchTransformer()).subscribe(object:
                            CallbackObserver<ImgBean>(){
                            override fun onSucceed(t: ImgBean, desc: String?) {
                                Logger.e("成功")
                                Logger.e("网络图片地址==${t.fileUrl}")
                                when(type){
                                    1->{picUrl1=t.fileUrl}
                                    2->{picUrl2=t.fileUrl}
                                    3->{picUrl3=t.fileUrl}
                                }
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

}