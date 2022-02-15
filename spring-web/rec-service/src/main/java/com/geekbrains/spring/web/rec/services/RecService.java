package com.geekbrains.spring.web.rec.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class RecService {
    private final RedisTemplate<String, Integer> redisTemplate;
    private List<String> mostPopularProducts;

    @PostConstruct
    public void init(){
        mostPopularProducts = new ArrayList<>();
    }

    public void updateProductAnalytics(HashMap<String, String> products) {
        Set<String> productsNames = products.keySet();
        String date =  LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String key;
        String name;
        Iterator<String> iter = productsNames.iterator();
        while (iter.hasNext()){
            name = iter.next();
            int value = Integer.parseInt(products.get(name));
            key = date + name;
            if (!redisTemplate.hasKey(key)) {
                redisTemplate.opsForValue().set(key, value);
            } else {
                Integer prev = redisTemplate.opsForValue().get(key);
                redisTemplate.opsForValue().set(key, prev + value);
            }
        }
    }

    public List<String> getProductsAnalytics() {
        return mostPopularProducts.subList(0, 5);
    }

    @Scheduled(fixedDelay = 10000)
    public void createProductsAnalytics(){
        String key =  LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        Set<String> productsByDay = redisTemplate.keys(key + "*");
        Iterator<String> iter = productsByDay.iterator();
        HashMap<Integer, String> products = new HashMap<>();
        while(iter.hasNext()){
            String next = iter.next();
            products.put(redisTemplate.opsForValue().get(next), next);
        }
        mostPopularProducts.clear();
        products.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
        products.entrySet().stream().forEach(p -> mostPopularProducts.add(p.getValue()));
        Collections.reverse(mostPopularProducts);
        for (int i = 0; i < mostPopularProducts.size(); i++) {
            mostPopularProducts.set(i, mostPopularProducts.get(i).substring(8));
        }
    }
}