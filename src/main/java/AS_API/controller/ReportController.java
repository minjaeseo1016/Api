package AS_API.controller;

import AS_API.dto.ReportRequestDto;
import AS_API.entity.Report;
import AS_API.service.ReportService;
import AS_API.config.CustomUserDetails; // 사용자의 인증 정보 클래스
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/create")
    public ResponseEntity<String> createReport(@RequestBody ReportRequestDto reportRequestDto,
                                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long reporterId = userDetails.getUser().getUserId();
        Report report = reportService.createReport(reporterId, reportRequestDto);
        return ResponseEntity.ok("신고가 생성되었습니다. 신고 ID: " + report.getId());
    }
}
