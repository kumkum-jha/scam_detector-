package com.scamguard.ai.service;

import com.scamguard.ai.entity.ScamReport;
import com.scamguard.ai.model.ScamAnalysisRequest;
import com.scamguard.ai.model.ScamAnalysisResult;
import com.scamguard.ai.repository.ScamReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScamReportService {
    private final ScamReportRepository scamReportRepository;

    public ScamReportService(ScamReportRepository scamReportRepository) {
        this.scamReportRepository = scamReportRepository;
    }

    public ScamReport saveAnalysis(ScamAnalysisRequest request, ScamAnalysisResult result) {
        ScamReport report = new ScamReport();
        report.setAnalysisType(result.getAnalysisType());
        report.setMessage(request.getMessage());
        report.setUrl(request.getUrl());
        report.setPhoneNumber(request.getPhoneNumber());
        report.setSuspicious(result.isSuspicious());
        report.setScamCategory(result.getScamCategory());
        report.setRiskScore(result.getRiskScore());
        report.setRiskLevel(result.getRiskLevel());
        report.setSuspiciousIndicators(joinList(result.getSuspiciousIndicators()));
        report.setExplanation(result.getExplanation());
        report.setRecommendedActions(joinList(result.getRecommendedActions()));
        return scamReportRepository.save(report);
    }

    public List<ScamReport> findAll() {
        return scamReportRepository.findAllByOrderByCreatedAtDesc();
    }

    public Optional<ScamReport> findById(Long id) {
        return scamReportRepository.findById(id);
    }

    public boolean deleteById(Long id) {
        if (!scamReportRepository.existsById(id)) {
            return false;
        }
        scamReportRepository.deleteById(id);
        return true;
    }

    private String joinList(List<String> values) {
        if (values == null || values.isEmpty()) {
            return "";
        }
        return values.stream().filter(item -> item != null && !item.isBlank()).collect(Collectors.joining("; "));
    }
}
