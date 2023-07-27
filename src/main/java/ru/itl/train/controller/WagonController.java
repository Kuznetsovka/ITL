package ru.itl.train.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itl.train.dto.Wagon;
import ru.itl.train.service.CommonService;

import java.util.List;

/**
 * @author Kuznetsovka 23.07.2023
 */
@RestController
@RequestMapping("/api/v1/wagon")
@Slf4j
@AllArgsConstructor
public class WagonController {

    private final CommonService<Wagon> service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Wagon createWagon(@RequestBody Wagon wagon){
        return service.save(wagon);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Wagon> update(@PathVariable("id") long id,
                                        @RequestBody Wagon wagon) throws Exception {
        return service.getById(id)
                .map(savedWagon -> {
                    Wagon updated = service.update(wagon);
                    return new ResponseEntity<>(updated, HttpStatus.OK);

                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value="{id}")
    public ResponseEntity<String> delete(@RequestBody Long id) throws Exception {
        Long deletedId = service.delete(id);
        String msg = String.format("Успешное удаление вагона %d", deletedId);
        log.info(msg);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping(value="/get/{id}")
    public ResponseEntity<Wagon> get(@PathVariable Long id) throws Exception {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value="/get")
    public List<Wagon> get() throws Exception {
        List<Wagon> wagons = service.getAll();
        return wagons;
    }
}
