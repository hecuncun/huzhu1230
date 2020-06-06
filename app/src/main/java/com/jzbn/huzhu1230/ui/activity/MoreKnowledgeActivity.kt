package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.adapter.KnowledgeAdapter
import com.jzbn.huzhu1230.bean.KnowledgeBean
import com.jzbn.huzhu1230.ui.home.VideoDetailActivity
import kotlinx.android.synthetic.main.activity_more_knowledge.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by hecuncun on 2020-5-30
 */
class MoreKnowledgeActivity:BaseActivity() {
    private var list= mutableListOf<KnowledgeBean>()
    private val knowledgeAdapter : KnowledgeAdapter by lazy {
        KnowledgeAdapter()
    }
    override fun attachLayoutRes(): Int = R.layout.activity_more_knowledge

    override fun initData() {
        initTestData()
    }

    override fun initView() {
        toolbar_title.text=intent.getStringExtra("title")
        initRecyclerView()
    }
    private fun initRecyclerView() {
        recyclerView.run {
            layoutManager = LinearLayoutManager(this@MoreKnowledgeActivity)
            adapter =knowledgeAdapter
        }
    }
    private fun initTestData() {
        for (i in 1..20){
            list.add(KnowledgeBean())
            knowledgeAdapter.setNewData(list)
        }
    }
    override fun initListener() {
      knowledgeAdapter.setOnItemClickListener { adapter, view, position ->
          //区分type类型
          val intent = Intent(this, VideoDetailActivity::class.java)
          startActivity(intent)
//          val intent=Intent(this, WebViewActivity::class.java)
//          intent.putExtra("type",1)
//          intent.putExtra("url","文章详情")
//          startActivity(intent)
      }
    }
}