package com.geekbrains.spring.web.rec.controllers;

import com.geekbrains.spring.web.api.core.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.geekbrains.spring.web.rec.services.RecService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RecController {
    private final RecService recService;

    @GetMapping("/day")
    public List<String> getDayStatistic() {
        return recService.getProductsAnalytics();
    }

    @PostMapping("/day")
    public void addToDayStatistic(@RequestBody HashMap<String, String> products) {
        recService.updateProductAnalytics(products);
    }
}
