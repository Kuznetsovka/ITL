package ru.itl.train.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.itl.train.dto.MapTrain;
import ru.itl.train.entity.MapTrainEntity;
import ru.itl.train.repository.MapTrainRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Kuznetsovka 23.07.2023
 */
@Slf4j
@Service
public class MapTrainServiceImpl implements CommonService<MapTrain> {

    @Autowired
    private MapTrainRepository repository;

    @Autowired
    private MapperService mapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public MapTrain update(MapTrain mapTrain) {
        MapTrainEntity entity = mapper.mapTrainEntityFromDto(mapTrain);
        MapTrainEntity savedMapTrain = repository.save(entity);
        return mapper.mapTrainDtoFromEntity(savedMapTrain);
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
    public List<MapTrain> getAll() {
        List<MapTrainEntity> mapTrains = repository.findAll();
        return mapTrains.stream().map(mapper::mapTrainDtoFromEntity).collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Optional<MapTrain> getById(Long id) {
        Optional<MapTrainEntity> mapTrain = repository.findById(id);
        MapTrain dto = mapTrain.map(mapper::mapTrainDtoFromEntity).orElse(null);
        return (dto == null) ? Optional.empty() : Optional.of(dto);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public MapTrain save(MapTrain mapTrain) {
        Optional<MapTrainEntity> savedEmployee = repository.findById(mapTrain.getId());
        if (savedEmployee.isPresent()) {
            String msg = String.format("Состав id %s уже существует.", mapTrain.getId());
            log.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        MapTrainEntity entity = mapper.mapTrainEntityFromDto(mapTrain);
        MapTrainEntity savedEntity = repository.save(entity);
        return mapper.mapTrainDtoFromEntity(savedEntity);
    }
}
