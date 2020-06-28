package com.jzbn.huzhu1230.ui.fragment

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.adapter.KnowledgeAdapter
import com.jzbn.huzhu1230.bean.KnowledgeBean
import com.jzbn.huzhu1230.bean.MessageUnReadBean
import com.jzbn.huzhu1230.constants.Constant
import com.jzbn.huzhu1230.ext.showToast
import com.jzbn.huzhu1230.net.CallbackListObserver
import com.jzbn.huzhu1230.net.CallbackObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import com.jzbn.huzhu1230.ui.activity.MoreKnowledgeActivity
import com.jzbn.huzhu1230.ui.activity.SearchHelpActivity
import com.jzbn.huzhu1230.ui.activity.WebViewActivity
import com.jzbn.huzhu1230.ui.home.MessageActivity
import com.jzbn.huzhu1230.ui.home.VideoDetailActivity
import com.lhzw.bluetooth.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_knowledge.*
import kotlinx.android.synthetic.main.toolbar.*

// Created by hesanwei on 2020/5/24.
class KnowledgeFragment: BaseFragment() {
    private var articleList= mutableListOf<KnowledgeBean.RowsBean>()
    private var videoList= mutableListOf<KnowledgeBean.RowsBean>()
    private val articleAdapter :KnowledgeAdapter by lazy {
        KnowledgeAdapter()
    }
    private val videoAdapter :KnowledgeAdapter by lazy {
        KnowledgeAdapter()
    }
    override fun attachLayoutRes(): Int = R.layout.fragment_knowledge

    override fun initView(view: View) {
        toolbar_title.text="互助项目"
        toolbar_right_img.setImageResource(R.mipmap.icon_look_msg)
        toolbar_right_img.visibility=View.VISIBLE
        initRecyclerView()
        getUnReadMsg()
    }
    //可见就刷新下消息
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!isHidden){
            getUnReadMsg()
        }
    }

    private fun getUnReadMsg() {
        //系统未读
        val sysMsgUnreadNumCall = SLMRetrofit.getInstance().api.getSysMsgUnreadNumCall(uid)
        sysMsgUnreadNumCall.compose(ThreadSwitchTransformer()).subscribe(object :
            CallbackListObserver<MessageUnReadBean>(){
            override fun onSucceed(t: MessageUnReadBean) {
                if (t.code== Constant.SUCCESSED_CODE){
                    if (t.data>0){
                        toolbar_red_dot.visibility=View.VISIBLE
                    }else{

                    }
                }else{
                    showToast(t.message)
                }
            }

            override fun onFailed() {

            }
        })
        //平台未读
        val platFormMsgUnreadNumCall = SLMRetrofit.getInstance().api.platFormMsgUnreadNumCall
        platFormMsgUnreadNumCall.compose(ThreadSwitchTransformer()).subscribe(object :
            CallbackListObserver<MessageUnReadBean>(){
            override fun onSucceed(t: MessageUnReadBean) {
                if (t.code== Constant.SUCCESSED_CODE){
                    if (t.data>plantFormMsgReadNum){//有新消息
                        toolbar_red_dot.visibility=View.VISIBLE
                    }else{

                    }
                }else{
                    showToast(t.message)
                }
            }

            override fun onFailed() {

            }
        })
    }
    private fun initRecyclerView() {
        rv_video.run {
            layoutManager = LinearLayoutManager(activity)
            adapter =videoAdapter
        }
        rv_article.run {
            layoutManager = LinearLayoutManager(activity)
            adapter =articleAdapter
        }
    }
    override fun initListener() {
        toolbar_right_img.setOnClickListener {
            startActivity(Intent(context, MessageActivity::class.java))
        }
        articleAdapter.setOnItemClickListener { adapter, view, position ->
            val intent = Intent(activity, WebViewActivity::class.java)
            intent.putExtra("type",1)
            intent.putExtra("url",articleList[position].content)
            intent.putExtra("objectId",articleList[position].magorid)
            startActivity(intent)
        }
        videoAdapter.setOnItemClickListener { adapter, view, position ->
            val intent = Intent(activity, VideoDetailActivity::class.java)
            intent.putExtra("bean",videoList[position])
            startActivity(intent)
        }
        tv_more_article.setOnClickListener {
            val intent =Intent(activity, MoreKnowledgeActivity::class.java)
            intent.putExtra("title","救援知识·热门文章")
            startActivity(intent)
        }
        tv_more_video.setOnClickListener {
            val intent =Intent(activity, MoreKnowledgeActivity::class.java)
            intent.putExtra("title","救援知识·热门视频")
            startActivity(intent)
        }

        ll_search_help.setOnClickListener { //进入寻找互助项目页
            val intent =Intent(activity, SearchHelpActivity::class.java)
            startActivity(intent)
        }
    }

    override fun lazyLoad() {
        val articleListCall = SLMRetrofit.getInstance().api.knowledgeListCall(
            1,
            "",
            1
        )
        articleListCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackObserver<KnowledgeBean>(){
            override fun onSucceed(t: KnowledgeBean, desc: String?) {
                articleList.addAll(t.rows)
                articleAdapter.setNewData(articleList)
            }

            override fun onFailed() {

            }
        })

        val videoListCall = SLMRetrofit.getInstance().api.knowledgeListCall(
            1,
            "",
            2
        )
        videoListCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackObserver<KnowledgeBean>(){
            override fun onSucceed(t: KnowledgeBean, desc: String?) {
                videoList.addAll(t.rows)
                videoAdapter.setNewData(videoList)
            }

            override fun onFailed() {

            }
        })

    }
    companion object {
        fun getInstance(): KnowledgeFragment {
            return KnowledgeFragment()
        }
    }
}