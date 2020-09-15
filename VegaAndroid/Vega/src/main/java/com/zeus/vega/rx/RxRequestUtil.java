package com.zeus.vega.rx;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * 观察者模式调用工具
 * @author 戴统民
 */
public class RxRequestUtil {

    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 异步请求（io执行，切换回main线程）
     * @param subscriber
     */
    public static <T> void call(CompositeDisposable compositeDisposable, Observable<T> observable, DisposableObserver subscriber) {
        compositeDisposable.add(subscriber);
        observable.compose(applySchedulers())
                .subscribe(subscriber);
    }

    /**
     * 在当前同步请求
     * @param subscriber 订阅者
     */
    public static <T> void callSync(CompositeDisposable compositeDisposable, Observable<T> observable, DisposableObserver subscriber) {
        compositeDisposable.add(subscriber);
        observable.subscribe(subscriber);
    }

    /**
     * 异步请求（io执行，切换回main线程）
     * @param subscriber 订阅者
     */
    public static <T> void call(Observable<T> observable, DisposableObserver subscriber) {
        observable.compose(applySchedulers())
                .subscribe(subscriber);
    }

    /**
     * 在当前同步请求
     * @param subscriber 订阅者
     */
    public static <T> void callSync(Observable<T> observable, DisposableObserver subscriber) {
        observable.subscribe(subscriber);
    }

}
