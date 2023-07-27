package ru.itl.train.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itl.train.dto.PartTrain;
import ru.itl.train.service.PartTrainService;

import java.util.List;

/**
 * @author Kuznetsovka 23.07.2023
 */
@RestController
@RequestMapping("/api/v1/part_train")
@Slf4j
@AllArgsConstructor
public class PartTrainController {

    private final PartTrainService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PartTrain createPartTrain(@RequestBody PartTrain partTrain) {
        return service.save(partTrain);
    }

    @PutMapping(value = "{order}/{numberWagon}")
    public ResponseEntity<PartTrain> update(@PathVariable("order") long order,
                                            @PathVariable("numberWagon") long numberWagon,
                                            @RequestBody PartTrain partTrain) throws Exception {
        return service.getByOrderAndWagonNumber(order, numberWagon)
                .map(savedPartTrain -> {
                    PartTrain updated = service.update(partTrain);
                    return new ResponseEntity<>(updated, HttpStatus.OK);

                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "{order}/{numberWagon}")
    public ResponseEntity<String> delete(@PathVariable("order") long order,
                                         @PathVariable("numberWagon") long numberWagon) throws Exception {
        String orderWagon = service.delete(order, numberWagon);
        String msg = String.format("Успешное удаление составного вагона %s", orderWagon);
        log.info(msg);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping(value = "/get/{order}/{numberWagon}")
    public ResponseEntity<PartTrain> get(@PathVariable("order") long order,
                                         @PathVariable("numberWagon") long numberWagon) throws Exception {
        return service.getByOrderAndWagonNumber(order, numberWagon)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/get")
    public List<PartTrain> get() throws Exception {
        List<PartTrain> partTrains = service.getAll();
        return partTrains;
    }
}
