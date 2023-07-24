package ru.itl.train.service;

import org.springframework.stereotype.Service;
import ru.itl.train.dto.*;
import ru.itl.train.entity.*;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Kuznetsovka 24.07.2023
 */
@Service
public class MapperServiceImpl implements MapperService {

    @Override
    public Road roadDtoFromEntity(RoadEntity entity) {
        return Road.builder()
                .number(entity.getNumber())
                .build();
    }

    @Override
    public RoadEntity roadEntityFromDto(Road dto) {
        return RoadEntity.builder()
                .number(dto.getNumber())
                .build();
    }

    @Override
    public Wagon wagonDtoFromEntity(WagonEntity entity) {
        return Wagon.builder()
                .id(entity.getId())
                .loadCapacity(entity.getLoadCapacity())
                .type(entity.getType())
                .number((entity.getWagonInfo() != null) ? entity.getWagonInfo().getNumber() : null)
                .weightWagon((entity.getWagonInfo() != null) ? entity.getWagonInfo().getWeightWagon() : null)
                .build();
    }

    @Override
    public WagonEntity wagonEntityFromDto(Wagon dto) {
        return WagonEntity.builder()
                .id(dto.getId())
                .loadCapacity(dto.getLoadCapacity())
                .type(dto.getType())
                .wagonInfo(new TechInfoEntity(dto.getNumber(), dto.getWeightWagon()))
                .build();
    }

    @Override
    public Station stationDtoFromEntity(StationEntity entity) {
        Set<Road> roads = entity.getRoad().stream()
                .map(this::roadDtoFromEntity)
                .collect(Collectors.toSet());
        return Station.builder()
                .id(entity.getId())
                .name(entity.getName())
                .road(roads)
                .build();
    }

    @Override
    public StationEntity stationEntityFromDto(Station dto) {
        Set<RoadEntity> roads = dto.getRoad().stream()
                .map(this::roadEntityFromDto)
                .collect(Collectors.toSet());
        return StationEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .road(roads)
                .build();
    }

    @Override
    public PartTrain partTrainDtoFromEntity(PartTrainEntity entity) {
        return PartTrain.builder()
                .order(entity.getOrder())
                .wagon(wagonDtoFromEntity(entity.getWagon()))
                .build();
    }

    @Override
    public PartTrainEntity partTrainEntityFromDto(PartTrain dto) {
        return PartTrainEntity.builder()
                .order(dto.getOrder())
                .wagon(wagonEntityFromDto(dto.getWagon()))
                .build();
    }

    @Override
    public NomenclatureCargo nomenclatureCargoDtoFromEntity(NomenclatureCargoEntity entity) {
        return NomenclatureCargo.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .build();
    }

    @Override
    public NomenclatureCargoEntity nomenclatureCargoEntityFromDto(NomenclatureCargo dto) {
        return NomenclatureCargoEntity.builder()
                .id(dto.getId())
                .code(dto.getCode())
                .name(dto.getName())
                .build();
    }

    @Override
    public MapTrain mapTrainDtoFromEntity(MapTrainEntity entity) {
        Set<PartTrain> parts = entity.getOrderWagon().stream()
                .map(this::partTrainDtoFromEntity)
                .collect(Collectors.toSet());
        Deque<PartTrain> deque = entity.getOrderWagon()
                .stream()
                .map(this::partTrainDtoFromEntity)
                .sorted(Comparator.comparing(PartTrain::getOrder))
                .collect(Collectors.toCollection(ArrayDeque::new));
        return MapTrain.builder()
                .id(entity.getId())
                .orderWagon(deque)
                .road(roadDtoFromEntity(entity.getRoad()))
                .build();
    }

    @Override
    public MapTrainEntity mapTrainEntityFromDto(MapTrain dto) {
        Set<PartTrainEntity> parts = dto.getOrderWagon().stream()
                .map(this::partTrainEntityFromDto)
                .collect(Collectors.toSet());
        return MapTrainEntity.builder()
                .id(dto.getId())
                .orderWagon(parts)
                .road(roadEntityFromDto(dto.getRoad()))
                .build();
    }

    @Override
    public ArrivalWagon arrivalWagonDtoFromEntity(ArrivalWagonEntity entity) {
        return ArrivalWagon.builder()
                .id(entity.getId())
                .nomenclatureCargo(entity.getNomenclatureCargo())
                .weightCargo(entity.getWeightCargo())
                .weightWagon(entity.getWagonInfo().getWeightWagon())
                .number(entity.getWagonInfo().getNumber())
                .build();
    }

    @Override
    public ArrivalWagonEntity arrivalWagonEntityFromDto(ArrivalWagon dto) {
        return ArrivalWagonEntity.builder()
                .id(dto.getId())
                .nomenclatureCargo(dto.getNomenclatureCargo())
                .weightCargo(dto.getWeightCargo())
                .wagonInfo(new TechInfoEntity(dto.getNumber(), dto.getWeightWagon()))
                .build();
    }
}
