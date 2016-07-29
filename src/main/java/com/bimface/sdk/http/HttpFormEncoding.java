package com.bimface.sdk.http;

import java.util.HashMap;
import java.util.Map;

/**
 * x-www-form-urlencoded表单提交的参数
 * 
 * @author bimface, 2016-06-01.
 */
public class HttpFormEncoding {

    private Map<String, String> params = new HashMap<String, String>();

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    /**
     * 追加参数
     * 
     * @param key Key
     * @param value Value
     */
    public void addParams(String key, String value) {
        this.params.put(key, value);
    }
}
