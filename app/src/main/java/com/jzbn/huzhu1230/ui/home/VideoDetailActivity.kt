package com.jzbn.huzhu1230.ui.home

import BaseActivity
import cn.jzvd.JzvdStd
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.base.BaseNoDataBean
import com.jzbn.huzhu1230.bean.InsertCollectionResponseBean
import com.jzbn.huzhu1230.bean.KnowledgeBean
import com.jzbn.huzhu1230.bean.RescueVideoBean
import com.jzbn.huzhu1230.bean.SearchCollectionInfoResponseBean
import com.jzbn.huzhu1230.constants.Constant
import com.jzbn.huzhu1230.ext.showToast
import com.jzbn.huzhu1230.glide.GlideUtils
import com.jzbn.huzhu1230.net.CallbackListObserver
import com.jzbn.huzhu1230.net.CallbackObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_video_detail.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by hecuncun on 2020-6-6
 */
class VideoDetailActivity : BaseActivity() {
    private var objectId = ""
    private var ids=""//收藏的信息 主键id
    private var type = 0
    private var isCollected = false
    override fun attachLayoutRes(): Int = R.layout.activity_video_detail

    override fun initData() {
        toolbar_title.text = resources.getString(R.string.video_content)
    }

    override fun initView() {
        val bean = intent.getParcelableExtra<KnowledgeBean.RowsBean>("bean")
        if (bean != null) {
            //从救援知识页跳过来的详情
            objectId = bean.magorid
            type = 2
            tvVideoTitle.text = bean.title
            jz_player.setUp(Constant.BASE_URL + bean.path, bean.title, JzvdStd.SCREEN_NORMAL)
            GlideUtils.showPlaceholder(
                this,
                jz_player.thumbImageView,
                Constant.BASE_URL + bean.photo,
                R.mipmap.icon_logo
            )
            tvPublishTime.text = "发布于${bean.createtime.split(" ")[0]}"
            tvPlayTimes.text = "${bean.numPlay}次播放"
        }
        val pid = intent.getStringExtra("pid")
        if (pid != null && pid.isNotEmpty()) {
            objectId = pid
            type = 3
            //从三级分类传过来的
            //获取救援详情
            val rescueVideoCall = SLMRetrofit.getInstance().api.rescueVideoCall(pid)
            rescueVideoCall.compose(ThreadSwitchTransformer())
                .subscribe(object : CallbackObserver<RescueVideoBean>() {
                    override fun onSucceed(t: RescueVideoBean, desc: String?) {
                        tvVideoTitle.text = t.title
                        jz_player.setUp(Constant.BASE_URL + t.path, t.title, JzvdStd.SCREEN_NORMAL)
                        GlideUtils.showPlaceholder(
                            this@VideoDetailActivity,
                            jz_player.thumbImageView,
                            Constant.BASE_URL + t.remark1,
                            R.mipmap.icon_logo
                        )
                        tvPublishTime.text = "发布于${t.createtime.split(" ")[0]}"
                        tvPlayTimes.text = "${t.numPlay}次播放"
                    }

                    override fun onFailed() {

                    }
                })
        }

        //查询是否已收藏
        val searchCollectInfoCall =
            SLMRetrofit.getInstance().api.searchCollectInfoCall(uid, objectId)
        searchCollectInfoCall.compose(ThreadSwitchTransformer())
            .subscribe(object : CallbackObserver<SearchCollectionInfoResponseBean>() {
                override fun onSucceed(t: SearchCollectionInfoResponseBean?, desc: String?) {
                    if (t == null) {
                        isCollected = false
                        Logger.e("未收藏 ")
                        tv_collect.setCompoundDrawablesWithIntrinsicBounds(
                            resources.getDrawable(R.mipmap.icon_star_uncheck),
                            null,
                            null,
                            null
                        )
                    } else {

                        ids=t.magorid
                        isCollected = true
                        tv_collect.setCompoundDrawablesWithIntrinsicBounds(
                            resources.getDrawable(R.mipmap.icon_star),
                            null,
                            null,
                            null
                        )
                        Logger.e("已收藏 ids=$ids ")
                    }

                }

                override fun onFailed() {

                }
            })
    }

    override fun initListener() {
        flBottomButton.setOnClickListener {
            //收藏视频
            if (isCollected) {
                //取消收藏
                val deleteCollection = SLMRetrofit.getInstance().api.deleteCollection(ids)
                deleteCollection.compose(ThreadSwitchTransformer()).subscribe(object:CallbackListObserver<BaseNoDataBean>(){
                    override fun onSucceed(t: BaseNoDataBean) {
                       if (t.code==Constant.SUCCESSED_CODE){
                           showToast("取消收藏成功")
                           tv_collect.setCompoundDrawablesWithIntrinsicBounds(
                               resources.getDrawable(R.mipmap.icon_star_uncheck),
                               null,
                               null,
                               null
                           )
                           isCollected=false
                       }
                    }

                    override fun onFailed() {
                    }
                })
            } else {
                //收藏
                val insertCollectCall =
                    SLMRetrofit.getInstance().api.insertCollectCall(uid, objectId, type)
                insertCollectCall.compose(ThreadSwitchTransformer())
                    .subscribe(object : CallbackObserver<InsertCollectionResponseBean>() {
                        override fun onSucceed(t: InsertCollectionResponseBean, desc: String?) {
                            ids=t.magorid
                            tv_collect.setCompoundDrawablesWithIntrinsicBounds(
                                resources.getDrawable(R.mipmap.icon_star),
                                null,
                                null,
                                null
                            )
                            isCollected=true
                            showToast("收藏成功")
                        }

                        override fun onFailed() {

                        }
                    })

            }

        }
    }

    override fun onBackPressed() {
        if (JzvdStd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        JzvdStd.goOnPlayOnPause()
    }

    override fun onResume() {
        super.onResume()
        JzvdStd.goOnPlayOnResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        JzvdStd.releaseAllVideos()
    }
}