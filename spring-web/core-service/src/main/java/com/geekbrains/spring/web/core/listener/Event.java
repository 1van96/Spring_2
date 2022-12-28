package com.geekbrains.spring.web.core.listener;

import com.geekbrains.spring.web.core.entities.Order;

public class Event {

    private final Order order;

    public Event(Order order) {
        this.order = order;
    }

    public Object getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "New order " + order;
    }
}
