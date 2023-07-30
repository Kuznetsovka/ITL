package ru.itl.train;

import com.github.javafaker.Faker;
import com.github.javafaker.service.RandomService;
import ru.itl.train.dto.Road;
import ru.itl.train.dto.Station;
import ru.itl.train.dto.Wagon;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Kuznetsovka 29.07.2023
 */
public class FakeGenerator {

    private final Random random = new Random();
    private final Faker faker = new Faker(new Locale("ru"), new RandomService());

    private long nextId = 1;

    public long fakeId() {
        return nextId++;
    }

    public Road fakeRoad() {
        return Road.builder()
                .number(fakeId())
                .build();
    }

    public Station fakeStation() {
        return Station.builder()
                .id(fakeId())
                .name(faker.address().cityName())
                .road(new HashSet<>())
                .build();
    }

    public Wagon fakeWagon() {
        return Wagon.builder()
                .weightWagon(BigDecimal.valueOf(random.nextInt(100_000_000) * 0.01)) // от 0 до 1_000_000
                .loadCapacity(BigDecimal.valueOf(random.nextInt(100_000_000) * 0.01)) // от 0 до 1_000_000
                .type(faker.funnyName().name())
                .number(random.nextLong(100_000_000))
                .build();
    }

    public List<Wagon> fakeWagonList(int number) {
        List<Wagon> list = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            list.add(fakeWagon());
        }
        return list;
    }
}
