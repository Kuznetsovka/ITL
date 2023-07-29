package ru.itl.train.service;

import ru.itl.train.dto.NomenclatureCargo;

import java.util.List;
import java.util.Optional;

/**
 * @author Kuznetsovka 29.07.2023
 */
public interface NomenclatureCargoService {
    NomenclatureCargo update(NomenclatureCargo nomenclatureCargo);

    Long delete(Long id);

    List<NomenclatureCargo> getAll();

    Optional<NomenclatureCargo> getById(Long id);

    NomenclatureCargo save(NomenclatureCargo nomenclatureCargo);
}
