package io.ride.DO;

import java.util.HashMap;
import java.util.Map;

public class Result {

    // 0 fail
    // 1 success
    private int code;
    private Map<String, Object> data;

    public Result() {
        data = new HashMap<String, Object>();
    }

    public Result(int code) {
        this.code = code;
        data = new HashMap<String, Object>();
    }

    public Result setData(String key, Object value) {
        data.put(key, value);
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }
}
