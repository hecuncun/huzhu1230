package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.support.v4.app.FragmentTransaction
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.ui.fragment.AedFragment
import com.jzbn.huzhu1230.ui.fragment.CommonFindFragment
import com.jzbn.huzhu1230.ui.fragment.EmergencyFindFragment
import kotlinx.android.synthetic.main.activity_my_publish.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by hecuncun on 2020-5-24
 */
class MyPublishActivity:BaseActivity() {
    private var emergencyFindFragment:EmergencyFindFragment?=null
    private var commonFindFragment:CommonFindFragment?=null
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
               if (emergencyFindFragment==null){
                   emergencyFindFragment=EmergencyFindFragment.getInstance()
                   transaction.add(R.id.fl_content,emergencyFindFragment!!)
               }
                  transaction.show(emergencyFindFragment!!)
                }
                R.id.rg_common->{
                    if (commonFindFragment==null){
                        commonFindFragment=CommonFindFragment.getInstance()
                        transaction.add(R.id.fl_content,commonFindFragment!!)
                    }
                    transaction.show(commonFindFragment!!)

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
        emergencyFindFragment?.let {
            transaction.hide(it)
        }
        commonFindFragment?.let {
            transaction.hide(it)
        }
        aedFragment?.let {
            transaction.hide(it)
        }
    }
}