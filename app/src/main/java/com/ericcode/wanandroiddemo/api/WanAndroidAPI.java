package com.ericcode.wanandroiddemo.api;

import com.ericcode.wanandroiddemo.bean.Banner;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface WanAndroidAPI {

    @GET("banner/json")
    Observable<Banner> getBanner();


}
