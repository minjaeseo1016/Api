package AS_API.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "BillProposer")
public class BillProposer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long proposerId;

    @ManyToOne
    @JoinColumn(name = "billId", nullable = false)
    private Bill bill;

    @Column(nullable = false, length = 255)
    private String proposerName;

    @Column(nullable = false, length = 255)
    private String career;

    @Column(nullable = false, length = 255)
    private String party;
}