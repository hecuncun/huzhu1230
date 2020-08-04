package com.jzbn.huzhu1230.net;

import com.jzbn.huzhu1230.base.BaseBean;
import com.jzbn.huzhu1230.base.BaseNoDataBean;
import com.jzbn.huzhu1230.bean.AedBean;
import com.jzbn.huzhu1230.bean.AliVideoBean;
import com.jzbn.huzhu1230.bean.CertificateBean;
import com.jzbn.huzhu1230.bean.CollectionResponseBean;
import com.jzbn.huzhu1230.bean.CommonRescueBean;
import com.jzbn.huzhu1230.bean.CreditBean;
import com.jzbn.huzhu1230.bean.DailyRescueBean;
import com.jzbn.huzhu1230.bean.HonorInfoBean;
import com.jzbn.huzhu1230.bean.ImgBean;
import com.jzbn.huzhu1230.bean.InsertCollectionResponseBean;
import com.jzbn.huzhu1230.bean.KnowledgeBean;
import com.jzbn.huzhu1230.bean.LanguageBean;
import com.jzbn.huzhu1230.bean.LoginBean;
import com.jzbn.huzhu1230.bean.MessageUnReadBean;
import com.jzbn.huzhu1230.bean.MsgBean;
import com.jzbn.huzhu1230.bean.MyAedDetailBean;
import com.jzbn.huzhu1230.bean.NearAedBean;
import com.jzbn.huzhu1230.bean.PersonalInfoBean;
import com.jzbn.huzhu1230.bean.PhoneCodeBean;
import com.jzbn.huzhu1230.bean.PublishResponseBean;
import com.jzbn.huzhu1230.bean.RescueVideoBean;
import com.jzbn.huzhu1230.bean.ScoreBean;
import com.jzbn.huzhu1230.bean.SearchCollectionInfoResponseBean;
import com.jzbn.huzhu1230.bean.SearchDetailBean;
import com.jzbn.huzhu1230.bean.SearchPersonBean;
import com.jzbn.huzhu1230.bean.SignBean;
import com.jzbn.huzhu1230.bean.SysMsgBean;
import com.jzbn.huzhu1230.bean.UserInfoBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by hecuncun on 2019/5/13
 */
public interface Api {

    /**
     * 获取手机验证码
     *
     * @param phone
     * @param type  1：注册，2：重置密码
     * @return
     */

    @POST("appTcmnPhoneCode/insertSelective")
    Observable<BaseBean<PhoneCodeBean>> phoneCodeCall(@Query("phone") String phone, @Query("type") String type);

    /**
     * 用户注册
     *
     * @param phone
     * @param code
     * @param pwd
     * @return
     */
    @POST("appUserBase/insertSelective")
    Observable<BaseBean<UserInfoBean>> registerCall(@Query("phone") String phone, @Query("code") String code, @Query("pwd") String pwd);

    /**
     * 登录
     *
     * @param phone
     * @param pwd
     * @return
     */
    @POST("appUserBase/logoin")
    Observable<BaseBean<LoginBean>> loginCall(@Query("phone") String phone, @Query("pwd") String pwd);

    /**
     * 重置密码
     *
     * @param phone
     * @param code
     * @param pwd
     * @return
     */
    @POST("appUserBase/resetPwd")
    Observable<BaseNoDataBean> resetPwdCall(@Query("phone") String phone, @Query("code") String code, @Query("pwd") String pwd);

    /**
     * 查询个人信息
     *
     * @param uid
     * @return
     */
    @POST("appUserBase/selectDetail")
    Observable<BaseBean<PersonalInfoBean>> personalInfoCall(@Query("uid") String uid);

    /**
     * 获取荣誉积分
     */
    @POST("appUserBase/selectUserHonor")
    Observable<HonorInfoBean> honorInfoCall(@Query("uid") String uid);

    /**
     * 用户签到
     *
     * @param uid
     * @return
     */
    @POST("appUserSignIn/insertSelective")
    Observable<SignBean> signCall(@Query("uid") String uid);

    /**
     * 积分记录
     */
    @POST("appUserIntegralDetail/searchForPage")
    Observable<BaseBean<ScoreBean>> scoreListCall(@Query("page") int page, @Query("uid") String uid);

