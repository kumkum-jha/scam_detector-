package com.scamguard.ai.entity;

import com.scamguard.ai.model.ScamAnalysisType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "scam_reports")
public class ScamReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ScamAnalysisType analysisType;

    @Column(length = 2048)
    private String message;

    @Column(length = 2048)
    private String url;

    @Column(length = 128)
    private String phoneNumber;

    @Column(nullable = false)
    private boolean suspicious;

    @Column(nullable = false, length = 64)
    private String scamCategory;

    @Column(nullable = false)
    private int riskScore;

    @Column(nullable = false, length = 32)
    private String riskLevel;

    @Column(length = 2048)
    private String suspiciousIndicators;

    @Column(length = 4096)
    private String explanation;

    @Column(length = 2048)
    private String recommendedActions;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public ScamReport() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ScamAnalysisType getAnalysisType() {
        return analysisType;
    }

    public void setAnalysisType(ScamAnalysisType analysisType) {
        this.analysisType = analysisType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isSuspicious() {
        return suspicious;
    }

    public void setSuspicious(boolean suspicious) {
        this.suspicious = suspicious;
    }

    public String getScamCategory() {
        return scamCategory;
    }

    public void setScamCategory(String scamCategory) {
        this.scamCategory = scamCategory;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(int riskScore) {
        this.riskScore = riskScore;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getSuspiciousIndicators() {
        return suspiciousIndicators;
    }

    public void setSuspiciousIndicators(String suspiciousIndicators) {
        this.suspiciousIndicators = suspiciousIndicators;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getRecommendedActions() {
        return recommendedActions;
    }

    public void setRecommendedActions(String recommendedActions) {
        this.recommendedActions = recommendedActions;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
