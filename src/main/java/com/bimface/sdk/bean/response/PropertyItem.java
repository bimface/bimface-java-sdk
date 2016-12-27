package com.bimface.sdk.bean.response;

import java.io.Serializable;

/**
 * 属性项
 * 
 * @author bimface, 2016-12-01.
 */
public class PropertyItem implements Serializable {

    private static final long serialVersionUID = 7879458622149382903L;
    private String            key;                                    // 属性key
    private Object            value;                                  // 属性值
    private String            unit;                                   // 属性单位

    public PropertyItem() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "PropertyItem [key=" + key + ", value=" + value + ", unit=" + unit + "]";
    }

}
