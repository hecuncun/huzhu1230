package com.jzbn.huzhu1230.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.jzbn.huzhu1230.R;
import com.jzbn.huzhu1230.event.MsgNoticeEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

/**
 * Created by hecuncun on 2020/7/8
 */

public class MyMessageReceiver extends MessageReceiver {
    // 消息接收部分的LOG_TAG
    public static final String REC_TAG = "receiver";
    @Override
    public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        // TODO 处理推送通知
        Log.e("MyMessageReceiver", "Receive notification, title: " + title + ", summary: " + summary + ", extraMap: " + extraMap);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder =new Notification.Builder(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 通知渠道的id
            String channelID = "1";
            // 用户可以看到的通知渠道的名字.
            CharSequence name = "notification channel";
            NotificationChannel mChannel = new NotificationChannel(channelID, name,android.app.NotificationManager.IMPORTANCE_HIGH);
            // 设置通知出现时的闪灯（如果 android 设备支持的话）
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            // 配置通知渠道的属性
            // 用户可以看到的通知渠道的描述
            String description = "notification description";
            mChannel.setDescription(description);
            // 设置通知出现时的震动（如果 android 设备支持的话）
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            //最后在notificationmanager中创建该通知渠道
            mNotificationManager.createNotificationChannel(mChannel);
            //创建通知时指定channelID
            builder.setChannelId(channelID);
        }
            builder.setContentText(summary);
            builder.setSmallIcon(R.mipmap.icon_logo);
            builder.setContentTitle(title);
            builder.setWhen(System.currentTimeMillis()); //发送时间
            builder.setAutoCancel(true);//打开程序后图标消失
            Intent intent =new Intent (context,NotificationClickReceiver.class);
            String type = extraMap.get("type");
            if ("1".equals(type)){
                String channelId = extraMap.get("channelId");
                intent.putExtra("type",type);
                intent.putExtra("channelId",channelId);
                PendingIntent pendingIntent =PendingIntent.getBroadcast(context, 0, intent, 0);
                builder.setContentIntent(pendingIntent);
                //播放音乐
                SoundPoolManager.getInstance(context);
            }else if ("2".equals(type)){
                //消息通知
                String phone =  extraMap.get("phone");
                String gpsArea =  extraMap.get("gpsArea");
                String content  =  extraMap.get("content");
                EventBus.getDefault().post(new MsgNoticeEvent(phone,gpsArea,content));

            }
            Notification notification = builder.build();
            mNotificationManager.notify(0,notification);

    }
    @Override
    public void onMessage(Context context, CPushMessage cPushMessage) {
        Log.e("MyMessageReceiver", "onMessage, messageId: " + cPushMessage.getMessageId() + ", title: " + cPushMessage.getTitle() + ", content:" + cPushMessage.getContent());
    }
    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        Log.e("MyMessageReceiver", "onNotificationOpened, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
    }
    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
        Log.e("MyMessageReceiver", "onNotificationClickedWithNoAction, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
    }
    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
        Log.e("MyMessageReceiver", "onNotificationReceivedInApp, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap + ", openType:" + openType + ", openActivity:" + openActivity + ", openUrl:" + openUrl);
    }
    @Override
    protected void onNotificationRemoved(Context context, String messageId) {
        Log.e("MyMessageReceiver", "onNotificationRemoved");
    }
}