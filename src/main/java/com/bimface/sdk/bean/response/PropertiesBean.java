package com.bimface.sdk.bean.response;

import java.util.List;

/**
 * 模型构件属性
 * 
 * @author bimface, 2016-08-17.
 */
public class PropertiesBean {

    private String                  id;         // 构件ID
    private String                  name;       // 构件名称
    private List<PropertiesElement> properties; // 属性信息

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PropertiesElement> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertiesElement> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "Property [id=" + id + ", name=" + name + ", properties=" + properties + "]";
    }

}
