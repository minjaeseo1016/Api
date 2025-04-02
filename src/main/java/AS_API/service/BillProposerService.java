package AS_API.service;

import AS_API.dto.BillProposerDto;
import AS_API.repository.BillProposerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BillProposerService {

    private final BillProposerRepository billProposerRepository;

    public Page<BillProposerDto> searchProposers(String keyword, Pageable pageable) {
        return billProposerRepository.searchByKeyword(
                (keyword == null || keyword.trim().isEmpty()) ? null : keyword,
                pageable
        );
    }
}
