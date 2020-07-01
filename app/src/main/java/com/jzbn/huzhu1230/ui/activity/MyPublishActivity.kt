package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.support.v4.app.FragmentTransaction
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.ui.fragment.AedFragment
import com.jzbn.huzhu1230.ui.fragment.MyCommonFindFragment
import com.jzbn.huzhu1230.ui.fragment.MyEmergencyFragment
import kotlinx.android.synthetic.main.activity_my_publish.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by hecuncun on 2020-5-24
 */
class MyPublishActivity:BaseActivity() {
    private var myEmergencyFragment:MyEmergencyFragment?=null
    private var myCommonFindFragment:MyCommonFindFragment?=null
    private var aedFragment:AedFragment?=null
    override fun attachLayoutRes(): Int = R.layout.activity_my_publish

    override fun initData() {

    }

    override fun initView() {
       toolbar_title.text="我的发布"
    }

    override fun initListener() {
        rg.setOnCheckedChangeListener { group, checkedId ->
            val transaction = supportFragmentManager.beginTransaction()
            hideAllFragment(transaction)
            when(checkedId){
                R.id.rg_emergency->{
               if (myEmergencyFragment==null){
                   myEmergencyFragment=MyEmergencyFragment.getInstance()
                   transaction.add(R.id.fl_content,myEmergencyFragment!!)
               }
                  transaction.show(myEmergencyFragment!!)
                }
                R.id.rg_common->{
                    if (myCommonFindFragment==null){
                        myCommonFindFragment=MyCommonFindFragment.getInstance()
                        transaction.add(R.id.fl_content,myCommonFindFragment!!)
                    }
                    transaction.show(myCommonFindFragment!!)

                }
                R.id.rg_aed->{
                    if (aedFragment==null){
                        aedFragment= AedFragment.getInstance()
                        transaction.add(R.id.fl_content,aedFragment!!)
                    }
                    transaction.show(aedFragment!!)
                }
            }
            transaction.commit()
        }

        rg.check(R.id.rg_emergency)

    }

    private fun hideAllFragment(transaction: FragmentTransaction) {
        myEmergencyFragment?.let {
            transaction.hide(it)
        }
        myCommonFindFragment?.let {
            transaction.hide(it)
        }
        aedFragment?.let {
            transaction.hide(it)
        }
    }
}