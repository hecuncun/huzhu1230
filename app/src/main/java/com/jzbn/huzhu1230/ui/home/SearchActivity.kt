package com.jzbn.huzhu1230.ui.home

import BaseActivity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.KnowledgeBean
import com.jzbn.huzhu1230.net.CallbackObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import com.jzbn.huzhu1230.ui.activity.WebViewActivity
import kotlinx.android.synthetic.main.activity_more_knowledge.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.recyclerView

// Created by hesanwei on 2020/5/31.
class SearchActivity : BaseActivity() {

    private var type = TYPE_VIDEO

    private val searchAdapter: SearchAdapter by lazy {
        SearchAdapter()
    }

    override fun attachLayoutRes(): Int = R.layout.activity_search

    private var currentPageVideo=1
    private var currentPageArticle=1
    private var totalVideo = 0
    private var totalArticle = 0
    private var articleList= mutableListOf<KnowledgeBean.RowsBean>()//文章
    private var videoList= mutableListOf<KnowledgeBean.RowsBean>()//视频
    private var content=""

    override fun initData() {
        //初始化  不筛选
        searchData()
    }
    override fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView?.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = searchAdapter
        }

        searchAdapter.setOnItemClickListener { adapter, view, position ->
            when (type) {
                TYPE_VIDEO -> {
                    val intent = Intent(this, VideoDetailActivity::class.java)
                    intent.putExtra("bean",videoList[position])
                    startActivity(intent)
                }

                TYPE_ARTICLE -> {
                    val intent = Intent(this, WebViewActivity::class.java)
                    intent.putExtra("type",1)
                    intent.putExtra("url",articleList[position].content)
                    intent.putExtra("objectId",articleList[position].magorid)
                    intent.putExtra("title",articleList[position].title)
                    intent.putExtra("picUrl",articleList[position].photo)
                    intent.putExtra("time",articleList[position].createtime)
                    intent.putExtra("numViews",articleList[position].numViews)
                    startActivity(intent)
                }
            }
        }
    }

    override fun initListener() {
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbVideo -> {
                    type = TYPE_VIDEO

                }
                R.id.rbArticle -> {
                    type = TYPE_ARTICLE
                }
            }
            showResult()
            recyclerView?.scrollToPosition(0)
        }

        etSearchContent.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (TextUtils.isEmpty(p0)) {
                    ivClear.visibility = View.GONE
                } else {
                    ivClear.visibility = View.VISIBLE
                }
                content=p0.toString().trim()
                searchData()
            }
        })

        ivClear.setOnClickListener {
            etSearchContent.setText("")
        }

        searchAdapter.disableLoadMoreIfNotFullPage(recyclerView)
        searchAdapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            if (type== TYPE_VIDEO){
                if (totalVideo < 2) {
                    searchAdapter.setEnableLoadMore(false)
                }
                //查下一页
                currentPageVideo++
                if (currentPageVideo > totalVideo) {
                    return@RequestLoadMoreListener
                }
                val knowledgeListCall = SLMRetrofit.getInstance().api.knowledgeListCall(
                    currentPageVideo,
                    "",
                    TYPE_VIDEO
                )
                knowledgeListCall.compose(ThreadSwitchTransformer()).subscribe(object :
                    CallbackObserver<KnowledgeBean>(){
                    override fun onSucceed(t: KnowledgeBean, desc: String?) {
                        videoList.addAll(t.rows)
                        searchAdapter.setNewData(videoList)
                        if (currentPageVideo == totalVideo) {
                            searchAdapter.loadMoreEnd()
                            searchAdapter.setEnableLoadMore(false)
                        } else {
                            searchAdapter.setEnableLoadMore(true)
                            searchAdapter.loadMoreComplete()
                        }
                    }

                    override fun onFailed() {

                    }
                })
            }else{
                if (totalArticle < 2) {
                    searchAdapter.setEnableLoadMore(false)
                }
                //查下一页
                currentPageArticle++
                if (currentPageArticle > totalArticle) {
                    return@RequestLoadMoreListener
                }
                val knowledgeListCall = SLMRetrofit.getInstance().api.knowledgeListCall(
                    currentPageArticle,
                    "",
                    TYPE_ARTICLE
                )
                knowledgeListCall.compose(ThreadSwitchTransformer()).subscribe(object :
                    CallbackObserver<KnowledgeBean>(){
                    override fun onSucceed(t: KnowledgeBean, desc: String?) {
                        articleList.addAll(t.rows)
                        searchAdapter.setNewData(articleList)
                        if (currentPageArticle == totalArticle) {
                            searchAdapter.loadMoreEnd()
                            searchAdapter.setEnableLoadMore(false)
                        } else {
                            searchAdapter.setEnableLoadMore(true)
                            searchAdapter.loadMoreComplete()
                        }
                    }

                    override fun onFailed() {

                    }
                })
            }

        }, recyclerView)
    }
    //搜索内容
    private fun searchData() {
        videoList.clear()
        articleList.clear()
        currentPageVideo=1
        currentPageArticle=1
        totalVideo = 0
        totalArticle = 0
        val knowledgeVideoCall = SLMRetrofit.getInstance().api.knowledgeListCall(currentPageVideo, content, TYPE_VIDEO)
        knowledgeVideoCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackObserver<KnowledgeBean>(){
            override fun onSucceed(t: KnowledgeBean, desc: String?) {
                totalVideo=t.total
                videoList.addAll(t.rows)
                searchAdapter.setNewData(videoList)
                showResult()
                //先查视频，再查文章
                initArticleData()
            }

            override fun onFailed() {

            }
        })

    }
    private fun initArticleData() {
        //初始化 搜索文章  不筛选
        val knowledgeArticleCall = SLMRetrofit.getInstance().api.knowledgeListCall(currentPageArticle, content, TYPE_ARTICLE)
        knowledgeArticleCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackObserver<KnowledgeBean>(){
            override fun onSucceed(t: KnowledgeBean, desc: String?) {
                totalArticle=t.total
                articleList.addAll(t.rows)
                //视频，文章不为空  显示内容
                showContent(videoList.isNotEmpty() || articleList.isNotEmpty())
            }

            override fun onFailed() {

            }
        })
    }

    private fun showResult() {
        when (type) {
            TYPE_VIDEO -> {
                searchAdapter.setNewData(videoList)
            }
            TYPE_ARTICLE -> {
                searchAdapter.setNewData(articleList)
            }
        }
    }

    private fun showContent(isShowResult: Boolean) {
        llSearchResult.visibility = if (isShowResult) View.VISIBLE else View.GONE
        llNoData.visibility = if (isShowResult) View.GONE else View.VISIBLE
        if (isShowResult) {
            showResult()
            radioGroup.check(R.id.rbVideo)
        }
    }

    companion object {
        const val TYPE_VIDEO = 2
        const val TYPE_ARTICLE = 1
    }
}