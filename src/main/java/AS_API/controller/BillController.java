package AS_API.controller;

import AS_API.dto.BillDto;
import AS_API.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillController {
    private final BillService billService;

    @GetMapping("/search")
    public ResponseEntity<?> searchBills(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String proposer,
            @RequestParam(required = false) String detail,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<BillDto> bills = billService.searchBills(
                    (title != null && !title.trim().isEmpty()) ? title : null,
                    (proposer != null && !proposer.trim().isEmpty()) ? proposer : null,
                    (detail != null && !detail.trim().isEmpty()) ? detail : null,
                    pageable
            );
            return ResponseEntity.ok(bills);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("에러 발생: " + e.getMessage());
        }
    }
}
