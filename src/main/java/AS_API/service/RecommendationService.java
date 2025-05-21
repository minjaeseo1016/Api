package AS_API.service;

import AS_API.dto.BillDto;
import AS_API.entity.Bill;
import AS_API.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final BillRepository billRepository;
    private final OpenAiEmbeddingService embeddingService;

    public List<BillDto> recommendByText(String text, int limit) throws IOException, InterruptedException {
        float[] queryEmbedding = embeddingService.getEmbedding(text);

        List<Bill> allBills = billRepository.findAll();
        List<Map.Entry<Bill, Double>> scored = new ArrayList<>();

        for (Bill bill : allBills) {
            float[] embedding = bill.getEmbedding();
            if (embedding == null || !isValidVector(embedding)) continue;

            double score = cosineSimilarity(queryEmbedding, embedding);
            if (Double.isNaN(score)) continue;

            scored.add(new AbstractMap.SimpleEntry<>(bill, score));
        }

        return scored.stream()
                .sorted(Map.Entry.<Bill, Double>comparingByValue().reversed())
                .limit(limit)
                .map(entry -> toDto(entry.getKey()))
                .collect(Collectors.toList());
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

    private boolean isValidVector(float[] vector) {
        if (vector.length == 0) return false;
        for (float v : vector) {
            if (Float.isNaN(v) || Float.isInfinite(v)) return false;
        }
        return true;
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
