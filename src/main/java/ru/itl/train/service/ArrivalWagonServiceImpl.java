package ru.itl.train.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.itl.train.dto.ArrivalWagon;
import ru.itl.train.entity.ArrivalWagonEntity;
import ru.itl.train.repository.ArrivalWagonRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Kuznetsovka 23.07.2023
 */
@Slf4j
@Service
@AllArgsConstructor
public class ArrivalWagonServiceImpl implements CommonService<ArrivalWagon> {

    private final ArrivalWagonRepository repository;

    private final MapperService mapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ArrivalWagon update(ArrivalWagon arrivalWagon) {
        ArrivalWagonEntity entity = mapper.arrivalWagonEntityFromDto(arrivalWagon);
        ArrivalWagonEntity savedArrivalWagon = repository.save(entity);
        return mapper.arrivalWagonDtoFromEntity(savedArrivalWagon);
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
    public List<ArrivalWagon> getAll() {
        List<ArrivalWagonEntity> arrivalWagons = repository.findAll();
        return arrivalWagons.stream().map(mapper::arrivalWagonDtoFromEntity).collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Optional<ArrivalWagon> getById(Long id) {
        Optional<ArrivalWagonEntity> arrivalWagon = repository.findById(id);
        ArrivalWagon dto = arrivalWagon.map(mapper::arrivalWagonDtoFromEntity).orElse(null);
        return (dto == null) ? Optional.empty() : Optional.of(dto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ArrivalWagon save(ArrivalWagon arrivalWagon) {
        Optional<ArrivalWagonEntity> savedEmployee = repository.findById(arrivalWagon.getId());
        if (savedEmployee.isPresent()) {
            String msg = String.format("Натурный лист id %s уже существует.", arrivalWagon.getId());
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        ArrivalWagonEntity entity = mapper.arrivalWagonEntityFromDto(arrivalWagon);
        ArrivalWagonEntity savedEntity = repository.save(entity);
        return mapper.arrivalWagonDtoFromEntity(savedEntity);
    }
}
