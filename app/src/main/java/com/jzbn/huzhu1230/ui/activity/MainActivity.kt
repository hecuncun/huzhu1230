package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.content.Intent
import android.support.design.bottomnavigation.LabelVisibilityMode
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentTransaction
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.ui.fragment.*
import com.jzbn.huzhu1230.ui.publish.PublishAedActivity
import com.jzbn.huzhu1230.ui.publish.PublishDialog
import com.jzbn.huzhu1230.ui.publish.PublishEmergencyActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var mIndex = FRAGMENT_HOME
    private var mHomeFragment: HomeFragment? = null
    private var mLookupFragment: LookupFragment? = null
    private var mReleaseFragment: ReleaseFragment? = null
    private var mKnowledgeFragment: KnowledgeFragment? = null
    private var mMineFragment: MineFragment? = null

    override fun attachLayoutRes(): Int = R.layout.activity_main

    override fun initData() {
    }

    override fun initView() {
        bottomNavigationView.run {
            labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
            itemIconTintList = null
            setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        }
        showFragment(mIndex)
    }

    override fun initListener() {
    }

    /**
     * 展示Fragment
     * @param index
     */
    private fun showFragment(index: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        mIndex = index
        when (index) {
            FRAGMENT_HOME // 首页
            -> {
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.getInstance()
                    transaction.add(R.id.container, mHomeFragment!!, "home")
                } else {
                    transaction.show(mHomeFragment!!)
                }
            }
            FRAGMENT_LOOKUP  // 寻人专区
            -> {
                if (mLookupFragment == null) {
                    mLookupFragment = LookupFragment.getInstance()
                    transaction.add(R.id.container, mLookupFragment!!, "lookup")
                } else {
                    transaction.show(mLookupFragment!!)
                }
            }
            FRAGMENT_RELEASE // 发布
            -> {
                val releaseDialog = PublishDialog()
                releaseDialog.setOnDialogClickListener(object : PublishDialog.OnDialogBtnClickListener{
                    override fun onClickReleaseEmergency() {
                        startActivity(Intent(this@MainActivity, PublishEmergencyActivity::class.java))
                    }

                    override fun onClickReleaseCommon() {
                        //todo 区分type
                        startActivity(Intent(this@MainActivity, PublishEmergencyActivity::class.java))

                    }

                    override fun onClickReleaseAed() {
                        startActivity(Intent(this@MainActivity, PublishAedActivity::class.java))

                    }

                })
                releaseDialog.show(supportFragmentManager, "ReleaseDialog")
            }
            FRAGMENT_KNOWLEDGE // 救援知识
            -> {
                if (mKnowledgeFragment == null) {
                    mKnowledgeFragment = KnowledgeFragment.getInstance()
                    transaction.add(R.id.container, mKnowledgeFragment!!, "knowledge")
                } else {
                    transaction.show(mKnowledgeFragment!!)
                }
            }
            FRAGMENT_MINE // 我的
            -> {
                if (mMineFragment == null) {
                    mMineFragment = MineFragment.getInstance()
                    transaction.add(R.id.container, mMineFragment!!, "mine")
                } else {
                    transaction.show(mMineFragment!!)
                }
            }
        }
        transaction.commit()
    }

    /**
     * 隐藏所有的Fragment
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        mHomeFragment?.let { transaction.hide(it) }
        mLookupFragment?.let { transaction.hide(it) }
        mReleaseFragment?.let { transaction.hide(it) }
        mKnowledgeFragment?.let { transaction.hide(it) }
        mMineFragment?.let { transaction.hide(it) }
    }

    /**
     * NavigationItemSelect监听
     */
    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    showFragment(FRAGMENT_HOME)
                    true
                }
                R.id.nav_lookup -> {
                    showFragment(FRAGMENT_LOOKUP)
                    true
                }
                R.id.nav_release -> {
                    showFragment(FRAGMENT_RELEASE)
                    true
                }
                R.id.nav_knowledge -> {
                    showFragment(FRAGMENT_KNOWLEDGE)
                    true
                }
                R.id.nav_mine -> {
                    showFragment(FRAGMENT_MINE)
                    true
                }
                else -> {
                    false
                }

            }
        }

    companion object {
        private const val FRAGMENT_HOME = 0x01
        private const val FRAGMENT_LOOKUP = 0x02
        private const val FRAGMENT_RELEASE = 0x03
        private const val FRAGMENT_KNOWLEDGE = 0x04
        private const val FRAGMENT_MINE = 0x05
    }
}
