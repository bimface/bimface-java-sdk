package com.bimface.sdk.bean.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 专业层次结构
 * 
 * @author bimface, 2016-12-01.
 */
public class SpecialtyTree implements Serializable {

    private static final long serialVersionUID = 3877631423890285752L;

    private int treeType = 1; // 专业下嵌套楼层

    private List<Specialty> tree = new ArrayList<Specialty>();

    public int getTreeType() {
        return treeType;
    }

    public void setTreeType(int treeType) {
        this.treeType = treeType;
    }

    public List<Specialty> getTree() {
        return tree;
    }

    public void setTree(List<Specialty> tree) {
        this.tree = tree;
    }

    public static class Specialty {

        private String      specialty;
        private List<Floor> floors = new ArrayList<Floor>();

        public Specialty() {
        }

        public Specialty(String specialty) {
            super();
            this.specialty = specialty;
        }

        public String getSpecialty() {
            return specialty;
        }

        public void setSpecialty(String specialty) {
            this.specialty = specialty;
        }

        public List<Floor> getFloors() {
            return floors;
        }

        public void setFloors(List<Floor> floors) {
            this.floors = floors;
        }

    }

    public static class Floor {

        private String             floor;
        private List<CategoryBean> categories = new ArrayList<CategoryBean>();

        public Floor() {
        }

        public Floor(String floor) {
            super();
            this.floor = floor;
        }

        public String getFloor() {
            return floor;
        }

        public void setFloor(String floor) {
            this.floor = floor;
        }

        public List<CategoryBean> getCategories() {
            return categories;
        }

        public void setCategories(List<CategoryBean> categories) {
            this.categories = categories;
        }

    }

}
