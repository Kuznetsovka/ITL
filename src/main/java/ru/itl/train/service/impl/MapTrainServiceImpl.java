package ru.itl.train.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.itl.train.dto.MapTrain;
import ru.itl.train.dto.Wagon;
import ru.itl.train.entity.MapTrainEntity;
import ru.itl.train.entity.PartTrainEntity;
import ru.itl.train.entity.RoadEntity;
import ru.itl.train.entity.WagonEntity;
import ru.itl.train.repository.MapTrainRepository;
import ru.itl.train.service.MapTrainService;
import ru.itl.train.service.MapperService;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author Kuznetsovka 23.07.2023
 */
@Slf4j
@Service
@AllArgsConstructor
public class MapTrainServiceImpl implements MapTrainService {

    private final MapTrainRepository repository;

    private final MapperService mapper;

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

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Optional<MapTrain> addWagons(RoadEntity road, List<Wagon> wagons) {
        // Получаем карту составов на пути
        Optional<MapTrainEntity> mapTrain = repository.findByRoad_Number(road.getNumber());
        //Если карта составов есть и на нем есть вагоны, получаем их
        AtomicReference<Set<PartTrainEntity>> partTrain = new AtomicReference<>(new HashSet<>());
        mapTrain.ifPresent(map -> partTrain.set(map.getOrderWagon()));
        //Создаем новый состав из полученных вагонов и возможных вагонов на пути
        Queue<PartTrainEntity> newPartTrain = new ArrayDeque<>();
        long lastOrder = partTrain.get().stream().map(PartTrainEntity::getOrder).max(Comparator.naturalOrder()).orElse(0L);
        for (Wagon dto : wagons) {
            WagonEntity wagon = mapper.wagonEntityFromDto(dto);
            newPartTrain.add(new PartTrainEntity(++lastOrder, wagon));
        }
        Set<PartTrainEntity> oldPartTrain = partTrain.get();
        //Добавляем вагоны к составу
        oldPartTrain.addAll(newPartTrain);

        MapTrainEntity mapTrainEntity = mapTrain.orElse(new MapTrainEntity());
        mapTrainEntity.setRoad(road);
        mapTrainEntity.setOrderWagon(oldPartTrain);
        repository.save(mapTrainEntity);
        return Optional.empty();
    }
}