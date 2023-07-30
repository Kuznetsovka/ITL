package ru.itl.train.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Состав
 * @author Kuznetsovka 20.07.2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_part_train",
        uniqueConstraints = @UniqueConstraint(
                name = "bk_tbl_part_train_order_wagon_id_idx", columnNames = {"wagon_id"}))
public class PartTrainEntity {

    @Id
    @Column(name = "id", nullable = false)
    private Long order;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "wagon_id")
    private WagonEntity wagon;
}
