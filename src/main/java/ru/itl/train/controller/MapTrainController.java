package ru.itl.train.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itl.train.dto.MapTrain;
import ru.itl.train.service.MapTrainService;

import java.util.List;

/**
 * @author Kuznetsovka 23.07.2023
 */
@Tag(name = "Карта расположения подвижного состава", description = "MapTrain management APIs")
@RestController
@RequestMapping("/api/v1/map_train")
@Slf4j
@AllArgsConstructor
@SecurityRequirement(name = "Security scheme")
public class MapTrainController {

    private final MapTrainService service;

    @Operation(summary = "СОХРАНЕНИЕ")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MapTrain createMapTrain(@RequestBody MapTrain mapTrain) {
        return service.save(mapTrain);
    }

    @Operation(summary = "ОБНОВЛЕНИЕ")
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

    @Operation(summary = "УДАЛЕНИЕ")
    @DeleteMapping(value = "{id}")
    public ResponseEntity<String> delete(@RequestBody Long number) throws Exception {
        Long deletedId = service.delete(number);
        String msg = String.format("Успешное удаление карты расположения подвижного состава %d", deletedId);
        log.info(msg);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @Operation(summary = "ПОЛУЧЕНИЕ по ID")
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<MapTrain> get(@PathVariable Long id) throws Exception {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "ПОЛУЧЕНИЕ СПИСКА")
    @GetMapping(value = "/get")
    public List<MapTrain> getAll() throws Exception {
        List<MapTrain> mapTrains = service.getAll();
        return mapTrains;
    }
}
