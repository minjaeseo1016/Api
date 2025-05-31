package AS_API.service;

import AS_API.config.EmbeddingCache;
import AS_API.dto.BillDto;
import AS_API.entity.Bill;
import AS_API.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final OpenAiEmbeddingService embeddingService;
    private final EmbeddingCache embeddingCache;
    private final BillRepository billRepository;

    public List<BillDto> recommendByText(String text, int limit) throws IOException, InterruptedException {
        float[] query = embeddingService.getNormalizedEmbedding(text);
        float[][] vectors = embeddingCache.getVectors();
        List<Long> ids = embeddingCache.getIds();

        List<Score> top = new ArrayList<>();
        for (int i = 0; i < vectors.length; i++) {
            float[] v = vectors[i];
            double score = dot(query, v);
            if (score >= 0.3) {
                top.add(new Score(ids.get(i), score));
            }
        }

        top.sort(Comparator.comparingDouble(Score::score).reversed());

        List<Long> topIds = top.stream()
                .limit(limit)
                .map(Score::id)
                .toList();

        List<Bill> topBills = billRepository.findAllById(topIds);

        Map<Long, Bill> billMap = new HashMap<>();
        for (Bill bill : topBills) {
            billMap.put(bill.getBillId(), bill);
        }

        List<BillDto> result = new ArrayList<>();
        for (Long id : topIds) {
            Bill b = billMap.get(id);
            if (b != null) result.add(toDto(b));
        }

        return result;
    }

    private double dot(float[] a, float[] b) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) sum += a[i] * b[i];
        return sum;
    }

    private BillDto toDto(Bill bill) {
        return new BillDto(
                bill.getBillId(),
                bill.getApiId(),
                bill.getBillNumber(),
                bill.getBillTitle(),
                bill.getBillProposer(),
                bill.getCommittee(),
                bill.getBillStatus(),
                bill.getProposer().getPoly()
        );
    }

    private record Score(Long id, double score) {}
}
