package com.scamguard.ai.service;

import com.scamguard.ai.model.ScamAnalysisRequest;
import com.scamguard.ai.model.ScamAnalysisResult;
import com.scamguard.ai.model.ScamAnalysisType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class ScamDetectionService {
    private final Map<String, Integer> indicatorWeights = Map.ofEntries(
            Map.entry("urgency", 18),
            Map.entry("financial", 20),
            Map.entry("credential", 22),
            Map.entry("phishing", 16),
            Map.entry("social engineering", 17),
            Map.entry("job scam", 15),
            Map.entry("prize", 17),
            Map.entry("authority", 15),
            Map.entry("otp", 22),
            Map.entry("payment", 18),
            Map.entry("suspicious url", 20),
            Map.entry("phone number", 12)
    );

    public ScamAnalysisResult analyze(ScamAnalysisRequest request) {
        String combinedText = buildCombinedText(request);
        String normalizedText = combinedText.toLowerCase(Locale.ROOT);
        List<String> indicators = new ArrayList<>();
        int score = 0;

        if (containsAny(normalizedText, List.of("act immediately", "urgent", "within 10 minutes", "limited time", "respond now", "today only", "account will be blocked", "expires today"))) {
            indicators.add("Urgency pressure");
            score += indicatorWeights.get("urgency");
        }

        if (containsAny(normalizedText, List.of("processing fee", "send money", "upi", "bank transfer", "payment", "advance payment", "crypto", "bitcoin", "gift card", "refund fee", "transaction fee"))) {
            indicators.add("Financial request");
            score += indicatorWeights.get("financial");
        }

        if (containsAny(normalizedText, List.of("otp", "one time password", "pin", "cvv", "password", "aadhaar", "pan number", "bank details", "login credentials", "verification code"))) {
            indicators.add("Credential theft attempt");
            score += indicatorWeights.get("credential");
        }

        if (containsAny(normalizedText, List.of("click here", "verify your account", "claim your reward", "login to continue", "secure your account", "bit.ly", "tinyurl", "shorturl", "http://", "https://"))) {
            indicators.add("Phishing link or fake verification");
            score += indicatorWeights.get("phishing");
        }

        if (containsAny(normalizedText, List.of("congratulations", "you won", "lottery", "prize", "reward", "official notice", "government", "bank support", "customer care"))) {
            indicators.add("Reward or impersonation claim");
            score += indicatorWeights.get("social engineering");
        }

        if (containsAny(normalizedText, List.of("work from home", "registration fee", "easy money", "interview process", "salary advance", "job offer"))) {
            indicators.add("Job scam pattern");
            score += indicatorWeights.get("job scam");
        }

        if (containsAny(normalizedText, List.of("official", "bank", "police", "government", "support team", "from the finance department"))) {
            indicators.add("Authority impersonation");
            score += indicatorWeights.get("authority");
        }

        if (request != null && request.getUrl() != null && !request.getUrl().isBlank()) {
            String url = request.getUrl().toLowerCase(Locale.ROOT);
            if (containsAny(url, List.of("bit.ly", "tinyurl", "t.co", "goo.gl", "shorturl", "example-suspicious-site", "verify-login", "secure-update"))) {
                indicators.add("Suspicious URL shortener or fake domain");
                score += indicatorWeights.get("suspicious url");
            }
            if (containsAny(url, List.of(".tk", ".ml", ".ga", ".cf", "free-money", "banking-update", "claim-now"))) {
                indicators.add("Suspicious or typo-squatted domain");
                score += indicatorWeights.get("suspicious url");
            }
        }

        if (request != null && request.getPhoneNumber() != null && !request.getPhoneNumber().isBlank()) {
            String phone = request.getPhoneNumber().replaceAll("[^0-9+]", "");
            if (phone.length() >= 10 && (phone.startsWith("+91") || phone.startsWith("91") || phone.length() >= 12)) {
                indicators.add("Phone number pattern suggests scam outreach");
                score += indicatorWeights.get("phone number");
            }
        }

        if (normalizedText.contains("http://") || normalizedText.contains("https://")) {
            indicators.add("External link shared in message");
            score += 10;
        }

        if (indicators.isEmpty()) {
            indicators.add("No strong scam indicators found");
        }

        score = Math.min(100, Math.max(0, score + 10));
        boolean suspicious = score >= 35 || (score >= 25 && indicators.size() >= 2);
        String riskLevel = determineRiskLevel(score);
        String category = determineCategory(indicators, normalizedText);
        String explanation = buildExplanation(category, indicators, suspicious);
        List<String> recommendedActions = buildRecommendations(category, suspicious);
        double confidence = calculateConfidence(score, suspicious);

        ScamAnalysisResult result = new ScamAnalysisResult();
        result.setAnalysisType(resolveAnalysisType(request));
        result.setSuspicious(suspicious);
        result.setScamCategory(category);
        result.setRiskLevel(riskLevel);
        result.setRiskScore(score);
        result.setConfidence(confidence);
        result.setSuspiciousIndicators(indicators);
        result.setExplanation(explanation);
        result.setRecommendedActions(recommendedActions);
        result.setShouldAvoidSensitiveActions(category.equals("CREDENTIAL_THEFT") || category.equals("FINANCIAL_FRAUD") || score >= 70);
        result.setDisclaimer("ScamGuard AI provides decision-support guidance only and is not a guaranteed determination of fraud.");
        return result;
    }

    private String buildCombinedText(ScamAnalysisRequest request) {
        if (request == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        if (request.getMessage() != null) {
            builder.append(request.getMessage()).append(" ");
        }
        if (request.getUrl() != null) {
            builder.append(request.getUrl()).append(" ");
        }
        if (request.getPhoneNumber() != null) {
            builder.append(request.getPhoneNumber()).append(" ");
        }
        return builder.toString();
    }

    private boolean containsAny(String text, List<String> values) {
        for (String value : values) {
            if (text.contains(value)) {
                return true;
            }
        }
        return false;
    }

    private ScamAnalysisType resolveAnalysisType(ScamAnalysisRequest request) {
        if (request == null || request.getAnalysisType() == null) {
            return ScamAnalysisType.TEXT;
        }
        return request.getAnalysisType();
    }

    private String determineRiskLevel(int score) {
        if (score >= 80) {
            return "CRITICAL";
        }
        if (score >= 60) {
            return "HIGH";
        }
        if (score >= 35) {
            return "MODERATE";
        }
        return "LOW";
    }

    private String determineCategory(List<String> indicators, String text) {
        if (containsAny(text, List.of("otp", "password", "pin", "cvv", "bank details", "aadhaar", "pan"))) {
            return "CREDENTIAL_THEFT";
        }
        if (containsAny(text, List.of("payment", "upi", "bank transfer", "gift card", "bitcoin", "crypto", "processing fee", "send money", "advance payment"))) {
            return "FINANCIAL_FRAUD";
        }
        if (containsAny(text, List.of("click here", "verify your account", "login to continue", "secure your account", "bit.ly", "tinyurl", "shorturl", "http://", "https://"))) {
            return "PHISHING";
        }
        if (containsAny(text, List.of("you won", "lottery", "prize", "congratulations"))) {
            return "PRIZE_SCAM";
        }
        if (containsAny(text, List.of("work from home", "registration fee", "job offer", "salary advance"))) {
            return "JOB_SCAM";
        }
        if (containsAny(text, List.of("official", "bank support", "government", "customer care", "support team"))) {
            return "IMPERSONATION_SCAM";
        }
        return indicators.contains("No strong scam indicators found") ? "LOW_RISK_INQUIRY" : "GENERAL_SCAM_PATTERN";
    }

    private String buildExplanation(String category, List<String> indicators, boolean suspicious) {
        StringBuilder explanation = new StringBuilder();
        if (suspicious) {
            explanation.append("This content shows several scam indicators consistent with a ")
                    .append(category.toLowerCase(Locale.ROOT)).append(" pattern. ");
        } else {
            explanation.append("This content does not show a strong scam pattern, but remains cautious due to generic online-risk signals. ");
        }

        explanation.append("The message includes signals such as: ")
                .append(String.join(", ", indicators))
                .append(". These patterns are commonly used to create urgency, impersonate trusted institutions, or pressure a user to act without verifying the sender.");
        return explanation.toString();
    }

    private List<String> buildRecommendations(String category, boolean suspicious) {
        List<String> actions = new ArrayList<>();
        actions.add("Do not click links or open attachments until the message is independently verified.");
        actions.add("Avoid sharing OTPs, CVV, bank account details, or personal information with unknown contacts.");
        if (suspicious) {
            actions.add("Contact the organization through an official website or verified customer-support channel.");
            actions.add("Report the suspicious message to the communication platform and your bank if financial requests are involved.");
        }
        if ("PHISHING".equals(category) || "CREDENTIAL_THEFT".equals(category)) {
            actions.add("Reset passwords and enable multi-factor authentication for important accounts.");
        }
        if ("FINANCIAL_FRAUD".equals(category)) {
            actions.add("Block the payment request and verify any money-transfer demand with the company directly.");
        }
        actions.add("If something feels urgent, pause and verify before acting.");
        return actions;
    }

    private double calculateConfidence(int score, boolean suspicious) {
        double normalized = score / 100.0;
        double confidence = suspicious ? (0.55 + normalized * 0.4) : (0.35 + normalized * 0.25);
        return Math.min(0.99, Math.max(0.35, confidence));
    }
}
