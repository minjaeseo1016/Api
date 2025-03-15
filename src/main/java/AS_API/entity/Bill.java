package AS_API.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;

    @Column(nullable = false, length = 255)
    private String apiId;

    @Column(nullable = false)
    private Long billNumber;

    @Column(nullable = false)  // 기본값: VARCHAR(255)
    private String billTitle;

    @Column(nullable = false, length = 255)
    private String billProposer;

    @Column(nullable = false, length = 255)
    private String committee;

    @Column(nullable = false, length = 255)
    private String billStatus;

    @Column(nullable = false)
    private LocalDateTime billDate;

    @Column(nullable = false, columnDefinition = "TEXT")  // 긴 텍스트 저장
    private String detail;

    @Column(nullable = false, columnDefinition = "TEXT")  // 긴 텍스트 저장
    private String summary;

    @Column(nullable = false, columnDefinition = "TEXT")  // 긴 텍스트 저장
    private String prediction;
}
