package com.geekbrains.spring.web.api.carts;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Модель корзины")
public class CartDto {
    @Schema(description = "Список продуктов", required = true)
    private List<CartItemDto> items;
    @Schema(description = "Итоговая сумма", required = true, example = "1234.15")
    private BigDecimal totalPrice;

    public List<CartItemDto> getItems() {
        return items;
    }

    public void setItems(List<CartItemDto> items) {
        this.items = items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public CartDto() {
    }

    public CartDto(List<CartItemDto> items, BigDecimal totalPrice) {
        this.items = items;
        this.totalPrice = totalPrice;
    }
}
