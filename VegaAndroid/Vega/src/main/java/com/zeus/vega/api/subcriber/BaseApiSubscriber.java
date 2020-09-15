package com.zeus.vega.api.subcriber;

import com.zeus.vega.api.exception.ApiException;
import com.zeus.vega.model.Result;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.InvalidParameterException;

import io.reactivex.observers.DisposableObserver;

/**
 * @author minggo(戴统民)
 * @date 2020/9/15
 */
public abstract class BaseApiSubscriber<T> extends DisposableObserver<Result<T>> {

    @Override
    public final void onComplete() {
        if (!isDisposed()){
            dispose();
        }
    }

    @Override
    public final void onError(Throwable e) {
        if (isDisposed()) {
            return;
        }else{
            dispose();
        }

        if (e != null) {
            onApiError(ApiException.getResponseThrowable(e));
        }
    }

    @Override
    public final void onNext(Result<T> result) {
        if (isDisposed()) {
            return;
        }else{
            dispose();
        }

        if (result != null) {
            if (isT2Result()){
                onApiNext((T) result);
            }else{
                onApiNext(result.content);
            }
        }
    }

    /**
     * 通过层层剥离泛型层级来判别Result<Result>，但暂时不能用来判断出List或其他装载类型（可以考虑获取typename的String类型来判别）
     * @return 是否需要返回最外层Result
     */
    private boolean isT2Result(){
        Type type = getClass().getGenericSuperclass();
        if (!(type instanceof Class<?>)) {
            if (type instanceof ParameterizedType) {
                Type actualTypeArgument = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                if (actualTypeArgument instanceof ParameterizedType) {
                    return false;
                }else if (actualTypeArgument instanceof  Class){
                    Class<T> tClass = (Class<T>) actualTypeArgument;
                    return tClass == Result.class;
                }else {
                    throw new InvalidParameterException("VegaHttpClient isn't supported this Type -> "+type.toString());
                }
            }
        }
        return false;
    }


    protected abstract void onApiNext(T t);
    protected abstract void onApiError(ApiException.ResponseThrowable responseThrowable);

}
