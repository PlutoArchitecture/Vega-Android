package com.zeus.vega.api.factory;

import com.zeus.vega.api.cofig.RequestConfig;

import org.jetbrains.annotations.NotNull;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * @author minggo(戴统民)
 * @date 2020/9/15
 */
public class BaseUrlCallFactory extends CallFactoryProxy {

    public BaseUrlCallFactory(Call.Factory delegate) {
        super(delegate);
    }

    @NotNull
    @Override
    public Call newCall(@NotNull Request request) {
        if (RequestConfig.baseUrlListener != null) {
            String baseUrlName = request.header(RequestConfig.URL_DOMAIN);
            if (baseUrlName != null) {
                HttpUrl newUrl = RequestConfig.baseUrlListener.getBaseUrl(baseUrlName, request);
                if (newUrl != null) {
                    HttpUrl newHttpUrl = request.url()
                            .newBuilder()
                            .scheme(newUrl.scheme())
                            .host(newUrl.host())
                            .port(newUrl.port())
                            .build();
                    Request newRequest = request.newBuilder()
                            .removeHeader(RequestConfig.URL_DOMAIN)
                            .url(newHttpUrl).build();
                    return delegate.newCall(newRequest);
                }
            }
        }
        return delegate.newCall(request);
    }
}
