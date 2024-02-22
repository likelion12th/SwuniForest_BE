package likelion12th.SwuniForest.entity;

import jakarta.persistence.*;
import likelion12th.SwuniForest.constant.ItemSellStatus;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name="item")
@Getter
public class Item extends Base {
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String itemName;
    @NotNull
    private Integer price;
    @NotNull
    private Integer stock;
    @NotNull
    private String itemDetail;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;
}