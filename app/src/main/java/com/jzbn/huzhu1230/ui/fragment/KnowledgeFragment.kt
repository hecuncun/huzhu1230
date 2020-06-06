package com.jzbn.huzhu1230.ui.fragment

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.adapter.KnowledgeAdapter
import com.jzbn.huzhu1230.bean.KnowledgeBean
import com.jzbn.huzhu1230.ui.activity.MoreKnowledgeActivity
import com.jzbn.huzhu1230.ui.activity.SearchHelpActivity
import com.jzbn.huzhu1230.ui.home.MessageActivity
import com.lhzw.bluetooth.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_knowledge.*
import kotlinx.android.synthetic.main.toolbar.*

// Created by hesanwei on 2020/5/24.
class KnowledgeFragment: BaseFragment() {
    private var list= mutableListOf<KnowledgeBean>()
    private val knowledgeAdapter :KnowledgeAdapter by lazy {
        KnowledgeAdapter()
    }
    override fun attachLayoutRes(): Int = R.layout.fragment_knowledge

    override fun initView(view: View) {
        toolbar_title.text="互助项目"
        toolbar_right_img.setImageResource(R.mipmap.icon_look_msg)
        toolbar_right_img.visibility=View.VISIBLE
        initRecyclerView()
    }
    private fun initRecyclerView() {
        rv_video.run {
            layoutManager = LinearLayoutManager(activity)
            adapter =knowledgeAdapter
        }
        rv_article.run {
            layoutManager = LinearLayoutManager(activity)
            adapter =knowledgeAdapter
        }
    }
    override fun initListener() {
        toolbar_right_img.setOnClickListener {
            startActivity(Intent(context, MessageActivity::class.java))
        }
        knowledgeAdapter.setOnItemClickListener { adapter, view, position ->

        }
        tv_more_article.setOnClickListener {
            val intent =(Intent(activity, MoreKnowledgeActivity::class.java))
            intent.putExtra("title","救援知识·热门文章")
            startActivity(intent)
        }
        tv_more_video.setOnClickListener {
            val intent =(Intent(activity, MoreKnowledgeActivity::class.java))
            intent.putExtra("title","救援知识·热门视频")
            startActivity(intent)
        }

        ll_search_help.setOnClickListener { //进入寻找互助项目页
            val intent =(Intent(activity, SearchHelpActivity::class.java))
            startActivity(intent)
        }
    }

    override fun lazyLoad() {
        initTestData()
    }
    private fun initTestData() {
        for (i in 1..4){
            list.add(KnowledgeBean())
            knowledgeAdapter.setNewData(list)
        }
    }
    companion object {
        fun getInstance(): KnowledgeFragment {
            return KnowledgeFragment()
        }
    }
}