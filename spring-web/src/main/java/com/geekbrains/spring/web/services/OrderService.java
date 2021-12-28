package com.geekbrains.spring.web.services;

import com.geekbrains.spring.web.dto.Cart;
import com.geekbrains.spring.web.dto.OrderItemDto;
import com.geekbrains.spring.web.entities.Order;
import com.geekbrains.spring.web.entities.OrderItem;
import com.geekbrains.spring.web.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ProductsService productsService;
    private final UserService userService;

    @Transactional
    public void createOrder() {
        Cart cart = cartService.getCurrentCart();
        Order order = new Order();
        order.setTotalPrice(cart.getTotalPrice());
        List<OrderItem> items = new ArrayList<>();
        for (OrderItemDto i : cart.getItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setPrice(i.getPrice());
            orderItem.setPrice_per_product(i.getPricePerProduct());
            orderItem.setQuantity(i.getQuantity());
            orderItem.setProduct(productsService.findById(i.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Не удалось найти продукт с ID " + i.getProductId())));
            items.add(orderItem);
        }
        order.setItems(items);
        order.setUser(userService.findByUsername("bob").get()); //Пока делаем одну корзину
        orderRepository.save(order).getItems();
    }
    // Заказ создаётся, но при его загрузке вылетает StackOverflowError (при попытке запросить items).
    // Сможете подсказать, где я ошибся в связке OneToMany и ManyToOne?
}
