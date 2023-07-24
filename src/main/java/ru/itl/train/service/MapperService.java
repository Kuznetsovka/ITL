package ru.itl.train.service;

import ru.itl.train.dto.*;
import ru.itl.train.entity.*;

/**
 * @author Kuznetsovka 24.07.2023
 */
public interface MapperService {

    Road roadDtoFromEntity(RoadEntity entity);

    RoadEntity roadEntityFromDto(Road dto);

    Wagon wagonDtoFromEntity(WagonEntity entity);

    WagonEntity wagonEntityFromDto(Wagon dto);


    Station stationDtoFromEntity(StationEntity entity);

    StationEntity stationEntityFromDto(Station dto);

    PartTrain partTrainDtoFromEntity(PartTrainEntity entity);

    PartTrainEntity partTrainEntityFromDto(PartTrain dto);

    NomenclatureCargo nomenclatureCargoDtoFromEntity(NomenclatureCargoEntity entity);

    NomenclatureCargoEntity nomenclatureCargoEntityFromDto(NomenclatureCargo dto);

    MapTrain mapTrainDtoFromEntity(MapTrainEntity entity);

    MapTrainEntity mapTrainEntityFromDto(MapTrain dto);

    ArrivalWagon arrivalWagonDtoFromEntity(ArrivalWagonEntity entity);

    ArrivalWagonEntity arrivalWagonEntityFromDto(ArrivalWagon dto);
}
