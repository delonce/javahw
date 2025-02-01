package org.delonce.demo.actuator;

import io.micrometer.core.instrument.binder.MeterBinder;
import org.delonce.demo.service.DemoService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CustomActuator {
    @Bean
    public MeterBinder bindNumOfShops(DemoService demoService) {
        return meterRegistry -> meterRegistry.gauge("num_of_shops", demoService.getNumOfShops());
    }
}
