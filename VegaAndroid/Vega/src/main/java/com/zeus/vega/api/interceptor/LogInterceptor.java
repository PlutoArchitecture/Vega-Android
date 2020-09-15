package com.zeus.vega.api.interceptor;

import android.text.TextUtils;


import com.zeus.vega.logger.Logger;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * @author minggo(戴统民)
 * @date 2020/9/15
 */
public class LogInterceptor implements Interceptor {
    public static final String TAG = "LogInterceptor";
    private String tag;
    private boolean showResponse;

    public LogInterceptor(String tag, boolean showResponse) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        this.showResponse = showResponse;
        this.tag = tag;
    }

    public LogInterceptor(String tag) {
        this(tag, true);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logRequest(request);
        Response response = null;
        try {
            response = chain.proceed(request);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return logResponse(response);
    }

    private void logRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();

            Logger.w(tag, "========request=======");
            Logger.w(tag, "method : " + request.method());
            Logger.w(tag, "url : " + url);
            if (headers != null && headers.size() > 0) {
                Logger.w(tag, "headers : " + headers.toString());
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                if (isTextMedia(requestBody.contentType())) {
                    Logger.w(tag, "requestBody's body content : " + bodyToString(request));
                } else {
                    Logger.w(tag, "requestBody's body length: " + requestBody.contentLength());
                }
            }
            Logger.w(tag, "========request=======end");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Response logResponse(Response response) {
        try {
            Logger.w(tag, "========response=======");
            if (Logger.isLogOn()) {
                Logger.w(tag, "request.url : " + response.request().url());
                Logger.w(tag, "response.code : " + response.code());
            }

            if (showResponse && response != null) {
                ResponseBody body = response.body();
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    if (isTextMedia(mediaType)) {
                        String resp = body.string();
                        if (Logger.isLogOn()) {
                            Logger.w(tag, "responseBody's content : " + resp);
                        }

                        // body.string()方法清空了response中的数据,需要重新放回去
                        response = response.newBuilder().body(ResponseBody.create(mediaType, resp)).build();
                    }
                }
            }

            Logger.w(tag, "========response=======end");
        } catch (Error | Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private boolean isTextMedia(MediaType mediaType) {
        if (mediaType == null) {
            return false;
        }

        if ("text".equalsIgnoreCase(mediaType.type())) {
            return true;
        }

        String subtype = mediaType.subtype();
        return "json".equalsIgnoreCase(subtype)
                || "xml".equalsIgnoreCase(subtype)
                || "html".equalsIgnoreCase(subtype)
                || "webviewhtml".equalsIgnoreCase(subtype)
                || "x-www-form-urlencoded".equalsIgnoreCase(subtype);

    }

    private String bodyToString(final Request request) {
        try {
            if (isTextMedia(request.body().contentType())) {
                final Buffer buffer = new Buffer();
                request.newBuilder().build().body().writeTo(buffer);
                return buffer.readUtf8();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
