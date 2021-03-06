package com.zeus.vega.net.subcriber;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.zeus.vega.net.exception.ResponseThrowable;

import java.lang.ref.WeakReference;

/**
 *
 * 接口调用
 * @author minggo(戴统民)
 * @date 2020/9/15
 */
public abstract class RequestSubscriber<T> extends BaseRequestSubscriber<T> {

    private WeakReference<Activity> activityWeakReference = null;

    public RequestSubscriber(@NonNull Activity activity) {
        activityWeakReference = new WeakReference<>(activity);
    }

    @Override
    protected final void onApiNext(T t) {
        if (activityWeakReference != null && activityWeakReference.get() != null) {
            Activity activity = activityWeakReference.get();
            if (activity.isFinishing() || activity.isDestroyed()) {
                return;
            }
            onSuccess(t);
        }
    }

    @Override
    protected final void onApiError(ResponseThrowable e) {
        if (activityWeakReference != null && activityWeakReference.get() != null) {
            Activity activity = activityWeakReference.get();
            if (activity.isFinishing() || activity.isDestroyed()) {
                return;
            }
            onFailed(e);
        }
    }

    protected void onSuccess(T t){

    }

    protected void onFailed(ResponseThrowable e){

    }
}
