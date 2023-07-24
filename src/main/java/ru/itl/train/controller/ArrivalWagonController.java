package ru.itl.train.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itl.train.dto.ArrivalWagon;
import ru.itl.train.service.CommonService;

import java.util.List;

/**
 * @author Kuznetsovka 23.07.2023
 */
@RestController
@RequestMapping("/api/v1/arrival_train")
@Slf4j
public class ArrivalWagonController {

    @Autowired
    private CommonService<ArrivalWagon> service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArrivalWagon createArrivalWagon(@RequestBody ArrivalWagon arrivalWagon) {
        return service.save(arrivalWagon);
    }

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

    @DeleteMapping(value = "{id}")
    public ResponseEntity<String> delete(@RequestBody Long number) throws Exception {
        Long deletedId = service.delete(number);
        String msg = String.format("Успешное удаление натурного листа %d", deletedId);
        log.info(msg);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<ArrivalWagon> get(@PathVariable Long id) throws Exception {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/get")
    public List<ArrivalWagon> get() throws Exception {
        List<ArrivalWagon> arrivalWagons = service.getAll();
        return arrivalWagons;
    }
}
