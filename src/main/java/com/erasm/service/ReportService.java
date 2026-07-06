package com.erasm.service;

import com.erasm.dto.ReportDTO;

public interface ReportService {

    ReportDTO getSkillReport();

    ReportDTO getUtilizationReport();

    ReportDTO getProjectAllocationReport();
}