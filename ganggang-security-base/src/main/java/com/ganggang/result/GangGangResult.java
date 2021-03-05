package com.ganggang.result;

import com.alibaba.fastjson.JSON;
import lombok.Data;

@Data
public class GangGangResult {
    // 响应业务状态
    private Integer code;

    // 响应消息
    private String message;

    // 响应中的数据
    private Object data;

    public GangGangResult() {
    }

    public GangGangResult(Object data) {
        this.code = 200;
        this.message = "OK";
        this.data = data;
    }

    public GangGangResult(String message, Object data) {
        this.code = 200;
        this.message = message;
        this.data = data;
    }

    public GangGangResult(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static GangGangResult ok() {
        return new GangGangResult(null);
    }

    public static GangGangResult ok(String message) {
        return new GangGangResult(message, null);
    }

    public static GangGangResult ok(Object data) {
        return new GangGangResult(data);
    }

    public static GangGangResult ok(String message, Object data) {
        return new GangGangResult(message, data);
    }

    public static GangGangResult build(Integer code, String message) {
        return new GangGangResult(code, message, null);
    }

    public static GangGangResult build(Integer code, String message, Object data) {
        return new GangGangResult(code, message, data);
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }

    /**
     * JSON字符串转成 GangGangResult 对象
     *
     * @param json
     * @return
     */
    public static GangGangResult format(String json) {
        try {
            return JSON.parseObject(json, GangGangResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
