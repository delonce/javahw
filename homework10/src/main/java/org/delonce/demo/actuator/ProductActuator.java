package org.delonce.demo.actuator;

import io.micrometer.core.instrument.binder.MeterBinder;
import org.delonce.demo.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ProductActuator {
    @Bean
    public MeterBinder getNumOfProducts(ProductService productService) {
        return meterRegistry -> meterRegistry.gauge("count_of_products", productService.countAllProducts());
    }

    @Bean
    public MeterBinder getAvgOfSum(ProductService productService) {
        return meterRegistry -> meterRegistry.gauge("avg_sum_of_products", productService.getAvgSum());
    }
}
