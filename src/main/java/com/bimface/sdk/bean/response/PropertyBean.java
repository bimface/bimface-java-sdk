package com.bimface.sdk.bean.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 属性对象
 * 
 * @author bimface, 2016-12-01.
 */
public class PropertyBean implements Serializable {

    private static final long serialVersionUID = -1191993400409611524L;

    private String              elementId;                                  // 构件id
    private String              name;                                       // 构件名
    private BoundingBox         boundingBox;                                // 包围盒
    private List<PropertyGroup> properties = new ArrayList<PropertyGroup>();// 属性列表

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    public List<PropertyGroup> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyGroup> properties) {
        this.properties = properties;
    }

    public void addPropertyGroup(PropertyGroup propertyGroup) {
        this.properties.add(propertyGroup);
    }

    @Override
    public String toString() {
        return "Property [id=" + elementId + ", properties=" + properties + "]";
    }
}
