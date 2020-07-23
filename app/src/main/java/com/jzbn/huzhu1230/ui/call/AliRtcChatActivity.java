package com.jzbn.huzhu1230.ui.call;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alivc.rtc.AliRtcAuthInfo;
import com.alivc.rtc.AliRtcEngine;
import com.alivc.rtc.AliRtcEngineEventListener;
import com.alivc.rtc.AliRtcEngineNotify;
import com.alivc.rtc.AliRtcRemoteUserInfo;
import com.jzbn.huzhu1230.R;
import com.jzbn.huzhu1230.bean.AliVideoBean;
import com.jzbn.huzhu1230.receiver.SoundPoolManager;
import com.orhanobut.logger.Logger;

import org.webrtc.sdk.SophonSurfaceView;

import java.text.DecimalFormat;
import java.util.List;

import static com.alivc.rtc.AliRtcEngine.AliRTCCameraType.AliRTCCameraFront;
import static com.alivc.rtc.AliRtcEngine.AliRtcAudioTrack.AliRtcAudioTrackNo;
import static com.alivc.rtc.AliRtcEngine.AliRtcRenderMode.AliRtcRenderModeAuto;
import static com.alivc.rtc.AliRtcEngine.AliRtcVideoTrack.AliRtcVideoTrackBoth;
import static com.alivc.rtc.AliRtcEngine.AliRtcVideoTrack.AliRtcVideoTrackCamera;
import static com.alivc.rtc.AliRtcEngine.AliRtcVideoTrack.AliRtcVideoTrackNo;
import static com.alivc.rtc.AliRtcEngine.AliRtcVideoTrack.AliRtcVideoTrackScreen;
import static org.webrtc.alirtcInterface.ErrorCodeEnum.ERR_ICE_CONNECTION_HEARTBEAT_TIMEOUT;
import static org.webrtc.alirtcInterface.ErrorCodeEnum.ERR_SESSION_REMOVED;


/**
 * 音视频通话的activity
 */
public class AliRtcChatActivity extends AppCompatActivity {
    private static final String TAG = AliRtcChatActivity.class.getName();


    public static final int CAMERA = 1001;
    public static final int SCREEN = 1002;

    public static final String[] VIDEO_INFO_KEYS = {"Width", "Height", "FPS", "LossRate"};

    private static final int PERMISSION_REQ_ID = 0x0002;

    private static final String[] REQUESTED_PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * 本地流播放view
     */
    private SophonSurfaceView mLocalView;
    /**
     * SDK提供的对音视频通话处理的引擎类
     */
    private AliRtcEngine mAliRtcEngine;
    /**
     * 前台服务的Intent
     */
    private Intent mForeServiceIntent;

    /**
     * 承载远程User的Adapter
     */
    private ChartUserAdapter mUserListAdapter;

