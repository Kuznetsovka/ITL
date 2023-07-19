package ru.itl.train.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Справочник номенклатур грузов (Код груза, Наименование груза)
 * @author Kuznetsovka 19.07.2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_nomenclature_cargo")
public class NomenclatureCargoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Код ЕТСНГ */
    private Long code;

    /** Наименование ЕТСНГ */
    private String name;
}
