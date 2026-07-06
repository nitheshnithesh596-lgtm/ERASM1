package com.erasm.dto;

import java.util.Map;

public class ReportDTO {

    private Map<String, Object> data;

    public ReportDTO() {
    }

    public ReportDTO(Map<String, Object> data) {
        this.data = data;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}