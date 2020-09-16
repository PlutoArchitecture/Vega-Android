package com.zeus.vega.api.exception;

/**
 * @author minggo(戴统民)
 * @date 2020/9/16
 */
public class ServerException extends RuntimeException {
    public int code;
    public String message;
}