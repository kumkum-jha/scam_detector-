package com.scamguard.ai.model;

import java.util.List;

public class ScamAnalysisResult {
    private ScamAnalysisType analysisType;
    private boolean suspicious;
    private String scamCategory;
    private String riskLevel;
    private int riskScore;
    private double confidence;
    private List<String> suspiciousIndicators;
    private String explanation;
    private List<String> recommendedActions;
    private boolean shouldAvoidSensitiveActions;
    private String disclaimer;

    public ScamAnalysisType getAnalysisType() {
        return analysisType;
    }

    public void setAnalysisType(ScamAnalysisType analysisType) {
        this.analysisType = analysisType;
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

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(int riskScore) {
        this.riskScore = riskScore;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public List<String> getSuspiciousIndicators() {
        return suspiciousIndicators;
    }

    public void setSuspiciousIndicators(List<String> suspiciousIndicators) {
        this.suspiciousIndicators = suspiciousIndicators;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public List<String> getRecommendedActions() {
        return recommendedActions;
    }

    public void setRecommendedActions(List<String> recommendedActions) {
        this.recommendedActions = recommendedActions;
    }

    public boolean isShouldAvoidSensitiveActions() {
        return shouldAvoidSensitiveActions;
    }

    public void setShouldAvoidSensitiveActions(boolean shouldAvoidSensitiveActions) {
        this.shouldAvoidSensitiveActions = shouldAvoidSensitiveActions;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }
}
