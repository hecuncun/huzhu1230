package com.jzbn.huzhu1230.ui.home

import BaseActivity
import android.content.Intent
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.ext.showToast
import kotlinx.android.synthetic.main.activity_search.*

// Created by hesanwei on 2020/5/31.
class SearchActivity : BaseActivity() {

    private var type = TYPE_VIDEO

    private val searchAdapter: SearchAdapter by lazy {
        SearchAdapter()
    }

    override fun attachLayoutRes(): Int = R.layout.activity_search

    override fun initData() {
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
                    startActivity(Intent(this, VideoDetailActivity::class.java))
                }

                TYPE_ARTICLE -> {
                    showToast("跳转H5页面")
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
                showContent(p0?.length!! > 5)
                if (TextUtils.isEmpty(p0)) {
                    ivClear.visibility = View.GONE
                } else {
                    ivClear.visibility = View.VISIBLE
                }
            }
        })

        ivClear.setOnClickListener {
            etSearchContent.setText("")
        }
    }

    private fun showResult() {
        when (type) {
            TYPE_VIDEO -> {
                searchAdapter.setNewData(mutableListOf("1", "1", "1", "1", "1", "1", "1"))
            }
            TYPE_ARTICLE -> {
                searchAdapter.setNewData(
                    mutableListOf(
                        "2",
                        "2",
                        "2",
                        "2",
                        "2",
                        "2",
                        "2",
                        "2",
                        "2",
                        "2",
                        "2"
                    )
                )
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
        const val TYPE_VIDEO = 0
        const val TYPE_ARTICLE = 1
    }
}