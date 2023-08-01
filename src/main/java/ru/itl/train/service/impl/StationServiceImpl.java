package ru.itl.train.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.itl.train.dto.PartTrain;
import ru.itl.train.dto.Station;
import ru.itl.train.entity.MapTrainEntity;
import ru.itl.train.entity.RoadEntity;
import ru.itl.train.entity.StationEntity;
import ru.itl.train.repository.StationRepository;
import ru.itl.train.service.MapTrainService;
import ru.itl.train.service.MapperService;
import ru.itl.train.service.StationService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * @author Kuznetsovka 23.07.2023
 */
@Slf4j
@Service
@AllArgsConstructor
public class StationServiceImpl implements StationService {

    private final StationRepository repository;

    private final MapTrainService mapTrainService;

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
        if (station.getId() != null) {
            Optional<StationEntity> savedEmployee = repository.findById(station.getId());
            if (savedEmployee.isPresent()) {
                String msg = String.format("Станция с номером %s уже существует.", station.getId());
                log.error(msg);
                throw new ResourceNotFoundException(msg);
            }
        }
        StationEntity entity = mapper.stationEntityFromDto(station);
        StationEntity savedEntity = repository.save(entity);
        return mapper.stationDtoFromEntity(savedEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public Optional<RoadEntity> checkRoadOnStation(Long number, Long id) {
        return repository.getRoadByNumberAndStationId(number, id);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    @Override
    public List<MapTrainEntity> checkTrainOnStationByRoad(Long number, List<PartTrain> partTrains) {
        List<Long> orders = partTrains.stream().map(PartTrain::getOrder).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        if (checkMissingWagon(orders))
            return Collections.emptyList();

        //Проверка находятся ли вагоны в середине или нет
        List<Long> minAndMax = mapTrainService.getMinAndMaxOrder();
        if (minAndMax != null && !minAndMax.isEmpty() &&
                !orders.get(0).equals(minAndMax.get(0)) || !orders.get(orders.size() - 1).equals(minAndMax.get(1)))
            return Collections.emptyList();

        List<MapTrainEntity> mapTrainEntities = mapTrainService.getRoadByPartTrains(partTrains);

        if (mapTrainEntities.isEmpty())
            return Collections.emptyList();

        boolean isOnOneStation = repository.existStationRoads(Arrays.asList(mapTrainEntities.get(0).getRoad().getNumber(), number));
        return (isOnOneStation) ? mapTrainEntities : Collections.emptyList();
    }

    @Override
    public boolean checkMissingWagon(List<Long> orders) {
        // todo Проверить эффективность подхода
        List<Long> listWithoutMissing = LongStream.range(orders.get(0), orders.get(orders.size() - 1) + 1).boxed().collect(Collectors.toList());
        return !Objects.equals(orders, listWithoutMissing);
    }
}
