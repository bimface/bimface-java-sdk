package com.bimface.sdk.bean.response;

import java.io.Serializable;

/**
 * 三维坐标
 * 
 * @author bimface, 2016-12-01.
 */
public class Coordinate implements Serializable {

    private static final long serialVersionUID = 2787787177457777747L;

    private double x;
    private double y;
    private double z;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof Coordinate) {
            Coordinate coordinate = (Coordinate) obj;
            if (this.x == coordinate.getX() && this.y == coordinate.getY() && this.z == coordinate.getZ()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer('[');
        sb.append(x).append(',').append(y).append(',').append(z).append(']');
        return sb.toString();
    }

}
