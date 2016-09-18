package com.bimface.sdk.bean.response;

import java.util.List;
import java.util.Map;

/**
 * 属性元素
 * 
 * @author bimface, 2016-08-17.
 */
public class PropertiesElement {

    private String                    group;// 属性分组
    private List<Map<String, String>> items;// 属性项

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<Map<String, String>> getItems() {
        return items;
    }

    public void setItems(List<Map<String, String>> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "PropertyElement [group=" + group + ", items=" + items + "]";
    }

}
