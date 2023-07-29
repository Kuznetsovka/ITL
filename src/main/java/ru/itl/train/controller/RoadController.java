package ru.itl.train.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itl.train.dto.Road;
import ru.itl.train.service.RoadService;

import java.util.List;

/**
 * @author Kuznetsovka 23.07.2023
 */
@Tag(name = "Пути", description = "Road management APIs")
@RestController
@RequestMapping("/api/v1/road")
@Slf4j
@AllArgsConstructor
@SecurityRequirement(name = "Security scheme")
public class RoadController {

    private final RoadService service;

    @Operation(summary = "СОХРАНЕНИЕ")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Road createRoad(@RequestBody Road road){
        return service.save(road);
    }

    @Operation(summary = "ОБНОВЛЕНИЕ")
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

    @Operation(summary = "УДАЛЕНИЕ")
    @DeleteMapping(value = "{id}")
    public ResponseEntity<String> delete(@RequestBody Long number) throws Exception {
        Long deletedId = service.delete(number);
        String msg = String.format("Успешное удаление пути %d", deletedId);
        log.info(msg);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @Operation(summary = "ПОЛУЧЕНИЕ по ID")
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Road> get(@PathVariable Long id) throws Exception {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @Operation(summary = "ПОЛУЧЕНИЕ СПИСКА")
    @GetMapping(value = "/get")
    public List<Road> getAll() throws Exception {
        List<Road> roads = service.getAll();
        return roads;
    }
}
