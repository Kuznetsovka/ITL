package ru.itl.train.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.itl.train.dto.Station;
import ru.itl.train.entity.StationEntity;
import ru.itl.train.repository.StationRepository;
import ru.itl.train.service.MapperService;
import ru.itl.train.service.StationService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Kuznetsovka 23.07.2023
 */
@Slf4j
@Service
@AllArgsConstructor
public class StationServiceImpl implements StationService {

    private final StationRepository repository;

    private final MapperService mapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Station update(Station station) {
        StationEntity entity = mapper.stationEntityFromDto(station);
        StationEntity savedStation = repository.save(entity);
        return mapper.stationDtoFromEntity(savedStation);
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
    public List<Station> getAll() {
        List<StationEntity> stations = repository.findAll();
        return stations.stream().map(mapper::stationDtoFromEntity).collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Optional<Station> getById(Long id) {
        Optional<StationEntity> station = repository.findById(id);
        Station dto = station.map(mapper::stationDtoFromEntity).orElse(null);
        return (dto == null) ? Optional.empty() : Optional.of(dto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Station save(Station station) {
        Optional<StationEntity> savedEmployee = repository.findById(station.getId());
        if (savedEmployee.isPresent()) {
            String msg = String.format("Станция с номером %s уже существует.", station.getId());
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        StationEntity entity = mapper.stationEntityFromDto(station);
        StationEntity savedEntity = repository.save(entity);
        return mapper.stationDtoFromEntity(savedEntity);
    }
}
