package com.jzbn.huzhu1230.ui.home

import BaseActivity
import cn.jzvd.JzvdStd
import com.jzbn.huzhu1230.R
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
        jz_player.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4","饺子闭眼睛", JzvdStd.SCREEN_NORMAL)
        GlideUtils.showPlaceholder(this,jz_player.thumbImageView,"http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640",R.mipmap.icon_logo)
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