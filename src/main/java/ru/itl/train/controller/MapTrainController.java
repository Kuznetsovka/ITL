package ru.itl.train.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itl.train.dto.MapTrain;
import ru.itl.train.service.CommonService;

import java.util.List;

/**
 * @author Kuznetsovka 23.07.2023
 */
@RestController
@RequestMapping("/api/v1/map_train")
@Slf4j
@AllArgsConstructor
public class MapTrainController {

    private final CommonService<MapTrain> service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MapTrain createMapTrain(@RequestBody MapTrain mapTrain) {
        return service.save(mapTrain);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<MapTrain> update(@PathVariable("id") long id,
                                           @RequestBody MapTrain mapTrain) throws Exception {
        return service.getById(id)
                .map(savedMapTrain -> {
                    MapTrain updated = service.update(mapTrain);
                    return new ResponseEntity<>(updated, HttpStatus.OK);

                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<String> delete(@RequestBody Long number) throws Exception {
        Long deletedId = service.delete(number);
        String msg = String.format("Успешное удаление карты расположения подвижного состава %d", deletedId);
        log.info(msg);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<MapTrain> get(@PathVariable Long id) throws Exception {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/get")
    public List<MapTrain> get() throws Exception {
        List<MapTrain> mapTrains = service.getAll();
        return mapTrains;
    }
}
