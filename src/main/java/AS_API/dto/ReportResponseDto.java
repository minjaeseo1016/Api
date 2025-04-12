package AS_API.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportResponseDto {
    private Long id;
    private String reporter;
    private String target;
    private String reason;
    private String status;
}
