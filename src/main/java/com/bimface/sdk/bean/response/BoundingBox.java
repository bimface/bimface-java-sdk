package com.bimface.sdk.bean.response;

import java.io.Serializable;

/**
 * 包围盒
 * 
 * @author bimface, 2016-12-01.
 */
public class BoundingBox implements Serializable {

    private static final long serialVersionUID = -5153614793149168712L;

    private Coordinate min;// 离原点的最近距离
    private Coordinate max;// 离原点的最远距离

    public Coordinate getMin() {
        return min;
    }

    public void setMin(Coordinate min) {
        this.min = min;
    }

    public Coordinate getMax() {
        return max;
    }

    public void setMax(Coordinate max) {
        this.max = max;
    }

}
