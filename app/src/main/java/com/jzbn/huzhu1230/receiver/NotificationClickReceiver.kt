package com.jzbn.huzhu1230.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.jzbn.huzhu1230.bean.AliVideoBean
import com.jzbn.huzhu1230.net.CallbackObserver
import com.jzbn.huzhu1230.net.SLMRetrofit
import com.jzbn.huzhu1230.net.ThreadSwitchTransformer
import com.jzbn.huzhu1230.ui.call.AliRtcChatActivity

/**
 * Created by hecuncun on 2020/7/10
 */
class NotificationClickReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.i("TAG", "userClick:我被点击啦！！！ ")
        //跳转视频页
//        Intent intent =new Intent(context, AliRtcChatActivity.class);
//        AliVideoBean bean = new  AliVideoBean();
//        intent.putExtra("bean",bean);
        //       context.startActivity(intent);
        SoundPoolManager.getInstance(context).release()
        val type = intent.getStringExtra("type")
        val channelId = intent.getStringExtra("channelId")
        if (type == "1") {
            val baseBeanObservable = SLMRetrofit.getInstance().api.notifyVideoCall(type, channelId)
            baseBeanObservable.compose(ThreadSwitchTransformer()).subscribe(object :CallbackObserver<AliVideoBean>(){
                override fun onSucceed(t: AliVideoBean, desc: String?) {
                    val intent2 = Intent(context, AliRtcChatActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent2.putExtra("bean",t)
                    intent2.putExtra("type","join")
                    context.startActivity(intent2)
                }

                override fun onFailed() {

                }
            })

        }
    }
}