package com.haiyingchuan.quickcore;

import com.kbryant.quickcore.util.RespBase;

import io.reactivex.Flowable;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MainService {
    /**
     * @param client_model 手机型号
     * @return
     */
    @POST(Api.postAppOpenRecord)
    @FormUrlEncoded
    Flowable<RespBase<String>> postAppOpenRecord(@Field("client_model") String client_model);
}
