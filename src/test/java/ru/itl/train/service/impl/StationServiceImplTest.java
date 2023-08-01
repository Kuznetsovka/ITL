package ru.itl.train.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.itl.train.service.StationService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * @author Kuznetsovka 02.08.2023
 */
@SpringBootTest
class StationServiceImplTest {

    @Autowired
    private StationService service;

    @Test
    void checkMissingWagonTest_True() {
        List<Long> orders = Arrays.asList(2L, 3L, 5L);
        assertTrue(service.checkMissingWagon(orders));
    }

    @Test
    void checkMissingWagonTest_False() {
        List<Long> orders = Arrays.asList(2L, 3L, 4L);
        assertFalse(service.checkMissingWagon(orders));
    }
}