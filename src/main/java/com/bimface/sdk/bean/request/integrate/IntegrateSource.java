package com.bimface.sdk.bean.request.integrate;

/**
 * 待合并文件的参数
 * 
 * @author bimface, 2016-12-13.
 */
public class IntegrateSource {

    private Long fileId;

    private String specialty;

    private Float  specialtySort;

    private String floor;

    private Float  floorSort;

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Float getSpecialtySort() {
        return specialtySort;
    }

    public void setSpecialtySort(Float specialtySort) {
        this.specialtySort = specialtySort;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public Float getFloorSort() {
        return floorSort;
    }

    public void setFloorSort(Float floorSort) {
        this.floorSort = floorSort;
    }

}
