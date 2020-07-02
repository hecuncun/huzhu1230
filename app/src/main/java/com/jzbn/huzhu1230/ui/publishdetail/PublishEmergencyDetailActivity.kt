package com.jzbn.huzhu1230.ui.publishdetail

import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MyLocationStyle
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.base.BaseNoDataBean
import com.jzbn.huzhu1230.bean.AddressBean
import com.jzbn.huzhu1230.bean.SearchDetailBean
import com.jzbn.huzhu1230.constants.Constant
import com.jzbn.huzhu1230.ext.showToast
import com.jzbn.huzhu1230.glide.GlideUtils
import com.jzbn.huzhu1230.net.CallbackListObserver
import com.jzbn.huzhu1230.net.CallbackObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import com.jzbn.huzhu1230.ui.publish.BaseMapActivity
import com.jzbn.huzhu1230.utils.MapUtil
import com.stx.xhb.xbanner.XBanner
import com.stx.xhb.xbanner.entity.SimpleBannerInfo
import kotlinx.android.synthetic.main.activity_publish_emergency_detail_layout.*
import kotlinx.android.synthetic.main.toolbar.*

//长期寻人/紧急寻人详情页
class PublishEmergencyDetailActivity : BaseMapActivity() {
    private val mLoseModeArray = arrayOf("步行", "汽车", "火车")
    private val bannerBeans = mutableListOf<MyBannerInfo>()
    private var list = mutableListOf<AddressBean>()

    val mAddressAdapter: AddressesAdapter by lazy {
        AddressesAdapter()
    }

    override fun getMapView(): MapView = map

    override fun attachLayoutRes(): Int = R.layout.activity_publish_emergency_detail_layout

    private var magorid = ""
    private var type:String?=""
    private var latLng: LatLng? = null //被寻人丢失经纬度
    override fun initData() {
        magorid = intent.getStringExtra("magorid")
        val searchDetailCall = SLMRetrofit.getInstance().api.searchDetailCall(magorid)
        searchDetailCall.compose(ThreadSwitchTransformer())
            .subscribe(object : CallbackObserver<SearchDetailBean>() {
                override fun onSucceed(t: SearchDetailBean, desc: String?) {
                    val split = t.photo.split(",")
                    for (item in split) {
                        bannerBeans.add(MyBannerInfo(item))
                    }
                    banner.setBannerData(bannerBeans)
                    banner.loadImage(XBanner.XBannerAdapter { banner, model, view, position ->
                        //1、此处使用的Glide加载图片，可自行替换自己项目中的图片加载框架
                        //2、返回的图片路径为Object类型，你只需要强转成你传输的类型就行，切记不要胡乱强转！
                        GlideUtils.loadRoundImg(
                            view as ImageView,
                            Constant.BASE_URL + (model as MyBannerInfo).picUrl,
                            R.mipmap.icon_logo,
                            6
                        )
                    })
                    tv_status.text = if (t.findStatus == 1) "寻人中" else "人已找到"
                    GlideUtils.showCircle(
                        iv_avatar,
                        Constant.BASE_URL + t.createUserPhoto,
                        R.mipmap.icon_head_def
                    )
                    tv_publish_time.text = "发布于${t.createtime}"
                    tv_publish_name.text = t.createUserName
                    tv_publish_duration.text = "历时${t.duration}"
                    latLng = LatLng(t.latitude.toDouble(), t.longitude.toDouble())
                    setLocation()
                    mAddressAdapter.setNewData(t.findClueList)
                    tv_lose_name.text = t.name
                    tv_lose_look.text = t.features
                    val contacts = t.contact.split(",")
                    if (contacts.size == 1) {
                        contact_one.text = contacts[0]
                        contact_two.visibility = View.GONE
                        contact_three.visibility = View.GONE
                    } else if (contacts.size == 2) {
                        contact_one.text = contacts[0]
                        contact_two.text = contacts[1]
                        contact_three.visibility = View.GONE

                    } else if (contacts.isNotEmpty()) {
                        contact_one.text = contacts[0]
                        contact_two.text = contacts[1]
                        contact_three.text = contacts[2]
                    }
                    tv_lose_relationship.text = t.relation
                    tv_lose_mode.text = mLoseModeArray[t.way.toInt()]
                    tv_lose_reason.text = t.reason
                    GlideUtils.showAnimation(
                        iv_wechat_code,
                        Constant.BASE_URL + t.qrcode,
                        R.mipmap.icon_logo
                    )
                    tv_lose_tip.text = t.content ?: "无"

                }

                override fun onFailed() {

                }
            })

    }

    private var publishType = ""
    override fun initView() {
        publishType = intent.getStringExtra("publishType")
        if (publishType == "common") {
            toolbar_title.text = "长期寻人详情"
        } else {
            toolbar_title.text = "紧急寻人详情"
        }
        type = intent.getStringExtra("type")
        if (type=="my"){
            btn_find.visibility=View.VISIBLE
        }else{
            btn_find.visibility=View.GONE
        }
        rv_addresses.apply {
            layoutManager = LinearLayoutManager(this@PublishEmergencyDetailActivity)
            adapter = mAddressAdapter
        }

    }

    //不显示地图的中心  保证能直接定位到对应的经纬度
    override fun getLocationType(): Int = MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER

    //根据经纬度 设置marker
    override fun setLocation() {
        //添加系统默认 marker
        if (latLng != null) {
            MapUtil.addMarkerToMap(
                aMap!!,
                latLng,
                BitmapFactory.decodeResource(resources, R.mipmap.icon_location_yellow)
            )
            //将中心移到自己的位置
            val mCameraUpdate = CameraUpdateFactory.newCameraPosition(
                CameraPosition(latLng, 15f, 0f, 0f)
            )

            aMap!!.moveCamera(mCameraUpdate)
        }

    }


    override fun initListener() {
        btn_provide_something.setOnClickListener {
            val intent = Intent(this, ProvideClueActivity::class.java)
            intent.putExtra("magorid", magorid)
            startActivity(intent)
        }
        btn_find.setOnClickListener {
           val findCall = SLMRetrofit.getInstance().api.findCall(uid, magorid)
            findCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackListObserver<BaseNoDataBean>(){
                override fun onSucceed(t: BaseNoDataBean) {
                    if (t.code==Constant.SUCCESSED_CODE){
                        showToast("成功找到")
                    }else{
                        showToast(t.message)
                    }

                }

                override fun onFailed() {

                }
            })
        }
    }

    class MyBannerInfo(var picUrl: String) : SimpleBannerInfo() {
        override fun getXBannerUrl(): Any {
            return Constant.BASE_URL + picUrl
        }

    }
}