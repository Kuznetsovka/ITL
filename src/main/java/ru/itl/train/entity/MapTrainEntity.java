package ru.itl.train.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

/**
 * Карта расположения подвижного состава
 * @author Kuznetsovka 20.07.2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_map_train",
        indexes = @Index(name = "idx_tbl_map_train_road_id", columnList = "road_id"))
public class MapTrainEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="road_id",
            foreignKey=@ForeignKey(name = "fk_tbl_map_train_road"))
    private RoadEntity road;

    @OneToMany
    @JoinColumn(foreignKey=@ForeignKey(name = "fk_tbl_map_train_order_wagon"))
    @OrderBy("order")
    private Set<PartTrainEntity> order_wagon;

}
