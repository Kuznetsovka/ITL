package ru.itl.train.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itl.train.dto.Road;
import ru.itl.train.service.CommonService;

import java.util.List;

/**
 * @author Kuznetsovka 23.07.2023
 */
@RestController
@RequestMapping("/api/v1/road")
@Slf4j
@AllArgsConstructor
public class RoadController {

    private final CommonService<Road> service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Road createRoad(@RequestBody Road road){
        return service.save(road);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Road> update(@PathVariable("id") long id,
                                       @RequestBody Road road) throws Exception {
        return service.getById(id)
                .map(savedRoad -> {
                    Road updated = service.update(road);
                    return new ResponseEntity<>(updated, HttpStatus.OK);

                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value="{id}")
    public ResponseEntity<String> delete(@RequestBody Long number) throws Exception {
        Long deletedId = service.delete(number);
        String msg = String.format("Успешное удаление пути %d", deletedId);
        log.info(msg);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping(value="/get/{id}")
    public ResponseEntity<Road> get(@PathVariable Long id) throws Exception {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value="/get")
    public List<Road> get() throws Exception {
        List<Road> roads = service.getAll();
        return roads;
    }
}
