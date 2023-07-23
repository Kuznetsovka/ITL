package ru.itl.train.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itl.train.dto.Wagon;
import ru.itl.train.service.WagonService;

import java.util.List;

/**
 * @author Kuznetsovka 23.07.2023
 */
@RestController
@RequestMapping("/api/v1/wagon")
@Slf4j
public class WagonController {

    @Autowired
    private WagonService wagonService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Wagon createWagon(@RequestBody Wagon wagon){
        return wagonService.save(wagon);
    }

    @PutMapping(value="{id}")
    public ResponseEntity<Wagon> update(@PathVariable("id") long wagonId,
                                              @RequestBody Wagon wagon) throws Exception {
        return wagonService.getById(wagonId)
                .map(savedWagon -> {
                    Wagon updatedEmployee = wagonService.update(wagon);
                    return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);

                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value="{id}")
    public ResponseEntity<String> delete(@RequestBody Long id) throws Exception {
        Long deletedId = wagonService.delete(id);
        String msg = String.format("Успешное удаление вагона %d", deletedId);
        log.info(msg);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping(value="/get/{id}")
    public ResponseEntity<Wagon> get(@PathVariable Long id) throws Exception {
        return wagonService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value="/get")
    public List<Wagon> get() throws Exception {
        List<Wagon> wagons = wagonService.getAllWagons();
        return wagons;
    }
}
