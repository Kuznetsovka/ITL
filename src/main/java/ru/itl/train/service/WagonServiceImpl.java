package ru.itl.train.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.itl.train.dto.Wagon;
import ru.itl.train.entity.TechInfoEntity;
import ru.itl.train.entity.WagonEntity;
import ru.itl.train.repository.WagonRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Kuznetsovka 23.07.2023
 */
@Slf4j
@Service
public class WagonServiceImpl implements WagonService {

    @Autowired
    private WagonRepository repository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Wagon update(Wagon wagon) {
        WagonEntity entity = entityFromDto(wagon);
        WagonEntity savedWagon = repository.save(entity);
        return dtoFromEntity(savedWagon);
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
    public List<Wagon> getAllWagons() {
        List<WagonEntity> wagons = repository.findAll();
        return wagons.stream().map(this::dtoFromEntity).collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Optional<Wagon> getById(Long id) {
        Optional<WagonEntity> wagon = repository.findById(id);
        Wagon dto = wagon.map(this::dtoFromEntity).orElse(null);
        return (dto == null) ? Optional.empty() : Optional.of(dto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Wagon save(Wagon wagon) {
        Optional<WagonEntity> savedEmployee = repository.findByWagonInfo_Number(wagon.getNumber());
        if (savedEmployee.isPresent()) {
            String msg = String.format("Вагон с номером %s уже существует.", wagon.getNumber());
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        WagonEntity entity = entityFromDto(wagon);
        WagonEntity savedEntity = repository.save(entity);
        return dtoFromEntity(savedEntity);
    }

    @Override
    public Wagon dtoFromEntity(WagonEntity entity) {
        return Wagon.builder()
                .id(entity.getId())
                .loadCapacity(entity.getLoadCapacity())
                .type(entity.getType())
                .number((entity.getWagonInfo() != null) ? entity.getWagonInfo().getNumber() : null)
                .weightWagon((entity.getWagonInfo() != null) ? entity.getWagonInfo().getWeightWagon() : null)
                .build();
    }

    @Override
    public WagonEntity entityFromDto(Wagon dto) {
        return WagonEntity.builder()
                .id(dto.getId())
                .loadCapacity(dto.getLoadCapacity())
                .type(dto.getType())
                .wagonInfo(new TechInfoEntity(dto.getNumber(), dto.getWeightWagon()))
                .build();
    }
}
