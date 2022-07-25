package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping("/comparisonOfMethods")
public class Test {

    @GetMapping
    public ResponseEntity StreamMethod() {
        long start1 = System.nanoTime();
        int sum1 = Stream
                .iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        long end1 = System.nanoTime();
        long time1 = end1 - start1;

        long start2 = System.nanoTime();
        int sum2 = Stream
                .iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, Integer::sum);
        long end2 = System.nanoTime();
        long time2 = end2 - start2;

        return ResponseEntity.ok("Время вычисления " + sum1 + " обычным методом = " + time1 +
                "время вычисления " + sum2 + " параллельным методом  = " + time2);
    }
}
