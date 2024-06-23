package com.sample.music.domain.result;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.sample.music.domain.result.HttpStatusCode.BAD_REQUEST;
import static com.sample.music.domain.result.HttpStatusCode.OK;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public static <T> Result<T> success() {
        return new Result<>(OK, "操作成功", null);
    }
    public static <T> Result<T> success(T data) {
        return new Result<>(OK, "操作成功", data);
    }
    public static <T> Result<T> success(int code, String message, T data) {
        return new Result<>(code, message, data);
    }
    public static <T> Result<T> error() {
        return new Result<>(BAD_REQUEST, "操作失败", null);
    }
    public static <T> Result<T> error(String message) {
        return new Result<>(BAD_REQUEST, message, null);
    }
    public static <T> Result<T> error(int code, String message, T data) {
        return new Result<>(code, message, data);
    }

    public String asJsonString(){
        return JSONObject.toJSONString(this, JSONWriter.Feature.WriteNulls);
    }
}
