package ru.itl.train.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itl.train.dto.ArrivalWagonPojo;
import ru.itl.train.dto.ChangeWagonPojo;
import ru.itl.train.dto.Wagon;
import ru.itl.train.service.ActionService;

import java.util.List;

/**
 * @author Kuznetsovka 29.07.2023
 */
@Tag(name = "Действия", description = "Action management APIs")
@RestController
@RequestMapping("/api/v1/action")
@Slf4j
@AllArgsConstructor
@SecurityRequirement(name = "Security scheme")
public class ActionController {

    private final ActionService actionService;

    /**
     * Операция приема вагонов на предприятие. На входе список вагонов с учетом на какой путь станции данные вагоны принимаются.
     * Вагоны могут приниматься только в конец состава.
     *
     * @param arrivalWagonPojo - Pojo класс для получения информации по запрашиваемым действиям
     * @return Response о действии приемки
     */
    @Operation(
            summary = "Операция приема вагонов",
            description = "Операция приема вагонов на предприятие. На входе список вагонов с учетом на какой путь станции данные вагоны принимаются.")
    @PostMapping(value = "/wagon/arrival")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> arrivalWagon(@RequestBody ArrivalWagonPojo arrivalWagonPojo) {
        String msg = actionService.addArrivalWagons(arrivalWagonPojo);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    /**
     * Операция перестановки вагонов внутри станции. На входе список вагонов и путь на который они будут перемещены.
     * Вагоны могут быть перемещены только в начало или конец состава.
     *
     * @param changeWagonPojo - Pojo класс для получения информации по запрашиваемым действиям
     * @return Response о действии перестановки
     */
    @Operation(
            summary = "Операция перестановки вагонов",
            description = "Операция перестановки вагонов. На входе список вагонов и путь на который они будут перемещены.")
    @PostMapping(value = "/wagon/change")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> changeWagon(@RequestBody ChangeWagonPojo changeWagonPojo) {
        String msg = actionService.changeWagons(changeWagonPojo);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    /**
     * Операция убытия вагонов на сеть РЖД. Вагоны могут убывать только с начала состава.
     */
    @Operation(
            summary = "убытия вагонов на сеть РЖД",
            description = "Операция убытия вагонов на сеть РЖД. Вагоны могут убывать только с начала состава.")
    @PostMapping(value = "/wagon/change")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> departureWagon(@RequestBody List<Wagon> wagon) {
        String msg = actionService.departureWagons(wagon);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
