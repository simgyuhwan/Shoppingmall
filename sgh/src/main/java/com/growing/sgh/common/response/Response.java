package com.growing.sgh.common.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Response {

    private boolean success;
    private int code;
    private Result result;

    public static Response success(){
        return new Response(true, 0, null);
    }

    public static <T> Response success(T data){
        return new Response(true, 0, new Success<T>(data));
    }

    public static Response failure(int code, String msg){
        return new Response(false, code, new Failure(msg));
    }
}
