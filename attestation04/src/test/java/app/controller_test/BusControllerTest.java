package app.controller_test;

import app.TestWithContainer;
import bus_app.BusApplication;
import bus_app.controllers.BusController;
import bus_app.dto.buses.BusRequestDto;
import bus_app.dto.buses.BusResponseDto;
import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import bus_app.models.*;
import bus_app.repositories.BusUserRepository;
import bus_app.security.bus_users.BusUserService;
import bus_app.security.jwt.JwtFilter;
import bus_app.security.jwt.JwtService;
import bus_app.services.BusService;
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
@SpringBootTest(classes = {BusController.class, JwtService.class,
        BusUserService.class, JwtFilter.class, BusUserRepository.class})
@ContextConfiguration(classes = BusApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
public class BusControllerTest extends TestWithContainer {

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockitoBean
    private BusService service;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @SneakyThrows
    @DisplayName("Buses - Controller - Read test")
    public void busReadTest() {
        List<Bus> buses = List.of(
                getBusForTest("403", 34, "бензин"),
                getBusForTest("2df", 20, "электрический")
        );

        Mockito.when(service.readAllBuses()).thenReturn(buses);

        String busesJson = mapper.writeValueAsString(buses.stream()
                .map(BusResponseDto::new).toList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/buses"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(busesJson));
    }

    @Test
    @SneakyThrows
    @WithMockUser
    @DisplayName("Buses - Controller - Create test - Correct")
    public void busCreateCorrectTest() {
        BusRequestDto requestDto = new BusRequestDto(
                "403", "m3", 1L, List.of(1L, 2L, 3L), 34, "бензин", true
        );

        Bus bus = getBusForTest("403", 34, "бензин");
        String jsonBus = mapper.writeValueAsString(new BusResponseDto(bus));

        Mockito.when(service.createBus(requestDto)).thenReturn(bus);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/buses")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(jsonBus));
    }

    @Test
    @SneakyThrows
    @DisplayName("Buses - Controller - Create test - No auth")
    public void busCreateNoAuthTest() {
        BusRequestDto requestDto = new BusRequestDto(
                "403", "m3", 1L, List.of(1L, 2L, 3L), 34, "бензин", true
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/buses")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @SneakyThrows
    @WithMockUser
    @DisplayName("Buses - Controller - Update test - Correct")
    public void busUpdateCorrectTest() {
        BusRequestDto requestDto = new BusRequestDto(
                "403", "m3", 1L, List.of(1L, 2L, 3L), 34, "бензин", true
        );

        Bus bus = getBusForTest("403", 34, "бензин");
        String jsonBus = mapper.writeValueAsString(new BusResponseDto(bus));

        Mockito.when(service.updateBus(requestDto)).thenReturn(bus);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/buses")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(jsonBus));
    }

    @Test
    @SneakyThrows
    @DisplayName("Buses - Controller - Update test - No auth")
    public void busUpdateNoAuthTest() {
        BusRequestDto requestDto = new BusRequestDto(
                "403", "m3", 1L, List.of(1L, 2L, 3L), 34, "бензин", true
        );

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/buses")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Buses - Controller - Delete test - Correct")
    public void busDeleteCorrectTest() {
        Mockito.doNothing().when(service).deleteBus("23");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/buses")
                        .content("23"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @SneakyThrows
    @WithMockUser
    @DisplayName("Buses - Controller - Delete test - No auth")
    public void busDeleteNoAuthTest() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/buses")
                        .content("23"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @SneakyThrows
    @WithMockUser
    @DisplayName("Buses - Controller - Advice - Incorrect body")
    public void busAdviceIncorrectBodyTest() {
        BusRequestDto requestDto = new BusRequestDto(
                "403", "m3", 1L, List.of(1L, 2L, 3L), 34, "бензин", true
        );

        Mockito.when(service.createBus(requestDto)).thenThrow(new IncorrectBodyException(
                "Wrong data!"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/buses")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Wrong data!"));
    }

    @Test
    @SneakyThrows
    @WithMockUser
    @DisplayName("Buses - Controller - Advice - No data")
    public void busAdviceNoDataTest() {
        BusRequestDto requestDto = new BusRequestDto(
                "403", "m3", 1L, List.of(1L, 2L, 3L), 34, "бензин", true
        );

        Mockito.when(service.createBus(requestDto)).thenThrow(new NoDataException(
                "Wrong data!"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/buses")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Wrong data!"));
    }

    private Bus getBusForTest(String number, int seats, String type) {
        return new Bus(
                number, getPathForTest(), getDepForTest(), List.of(getDriverForTest(1L)),
                seats, type, true, false);
    }

    private Driver getDriverForTest(Long id) {
        return new Driver(
                id, "name", 34, "phone", false
        );
    }

    private Department getDepForTest() {
        return new Department(1L, "dep_name", "dep_address", false);
    }

    private Path getPathForTest() {
        return new Path("323", getStationForTest(), getStationForTest(), List.of(), 1341413, false);
    }

    private Station getStationForTest() {
        return new Station(1L, "station_name", "station_district", false);
    }
}