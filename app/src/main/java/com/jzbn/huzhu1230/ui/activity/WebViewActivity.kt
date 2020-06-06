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
import com.jzbn.huzhu1230.widget.MyWebView
import kotlinx.android.synthetic.main.activity_webview.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by hecuncun on 2019/12/14
 *
 * type: 0 关于我们  1 文章详情
 */
class WebViewActivity :BaseActivity() {
    private var type = -1
    private var url =""
    private var mWebView: MyWebView? = null
    override fun attachLayoutRes(): Int= R.layout.activity_webview
    override fun initData() {
//        val bundle = intent.extras
//        if (bundle.containsKey("url")) {
//            val data = bundle.getString("url")
//
//        }
//        if (bundle.containsKey("title")) {
//            val title = bundle.getString("title")
//            toolbar_title.text=title
//        }
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
        mWebView=findViewById(R.id.webView)
        type = intent.extras.getInt("type")
        url = intent.extras.getString("url")
        when(type){
            0-> toolbar_title.text="关于我们"
            1-> {
                ll_collection.visibility= View.VISIBLE
                toolbar_title.text="文章详情"
            }

        }
        mWebView!!.webViewClient=object : WebViewClient() {
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

    private fun setUrl(type:Int) {
        mWebView?.post {
           when(type){
               //加载 html
               0,1->mWebView?.loadDataWithBaseURL(null,getHtmlData(url), "text/html" , "utf-8", null)
               //加载H5
               else->{mWebView?.loadUrl(url)}
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