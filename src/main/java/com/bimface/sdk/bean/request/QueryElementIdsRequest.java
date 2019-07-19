package com.bimface.sdk.bean.request;

import com.bimface.data.enums.ToleranceType;

/**
 * 查询满足条件的构件ID列表的请求参数
 *
 * @author bimface 2019/07/19
 */
public class QueryElementIdsRequest {
    private String specialty;
    private String floor;
    private String categoryId;
    private String family;
    private String familyType;
    private String systemType;
    private String roomId;
    private ToleranceType roomToleranceZ;
    private ToleranceType roomToleranceXY;
    private String paginationContextId;
    private Integer paginationNo;
    private Integer paginationSize;

    public QueryElementIdsRequest() {
    }

    public QueryElementIdsRequest(String specialty, String floor, String categoryId, String family, String familyType) {
        this.specialty = specialty;
        this.floor = floor;
        this.categoryId = categoryId;
        this.family = family;
        this.familyType = familyType;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getFamilyType() {
        return familyType;
    }

    public void setFamilyType(String familyType) {
        this.familyType = familyType;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public ToleranceType getRoomToleranceZ() {
        return roomToleranceZ;
    }

    public void setRoomToleranceZ(ToleranceType roomToleranceZ) {
        this.roomToleranceZ = roomToleranceZ;
    }

    public ToleranceType getRoomToleranceXY() {
        return roomToleranceXY;
    }

    public void setRoomToleranceXY(ToleranceType roomToleranceXY) {
        this.roomToleranceXY = roomToleranceXY;
    }

    public String getPaginationContextId() {
        return paginationContextId;
    }

    public void setPaginationContextId(String paginationContextId) {
        this.paginationContextId = paginationContextId;
    }

    public Integer getPaginationNo() {
        return paginationNo;
    }

    public void setPaginationNo(Integer paginationNo) {
        this.paginationNo = paginationNo;
    }

    public Integer getPaginationSize() {
        return paginationSize;
    }

    public void setPaginationSize(Integer paginationSize) {
        this.paginationSize = paginationSize;
    }

    @Override
    public String toString() {
        return "QueryElementIdsRequest{" +
                "specialty='" + specialty + '\'' +
                ", floor='" + floor + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", family='" + family + '\'' +
                ", familyType='" + familyType + '\'' +
                ", systemType='" + systemType + '\'' +
                ", roomId='" + roomId + '\'' +
                ", roomToleranceZ=" + roomToleranceZ +
                ", roomToleranceXY=" + roomToleranceXY +
                ", paginationContextId='" + paginationContextId + '\'' +
                ", paginationNo=" + paginationNo +
                ", paginationSize=" + paginationSize +
                '}';
    }
}
