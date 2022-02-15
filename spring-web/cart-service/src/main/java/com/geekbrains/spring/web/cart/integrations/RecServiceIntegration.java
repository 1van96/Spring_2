package com.geekbrains.spring.web.cart.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class RecServiceIntegration {
    private final RestTemplate restTemplate;

    @Value("${integrations.rec-service.url}")
    private String recServiceUrl;

    public void sendStatistic(HashMap<String, String> products) {
        restTemplate.postForObject(recServiceUrl + "/api/v1/day", products, HashMap.class);
    }
}
