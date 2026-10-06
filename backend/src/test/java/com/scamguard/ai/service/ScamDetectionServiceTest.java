package com.scamguard.ai.service;

import com.scamguard.ai.model.ScamAnalysisRequest;
import com.scamguard.ai.model.ScamAnalysisResult;
import com.scamguard.ai.model.ScamAnalysisType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScamDetectionServiceTest {

    private final ScamDetectionService service = new ScamDetectionService();

    @Test
    void analyzeDetectsUrgentPhishingText() {
        ScamAnalysisRequest request = new ScamAnalysisRequest();
        request.setAnalysisType(ScamAnalysisType.TEXT);
        request.setMessage("Your account will be blocked today. Verify immediately using this link: https://bit.ly/secure-login");

        ScamAnalysisResult result = service.analyze(request);

        assertTrue(result.isSuspicious());
        assertEquals("PHISHING", result.getScamCategory());
        assertTrue(result.getRiskScore() >= 50);
        assertTrue(result.getConfidence() > 0.5);
        assertFalse(result.getSuspiciousIndicators().isEmpty());
    }

    @Test
    void analyzeReturnsLowRiskForSafeMessage() {
        ScamAnalysisRequest request = new ScamAnalysisRequest();
        request.setAnalysisType(ScamAnalysisType.TEXT);
        request.setMessage("Your electricity bill is available on the official provider website. Please use the official portal to view it.");

        ScamAnalysisResult result = service.analyze(request);

        assertFalse(result.isSuspicious());
        assertEquals("LOW", result.getRiskLevel());
        assertTrue(result.getRiskScore() <= 25);
    }
}
