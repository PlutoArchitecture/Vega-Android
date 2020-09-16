package com.zeus.vega.net;

import com.zeus.vega.net.cofig.RequestConfig;
import com.zeus.vega.net.factory.BaseUrlCallFactory;
import com.zeus.vega.net.interceptor.LogInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author minggo(戴统民)
 * @date 2020/9/15
 */
public class VegaHttpClient {

    private static VegaHttpClient vegaHttpClient;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private OkHttpClient.Builder okHttpBuilder;

    private VegaHttpClient() {
        initOkHttpBuilder();
        addInterceptors();
        initOkHttpClient();
        initRetrofit();
    }

    /**
     * 创建OkHttpClient.Builder
     */
    private void initOkHttpBuilder(){
        okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder
                .connectTimeout(RequestConfig.CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(RequestConfig.READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(RequestConfig.WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS);
    }
    /**
     * 添加拦截器
     */
    private void addInterceptors(){
        // 添加用户配置拦截器
        for (Interceptor interceptor : RequestConfig.userInterceptors) {
            okHttpBuilder.addInterceptor(interceptor);
        }
        // 添加日记拦截器
        okHttpBuilder.addInterceptor(new LogInterceptor("VegaHttpClient", RequestConfig.DEBUG));
    }
    /**
     * 创建client
     */
    private void initOkHttpClient(){
        okHttpClient = okHttpBuilder.build();
    }
    /**
     * 创建Retrofit
     */
    private void initRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(RequestConfig.URL_DOMAIN)
                //创建动态更换域名工厂
                .callFactory(new BaseUrlCallFactory(okHttpClient))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 创建Retrofit接口实例
     * @param service Service接口类
     * @param <T> 返回类型
     * @return
     */
    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }

    private static class SingletonHolder {
        private static final VegaHttpClient INSTANCE = new VegaHttpClient();
    }

    public static VegaHttpClient getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
