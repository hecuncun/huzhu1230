package com.jzbn.huzhu1230.net;

import com.jzbn.huzhu1230.base.BaseBean;
import com.jzbn.huzhu1230.base.BaseNoDataBean;
import com.jzbn.huzhu1230.bean.CollectionResponseBean;
import com.jzbn.huzhu1230.bean.CommonRescueBean;
import com.jzbn.huzhu1230.bean.DailyRescueBean;
import com.jzbn.huzhu1230.bean.HonorInfoBean;
import com.jzbn.huzhu1230.bean.InsertCollectionResponseBean;
import com.jzbn.huzhu1230.bean.KnowledgeBean;
import com.jzbn.huzhu1230.bean.LoginBean;
import com.jzbn.huzhu1230.bean.MsgBean;
import com.jzbn.huzhu1230.bean.PersonalInfoBean;
import com.jzbn.huzhu1230.bean.PhoneCodeBean;
import com.jzbn.huzhu1230.bean.RescueVideoBean;
import com.jzbn.huzhu1230.bean.ScoreBean;
import com.jzbn.huzhu1230.bean.SearchCollectionInfoResponseBean;
import com.jzbn.huzhu1230.bean.SignBean;
import com.jzbn.huzhu1230.bean.MessageUnReadBean;
import com.jzbn.huzhu1230.bean.SysMsgBean;
import com.jzbn.huzhu1230.bean.UserInfoBean;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by hecuncun on 2019/5/13
 */
public interface Api {

    /**
     * 获取手机验证码
     * @param phone
     * @param type 1：注册，2：重置密码
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
     * @param phone
     * @param pwd
     * @return
     */
    @POST("appUserBase/logoin")
    Observable<BaseBean<LoginBean>> loginCall(@Query("phone") String phone, @Query("pwd") String pwd);

    /**
     * 重置密码
     * @param phone
     * @param code
     * @param pwd
     * @return
     */
    @POST("appUserBase/resetPwd")
    Observable<BaseNoDataBean> resetPwdCall(@Query("phone") String phone, @Query("code") String code, @Query("pwd") String pwd);

    /**
     * 查询个人信息
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
     * @param uid
     * @return
     */
    @POST("appUserSignIn/insertSelective")
    Observable<SignBean> signCall(@Query("uid") String uid);
    /**
     *积分记录
     */
    @POST("appUserIntegralDetail/searchForPage")
    Observable<BaseBean<ScoreBean>> scoreListCall(@Query("page") int page, @Query("uid") String uid);

    /**
     * 获取救援知识
     * @param page
     * @param content
     * @param type  1文章,2视频
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
     * @return
     */
    @POST("appRescueItem/searchAll")
    Observable<CommonRescueBean> commonRescueCall();

    /**
     * 获取日常救援列表
     * @param page
     * @param pid
     * @return
     */
    @POST("appRescueItem/searchForPage")
    Observable<BaseBean<DailyRescueBean>> dailyRescueCall(@Query("page") int page,@Query("pid") String pid);

    /**
     * 获取救援项目详情
     * @param rid
     * @return
     */
    @POST("appRescueItem/selectDetail")
    Observable<BaseBean<RescueVideoBean>> rescueVideoCall(@Query("rid") String rid);

    /**
     *新增收藏
     */
    @POST("appUserCollection/insertSelective")
     Observable<BaseBean<InsertCollectionResponseBean>> insertCollectCall(@Query("uid") String uid,@Query("objectId") String objectId,@Query("type") int type);

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
    Observable<BaseBean<CollectionResponseBean>> getCollectionListCall(@Query("page") int page,@Query("uid") String uid);
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
//
//    /**
//     * 修改用户信息
//     * @param uid
//     * @param nickName
//     * @param picture
//     * @return
//     */
//    @POST("appUserBase/updateById")
//    Observable<BaseNoDataBean> updateInfoCall(@Query("uid") String uid, @Query("nickName") String nickName, @Query("picture") String picture);
//


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