package com.geekbrains.spring.web.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "integrations.cart-service.timeouts")
@Data
public class TimeoutProperties {
    private Integer connectTimeout;
    private Integer readTimeout;
    private Integer writeTimeout;
}
