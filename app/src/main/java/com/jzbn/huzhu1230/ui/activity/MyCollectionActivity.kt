package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.adapter.CollectionAdapter
import com.jzbn.huzhu1230.base.BaseNoDataBean
import com.jzbn.huzhu1230.bean.CollectionResponseBean
import com.jzbn.huzhu1230.bean.DailyRescueBean
import com.jzbn.huzhu1230.constants.Constant
import com.jzbn.huzhu1230.ext.showToast
import com.jzbn.huzhu1230.net.CallbackListObserver
import com.jzbn.huzhu1230.net.CallbackObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import com.jzbn.huzhu1230.ui.home.VideoDetailActivity
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_my_collection.*
import kotlinx.android.synthetic.main.fragment_emergency_find.recyclerView
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by hecuncun on 2020-5-25
 */
class MyCollectionActivity : BaseActivity() {
    private var currentPage = 1
    private var total = 0
    private var list = mutableListOf<CollectionResponseBean.RowsBean>()
    private val collectionAdapter: CollectionAdapter by lazy {
        CollectionAdapter()
    }

    override fun attachLayoutRes(): Int = R.layout.activity_my_collection

    override fun initData() {

        searchData()

    }

    private fun searchData() {
        currentPage=1
        total=0
        list.clear()
        val collectionListCall = SLMRetrofit.getInstance().api.getCollectionListCall(currentPage, uid)
        collectionListCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackObserver<CollectionResponseBean>(){
            override fun onSucceed(t: CollectionResponseBean, desc: String?) {
                total = t.total
                list.addAll(t.rows)
                collectionAdapter.setNewData(list)
            }

            override fun onFailed() {

            }
        })
    }


    override fun initView() {
        toolbar_title.text="我的收藏"
        toolbar_right_tv.text="编辑"
        toolbar_right_tv.visibility= View.VISIBLE
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView.run {
            layoutManager = LinearLayoutManager(this@MyCollectionActivity)
            adapter = collectionAdapter
        }
    }
    private var ids=""//要移除的收藏id
    private var sb =StringBuilder()
    override fun initListener() {
        toolbar_right_tv.setOnClickListener {
            if (list.isEmpty()){
                return@setOnClickListener
            }
            if (toolbar_right_tv.text == "编辑") {
                collectionAdapter.setShowCheckIcon(true)
                toolbar_right_tv.text = "完成"
                tv_delete.visibility = View.VISIBLE
            } else if (toolbar_right_tv.text == "完成") {
                collectionAdapter.setShowCheckIcon(false)
                toolbar_right_tv.text = "编辑"
                tv_delete.visibility = View.GONE
            }
        }
        collectionAdapter.setOnItemClickListener { adapter, view, position ->
            val bean = (adapter.getItem(position) as CollectionResponseBean.RowsBean)
            if (bean.type==3){
                //三级分类，进视频
                val intent = Intent(this, VideoDetailActivity::class.java)
                intent.putExtra("pid",bean.magorid)
                startActivity(intent)
            }else if (bean.type==1){//文章  todo  添加信息
//                val intent = Intent(this, WebViewActivity::class.java)
//                intent.putExtra("type",1)
//                intent.putExtra("url",list[position].content)
//                intent.putExtra("objectId",bean.magorid)
//                startActivity(intent)

            }else{//救援知识视频  、、TODO  添加信息
                val intent = Intent(this, VideoDetailActivity::class.java)
                intent.putExtra("pid",bean.magorid)
                startActivity(intent)
            }

        }

        collectionAdapter.setOnItemChildClickListener { adapter, view, position ->
            when(view.id){
                R.id.iv_check->{
                    val bean = (adapter.getItem(position) as CollectionResponseBean.RowsBean)
                    bean.isChecked = !bean.isChecked
                    collectionAdapter.notifyItemChanged(position)
                }
            }
        }
        tv_delete.setOnClickListener {//取消收藏
           for (item in collectionAdapter.data) {
               if (item.isChecked){
                   sb.append(item.magorid)
                   sb.append(",")
               }
           }
            val trim = sb.toString().trim()
            ids = trim.substring(0,trim.length)
            Logger.e("ids = $ids")
            val deleteCollection = SLMRetrofit.getInstance().api.deleteCollection(ids)
            deleteCollection.compose(ThreadSwitchTransformer()).subscribe(object :CallbackListObserver<BaseNoDataBean>(){
                override fun onSucceed(t: BaseNoDataBean) {
                   if (t.code==Constant.SUCCESSED_CODE){
                       showToast("移除成功")
                       searchData()//刷新数据
                   }
                }

                override fun onFailed() {

                }
            })

        }

        collectionAdapter.disableLoadMoreIfNotFullPage(recyclerView)
        collectionAdapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            if (total < 2) {
                collectionAdapter.setEnableLoadMore(false)
            }
            //查下一页
            currentPage++
            if (currentPage > total) {
                return@RequestLoadMoreListener
            }
            val collectionListCall = SLMRetrofit.getInstance().api.getCollectionListCall(currentPage, uid)
            collectionListCall.compose(ThreadSwitchTransformer()).subscribe(object :CallbackObserver<CollectionResponseBean>(){
                override fun onSucceed(t: CollectionResponseBean, desc: String?) {
                    list.addAll(t.rows)
                    collectionAdapter.setNewData(list)
                        if (currentPage == total) {
                            collectionAdapter.loadMoreEnd()
                            collectionAdapter.setEnableLoadMore(false)
                        } else {
                            collectionAdapter.setEnableLoadMore(true)
                            collectionAdapter.loadMoreComplete()
                        }
                }

                override fun onFailed() {

                }
            })

        }, recyclerView)
    }
}