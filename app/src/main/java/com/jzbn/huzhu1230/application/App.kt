package com.jzbn.huzhu1230.application

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.multidex.MultiDexApplication
import android.util.Log
import com.alibaba.sdk.android.push.CommonCallback
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory
import com.jzbn.huzhu1230.BuildConfig
import com.jzbn.huzhu1230.utils.LogCatStrategy
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.simple.spiderman.SpiderMan
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import org.litepal.LitePal
import kotlin.properties.Delegates

/**
 * Created by hecuncun on 2019/12/2
 */
class App : MultiDexApplication() {

// 阿里音视频通话
//appkey   0d79eb619746d6f8625143cdf58b124f  应用id :  50ucoiom

    private var refWatcher: RefWatcher? = null

    companion object {

        private val TAG = "App"

        var context: Context by Delegates.notNull()
            private set//  对于属性context，如果你想改变访问的可见性，但是又不想改变它的默认实现，那么你就可以定义set和get但不进行实现。

        lateinit var instance: Application

        fun getRefWatcher(context: Context): RefWatcher? {
            val app = context.applicationContext as App
            return app.refWatcher
        }

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
        SpiderMan.init(this)//奔溃日志
        refWatcher = setupLeakCanary()
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
        initLoggerConfig()
        //初始化数据库
        LitePal.initialize(this)
        //阿里推送初始化
        initCloudChannel(this)
        //BUGly初始化
      //  CrashReport.initCrashReport(applicationContext, "6ed7ce60df", false)
    }

    private fun initCloudChannel(app: Context) {
        PushServiceFactory.init(app)
        val pushService=PushServiceFactory.getCloudPushService()
        pushService.register(app,object :CommonCallback{
            override fun onSuccess(response: String?) {
                Log.d(TAG, "init cloudchannel success");
            }

            override fun onFailed(errorCode: String?, errorMessage: String?) {
                Log.d(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        })
    }

    private fun setupLeakCanary(): RefWatcher? {
        return if (LeakCanary.isInAnalyzerProcess(this)) {
            RefWatcher.DISABLED
        } else LeakCanary.install(this)
    }

    /**
     * 初始化LOGGER配置
     */
    private fun initLoggerConfig() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .logStrategy(LogCatStrategy())
                .tag("HZtag")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }


    private val mActivityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            Log.d(TAG, "onCreated: " + activity.componentName.className)
        }

        override fun onActivityStarted(activity: Activity) {
            Log.d(TAG, "onStart: " + activity.componentName.className)
        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            Log.d(TAG, "onDestroy: " + activity.componentName.className)
        }
    }

}