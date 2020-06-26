package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.adapter.KnowledgeAdapter
import com.jzbn.huzhu1230.bean.KnowledgeBean
import com.jzbn.huzhu1230.net.CallbackObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import com.jzbn.huzhu1230.ui.home.VideoDetailActivity
import kotlinx.android.synthetic.main.activity_more_knowledge.recyclerView
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by hecuncun on 2020-5-30
 */
class MoreKnowledgeActivity:BaseActivity() {
    private var list= mutableListOf<KnowledgeBean.RowsBean>()
    private var type =1//1文章 2视频
    private var currentPage = 1
    private var total = 0
    private val knowledgeAdapter : KnowledgeAdapter by lazy {
        KnowledgeAdapter()
    }
    override fun attachLayoutRes(): Int = R.layout.activity_more_knowledge
    override fun initData() {
        val knowledgeListCall = SLMRetrofit.getInstance().api.knowledgeListCall(
            1,
            "",
            type
        )
        knowledgeListCall.compose(ThreadSwitchTransformer()).subscribe(object :
            CallbackObserver<KnowledgeBean>(){
            override fun onSucceed(t: KnowledgeBean, desc: String?) {
                total = t.total
                list.addAll(t.rows)
                knowledgeAdapter.setNewData(list)
            }

            override fun onFailed() {

            }
        })
    }

    override fun initView() {
        toolbar_title.text=intent.getStringExtra("title")
        type = if (intent.getStringExtra("title")=="救援知识·热门视频") 2 else 1
        initRecyclerView()
    }
    private fun initRecyclerView() {
        recyclerView.run {
            layoutManager = LinearLayoutManager(this@MoreKnowledgeActivity)
            adapter =knowledgeAdapter
        }
    }
    override fun initListener() {
      knowledgeAdapter.setOnItemClickListener { adapter, view, position ->
          //区分type类型
          if (type==2){
              val intent = Intent(this, VideoDetailActivity::class.java)
              intent.putExtra("bean",list[position])
              startActivity(intent)
          }else{
              val intent = Intent(this, WebViewActivity::class.java)
              intent.putExtra("type",1)
              intent.putExtra("url",list[position].content)
              intent.putExtra("objectId",list[position].magorid)
              startActivity(intent)
          }
      }


        knowledgeAdapter.disableLoadMoreIfNotFullPage(recyclerView)
        knowledgeAdapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            if (total < 2) {
                knowledgeAdapter.setEnableLoadMore(false)
            }
            //查下一页
            currentPage++
            if (currentPage > total) {
                return@RequestLoadMoreListener
            }
            val knowledgeListCall = SLMRetrofit.getInstance().api.knowledgeListCall(
                currentPage,
                "",
                type
            )
            knowledgeListCall.compose(ThreadSwitchTransformer()).subscribe(object :
                CallbackObserver<KnowledgeBean>(){
                override fun onSucceed(t: KnowledgeBean, desc: String?) {
                    list.addAll(t.rows)
                    knowledgeAdapter.setNewData(list)
                    if (currentPage == total) {
                        knowledgeAdapter.loadMoreEnd()
                        knowledgeAdapter.setEnableLoadMore(false)
                    } else {
                        knowledgeAdapter.setEnableLoadMore(true)
                        knowledgeAdapter.loadMoreComplete()
                    }
                }

                override fun onFailed() {

                }
            })
        }, recyclerView)
    }
}