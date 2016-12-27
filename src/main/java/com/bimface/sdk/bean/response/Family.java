package com.bimface.sdk.bean.response;

import java.io.Serializable;
import java.util.List;

/**
 * 族元素
 *
 * @author bimface, 2016-11-01.
 */
public class Family implements Serializable {

    private static final long serialVersionUID = -3941838803765875345L;
    private String            family;                                  // 族名称
    private List<String>      familyTypes;                             // 族类型

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public List<String> getFamilyTypes() {
        return familyTypes;
    }

    public void setFamilyTypes(List<String> familyTypes) {
        this.familyTypes = familyTypes;
    }

    @Override
    public String toString() {
        return "Family [family=" + family + ", familyTypes=" + familyTypes + "]";
    }

}
