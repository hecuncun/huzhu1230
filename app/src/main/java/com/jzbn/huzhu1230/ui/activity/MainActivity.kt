package com.jzbn.huzhu1230.ui.activity

import BaseActivity
import android.Manifest
import android.content.Intent
import android.support.design.bottomnavigation.LabelVisibilityMode
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentTransaction
import com.flyco.animation.BounceEnter.BounceTopEnter
import com.jzbn.huzhu1230.R
import com.jzbn.huzhu1230.bean.TopMsgBean
import com.jzbn.huzhu1230.ui.fragment.*
import com.jzbn.huzhu1230.ui.publish.PublishAedActivity
import com.jzbn.huzhu1230.ui.publish.PublishDialog
import com.jzbn.huzhu1230.ui.publish.PublishEmergencyActivity
import com.jzbn.huzhu1230.utils.KeyUtil
import com.jzbn.huzhu1230.widget.TopMsgDialog
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var mIndex = FRAGMENT_HOME
    private var mHomeFragment: HomeFragment? = null
    private var mLookupFragment: LookupFragment? = null
    private var mReleaseFragment: ReleaseFragment? = null
    private var mKnowledgeFragment: KnowledgeFragment? = null
    private var mMineFragment: MineFragment? = null
    private val PERMISS_REQUEST_CODE = 0x100

    override fun attachLayoutRes(): Int = R.layout.activity_main

    override fun initData() {
      val sha1=  KeyUtil.getSHA1(this)
        Logger.e("SHA1==$sha1")


        if (checkPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION))) {
            //LitePal.getDatabase()
        } else {
            requestPermission(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), PERMISS_REQUEST_CODE)
        }

        val bean =TopMsgBean()
        val dialog = TopMsgDialog(this@MainActivity,bean)
        dialog.showAnim(BounceTopEnter())
//        dialog.show()
    }

    /**
     * 获得权限后的回调
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (PERMISS_REQUEST_CODE == requestCode) {
            //先初始化一个用户对象
            //LitePal.getDatabase()

        }
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
        cvPublish.setOnClickListener {
            showPublishDialog()
        }
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

    private fun showPublishDialog(){
        val releaseDialog = PublishDialog()
        releaseDialog.setOnDialogClickListener(object : PublishDialog.OnDialogBtnClickListener{
            override fun onClickReleaseEmergency() {
                val intent =Intent(this@MainActivity, PublishEmergencyActivity::class.java)
                intent.putExtra("publishType","emergency")
                startActivity(intent)
            }

            override fun onClickReleaseCommon() {
                //todo 区分type
                val intent =Intent(this@MainActivity, PublishEmergencyActivity::class.java)
                intent.putExtra("publishType","common")
                startActivity(intent)

            }

            override fun onClickReleaseAed() {
                startActivity(Intent(this@MainActivity, PublishAedActivity::class.java))

            }

        })
        releaseDialog.show(supportFragmentManager, "ReleaseDialog")
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
//                    showFragment(FRAGMENT_RELEASE)
                    showPublishDialog()
                    false
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
