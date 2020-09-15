package com.zeus.vega.api.factory;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * @author minggo(戴统民)
 * @date 2020/9/15
 */
public interface IBaseUrlListener {
    HttpUrl getBaseUrl(String domain, Request request);
}
