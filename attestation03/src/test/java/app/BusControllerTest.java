package app;

import bus_app.BusApplication;
import bus_app.controllers.BusController;
import bus_app.models.*;
import bus_app.services.BusService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BusController.class)
@ContextConfiguration(classes = BusController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BusControllerTest {

    @MockBean
    private BusService service;

    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Bus: controller GET test")
    public void busGetTest() throws Exception {
        List<Bus> buses = List.of(getBusForTest());

        Mockito.when(service.readAllBuses()).thenReturn(buses);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/buses"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        objectMapper.writeValueAsString(buses)
                ));
    }

    private Bus getBusForTest() {
        Driver driver = new Driver(1L, "name", 28, "phone", false);
        Department department = new Department(1L, "name", "address", false);
        Station station = new Station(1L, "name", "district", false);
        Path path = new Path("number", station, station,
                List.of(new PathStation(null, station, 1L)), 1, false);

        return new Bus("number", path, department, List.of(driver), 14, "type", true, false);
    }
}