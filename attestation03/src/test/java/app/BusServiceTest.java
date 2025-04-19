package app;

import bus_app.services.BusService;
import org.mockito.Mockito;

public class BusServiceTest {

    private final BusService service;

    public BusServiceTest() {
        this.service = Mockito.mock(BusService.class);
    }
}