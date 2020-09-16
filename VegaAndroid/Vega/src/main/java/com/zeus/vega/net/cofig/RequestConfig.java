package com.zeus.vega.net.cofig;

import com.zeus.vega.net.factory.IBaseUrlListener;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;

/**
 * @author minggo(戴统民)
 * @date 2020/9/15
 */
public final class RequestConfig {
    /**必传参数**/
    public static boolean DEBUG;
    /**Retrofit**/
    public static String URL_DOMAIN;

    /***请求接口超时设定**/
    public static int CONNECT_TIMEOUT_SECONDS=60;
    public static int READ_TIMEOUT_SECONDS=60;
    public static int WRITE_TIMEOUT_SECONDS=60;

    /**
     * 动态修改baseURL接口实现接口类
     */
    public static IBaseUrlListener baseUrlListener;
    /**
     * 配置拦截器
     */
    public static List<Interceptor> userInterceptors = new ArrayList<>();
}
