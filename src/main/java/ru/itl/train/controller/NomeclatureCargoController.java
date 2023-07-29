package ru.itl.train.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itl.train.dto.NomenclatureCargo;
import ru.itl.train.service.NomenclatureCargoService;

import java.util.List;

/**
 * @author Kuznetsovka 23.07.2023
 */
@Tag(name = "Справочник номенклатур грузов", description = "NomenclatureCargo management APIs")
@RestController
@RequestMapping("/api/v1/nomeclature_cargo")
@Slf4j
@AllArgsConstructor
@SecurityRequirement(name = "Security scheme")
public class NomeclatureCargoController {

    private final NomenclatureCargoService service;

    @Operation(summary = "СОХРАНЕНИЕ")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NomenclatureCargo createNomenclatureCargo(@RequestBody NomenclatureCargo nomenclatureCargo) {
        return service.save(nomenclatureCargo);
    }

    @Operation(summary = "ОБНОВЛЕНИЕ")
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

    @Operation(summary = "УДАЛЕНИЕ")
    @DeleteMapping(value = "{id}")
    public ResponseEntity<String> delete(@RequestBody Long id) throws Exception {
        Long deletedId = service.delete(id);
        String msg = String.format("Успешное удаление записи справочника номенклатурных грузов %d", deletedId);
        log.info(msg);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @Operation(summary = "ПОЛУЧЕНИЕ по ID")
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<NomenclatureCargo> get(@PathVariable Long id) throws Exception {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "ПОЛУЧЕНИЕ СПИСКА")
    @GetMapping(value = "/get")
    public List<NomenclatureCargo> getAll() throws Exception {
        List<NomenclatureCargo> nomenclatureCargos = service.getAll();
        return nomenclatureCargos;
    }
}
