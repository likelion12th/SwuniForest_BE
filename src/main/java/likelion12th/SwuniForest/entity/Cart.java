package likelion12th.SwuniForest.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name="cart")
@Getter
public class Cart extends Base {
    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // FK

}