    /**
     * 获取救援知识
     *
     * @param page
     * @param content
     * @param type    1文章,2视频
     * @return
     */
    @POST("appKnowledge/searchForPage")
    Observable<BaseBean<KnowledgeBean>> knowledgeListCall(@Query("page") int page, @Query("content") String content, @Query("type") int type);

    /**
     * 查询平台消息列表
     */
    @POST("appUserMessage/selectPlatformMessageForPage")
    Observable<BaseBean<MsgBean>> platFormMsgCall(@Query("page") int page);

    /**
     * 查询系统消息列表
     */
    @POST("appUserMessage/searchForPage")
    Observable<BaseBean<SysMsgBean>> sysMsgCall(@Query("page") int page, @Query("uid") String uid);

    /**
     * 常用救援
     *
     * @return
     */
    @POST("appRescueItem/searchAll")
    Observable<CommonRescueBean> commonRescueCall();

    /**
     * 获取日常救援列表
     *
     * @param page
     * @param pid
     * @return
     */
    @POST("appRescueItem/searchForPage")
    Observable<BaseBean<DailyRescueBean>> dailyRescueCall(@Query("page") int page, @Query("pid") String pid);

    /**
     * 获取救援项目详情
     *
     * @param rid
     * @return
     */
    @POST("appRescueItem/selectDetail")
    Observable<BaseBean<RescueVideoBean>> rescueVideoCall(@Query("rid") String rid,@Query("uid") String uid,@Query("longitude") String longitude,@Query("latitude") String latitude,@Query("gpsArea") String gpsArea);

    /**
     * 新增收藏
     */
    @POST("appUserCollection/insertSelective")
    Observable<BaseBean<InsertCollectionResponseBean>> insertCollectCall(@Query("uid") String uid, @Query("objectId") String objectId, @Query("type") int type);

    /**
     * 查询是否已收藏
     */
    @POST("appUserCollection/searchCollectionInfo")
    Observable<BaseBean<SearchCollectionInfoResponseBean>> searchCollectInfoCall(@Query("uid") String uid, @Query("objectId") String objectId);

    /**
     * 取消收藏
     */
    @POST("appUserCollection/deleteById")
    Observable<BaseNoDataBean> deleteCollection(@Query("ids") String ids);

    /**
     * 获取收藏列表
     */
    @POST("appUserCollection/searchForPage")
    Observable<BaseBean<CollectionResponseBean>> getCollectionListCall(@Query("page") int page, @Query("uid") String uid);

    /**
     * 获取用户系统未读消息数量
     */
    @POST("appUserMessage/searchCount")
    Observable<MessageUnReadBean> getSysMsgUnreadNumCall(@Query("uid") String uid);

    /**
     * 获取平台消息总数
     */
    @POST("appUserMessage/countPlatformMessageTotal")
    Observable<MessageUnReadBean> getPlatFormMsgUnreadNumCall();

    /**
     * 获取附近的AED信息列表
     */
    @POST("appAedInfo/searchNearAED")
    Observable<NearAedBean> searchNearAedList(@Query("longitude") String longitude, @Query("latitude") String latitude);

    /**
     * 发布Aed信息
     */
    @POST("appAedInfo/insertSelective")
    Observable<PublishResponseBean> publishAedCall(@Query("uid") String uid, @Query("name") String name, @Query("area") String area, @Query("areaDetail") String areaDetail, @Query("longitude") String longitude, @Query("latitude") String latitude, @Query("phone") String phone);

    /**
     * 获取我发布的AED
     */
    @POST("appAedInfo/searchForPage")
    Observable<BaseBean<AedBean>> myAedCall(@Query("page") int page, @Query("uid") String uid);

    /**
     * 获取证书列表
     */
    @POST("appTcmnCard/searchAll")
    Observable<CertificateBean> certificateBeanCall();

    /**
     * 获取语言列表
     */
    @POST("appTcmnLanguage/searchAll")
    Observable<LanguageBean> languageBeanCall();

