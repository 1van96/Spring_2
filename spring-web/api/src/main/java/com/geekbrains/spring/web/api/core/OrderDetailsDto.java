package com.geekbrains.spring.web.api.core;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Данные по заказу")
public class OrderDetailsDto {
    @Schema(description = "Адрес доставки", maxLength = 255, minLength = 10)
    private String address;
    @Schema(description = "Телефон", maxLength = 25, minLength = 10, example = "+7-123-456-78-90")
    private String phone;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public OrderDetailsDto() {
    }

    public OrderDetailsDto(String address, String phone) {
        this.address = address;
        this.phone = phone;
    }
}
