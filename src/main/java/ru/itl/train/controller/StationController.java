package ru.itl.train.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itl.train.dto.Station;
import ru.itl.train.service.StationService;

import java.util.List;

/**
 * @author Kuznetsovka 23.07.2023
 */
@Tag(name = "Станции", description = "Station management APIs")
@RestController
@RequestMapping("/api/v1/station")
@Slf4j
@AllArgsConstructor
@SecurityRequirement(name = "Security scheme")
public class StationController {

    private final StationService service;

    @Operation(summary = "СОХРАНЕНИЕ")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Station createStation(@RequestBody Station station) {
        return service.save(station);
    }

    @Operation(summary = "ОБНОВЛЕНИЕ")
    @PutMapping(value = "{id}")
    public ResponseEntity<Station> update(@PathVariable("id") long id,
                                          @RequestBody Station station) throws Exception {
        return service.getById(id)
                .map(savedStation -> {
                    Station updated = service.update(station);
                    return new ResponseEntity<>(updated, HttpStatus.OK);

                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "УДАЛЕНИЕ")
    @DeleteMapping(value = "{id}")
    public ResponseEntity<String> delete(@RequestBody Long id) throws Exception {
        Long deletedId = service.delete(id);
        String msg = String.format("Успешное удаление станции %d", deletedId);
        log.info(msg);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @Operation(summary = "ПОЛУЧЕНИЕ по ID")
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Station> get(@PathVariable Long id) throws Exception {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "ПОЛУЧЕНИЕ СПИСКА")
    @GetMapping(value = "/get")
    public List<Station> getAll() throws Exception {
        List<Station> stations = service.getAll();
        return stations;
    }
}
