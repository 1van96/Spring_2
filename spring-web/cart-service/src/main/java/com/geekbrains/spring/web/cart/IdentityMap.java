package com.geekbrains.spring.web.cart;

import com.geekbrains.spring.web.cart.models.Cart;

import java.util.HashMap;
import java.util.Map;

public class IdentityMap {

    private static Map<String, Cart> identity = new HashMap<String, Cart>();

    public static Boolean isInto(String key){
        return identity.containsKey(key);
    }

    public static Cart get(String key){
        return identity.get(key);
    }

    public static void add(String key, Cart cart) {
        identity.put(key, cart);
    }
}
