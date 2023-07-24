package ru.itl.train.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itl.train.dto.NomenclatureCargo;
import ru.itl.train.service.CommonService;

import java.util.List;

/**
 * @author Kuznetsovka 23.07.2023
 */
@RestController
@RequestMapping("/api/v1/nomeclature_cargo")
@Slf4j
public class NomeclatureCargoController {

    @Autowired
    private CommonService<NomenclatureCargo> service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NomenclatureCargo createNomenclatureCargo(@RequestBody NomenclatureCargo nomenclatureCargo) {
        return service.save(nomenclatureCargo);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<NomenclatureCargo> update(@PathVariable("id") long id,
                                                    @RequestBody NomenclatureCargo nomenclatureCargo) throws Exception {
        return service.getById(id)
                .map(savedNomenclatureCargo -> {
                    NomenclatureCargo updated = service.update(nomenclatureCargo);
                    return new ResponseEntity<>(updated, HttpStatus.OK);

                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<String> delete(@RequestBody Long id) throws Exception {
        Long deletedId = service.delete(id);
        String msg = String.format("Успешное удаление записи справочника номенклатурных грузов %d", deletedId);
        log.info(msg);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<NomenclatureCargo> get(@PathVariable Long id) throws Exception {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/get")
    public List<NomenclatureCargo> get() throws Exception {
        List<NomenclatureCargo> nomenclatureCargos = service.getAll();
        return nomenclatureCargos;
    }
}
