package likelion12th.SwuniForest.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name="order_item")
@Getter
public class OrderItem extends Base {
    @Id
    @Column(name = "order_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private Integer orderPrice; // 가격
    private Integer count; // 주문 수량
}