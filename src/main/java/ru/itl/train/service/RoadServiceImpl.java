package ru.itl.train.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.itl.train.dto.Road;
import ru.itl.train.entity.RoadEntity;
import ru.itl.train.repository.RoadRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Kuznetsovka 23.07.2023
 */
@Slf4j
@Service
public class RoadServiceImpl implements RoadService {

    @Autowired
    private RoadRepository repository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Road update(Road road) {
        RoadEntity entity = entityFromDto(road);
        RoadEntity savedRoad = repository.save(entity);
        return dtoFromEntity(savedRoad);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long delete(Long number) {
        if (number != null && repository.existsByNumber(number)) {
            repository.delete(repository.getByNumber(number));
        }
        return number;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Road> getAllRoads() {
        List<RoadEntity> roads = repository.findAll();
        return roads.stream().map(this::dtoFromEntity).collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Optional<Road> getById(Long id) {
        Optional<RoadEntity> road = repository.findByNumber(id);
        Road dto = road.map(this::dtoFromEntity).orElse(null);
        return (dto == null) ? Optional.empty() : Optional.of(dto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Road save(Road road) {
        Optional<RoadEntity> savedEmployee = repository.findByNumber(road.getNumber());
        if (savedEmployee.isPresent()) {
            String msg = String.format("Путь с номером %s уже существует.", road.getNumber());
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        RoadEntity entity = entityFromDto(road);
        RoadEntity savedEntity = repository.save(entity);
        return dtoFromEntity(savedEntity);
    }

    @Override
    public Road dtoFromEntity(RoadEntity entity) {
        return Road.builder()
                .number(entity.getNumber())
                .station(entity.getStation())
                .build();
    }

    @Override
    public RoadEntity entityFromDto(Road dto) {
        return RoadEntity.builder()
                .number(dto.getNumber())
                .station(dto.getStation())
                .build();
    }
}
