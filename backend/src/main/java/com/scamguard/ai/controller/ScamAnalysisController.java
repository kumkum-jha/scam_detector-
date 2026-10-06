package com.scamguard.ai.controller;

import com.scamguard.ai.dto.ApiResponse;
import com.scamguard.ai.entity.ScamReport;
import com.scamguard.ai.model.ScamAnalysisRequest;
import com.scamguard.ai.model.ScamAnalysisResult;
import com.scamguard.ai.service.ScamDetectionService;
import com.scamguard.ai.service.ScamReportService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ScamAnalysisController {
    private final ScamDetectionService scamDetectionService;
    private final ScamReportService scamReportService;

    public ScamAnalysisController(ScamDetectionService scamDetectionService, ScamReportService scamReportService) {
        this.scamDetectionService = scamDetectionService;
        this.scamReportService = scamReportService;
    }

    @GetMapping({"/scam/health", "/scams/health"})
    public Map<String, String> health() {
        return Map.of("status", "UP", "service", "ScamGuard AI");
    }

    @PostMapping({"/scam/analyze", "/scams/analyze"})
    public ResponseEntity<ApiResponse<ScamAnalysisResult>> analyze(@Valid @RequestBody ScamAnalysisRequest request) {
        ScamAnalysisResult result = scamDetectionService.analyze(request);
        scamReportService.saveAnalysis(request, result);
        return ResponseEntity.ok(ApiResponse.success("Analysis completed successfully", result));
    }

    @GetMapping({"/scams/history", "/scam/history"})
    public ResponseEntity<ApiResponse<List<ScamReport>>> history() {
        return ResponseEntity.ok(ApiResponse.success("History retrieved successfully", scamReportService.findAll()));
    }

    @GetMapping({"/scams/{id}", "/scam/{id}"})
    public ResponseEntity<ApiResponse<ScamReport>> getById(@PathVariable Long id) {
        return scamReportService.findById(id)
                .map(report -> ResponseEntity.ok(ApiResponse.success("Analysis retrieved successfully", report)))
                .orElseGet(() -> ResponseEntity.status(404).body(ApiResponse.error("Analysis not found", List.of("No analysis matched the supplied id."))));
    }

    @DeleteMapping({"/scams/{id}", "/scam/{id}"})
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable Long id) {
        boolean deleted = scamReportService.deleteById(id);
        if (!deleted) {
            return ResponseEntity.status(404).body(ApiResponse.error("Analysis not found", List.of("No analysis matched the supplied id.")));
        }
        return ResponseEntity.ok(ApiResponse.success("Analysis deleted successfully", null));
    }
}
