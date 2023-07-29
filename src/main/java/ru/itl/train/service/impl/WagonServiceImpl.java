package ru.itl.train.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.itl.train.dto.Wagon;
import ru.itl.train.entity.WagonEntity;
import ru.itl.train.repository.WagonRepository;
import ru.itl.train.service.MapperService;
import ru.itl.train.service.WagonService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Kuznetsovka 23.07.2023
 */
@Slf4j
@Service
@AllArgsConstructor
public class WagonServiceImpl implements WagonService {

    private final WagonRepository repository;

    private final MapperService mapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Wagon update(Wagon wagon) {
        WagonEntity entity = mapper.wagonEntityFromDto(wagon);
        WagonEntity savedWagon = repository.save(entity);
        return mapper.wagonDtoFromEntity(savedWagon);
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
    public List<Wagon> getAll() {
        List<WagonEntity> wagons = repository.findAll();
        return wagons.stream().map(mapper::wagonDtoFromEntity).collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Optional<Wagon> getById(Long id) {
        Optional<WagonEntity> wagon = repository.findById(id);
        Wagon dto = wagon.map(mapper::wagonDtoFromEntity).orElse(null);
        return (dto == null) ? Optional.empty() : Optional.of(dto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Wagon save(Wagon wagon) {
        if (wagon.getNumber() != null) {
            Optional<WagonEntity> savedEmployee = repository.findByWagonInfo_Number(wagon.getNumber());
            if (savedEmployee.isPresent()) {
                String msg = String.format("Вагон с номером %s уже существует.", wagon.getNumber());
                log.error(msg);
                throw new ResourceNotFoundException(msg);
            }
        }
        WagonEntity entity = mapper.wagonEntityFromDto(wagon);
        WagonEntity savedEntity = repository.save(entity);
        return mapper.wagonDtoFromEntity(savedEntity);
    }
}
