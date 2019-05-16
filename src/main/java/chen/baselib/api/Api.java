package chen.baselib.api;

/**
 * Created by CHEN_ on 2019/4/11.
 */

public interface Api {
    String BASE_URL = "http://admin.lgw.com/api/";    //线下
    String BASE_IMAGE_URL = "http://admin.lgw.com/";    //线下图片
//    String BASE_URL = "https://api.laigewan.com/api/";    //线上
//    String BASE_IMAGE_URL = "https://api.laigewan.com/";    //线上图片

    /*    *//**
     * 版本更新
     *
     * @return
     *//*
    @POST(Url.versionsUpdate)
    Observable<BaseWebEntity<AppVersion>> versionUpdated();

    *//**
     * 发送验证码
     *//*
    @FormUrlEncoded
    @POST(Url.sendAuth)
    Observable<BaseWebEntity<String>> sendCode(@Field("auth_user") String autu_user, @Field("auth_type") int auth_type, @Field("phone") String phone);

    *//**
     * 注册
     *//*
    @FormUrlEncoded
    @POST(Url.register)
    Observable<BaseWebEntity<EmptyEntity>> doRegister(@FieldMap Map<String, String> data);*/

}
