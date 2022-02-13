package com.geekbrains.spring.web;

import com.geekbrains.spring.web.core.entities.Product;
import com.geekbrains.spring.web.core.services.CartService;
import com.geekbrains.spring.web.core.services.ProductsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.Optional;

@SpringBootTest
public class CartServiceTest {
    private final String TEST_CART_NAME_1 = "test_cart_1";
    private final String TEST_CART_NAME_2 = "test_cart_2";

    @Autowired
    private CartService cartService;

    @MockBean
    private ProductsService productsService;

    @BeforeEach
    public void initCart() {
        cartService.clearCart(TEST_CART_NAME_1);
        cartService.clearCart(TEST_CART_NAME_2);
    }

    @Test
    public void cartTest() {

        Product anotherProduct = new Product();
        anotherProduct.setId(2L);
        anotherProduct.setTitle("Apple");
        anotherProduct.setPrice(100);

        Product product = new Product();
        product.setId(3L);
        product.setTitle("Cheese");
        product.setPrice(300);

        Mockito.doReturn(Optional.of(product)).when(productsService).findById(3L);
        Mockito.doReturn(Optional.of(anotherProduct)).when(productsService).findById(2L);

        cartService.addToCart(TEST_CART_NAME_1, 3L);
        cartService.addToCart(TEST_CART_NAME_1, 3L);


        Mockito.verify(productsService, Mockito.times(2)).findById(ArgumentMatchers.eq(3L));
        Assertions.assertEquals(1, cartService.getCurrentCart(TEST_CART_NAME_1).getItems().size());

        cartService.decrementItem(TEST_CART_NAME_1, 3L);

        Assertions.assertEquals(1, cartService.getCurrentCart(TEST_CART_NAME_1).getItems().size());
        Assertions.assertEquals(1, cartService.getCurrentCart(TEST_CART_NAME_1).getItems().get(0).getQuantity());

        cartService.removeItemFromCart(TEST_CART_NAME_1, 3L);

        Assertions.assertEquals(0, cartService.getCurrentCart(TEST_CART_NAME_1).getItems().size());

        cartService.addToCart(TEST_CART_NAME_1, 3L);
        cartService.addToCart(TEST_CART_NAME_1, 3L);
        cartService.addToCart(TEST_CART_NAME_2, 2L);

        cartService.merge(TEST_CART_NAME_1, TEST_CART_NAME_2);
        Assertions.assertEquals(2, cartService.getCurrentCart(TEST_CART_NAME_1).getItems().size());
        Assertions.assertEquals(0, cartService.getCurrentCart(TEST_CART_NAME_2).getItems().size());

    }
}