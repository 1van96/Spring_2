package com.geekbrains.spring.web.soap;

import com.geekbrains.spring.web.services.ProductsService;
import com.geekbrains.spring.web.soap.products.GetAllProductsRequest;
import com.geekbrains.spring.web.soap.products.GetAllProductsResponse;
import com.geekbrains.spring.web.soap.products.GetProductByTitleRequest;
import com.geekbrains.spring.web.soap.products.GetProductByTitleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://www.flamexander.com/spring/ws/products";
    private final ProductsService productsService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByTitleRequest")
    @ResponsePayload
    public GetProductByTitleResponse getProductByTitle(@RequestPayload GetProductByTitleRequest request) {
        GetProductByTitleResponse response = new GetProductByTitleResponse();
        System.out.println(request.getTitle());
        response.setProduct(productsService.findByTitleDto(request.getTitle()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) {
        GetAllProductsResponse response = new GetAllProductsResponse();
        productsService.findAll().forEach(response.getProducts()::add);
        return response;
    }
}
