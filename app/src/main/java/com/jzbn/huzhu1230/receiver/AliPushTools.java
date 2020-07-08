package com.jzbn.huzhu1230.receiver;

import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;

/**
 * 阿里推送别名工具类
 */
public class AliPushTools {
    private SPUtils spUtils;
    public static final String KEY_STATE = "STATE";
    private static AliPushTools aliPushTools;

    public AliPushTools() {
        spUtils = SPUtils.getInstance("AliPushTools");
        ;
    }

    public static AliPushTools getInstance() {
        if (aliPushTools == null) {
            synchronized (AliPushTools.class) {
                aliPushTools = new AliPushTools();
            }
        }
        return aliPushTools;
    }

    /**
     * 绑定别名
     *
     * @param alias
     */
    public void bindAlias(final String alias) {
        if (!spUtils.getBoolean(KEY_STATE)) {
            PushServiceFactory.getCloudPushService().listAliases(new CommonCallback() {
                @Override
                public void onSuccess(String s) {
                    LogUtils.i("别名列表", s);
                    if (!StringUtils.isEmpty(s)) {
//                        如果别名不为空 ，清空所有
                        PushServiceFactory.getCloudPushService().removeAlias(null, new CommonCallback() {
                            @Override
                            public void onSuccess(String s) {
//                                          如果所有别名都被清空， 则设置新的别名
                                PushServiceFactory.getCloudPushService().addAlias(alias, new CommonCallback() {
                                    @Override
                                    public void onSuccess(String s) {
                                        spUtils.put(KEY_STATE, true);
                                    }

                                    @Override
                                    public void onFailed(String s, String s1) {
                                        //                bindAlias(alias);
                                    }
                                });
                            }

                            @Override
                            public void onFailed(String s, String s1) {

                            }
                        });
                    } else {
//                        如果之前别名列表为空，则直接设置别名
                        PushServiceFactory.getCloudPushService().addAlias(alias, new CommonCallback() {
                            @Override
                            public void onSuccess(String s) {
                                spUtils.put(KEY_STATE, true);
                            }

                            @Override
                            public void onFailed(String s, String s1) {
                                //                bindAlias(alias);
                            }
                        });
                    }
                }

                @Override
                public void onFailed(String s, String s1) {
                }
            });
        }
    }

    /**
     * 清空别名
     *
     * @param alias
     */
    public void unbindAlias(String alias) {
        PushServiceFactory.getCloudPushService().removeAlias(alias, new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                spUtils.put(KEY_STATE, false);
            }

            @Override
            public void onFailed(String s, String s1) {

            }
        });
    }
}