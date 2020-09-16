package com.zeus.vega.common;

import android.app.Application;

import com.zeus.vega.Vega;

/**
 * @author minggo(戴统民)
 * @date 2020/9/16
 */
public class ExampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Vega.init(this);
    }
}
