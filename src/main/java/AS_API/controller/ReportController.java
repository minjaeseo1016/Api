package AS_API.controller;

import AS_API.dto.ReportRequestDto;
import AS_API.dto.ReportResponseDto;
import AS_API.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ReportResponseDto createReport(@RequestBody ReportRequestDto dto) {
        return reportService.createReport(dto);
    }

    @GetMapping
    public List<ReportResponseDto> getAllReports() {
        return reportService.getAllReports();
    }

    @GetMapping("/{id}")
    public ReportResponseDto getReport(@PathVariable Long id) {
        return reportService.getReport(id);
    }
}