    /**
     * 上传头像接口
     */
    @POST("appimg/upload")
    @Multipart
    Observable<BaseBean<ImgBean>> uploadCall(@Part MultipartBody.Part file);
    /**
     * 修改个人信息
     */
    @POST("appUserBase/updateById")
    Observable<BaseNoDataBean> updateUserInfo(@QueryMap Map<String, String> map,@Query("uid") String uid);

    /**
     * 获取常年寻人列表
     */
    @POST("appFindInfo/searchPerennialForPage")
    Observable<BaseBean<SearchPersonBean>> getCommonSearchPersonBeanCall(@Query("page") int page);

    /**
     * 获取紧急寻人列表
     */
    @POST("appFindInfo/searchEmergencyForPage")
    Observable<BaseBean<SearchPersonBean>> getEmergencySearchPersonBeanCall(@Query("page") int page,@Query("longitude") String longitude,@Query("latitude") String latitude);

    /**
     * 获取我发的常年寻人
     */
    @POST("appFindInfo/searchMyPerennialForPage")
    Observable<BaseBean<SearchPersonBean>> getMyCommonSearchPersonCall(@Query("page") int page,@Query("uid") String uid);

    /**
     * 获取我发的紧急寻人
     */
    @POST("appFindInfo/searchMyEmergencyForPage")
    Observable<BaseBean<SearchPersonBean>> getMyEmergencySearchPersonBeanCall(@Query("page") int page,@Query("uid") String uid);
    /**
     * 发布寻人信息
     */
    @POST("appFindInfo/insertSelective")
    Observable<PublishResponseBean> publishSearchCall(@Query("uid") String uid,@Query("name") String name,@Query("card") String card,
                                                 @Query("photo") String photo,@Query("ddate") String ddate,@Query("area") String area,
                                                 @Query("areaDetail") String areaDetail, @Query("longitude") String longitude,@Query("latitude") String latitude,
                                                 @Query("way") String way, @Query("reason") String reason,@Query("features") String features,
                                                 @Query("region") String region, @Query("contact") String contact,@Query("relation") String relation,
                                                 @Query("qrcode") String qrcode, @Query("content") String content);
    /**
     * 获取寻人详情
     */
    @POST("appFindInfo/selectDetail")
    Observable<BaseBean<SearchDetailBean>> searchDetailCall(@Query("magorid") String magorid);

    /**
     * 提供线索
     */
    @POST("appFindInfo/insertProvideClues")
    Observable<BaseNoDataBean> provideClueCall(@Query("uid") String uid,@Query("findId") String findId,@Query("photo") String photo,@Query("ddate") String ddate,
                                               @Query("area") String area,@Query("areaDetail") String areaDetail,@Query("longitude") String longitude,@Query("latitude") String latitude,
                                               @Query("content") String content);

    /**
     * 人已找到
     */
    @POST("appFindInfo/updateFindStatus")
    Observable<BaseNoDataBean> findCall(@Query("uid") String uid,@Query("findId") String findId);

    /**
     * 信誉分列表
     */
    @POST("appUserScoreDetail/searchForPage")
    Observable<BaseBean<CreditBean>> creditListCall(@Query("page") int page,@Query("uid") String uid);

    /**
     * 阿里云视频
     */
    @POST("appRTC/selectDetail")
    Observable<BaseBean<AliVideoBean>> aliVideoCall(@Query("uid") String uid ,@Query("rid") String rid);

    /**
     * 加入房间
     */
    @POST("appRTC/selectToken")
    Observable<BaseBean<AliVideoBean>> notifyVideoCall(@Query("uid") String uid,@Query("channelId") String channelId);

    /**
     * 更新用户位置信息
     */
    @POST("appUserArea/updateById")
    Observable<BaseNoDataBean> updateUserLocation(@Query("uid") String uid,@Query("longitude") String longitude,@Query("latitude") String latitude);

    /**
     * 获取Aed详情
     */
    @POST("appAedInfo/selectDetail")
    Observable<BaseBean<MyAedDetailBean>> myAedDetailCall(@Query("magorid") String magorid);

    /**
     * 更新AED
     */
    @POST("appAedInfo/updateById")
    Observable<BaseNoDataBean> updateMyAedCall(@Query("uid") String uid ,@Query("magorid") String magorid,@Query("name") String name,
                                               @Query("area") String area,@Query("areaDetail") String areaDetail,@Query("longitude") String longitude,
                                               @Query("latitude") String latitude,@Query("phone") String phone);

