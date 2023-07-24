package ru.itl.train.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.itl.train.dto.NomenclatureCargo;
import ru.itl.train.entity.NomenclatureCargoEntity;
import ru.itl.train.repository.NomenclatureCargoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Kuznetsovka 23.07.2023
 */
@Slf4j
@Service
public class NomenclatureCargoServiceImpl implements CommonService<NomenclatureCargo> {

    @Autowired
    private NomenclatureCargoRepository repository;

    @Autowired
    private MapperService mapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public NomenclatureCargo update(NomenclatureCargo nomenclatureCargo) {
        NomenclatureCargoEntity entity = mapper.nomenclatureCargoEntityFromDto(nomenclatureCargo);
        NomenclatureCargoEntity savedNomenclatureCargo = repository.save(entity);
        return mapper.nomenclatureCargoDtoFromEntity(savedNomenclatureCargo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long delete(Long id) {
        if (id != null && repository.existsById(id)) {
            repository.delete(repository.getById(id));
        }
        return id;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<NomenclatureCargo> getAll() {
        List<NomenclatureCargoEntity> nomenclatureCargos = repository.findAll();
        return nomenclatureCargos.stream().map(mapper::nomenclatureCargoDtoFromEntity).collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Optional<NomenclatureCargo> getById(Long id) {
        Optional<NomenclatureCargoEntity> nomenclatureCargo = repository.findById(id);
        NomenclatureCargo dto = nomenclatureCargo.map(mapper::nomenclatureCargoDtoFromEntity).orElse(null);
        return (dto == null) ? Optional.empty() : Optional.of(dto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public NomenclatureCargo save(NomenclatureCargo nomenclatureCargo) {
        Optional<NomenclatureCargoEntity> savedEmployee = repository.findById(nomenclatureCargo.getId());
        if (savedEmployee.isPresent()) {
            String msg = String.format("Запить из справочника номенклатурных грузов id %s уже существует.", nomenclatureCargo.getId());
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        NomenclatureCargoEntity entity = mapper.nomenclatureCargoEntityFromDto(nomenclatureCargo);
        NomenclatureCargoEntity savedEntity = repository.save(entity);
        return mapper.nomenclatureCargoDtoFromEntity(savedEntity);
    }
}
