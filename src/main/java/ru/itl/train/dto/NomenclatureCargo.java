package ru.itl.train.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Справочник номенклатур грузов (Код груза, Наименование груза)
 * @author Kuznetsovka 19.07.2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NomenclatureCargo {

    private Long id;
    private Long code;
    private String name;
}
