package AS_API.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportRequestDto {
    private String reporter;
    private String target;
    private String reason;
}
