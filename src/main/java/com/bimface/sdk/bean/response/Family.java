package com.bimface.sdk.bean.response;

import java.util.List;

/**
 * 族元素
 *
 * @author bimface, 2016-11-01.
 */
public class Family {
    private String familyName; // 族名称
    private List<String> types; // 族类型

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "Family [name=" + familyName + ", types=" + types + "]";
    }
}
