package com.bimface.sdk.bean.request;

import com.bimface.file.enums.FileStatus;

import java.time.LocalDate;

/**
 * 文件批量查询的参数
 *
 * @author bimface 2109/07/19
 */
public class FileBatchQueryRequest {
    private String suffix;
    private FileStatus status;
    private LocalDate startTime;
    private LocalDate endTime;
    private Long offset;
    private Long rows = 100L;

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public FileStatus getStatus() {
        return status;
    }

    public void setStatus(FileStatus status) {
        this.status = status;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getRows() {
        return rows;
    }

    public void setRows(Long rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "FileBatchQueryRequest{" +
                "suffix='" + suffix + '\'' +
                ", status=" + status +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", offset=" + offset +
                ", rows=" + rows +
                '}';
    }
}
