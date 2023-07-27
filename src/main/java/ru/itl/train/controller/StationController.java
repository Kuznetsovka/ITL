package ru.itl.train.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itl.train.dto.Station;
import ru.itl.train.service.CommonService;

import java.util.List;

/**
 * @author Kuznetsovka 23.07.2023
 */
@RestController
@RequestMapping("/api/v1/station")
@Slf4j
@AllArgsConstructor
public class StationController {

    private final CommonService<Station> service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Station createStation(@RequestBody Station station) {
        return service.save(station);
    }

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

    @DeleteMapping(value = "{id}")
    public ResponseEntity<String> delete(@RequestBody Long id) throws Exception {
        Long deletedId = service.delete(id);
        String msg = String.format("Успешное удаление станции %d", deletedId);
        log.info(msg);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Station> get(@PathVariable Long id) throws Exception {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/get")
    public List<Station> get() throws Exception {
        List<Station> stations = service.getAll();
        return stations;
    }
}
