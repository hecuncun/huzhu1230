package com.jzbn.huzhu1230.receiver;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.jzbn.huzhu1230.R;
import com.orhanobut.logger.Logger;


/**
 * Created by hecuncun on 2018/11/14
 * 语音播报类
 */

public class SoundPoolManager {
    private static SoundPoolManager instance;
    private boolean playing = false;//是否处于播放状态
    private boolean loaded = false;//资源加载完毕才能播放

    private SoundPool soundPool;
    private int repaireSoundId;
//    private int rescueSoundId;
//    private int maintainSoundId;
//    private int checkCarSoundId;
//    private int monitorSoundId;

    int count = 0;//加载成功数

    private SoundPoolManager(Context context) {


        int maxStreams = 1;
        soundPool = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                count++;
                if (count == 1) {//总共有几个音乐文件
                    Logger.e("音频文件加载完成了==" + sampleId);
                    loaded = true;
                    play(1);
                }
            }

        });
        //加载资源ID
        repaireSoundId = soundPool.load(context, R.raw.alarm, 1);
//        repaireSoundId = soundPool.load(context, R.raw.repaire, 1);
//        rescueSoundId = soundPool.load(context, R.raw.rescue, 1);
//        checkCarSoundId = soundPool.load(context, R.raw.checkcar, 1);
//        monitorSoundId = soundPool.load(context, R.raw.monitor, 1);
//        maintainSoundId = soundPool.load(context, R.raw.maintain, 1);


    }

    public static SoundPoolManager getInstance(Context context) {
        if (instance == null) {
            synchronized (SoundPoolManager.class) {
                if (instance == null) {
                    instance = new SoundPoolManager(context);
                }
            }

        }
        return instance;
    }

    //播放
    public void play(int type) {
        //此处我自定义type 5种单的类型
        // 1.维修单，2救援单，3保养单，4检测单，5验车单
        int resId = repaireSoundId;//音频id
//        switch (type) {
//            case 1:
//                resId = repaireSoundId;
//                break;
//            case 2:
//                resId = rescueSoundId;
//                break;
//            case 3:
//                resId = maintainSoundId;
//                break;
//            case 4:
//                resId = monitorSoundId;
//                break;
//            case 5:
//                resId = checkCarSoundId;
//                break;
//            case 6:
//                break;
//            default:
//                break;
   //     }

        /*
     * @param soundID 加载资源得到的id
     * @param leftVolume 音量
     * @param rightVolume 音量
     * @param 音频流优先级
     * @param loop 循环 0 不循环 -1 无限循环
     * @param rate 播放速率 1.0 正常速率（0.5-2.0）
     * @return 成功返回id，否则为0
     */
        Logger.e( "走播放方法之前loaded==" + loaded);
        if (loaded && !playing) {
            Logger.e("走播放方法");
            if (soundPool!=null){
                soundPool.play(resId, 1, 1, 0, -1, 1f);
            }

        }
    }

    //退出APP销毁
    public void release() {
        if (soundPool != null) {
            soundPool.unload(repaireSoundId);
//            soundPool.unload(rescueSoundId);
//            soundPool.unload(maintainSoundId);
//            soundPool.unload(checkCarSoundId);
//            soundPool.unload(monitorSoundId);
            soundPool.release();
            soundPool = null;
        }
        instance = null;
    }


}
