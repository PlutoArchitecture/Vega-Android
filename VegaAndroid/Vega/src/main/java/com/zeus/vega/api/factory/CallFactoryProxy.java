package com.zeus.vega.api.factory;

import java.util.Objects;

import okhttp3.Call;

/**
 * @author minggo(戴统民)
 * @date 2020/9/15
 */
public abstract class CallFactoryProxy implements Call.Factory {

    protected final Call.Factory delegate;

    public CallFactoryProxy(Call.Factory delegate) {
        this.delegate = delegate;
    }

}
