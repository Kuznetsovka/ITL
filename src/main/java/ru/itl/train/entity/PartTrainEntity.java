package ru.itl.train.entity;

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
@Builder
@Entity
@Table(name = "tbl_part_train",
        uniqueConstraints = @UniqueConstraint(
                name = "bk_tbl_part_train_order_wagon_id_idx", columnNames = {"wagon_id"}))
public class PartTrainEntity {

    @Id
    @Column(name = "id", nullable = false)
    private Long order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_tbl_part_train_wagon"), name = "wagon_id")
    private WagonEntity wagon;

    public PartTrainEntity(Long order, WagonEntity wagon) {
        this.order = order;
        this.wagon = wagon;
    }
}
