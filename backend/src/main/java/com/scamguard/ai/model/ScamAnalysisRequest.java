package com.scamguard.ai.model;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Size;

public class ScamAnalysisRequest {
    @Size(max = 4000, message = "Message must not exceed 4000 characters")
    private String message;

    @Size(max = 2048, message = "URL must not exceed 2048 characters")
    private String url;

    @Size(max = 128, message = "Phone number must not exceed 128 characters")
    private String phoneNumber;

    private ScamAnalysisType analysisType = ScamAnalysisType.TEXT;

    @AssertTrue(message = "At least one of message, URL, or phone number must be provided")
    public boolean isAnyInputProvided() {
        return (message != null && !message.isBlank())
                || (url != null && !url.isBlank())
                || (phoneNumber != null && !phoneNumber.isBlank());
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

    public ScamAnalysisType getAnalysisType() {
        return analysisType;
    }

    public void setAnalysisType(ScamAnalysisType analysisType) {
        this.analysisType = analysisType;
    }
}
