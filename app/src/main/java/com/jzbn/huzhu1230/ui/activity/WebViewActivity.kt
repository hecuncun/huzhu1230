package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.annotation.SuppressLint
import android.os.Build
import android.view.KeyEvent
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.base.BaseNoDataBean
import com.jzbn.huzhu1230.bean.InsertCollectionResponseBean
import com.jzbn.huzhu1230.bean.SearchCollectionInfoResponseBean
import com.jzbn.huzhu1230.constants.Constant
import com.jzbn.huzhu1230.ext.showToast
import com.jzbn.huzhu1230.glide.GlideUtils
import com.jzbn.huzhu1230.net.CallbackListObserver
import com.jzbn.huzhu1230.net.CallbackObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import com.jzbn.huzhu1230.widget.MyWebView
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_video_detail.*
import kotlinx.android.synthetic.main.activity_webview.*
import kotlinx.android.synthetic.main.activity_webview.flBottomButton
import kotlinx.android.synthetic.main.activity_webview.tv_collect
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by hecuncun on 2019/12/14
 *
 * type: 0 关于我们  1 文章详情
 */
class WebViewActivity : BaseActivity() {
    private var type = -1
    private var url = ""
    private var objectId: String? = null
    private var isCollected = false
    private var ids = ""
    private var mWebView: MyWebView? = null
    override fun attachLayoutRes(): Int = R.layout.activity_webview
    override fun initData() {
        if (type == 1) {
            //文章 获取
            flBottomButton.visibility = View.VISIBLE
            ll_article_container.visibility = View.VISIBLE
            objectId = intent.extras.getString("objectId")
            val title = intent.extras.getString("title")
            val picUrl = intent.extras.getString("picUrl")
            val time = intent.extras.getString("time")
            val numViews = intent.extras.getInt("numViews")

            tv_title.text = title
            GlideUtils.showPlaceholder(this, iv_pic, Constant.BASE_URL + picUrl, R.mipmap.hz_logo)
            tv_create_time.text = "发布于$time"
            tv_numView.text = "$numViews 次浏览"
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
                            ids = t.magorid
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
        } else {
            flBottomButton.visibility = View.GONE
            ll_article_container.visibility = View.GONE
        }
    }

    /**
     * 富文本的样式做到适配屏幕
     */
    private fun getHtmlData(bodyHTML: String): String {
        val head = ("<head>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
                + "<style>img{max-width: 100%; width:100%; height:auto;}*{margin:0px;}</style>"
                + "</head>")
        return "<html>$head<body>$bodyHTML</body></html>"
    }

    override fun initView() {
        mWebView = findViewById(R.id.webView)
        type = intent.extras.getInt("type")
        url = intent.extras.getString("url")
        when (type) {
            0 -> toolbar_title.text = "关于我们"
            1 -> {
                ll_collection.visibility = View.VISIBLE
                toolbar_title.text = "文章详情"
            }

        }
        mWebView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                url: String?
            ): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        initWeb()
        setUrl(type)
    }

    private fun setUrl(type: Int) {
        mWebView?.post {
            when (type) {
                //加载 html
                0, 1 -> mWebView?.loadDataWithBaseURL(
                    null,
                    getHtmlData(url),
                    "text/html",
                    "utf-8",
                    null
                )
                //加载H5
                else -> {
                    mWebView?.loadUrl(url)
                }
            }
        }


    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWeb() {
        val settings = mWebView?.settings
        settings?.defaultTextEncodingName = "utf-8"
        settings?.javaScriptEnabled = true
        settings?.setSupportZoom(false)
        settings?.builtInZoomControls = true
        settings?.useWideViewPort = true
        settings?.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings?.loadWithOverviewMode = true
        //隐藏缩放控件
        settings?.displayZoomControls = false
        //解决HTTPS协议下出现的mixed content问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings?.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        settings?.cacheMode = WebSettings.LOAD_DEFAULT
        settings?.domStorageEnabled = true
        settings?.databaseEnabled = true
        settings?.setAppCachePath(cacheDir.path)
        settings?.setAppCacheEnabled(true)
    }

    override fun initListener() {
        flBottomButton.setOnClickListener {
            //收藏视频
            if (isCollected) {
                //取消收藏
                val deleteCollection = SLMRetrofit.getInstance().api.deleteCollection(ids)
                deleteCollection.compose(ThreadSwitchTransformer()).subscribe(object :
                    CallbackListObserver<BaseNoDataBean>() {
                    override fun onSucceed(t: BaseNoDataBean) {
                        if (t.code == Constant.SUCCESSED_CODE) {
                            showToast("取消收藏成功")
                            tv_collect.setCompoundDrawablesWithIntrinsicBounds(
                                resources.getDrawable(R.mipmap.icon_star_uncheck),
                                null,
                                null,
                                null
                            )
                            isCollected = false
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
                            ids = t.magorid
                            tv_collect.setCompoundDrawablesWithIntrinsicBounds(
                                resources.getDrawable(R.mipmap.icon_star),
                                null,
                                null,
                                null
                            )
                            isCollected = true
                            showToast("收藏成功")
                        }

                        override fun onFailed() {

                        }
                    })

            }

        }
    }

    override fun onPause() {
        super.onPause()
        mWebView?.pauseTimers()
    }

    override fun onResume() {
        super.onResume()
        mWebView?.resumeTimers()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (mWebView?.canGoBack()!!) {
            mWebView?.goBack()
        } else {
            finish()
        }
        return true
    }
//
//    companion object {
//        fun start(context: Context, url: String, title: String) {
//            val bundle = Bundle()
//            val intent = Intent(context, WebViewActivity::class.java)
//            bundle.putString("url", url)
//            bundle.putString("title", title)
//            intent.putExtras(bundle)
//            context.startActivity(intent)
//        }
//    }
}