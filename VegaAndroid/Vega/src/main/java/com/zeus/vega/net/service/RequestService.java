package com.zeus.vega.net.service;

import android.util.ArrayMap;

import com.zeus.vega.model.Result;
import com.zeus.vega.model.User;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * 接口定义类
 * @author minggo(戴统民)
 * @date 2020/9/16
 */
public interface RequestService {


//    @GET
//    <T> Observable<Result<T>> get(@Url String url, @QueryMap  ArrayMap<String, Object> map);

    @GET
    Observable<Result> get(@Url String url, @QueryMap  ArrayMap<String, Object> map);

    @POST
    Observable<Result> post(@Url String url, @FieldMap  ArrayMap<String, Object> map);

    @GET
    Observable<Result<User>> test(@Url String url, @QueryMap  ArrayMap<String, Object> map);
}
