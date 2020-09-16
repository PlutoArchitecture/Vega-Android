package com.zeus.vega;

import android.app.Application;
import android.os.Environment;

import com.zeus.vega.net.VegaHttpClient;
import com.zeus.vega.net.cofig.RequestConfig;

/**
 * Pluto 框架配置
 * @author minggo(戴统民)
 * @date 2020/9/15
 */
public class Vega {

    public static boolean DEBUG ;
    /**
     * 域名
     */
    public static String URL_DOMAIN = "http://m8en.com:8877/";
    /**
     * 项目缓存目录
     */
    public static String APP_CACHE_FILE = "Vega";
    /**
     * 主程序SD卡目录
     */
    public static String SDPATH = Environment.getExternalStorageDirectory().getPath() + "/" + APP_CACHE_FILE + "/";

    public static void init(Application context){
        RequestConfig.URL_DOMAIN = URL_DOMAIN;
        RequestConfig.DEBUG = true;
        VegaHttpClient.getInstance().init();
    }
}
