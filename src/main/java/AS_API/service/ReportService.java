package AS_API.service;

import AS_API.entity.Report;
import AS_API.dto.ReportRequestDto;
import AS_API.dto.ReportResponseDto;
import AS_API.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public ReportResponseDto createReport(ReportRequestDto dto) {
        Report report = Report.builder()
                .reporter(dto.getReporter())
                .target(dto.getTarget())
                .reason(dto.getReason())
                .status("PENDING")
                .build();
        return toResponseDto(reportRepository.save(report));
    }

    public List<ReportResponseDto> getAllReports() {
        return reportRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public ReportResponseDto getReport(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        return toResponseDto(report);
    }

    private ReportResponseDto toResponseDto(Report report) {
        return ReportResponseDto.builder()
                .id(report.getId())
                .reporter(report.getReporter())
                .target(report.getTarget())
                .reason(report.getReason())
                .status(report.getStatus())
                .build();
    }
}
