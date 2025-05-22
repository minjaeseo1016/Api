package AS_API.service;

import AS_API.config.EmbeddingCache;
import AS_API.dto.BillDto;
import AS_API.entity.Bill;
import AS_API.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final OpenAiEmbeddingService embeddingService;
    private final EmbeddingCache embeddingCache;
    private final BillRepository billRepository;

    public List<BillDto> recommendByText(String text, int limit) throws IOException, InterruptedException {
        float[] queryEmbedding = embeddingService.getEmbedding(text);

        List<Map.Entry<Long, Double>> scored = embeddingCache.getEmbeddingMap().entrySet().stream()
                .map(entry -> Map.entry(entry.getKey(), cosineSimilarity(queryEmbedding, entry.getValue())))
                .filter(e -> !Double.isNaN(e.getValue()))
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toList());

        List<Bill> topBills = billRepository.findAllById(
                scored.stream().map(Map.Entry::getKey).toList()
        );

        return topBills.stream().map(this::toDto).collect(Collectors.toList());
    }

    private double cosineSimilarity(float[] a, float[] b) {
        if (a.length != b.length) return Double.NaN;
        double dot = 0, normA = 0, normB = 0;
        for (int i = 0; i < a.length; i++) {
            dot += a[i] * b[i];
            normA += a[i] * a[i];
            normB += b[i] * b[i];
        }
        if (normA == 0 || normB == 0) return Double.NaN;
        return dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    private BillDto toDto(Bill bill) {
        return new BillDto(
                bill.getBillId(),
                bill.getApiId(),
                bill.getBillNumber(),
                bill.getBillTitle(),
                bill.getBillProposer(),
                bill.getCommittee(),
                bill.getBillStatus()
        );
    }
}
