package ru.itl.train.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.itl.train.dto.Road;
import ru.itl.train.entity.RoadEntity;
import ru.itl.train.repository.RoadRepository;
import ru.itl.train.service.MapperService;
import ru.itl.train.service.RoadService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Kuznetsovka 23.07.2023
 */
@Slf4j
@Service
@AllArgsConstructor
public class RoadServiceImpl implements RoadService {

    private final RoadRepository repository;

    private final MapperService mapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Road update(Road road) {
        RoadEntity entity = mapper.roadEntityFromDto(road);
        RoadEntity savedRoad = repository.save(entity);
        return mapper.roadDtoFromEntity(savedRoad);
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
    public List<Road> getAll() {
        List<RoadEntity> roads = repository.findAll();
        return roads.stream().map(mapper::roadDtoFromEntity).collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Optional<Road> getById(Long id) {
        Optional<RoadEntity> road = repository.findByNumber(id);
        Road dto = road.map(mapper::roadDtoFromEntity).orElse(null);
        return (dto == null) ? Optional.empty() : Optional.of(dto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Road save(Road road) {
        if (road.getNumber() != null) {
            Optional<RoadEntity> savedEmployee = repository.findByNumber(road.getNumber());
            if (savedEmployee.isPresent()) {
                String msg = String.format("Путь с номером %s уже существует.", road.getNumber());
                log.error(msg);
                throw new ResourceNotFoundException(msg);
            }
        }
        RoadEntity entity = mapper.roadEntityFromDto(road);
        RoadEntity savedEntity = repository.save(entity);
        return mapper.roadDtoFromEntity(savedEntity);
    }
}
