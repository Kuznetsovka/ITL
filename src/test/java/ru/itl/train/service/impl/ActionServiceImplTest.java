package ru.itl.train.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.itl.train.FakeGenerator;
import ru.itl.train.dto.ArrivalWagonPojo;
import ru.itl.train.dto.Road;
import ru.itl.train.dto.Station;
import ru.itl.train.dto.Wagon;
import ru.itl.train.entity.RoadEntity;
import ru.itl.train.entity.StationEntity;
import ru.itl.train.repository.MapTrainRepository;
import ru.itl.train.repository.RoadRepository;
import ru.itl.train.repository.StationRepository;
import ru.itl.train.repository.WagonRepository;
import ru.itl.train.service.ActionService;
import ru.itl.train.service.MapperService;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author Kuznetsovka 29.07.2023
 */
@SpringBootTest
class ActionServiceImplTest {

    @Autowired
    private ActionService service;

    private static final MapperService mapper = new MapperServiceImpl();
    @Autowired
    private RoadRepository roadRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private WagonRepository wagonRepository;

    @Autowired
    private MapTrainRepository mapTrainRepository;

    private static FakeGenerator fakeGenerator;
    private static Random random;

    @BeforeAll
    public static void init() {
        fakeGenerator = new FakeGenerator();
        random = new Random();
    }

    @BeforeEach
    void setUp() {
        roadRepository.deleteAll();
        stationRepository.deleteAll();
        wagonRepository.deleteAll();
        mapTrainRepository.deleteAll();
    }

    @RepeatedTest(10)
    void addArrivalWagons_When_Station_NotExist() {
        final int MAX_NUMBER_OF_ELEMENTS = 20;
        String errorMsg = "Вагоны не могут быть приняты! Указанный путь не существует на указанной станции";
        List<Wagon> wagons = fakeGenerator.fakeWagonList(random.nextInt(MAX_NUMBER_OF_ELEMENTS));
        Station station = fakeGenerator.fakeStation();
        Road road = fakeGenerator.fakeRoad();
        ArrivalWagonPojo pojo = ArrivalWagonPojo.builder()
                .station(station)
                .wagons(wagons)
                .road(road)
                .build();
        assertEquals(errorMsg, service.addArrivalWagons(pojo));
    }

    @RepeatedTest(10)
    void addArrivalWagons_When_Road_NotExist() {
        final int MAX_NUMBER_OF_ELEMENTS = 20;
        String errorMsg = "Вагоны не могут быть приняты! Указанный путь не существует на указанной станции";
        List<Wagon> wagons = fakeGenerator.fakeWagonList(random.nextInt(MAX_NUMBER_OF_ELEMENTS));
        Station station = fakeGenerator.fakeStation();
        StationEntity entity = stationRepository.save(mapper.stationEntityFromDto(station));
        assertDtoFieldsStation(entity, station);
        Road road = fakeGenerator.fakeRoad();
        ArrivalWagonPojo pojo = ArrivalWagonPojo.builder()
                .station(station)
                .wagons(wagons)
                .road(road)
                .build();
        assertEquals(errorMsg, service.addArrivalWagons(pojo));
    }

    @RepeatedTest(10)
    void addArrivalWagons_When_Road_Exist() {
        final int MAX_NUMBER_OF_ELEMENTS = 20;
        String errorMsg1 = "Вагоны не могут быть приняты! Указанный путь не существует на указанной станции";
        String errorMsg2 = "Вагоны не могут быть приняты!В json не указаны вагоны.";
        List<Wagon> wagons = fakeGenerator.fakeWagonList(random.nextInt(MAX_NUMBER_OF_ELEMENTS));
        Road road = fakeGenerator.fakeRoad();
        Station station = fakeGenerator.fakeStation();
        station.setRoad(Collections.singleton(road));
        StationEntity entity = stationRepository.save(mapper.stationEntityFromDto(station));
        assertDtoFieldsStation(entity, station);

        ArrivalWagonPojo pojo = ArrivalWagonPojo.builder()
                .station(station)
                .wagons(wagons)
                .road(road)
                .build();
        String error = (wagons.isEmpty()) ? errorMsg2 : errorMsg1;
        assertEquals(error, service.addArrivalWagons(pojo));
    }

    @RepeatedTest(1)
    void addArrivalWagons_When_Wagon_Added() {
        final int MAX_NUMBER_OF_ELEMENTS = 20;
        List<Wagon> wagons = fakeGenerator.fakeWagonList(random.nextInt(MAX_NUMBER_OF_ELEMENTS));
        Road road = fakeGenerator.fakeRoad();
        RoadEntity entityRoad = roadRepository.save(mapper.roadEntityFromDto(road));
        Station station = fakeGenerator.fakeStation();
        station.setRoad(Collections.singleton(mapper.roadDtoFromEntity(entityRoad)));
        StationEntity entity = stationRepository.save(mapper.stationEntityFromDto(station));
        station.setId(entity.getId());
        assertDtoFieldsStation(entity, station);

        ArrivalWagonPojo pojo = ArrivalWagonPojo.builder()
                .station(station)
                .wagons(wagons)
                .road(road)
                .build();

        String numberWagons = pojo.getWagons().stream()
                .map(Wagon::getNumber)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        String numberStation = pojo.getStation().getName();
        if (numberStation == null) {
            stationRepository.findById(pojo.getStation().getId()).get().getName();
        }
        String msg = String.format("Вагоны [%s] приняты на станцию [%s]!", numberWagons, numberStation);
        assertEquals(msg, service.addArrivalWagons(pojo));
    }

    @Test
    void changeWagons() {
    }

    @Test
    void departureWagons() {
    }

    public static void assertDtoFieldsStation(StationEntity entity, Station dto) {
        assertEquals(entity.getName(), dto.getName());
        if (entity.getRoads() != null && !entity.getRoads().isEmpty()) {
            Set<RoadEntity> roadEntitySet = dto.getRoad().stream()
                    .map(mapper::roadEntityFromDto)
                    .collect(Collectors.toSet());
            assertEquals(entity.getRoads(), roadEntitySet);
        }
    }
}