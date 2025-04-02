package AS_API.controller;

import AS_API.dto.BillProposerDto;
import AS_API.service.BillProposerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/proposers")
@RequiredArgsConstructor
public class BillProposerController {

    private final BillProposerService billProposerService;

    @GetMapping("/search")
    public ResponseEntity<?> searchProposers(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<BillProposerDto> proposers = billProposerService.searchProposers(keyword, pageable);
            return ResponseEntity.ok(proposers);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("에러 발생: " + e.getMessage());
        }
    }
}
