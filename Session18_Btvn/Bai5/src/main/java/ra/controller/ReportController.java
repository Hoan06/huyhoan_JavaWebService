package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.model.dto.response.RevenueReportResponse;
import ra.service.ReportService;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/revenue")
    public ResponseEntity<List<RevenueReportResponse>> getRevenueReport(@RequestParam(defaultValue = "month") String type) {
        List<RevenueReportResponse> report = reportService.getRevenueReport(type);
        return ResponseEntity.ok(report);
    }
}