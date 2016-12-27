package com.bimface.sdk.bean.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 楼层层次结构
 * 
 * @author bimface, 2016-12-01.
 */
public class FloorTree implements Serializable {

    private static final long serialVersionUID = 1836182215640357663L;

    private int treeType = 2; // 楼层下嵌套专业

    private List<Floor> tree = new ArrayList<Floor>();

    public int getTreeType() {
        return treeType;
    }

    public void setTreeType(int treeType) {
        this.treeType = treeType;
    }

    public List<Floor> getTree() {
        return tree;
    }

    public void setTree(List<Floor> tree) {
        this.tree = tree;
    }

    public static class Floor {

        private String          floor;
        private List<Specialty> specialties = new ArrayList<Specialty>();

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

        public List<Specialty> getSpecialties() {
            return specialties;
        }

        public void setSpecialties(List<Specialty> specialties) {
            this.specialties = specialties;
        }

    }

    public static class Specialty {

        private String             specialty;
        private List<CategoryBean> categories;

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

        public List<CategoryBean> getCategories() {
            return categories;
        }

        public void setCategories(List<CategoryBean> categories) {
            this.categories = categories;
        }

    }

}
