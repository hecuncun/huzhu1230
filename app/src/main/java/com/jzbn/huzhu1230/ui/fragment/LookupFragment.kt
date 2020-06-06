package com.jzbn.huzhu1230.ui.fragment

import android.content.Intent
import android.support.v4.app.FragmentTransaction
import android.view.View
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.ui.home.MessageActivity
import com.lhzw.bluetooth.base.BaseFragment
import kotlinx.android.synthetic.main.activity_my_publish.*
import kotlinx.android.synthetic.main.toolbar.*

// Created by hesanwei on 2020/5/24.
class LookupFragment : BaseFragment() {

    private var emergencyLookUpFragment:EmergencyLookUpFragment?=null
    private var commonLookUpFragment:CommonLookUpFragment?=null
    override fun attachLayoutRes(): Int = R.layout.fragment_lookup

    override fun initView(view: View) {
        toolbar_title.text="寻人专区"
        toolbar_right_img.setImageResource(R.mipmap.icon_look_msg)
        toolbar_right_img.visibility=View.VISIBLE

    }

    override fun initListener() {
        toolbar_right_img.setOnClickListener {
            startActivity(Intent(context, MessageActivity::class.java))
        }
        rg.setOnCheckedChangeListener { group, checkedId ->
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            hideAllFragment(transaction)
            when (checkedId) {
                R.id.rg_emergency -> {
                    if (emergencyLookUpFragment == null) {
                        emergencyLookUpFragment = EmergencyLookUpFragment.getInstance()
                        transaction.add(R.id.fl_content, emergencyLookUpFragment!!)
                    }
                    transaction.show(emergencyLookUpFragment!!)
                }
                R.id.rg_common -> {
                    if (commonLookUpFragment == null) {
                        commonLookUpFragment = CommonLookUpFragment.getInstance()
                        transaction.add(R.id.fl_content, commonLookUpFragment!!)
                    }
                    transaction.show(commonLookUpFragment!!)

                }
            }
            transaction.commit()
        }

        rg.check(R.id.rg_emergency)
    }

    override fun lazyLoad() {
    }

    private fun hideAllFragment(transaction: FragmentTransaction) {
        emergencyLookUpFragment?.let {
            transaction.hide(it)
        }
        commonLookUpFragment?.let {
            transaction.hide(it)
        }
    }

    companion object {
        fun getInstance(): LookupFragment {
            return LookupFragment()
        }
    }
}