    private AliVideoBean bean;
    private TextView tvTip;
    private TextView tvDuration;
   private String type="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alirtc_activity_chat);
        bean = (AliVideoBean) getIntent().getParcelableExtra("bean");
        // 初始化界面上的view
        type = getIntent().getStringExtra("type");
        if("call".equals(type)){
            SoundPoolManager.getInstance(this);
        }
        initView();
        if (checkSelfPermission(REQUESTED_PERMISSIONS[0], PERMISSION_REQ_ID) &&
                checkSelfPermission(REQUESTED_PERMISSIONS[1], PERMISSION_REQ_ID) &&
                checkSelfPermission(REQUESTED_PERMISSIONS[2], PERMISSION_REQ_ID)) {
            // 初始化引擎以及打开预览界面
            initRTCEngineAndStartPreview();
        }



    }

    private boolean checkSelfPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, requestCode);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQ_ID) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED ||
                    grantResults[1] != PackageManager.PERMISSION_GRANTED ||
                    grantResults[2] != PackageManager.PERMISSION_GRANTED) {
                showToast("Need permissions " + Manifest.permission.RECORD_AUDIO +
                        "/" + Manifest.permission.CAMERA + "/" + Manifest.permission.WRITE_EXTERNAL_STORAGE);
                finish();
                return;
            }
            initRTCEngineAndStartPreview();
        }
    }

    private void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        mLocalView = findViewById(R.id.sf_local_view);
        // 承载远程User的Adapter
        mUserListAdapter = new ChartUserAdapter();
        RecyclerView chartUserListView = findViewById(R.id.chart_content_userlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        chartUserListView.setLayoutManager(layoutManager);
        chartUserListView.addItemDecoration(new BaseRecyclerViewAdapter.DividerGridItemDecoration(
                getResources().getDrawable(R.drawable.chart_content_userlist_item_divider)));
        DefaultItemAnimator anim = new DefaultItemAnimator();
        anim.setSupportsChangeAnimations(false);
        chartUserListView.setItemAnimator(anim);
        chartUserListView.setAdapter(mUserListAdapter);
        mUserListAdapter.setOnSubConfigChangeListener(mOnSubConfigChangeListener);
        TextView tvChangeCamera = findViewById(R.id.tv_change_camera);
        tvTip = findViewById(R.id.tv_tip);
        tvDuration = findViewById(R.id.tv_duration);
        ImageView ivFinish = findViewById(R.id.iv_finish);

        tvChangeCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AliRtcEngine.AliRTCCameraType currentCameraType = mAliRtcEngine.getCurrentCameraType();
                if (currentCameraType == AliRTCCameraFront) {

                    mAliRtcEngine.setPreCameraType(0);//0表示后置，1表示前置
                    mAliRtcEngine.switchCamera();
                } else {

                    mAliRtcEngine.setPreCameraType(1);//0表示后置，1表示前置
                    mAliRtcEngine.switchCamera();
                }
            }
        });

        ivFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




    }



    private final long[] baseTimer = {SystemClock.elapsedRealtime()};
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (0 == baseTimer[0]) {
                baseTimer[0] = SystemClock.elapsedRealtime();
            }

            int time = (int) ((SystemClock.elapsedRealtime() - baseTimer[0]) / 1000);
            String hh = new DecimalFormat("00").format(time / 3600);
            String mm = new DecimalFormat("00").format(time % 3600 / 60);
            String ss = new DecimalFormat("00").format(time % 60);
            if (null != baseTimer) {
                tvDuration.setText(hh + ":" + mm + ":" + ss);
            }
            sendMessageDelayed(Message.obtain(this, 0x0), 1000);
        }
    };

    private void initRTCEngineAndStartPreview() {

        //默认不开启兼容H5
        AliRtcEngine.setH5CompatibleMode(0);
        // 防止初始化过多
        if (mAliRtcEngine == null) {
            //实例化,必须在主线程进行。
            mAliRtcEngine = AliRtcEngine.getInstance(getApplicationContext());
            //设置事件的回调监听
            mAliRtcEngine.setRtcEngineEventListener(mEventListener);
            //设置接受通知事件的回调
            mAliRtcEngine.setRtcEngineNotify(mEngineNotify);
            // 初始化本地视图
            initLocalView();
            //开启预览
            startPreview();
            //加入频道
            joinChannel();
        }
    }

    private void startPreview() {
        if (mAliRtcEngine == null) {
            return;
        }
        try {
            mAliRtcEngine.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置本地的预览视图的view
     */
    private void initLocalView() {
        mLocalView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        mLocalView.setZOrderOnTop(false);
        mLocalView.setZOrderMediaOverlay(false);
        AliRtcEngine.AliVideoCanvas aliVideoCanvas = new AliRtcEngine.AliVideoCanvas();
        aliVideoCanvas.view = mLocalView;
        aliVideoCanvas.renderMode = AliRtcRenderModeAuto;
        if (mAliRtcEngine != null) {
            mAliRtcEngine.setLocalViewConfig(aliVideoCanvas, AliRtcVideoTrackCamera);
        }
    }

    // 阿里音视频通话
//appkey   0d79eb619746d6f8625143cdf58b124f  应用id :  50ucoiom
    private void joinChannel() {
        //从控制台生成的鉴权信息，具体内容请查阅:https://help.aliyun.com/document_detail/146833.html
        if (mAliRtcEngine == null) {
            return;
        }
        AliRtcAuthInfo userInfo = new AliRtcAuthInfo();
        userInfo.setAppid(bean.getAppId());
        userInfo.setNonce(bean.getNonce());
        List<String> gslb = bean.getGSLB();
        String[] strings = new String[gslb.size()];
        userInfo.setGslb(gslb.toArray(strings));
        userInfo.setTimestamp(bean.getTimestamp());
        userInfo.setToken(bean.getToken());
        userInfo.setConferenceId(bean.getChannelId());
        userInfo.setUserId(bean.getUserId());
        /*
         *设置自动发布和订阅，只能在加入频道之前设置。
         *autoPub：是否自动发布，取值true|false。
         *autoSub：是否自动订阅，取值true|false。
         */
        mAliRtcEngine.setAutoPublishSubscribe(true, true);

        String[] onlineRemoteUsers = mAliRtcEngine.getOnlineRemoteUsers();
        Logger.e("房间远程人数=="+onlineRemoteUsers.length);
        if (type.equals("call")){
            // 加入频道，需要填写鉴权信息和用户名。
                mAliRtcEngine.joinChannel(userInfo, "用户名");

        }else {
            if (onlineRemoteUsers.length>1){
                showToast("已有人在提供帮助");
            }else if(onlineRemoteUsers.length == 0){
                showToast("对方已取消通话");
            }else {
                // 加入频道，需要填写鉴权信息和用户名。
                mAliRtcEngine.joinChannel(userInfo, "用户名");
            }
        }



    }

    private void addRemoteUser(String uid) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Logger.e("远端用户id=="+uid);
                //判断用户是否在线
                if (null == mAliRtcEngine) {
                    return;
                }
                AliRtcRemoteUserInfo remoteUserInfo = mAliRtcEngine.getUserInfo(uid);
                if (remoteUserInfo != null) {
                    mUserListAdapter.updateData(convertRemoteUserToUserData(remoteUserInfo), true);
                    tvTip.setVisibility(View.GONE);
                    handler.sendMessageDelayed(Message.obtain(handler, 0x0), 1000);
                }
            }
        });
    }

    private void removeRemoteUser(String uid) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mUserListAdapter.removeData(uid, true);
            }
        });
    }

    private void updateRemoteDisplay(String uid, AliRtcEngine.AliRtcAudioTrack at, AliRtcEngine.AliRtcVideoTrack vt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (null == mAliRtcEngine) {
                    return;
                }
                AliRtcRemoteUserInfo remoteUserInfo = mAliRtcEngine.getUserInfo(uid);
                // 如果没有，说明已经退出了或者不存在。则不需要添加，并且删除
                if (remoteUserInfo == null) {
                    // remote user exit room
                    Log.e(TAG, "updateRemoteDisplay remoteUserInfo = null, uid = " + uid);
                    return;
                }
                //change
                AliRtcEngine.AliVideoCanvas cameraCanvas = remoteUserInfo.getCameraCanvas();
                AliRtcEngine.AliVideoCanvas screenCanvas = remoteUserInfo.getScreenCanvas();
                //视频情况
                if (vt == AliRtcVideoTrackNo) {
                    //没有视频流
                    cameraCanvas = null;
                    screenCanvas = null;
                } else if (vt == AliRtcVideoTrackCamera) {
                    //相机流
                    screenCanvas = null;
                    cameraCanvas = createCanvasIfNull(cameraCanvas);
                    //SDK内部提供进行播放的view
                    mAliRtcEngine.setRemoteViewConfig(cameraCanvas, uid, AliRtcVideoTrackCamera);
                } else if (vt == AliRtcVideoTrackScreen) {
                    //屏幕流
                    cameraCanvas = null;
                    screenCanvas = createCanvasIfNull(screenCanvas);
                    //SDK内部提供进行播放的view
                    mAliRtcEngine.setRemoteViewConfig(screenCanvas, uid, AliRtcVideoTrackScreen);
                } else if (vt == AliRtcVideoTrackBoth) {
                    //多流
                    cameraCanvas = createCanvasIfNull(cameraCanvas);
                    //SDK内部提供进行播放的view
                    mAliRtcEngine.setRemoteViewConfig(cameraCanvas, uid, AliRtcVideoTrackCamera);
                    screenCanvas = createCanvasIfNull(screenCanvas);
                    //SDK内部提供进行播放的view
                    mAliRtcEngine.setRemoteViewConfig(screenCanvas, uid, AliRtcVideoTrackScreen);
                } else {
                    return;
                }
                ChartUserBean chartUserBean = convertRemoteUserInfo(remoteUserInfo, cameraCanvas, screenCanvas);
                mUserListAdapter.updateData(chartUserBean, true);

            }
        });
    }

    private ChartUserBean convertRemoteUserToUserData(AliRtcRemoteUserInfo remoteUserInfo) {
        String uid = remoteUserInfo.getUserID();
        ChartUserBean ret = mUserListAdapter.createDataIfNull(uid);
        ret.mUserId = uid;
        ret.mUserName = remoteUserInfo.getDisplayName();
        ret.mIsCameraFlip = false;
        ret.mIsScreenFlip = false;
        return ret;
    }

    private AliRtcEngine.AliVideoCanvas createCanvasIfNull(AliRtcEngine.AliVideoCanvas canvas) {
        if (canvas == null || canvas.view == null) {
            //创建canvas，Canvas为SophonSurfaceView或者它的子类
            canvas = new AliRtcEngine.AliVideoCanvas();
            SophonSurfaceView surfaceView = new SophonSurfaceView(this);
            surfaceView.setZOrderOnTop(true);
            surfaceView.setZOrderMediaOverlay(true);
            canvas.view = surfaceView;
            //renderMode提供四种模式：Auto、Stretch、Fill、Crop，建议使用Auto模式。
            canvas.renderMode = AliRtcRenderModeAuto;
        }
        return canvas;
    }

    private ChartUserBean convertRemoteUserInfo(AliRtcRemoteUserInfo remoteUserInfo,
                                                AliRtcEngine.AliVideoCanvas cameraCanvas,
                                                AliRtcEngine.AliVideoCanvas screenCanvas) {
        String uid = remoteUserInfo.getUserID();
        ChartUserBean ret = mUserListAdapter.createDataIfNull(uid);
        ret.mUserId = remoteUserInfo.getUserID();

        ret.mUserName = remoteUserInfo.getDisplayName();

        ret.mCameraSurface = cameraCanvas != null ? cameraCanvas.view : null;
        ret.mIsCameraFlip = cameraCanvas != null && cameraCanvas.mirrorMode == AliRtcEngine.AliRtcRenderMirrorMode.AliRtcRenderMirrorModeAllEnabled;

        ret.mScreenSurface = screenCanvas != null ? screenCanvas.view : null;
        ret.mIsScreenFlip = screenCanvas != null && screenCanvas.mirrorMode == AliRtcEngine.AliRtcRenderMirrorMode.AliRtcRenderMirrorModeAllEnabled;

        return ret;
    }

    /**
     * 特殊错误码回调的处理方法
     *
     * @param error 错误码
     */
    private void processOccurError(int error) {
        switch (error) {
            case ERR_ICE_CONNECTION_HEARTBEAT_TIMEOUT:
            case ERR_SESSION_REMOVED:
                noSessionExit(error);
                break;
            default:
                break;
        }
    }

    /**
     * 错误处理
     *
     * @param error 错误码
     */
    private void noSessionExit(int error) {
        runOnUiThread(() -> new AlertDialog.Builder(AliRtcChatActivity.this)
                .setTitle("ErrorCode : " + error)
                .setMessage("发生错误，请退出房间")
                .setPositiveButton("确定", (dialog, which) -> {
                    dialog.dismiss();
                    onBackPressed();
                })
                .create()
                .show());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
        if (mAliRtcEngine != null) {
            mAliRtcEngine.destroy();
        }
        SoundPoolManager.getInstance(AliRtcChatActivity.this).release();
    }

    /**
     * 用户操作回调监听(回调接口都在子线程)
     */
    private AliRtcEngineEventListener mEventListener = new AliRtcEngineEventListener() {

        /**
         * 加入房间的回调
         * @param result 结果码
         */
        @Override
        public void onJoinChannelResult(int result) {
            runOnUiThread(() -> {
                if (result == 0) {
                    showToast("加入频道成功");
                } else {
                    showToast("加入频道失败 错误码: " + result);
                }
            });
        }

        /**
         * 订阅成功的回调
         * @param s userid
         * @param i 结果码
         * @param aliRtcVideoTrack 视频的track
         * @param aliRtcAudioTrack 音频的track
         */
        @Override
        public void onSubscribeResult(String s, int i, AliRtcEngine.AliRtcVideoTrack aliRtcVideoTrack,
                                      AliRtcEngine.AliRtcAudioTrack aliRtcAudioTrack) {
            if (i == 0) {
                updateRemoteDisplay(s, aliRtcAudioTrack, aliRtcVideoTrack);
            }
        }

        /**
         * 取消的回调
         * @param i 结果码
         * @param s userid
         */
        @Override
        public void onUnsubscribeResult(int i, String s) {
            updateRemoteDisplay(s, AliRtcAudioTrackNo, AliRtcVideoTrackNo);
        }

        /**
         * 出现错误的回调
         * @param error 错误码
         */
        @Override
        public void onOccurError(int error) {
            //错误处理
            processOccurError(error);
        }
    };

    /**
     * SDK事件通知(回调接口都在子线程)
     */
    private AliRtcEngineNotify mEngineNotify = new AliRtcEngineNotify() {
        /**
         * 远端用户停止发布通知，处于OB（observer）状态
         * @param aliRtcEngine 核心引擎对象
         * @param s userid
         */
        @Override
        public void onRemoteUserUnPublish(AliRtcEngine aliRtcEngine, String s) {
            updateRemoteDisplay(s, AliRtcAudioTrackNo, AliRtcVideoTrackNo);
        }

        /**
         * 远端用户上线通知
         * @param s userid
         */
        @Override
        public void onRemoteUserOnLineNotify(String s) {
            addRemoteUser(s);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SoundPoolManager.getInstance(AliRtcChatActivity.this).release();
                }
            });
        }

        /**
         * 远端用户下线通知
         * @param s userid
         */
        @Override
        public void onRemoteUserOffLineNotify(String s) {
            removeRemoteUser(s);
        }

        /**
         * 远端用户发布音视频流变化通知
         * @param s userid
         * @param aliRtcAudioTrack 音频流
         * @param aliRtcVideoTrack 相机流
         */
        @Override
        public void onRemoteTrackAvailableNotify(String s, AliRtcEngine.AliRtcAudioTrack aliRtcAudioTrack,
                                                 AliRtcEngine.AliRtcVideoTrack aliRtcVideoTrack) {
            updateRemoteDisplay(s, aliRtcAudioTrack, aliRtcVideoTrack);
        }
    };

    private ChartUserAdapter.OnSubConfigChangeListener mOnSubConfigChangeListener = new ChartUserAdapter.OnSubConfigChangeListener() {
        @Override
        public void onFlipView(String uid, int flag, boolean flip) {
            AliRtcRemoteUserInfo userInfo = mAliRtcEngine.getUserInfo(uid);
            switch (flag) {
                case CAMERA:
                    if (userInfo != null) {
                        AliRtcEngine.AliVideoCanvas cameraCanvas = userInfo.getCameraCanvas();
                        if (cameraCanvas != null) {
                            cameraCanvas.mirrorMode = flip ? AliRtcEngine.AliRtcRenderMirrorMode.AliRtcRenderMirrorModeAllEnabled : AliRtcEngine.AliRtcRenderMirrorMode.AliRtcRenderMirrorModeAllDisable;
                            mAliRtcEngine.setRemoteViewConfig(cameraCanvas, uid, AliRtcVideoTrackCamera);
                        }
                    }
                    break;
                case SCREEN:
                    if (userInfo != null) {
                        AliRtcEngine.AliVideoCanvas screenCanvas = userInfo.getScreenCanvas();
                        if (screenCanvas != null) {
                            screenCanvas.mirrorMode = flip ? AliRtcEngine.AliRtcRenderMirrorMode.AliRtcRenderMirrorModeAllEnabled : AliRtcEngine.AliRtcRenderMirrorMode.AliRtcRenderMirrorModeAllDisable;
                            mAliRtcEngine.setRemoteViewConfig(screenCanvas, uid, AliRtcVideoTrackScreen);
                        }
                    }
                    break;
            }
        }

        @Override
        public void onShowVideoInfo(String uid, int flag) {
            AliRtcEngine.AliRtcVideoTrack track = AliRtcVideoTrackNo;
            switch (flag) {
                case CAMERA:
                    track = AliRtcVideoTrackCamera;
                    break;
                case SCREEN:
                    track = AliRtcVideoTrackScreen;
                    break;
            }
            if (mAliRtcEngine != null) {
                String result = mAliRtcEngine.getMediaInfoWithUserId(uid, track, VIDEO_INFO_KEYS);

                Toast.makeText(AliRtcChatActivity.this, result, Toast.LENGTH_SHORT).show();
            }
        }
    };


}
