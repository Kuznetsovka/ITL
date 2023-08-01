package ru.itl.train.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
        uniqueConstraints = @UniqueConstraint(
                name = "bk_tbl_map_train_road_wagonOrder_idx", columnNames = {"road_id", "orderWagon"}))
public class MapTrainEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "road_id",
            foreignKey = @ForeignKey(name = "fk_tbl_map_train_road"))
    private RoadEntity road;

    @Embedded
    private PartTrainEntity orderWagon;

}
