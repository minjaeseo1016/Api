package AS_API.config;

import AS_API.entity.Bill;
import AS_API.repository.BillRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class EmbeddingCache {

    private final BillRepository billRepository;
    private final Map<Long, float[]> embeddingMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void preloadEmbeddings() {
        List<Bill> bills = billRepository.findAll();
        for (Bill bill : bills) {
            float[] embedding = bill.getEmbedding();
            if (embedding != null && isValidVector(embedding)) {
                embeddingMap.put(bill.getBillId(), embedding);
            }
        }
        System.out.printf("✅ 임베딩 캐시 로딩 완료: %d개\n", embeddingMap.size());
    }

    public Map<Long, float[]> getEmbeddingMap() {
        return embeddingMap;
    }

    private boolean isValidVector(float[] vector) {
        if (vector.length == 0) return false;
        for (float v : vector) {
            if (Float.isNaN(v) || Float.isInfinite(v)) return false;
        }
        return true;
    }
}
