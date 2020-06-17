package com.jzbn.huzhu1230.ui.home

import BaseActivity
import cn.jzvd.JzvdStd
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.KnowledgeBean
import com.jzbn.huzhu1230.constants.Constant
import com.jzbn.huzhu1230.glide.GlideUtils
import kotlinx.android.synthetic.main.activity_video_detail.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by hecuncun on 2020-6-6
 */
class VideoDetailActivity:BaseActivity() {
    override fun attachLayoutRes(): Int = R.layout.activity_video_detail

    override fun initData() {
        toolbar_title.text = resources.getString(R.string.video_content)
    }

    override fun initView() {
        val bean= intent.getParcelableExtra<KnowledgeBean.RowsBean>("bean")
        if (bean!=null){
            jz_player.setUp(Constant.BASE_URL+bean.path,bean.title, JzvdStd.SCREEN_NORMAL)
            GlideUtils.showPlaceholder(this,jz_player.thumbImageView,Constant.BASE_URL+bean.photo,R.mipmap.icon_logo)
            tvPublishTime.text="发布于${bean.createtime.split(" ")[0]}"
        }

    }

    override fun initListener() {
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