    //    /**
//     * 修改自定义头像接口
//     */
//    @POST("vms/appapi/account/editPic")
//    @Multipart
//    Observable<BaseBean<NewPhotoBean>> changeHeadPhotoCall(@Part MultipartBody.Part file);
//

//     * 一键反馈
//     */
//    @POST("vms/appapi/sysMgr/feedback")
//    @Multipart
//    Observable<BaseNoDataBean> feedbackCall(@Query("content") String content, @Part MultipartBody.Part file, @Part List<MultipartBody.Part> list, @Query("phone") String phone);
//

//    /**
//     * 上传视频
//     */
//    @POST("vms/appapi/video/uploadVideo")
//    @Multipart
//    Observable<UploadVideoBean> uploadVideoCall(@Part MultipartBody.Part file);
//

//
//    /**
//     * 重置密码
//     *
//     * @param phone
//     * @param code
//     * @param pwd
//     * @return
//     */
//    @POST("appUserBase/resetPwd")
//    Observable<BaseNoDataBean> resetPwdCall(@Query("phone") String phone, @Query("code") String code, @Query("pwd") String pwd);
//


//    /**
//     * 积分列表
//     *
//     * @param page
//     * @param uid
//     * @return
//     */
//    @POST("appUserIntegral/searchForPage")
//    Observable<ScoreListBean> scoreListCall(@Query("page") int page, @Query("uid") String uid);

//
//    /**
//     * 上传图片
//     * @param file
//     * @return
//     */
//    @POST("appimg/upload")
//    @Multipart
//    Observable<BaseBean<ImgBean>> uploadCall(@Part MultipartBody.Part file);

//    /**
//     * 新增订单
//     * @param uid
//     * @param addressId
//     * @param cardId
//     * @param integralNum
//     * @param goodsInfoJsonStr
//     * @return
//     */
//    @FormUrlEncoded//str有中文必须加上此注解  防止乱码
//    @POST("appOrderInfo/insertSelective")
//    Observable<AddOrderBean> addOrderCall(@Query("uid") String uid, @Query("addressId") String addressId, @Query("cardId") String cardId, @Query("integralNum") int integralNum, @Field("goodsInfoJsonStr") String goodsInfoJsonStr);
//
//    /**
//     *wx App支付签名(统一下单),已完成二次签名,直接调用支付即可
//     * @param uid
//     * @param oid
//     * @param way
//     * @return
//     */
//
//    @POST("apppay/appsign")
//    Observable<WxPaySignBean> wxPaySignCall(@Query("uid") String uid, @Query("oid") String oid, @Query("way") int way);
//


//
//    /**
//     * 手持机与手表绑定接口
//     *
//     * @param mac         手持机mac号
//     * @param childNumber 手表的固话注册码
//     * @return
//     */
//    @POST("handsets/binding/{handsetNumber}-{childNumber}")
//    Observable<BaseBean> canBinding(@Path("handsetNumber") String mac, @Path("childNumber") String childNumber);
//
//    /**
//     * 手持机与手表解绑接口
//     *
//     * @param mac         手持机的mac号
//     * @param childNumber 手表的固话注册码   如果是多个之间用,隔开
//     * @return
//     */
//    @DELETE("handsets/binding/{handsetNumber}-{childNumber}")
//    Observable<BaseBean> deleteBinding(@Path("handsetNumber") String mac, @Path("childNumber") String childNumber);
//


//
//    /**
//     * 根据手持机的mac号  查询绑定的手表
//     */
//    @GET("handsets/binding/{handsetNumber}/watch")
//    Observable<BindingWatchBean> getBindingWatch(@Path("handsetNumber") String mac);
//
//    /**
//     * mac与北斗上传绑定关系的接口
//     */
//
//    @PUT("handsets/binding/bd/{handsetNumber}-{bdNumber}")
//    Observable<BaseBean> uploadMacAndBdNum(@Path("handsetNumber") String mac, @Path("bdNumber") String bdNumber);
}