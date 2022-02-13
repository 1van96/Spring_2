package com.geekbrains.spring.web;

import com.geekbrains.spring.web.core.dto.ProductDto;
import com.geekbrains.spring.web.core.entities.Product;
import com.geekbrains.spring.web.core.repositories.ProductsRepository;
import com.geekbrains.spring.web.core.repositories.specifications.ProductsSpecifications;
import com.geekbrains.spring.web.core.services.ProductsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = {ProductsService.class})
public class ProductsServiceTest {
    @Autowired
    private ProductsService productsService;
    @MockBean
    private ProductsRepository productsRepository;

    @Test
    public void findProductByIdTest() {
        Product cheese = new Product(3L, "Cheese", 300);
        Mockito.doReturn(Optional.of(cheese)).when(productsRepository).findById(3L);
        Product product = productsService.findById(3L).get();
        Assertions.assertEquals(cheese, product);
    }

    @Test
    public void updateProductTest() {
        Product apple = new Product(1L, "Apple", 100);
        Product updatedApple = new Product(1L, "Cheese", 300);

        ProductDto cheese = new ProductDto(1L, "Cheese", 300);
        Mockito.doReturn(Optional.of(apple)).when(productsRepository).findById(1L);
        Product newApple = productsService.update(cheese);
        Assertions.assertEquals(updatedApple, newApple);
    }
}