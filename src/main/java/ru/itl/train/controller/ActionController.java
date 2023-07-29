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
import ru.itl.train.service.ActionService;

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
}