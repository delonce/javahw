package app.controller_test;

import app.TestWithContainer;
import bus_app.BusApplication;
import bus_app.controllers.DriverController;
import bus_app.dto.drivers.DriverDto;
import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import bus_app.models.*;
import bus_app.repositories.BusUserRepository;
import bus_app.security.bus_users.BusUserService;
import bus_app.security.jwt.JwtFilter;
import bus_app.security.jwt.JwtService;
import bus_app.services.DriverService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@ExtendWith({SpringExtension.class})
@SpringBootTest(classes = {DriverController.class, JwtService.class,
        BusUserService.class, JwtFilter.class, BusUserRepository.class})
@ContextConfiguration(classes = BusApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
public class DriverControllerTest extends TestWithContainer {

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockitoBean
    private DriverService service;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @SneakyThrows
    @DisplayName("Drivers - Controller - Read test")
    public void driverReadTest() {
        List<Driver> drivers = List.of(
                getDriverForTest(1L, "name_1"),
                getDriverForTest(2L, "name_2")
        );

        Mockito.when(service.readAllDrivers()).thenReturn(drivers);

        String driversJson = mapper.writeValueAsString(drivers.stream()
                .map(DriverDto::new).toList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/drivers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(driversJson));
    }

    @Test
    @SneakyThrows
    @WithMockUser
    @DisplayName("Drivers - Controller - Create test - Correct")
    public void driverCreateCorrectTest() {
        DriverDto requestDto = new DriverDto("name", 33, "phone");

        Driver driver = getDriverForTest(1L, "name");
        String jsonDriver = mapper.writeValueAsString(new DriverDto(driver));

        Mockito.when(service.addDriver(requestDto)).thenReturn(driver);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/drivers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(jsonDriver));
    }

    @Test
    @SneakyThrows
    @DisplayName("Drivers - Controller - Create test - No auth")
    public void driverCreateNoAuthTest() {
        DriverDto requestDto = new DriverDto("name", 33, "phone");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/drivers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @SneakyThrows
    @WithMockUser
    @DisplayName("Drivers - Controller - Update test - Correct")
    public void driverUpdateCorrectTest() {
        DriverDto requestDto = new DriverDto("name", 33, "phone");

        Driver driver = getDriverForTest(1L, "name");
        String jsonDriver = mapper.writeValueAsString(new DriverDto(driver));

        Mockito.when(service.updateDriver(1L, requestDto)).thenReturn(driver);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/drivers/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(jsonDriver));
    }

    @Test
    @SneakyThrows
    @DisplayName("Drivers - Controller - Update test - No auth")
    public void driverUpdateNoAuthTest() {
        DriverDto requestDto = new DriverDto("name", 33, "phone");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/drivers/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Drivers - Controller - Delete test - Correct")
    public void driverDeleteCorrectTest() {
        Mockito.doNothing().when(service).deleteDriver(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/drivers/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @SneakyThrows
    @WithMockUser
    @DisplayName("Drivers - Controller - Delete test - No auth")
    public void driverDeleteNoAuthTest() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/drivers/1"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @SneakyThrows
    @WithMockUser
    @DisplayName("Drivers - Controller - Advice - Incorrect body")
    public void driverAdviceIncorrectBodyTest() {
        DriverDto requestDto = new DriverDto("name", 33, "phone");

        Mockito.when(service.addDriver(requestDto)).thenThrow(new IncorrectBodyException(
                "Wrong data!"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/drivers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Wrong data!"));
    }

    @Test
    @SneakyThrows
    @WithMockUser
    @DisplayName("Drivers - Controller - Advice - No data")
    public void driverAdviceNoDataTest() {
        DriverDto requestDto = new DriverDto("name", 33, "phone");

        Mockito.when(service.addDriver(requestDto)).thenThrow(new NoDataException(
                "Wrong data!"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/drivers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Wrong data!"));
    }

    private Driver getDriverForTest(Long id, String name) {
        return new Driver(
                id, name, 34, "phone", false
        );
    }
}