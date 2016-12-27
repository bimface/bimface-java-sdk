package com.bimface.sdk.bean.response;

import java.io.Serializable;
import java.util.List;

/**
 * 属性元素
 * 
 * @author bimface, 2016-08-17.
 */
public class PropertyGroup implements Serializable {

    private static final long  serialVersionUID = 5071579400913290355L;
    private String             group;                                  // 属性分组
    private List<PropertyItem> items;                                  // 属性项

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<PropertyItem> getItems() {
        return items;
    }

    public void setItems(List<PropertyItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "PropertiesElement [group=" + group + ", items=" + items + "]";
    }

}
