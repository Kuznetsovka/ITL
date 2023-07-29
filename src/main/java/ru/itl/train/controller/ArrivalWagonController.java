package ru.itl.train.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itl.train.dto.ArrivalWagon;
import ru.itl.train.service.ArrivalWagonService;

import java.util.List;

/**
 * @author Kuznetsovka 23.07.2023
 */
@Tag(name = "Натурный лист для приема вагонов", description = "ArrivalWagon management APIs")
@RestController
@RequestMapping("/api/v1/arrival_train")
@Slf4j
@AllArgsConstructor
@SecurityRequirement(name = "Security scheme")
public class ArrivalWagonController {

    private final ArrivalWagonService service;

    @Operation(summary = "СОХРАНЕНИЕ")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArrivalWagon createArrivalWagon(@RequestBody ArrivalWagon arrivalWagon) {
        return service.save(arrivalWagon);
    }

    @Operation(summary = "ОБНОВЛЕНИЕ")
    @PutMapping(value = "{id}")
    public ResponseEntity<ArrivalWagon> update(@PathVariable("id") long id,
                                               @RequestBody ArrivalWagon arrivalWagon) throws Exception {
        return service.getById(id)
                .map(savedArrivalWagon -> {
                    ArrivalWagon updated = service.update(arrivalWagon);
                    return new ResponseEntity<>(updated, HttpStatus.OK);

                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "УДАЛЕНИЕ")
    @DeleteMapping(value = "{id}")
    public ResponseEntity<String> delete(@RequestBody Long number) throws Exception {
        Long deletedId = service.delete(number);
        String msg = String.format("Успешное удаление натурного листа %d", deletedId);
        log.info(msg);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @Operation(summary = "ПОЛУЧЕНИЕ по ID")
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<ArrivalWagon> get(@PathVariable Long id) throws Exception {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "ПОЛУЧЕНИЕ СПИСКА")
    @GetMapping(value = "/get")
    public List<ArrivalWagon> getAll() throws Exception {
        List<ArrivalWagon> arrivalWagons = service.getAll();
        return arrivalWagons;
    }
}
