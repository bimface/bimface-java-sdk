package com.bimface.sdk.bean.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 构件列表
 * 
 * @author bimface, 2016-12-01.
 */
public class ElementsBean implements Serializable {

    private static final long serialVersionUID = 5052916393029573285L;

    private List<Element> elements = new ArrayList<Element>(); // 构件列表
    private BoundingBox   boundingBox;                         // 构件所在的包围盒

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

}
