package app.controller_test;

import app.TestWithContainer;
import bus_app.BusApplication;
import bus_app.controllers.StationController;
import bus_app.dto.stations.StationDto;
import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import bus_app.models.*;
import bus_app.repositories.BusUserRepository;
import bus_app.security.bus_users.BusUserService;
import bus_app.security.jwt.JwtFilter;
import bus_app.security.jwt.JwtService;
import bus_app.services.StationService;
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
@SpringBootTest(classes = {StationController.class, JwtService.class,
        BusUserService.class, JwtFilter.class, BusUserRepository.class})
@ContextConfiguration(classes = BusApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
public class StationControllerTest extends TestWithContainer {

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockitoBean
    private StationService service;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @SneakyThrows
    @DisplayName("Stations - Controller - Read test")
    public void stationReadTest() {
        List<Station> stations = List.of(
                getStationForTest(1L, "name_1"),
                getStationForTest(2L, "name_2")
        );

        Mockito.when(service.readAllStations()).thenReturn(stations);

        String busesJson = mapper.writeValueAsString(stations.stream()
                .map(StationDto::new).toList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/stations"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(busesJson));
    }

    @Test
    @SneakyThrows
    @WithMockUser
    @DisplayName("Stations - Controller - Create test - Correct")
    public void stationCreateCorrectTest() {
        StationDto requestDto = new StationDto("name", "district");

        Station station = getStationForTest(1L, "name");
        String jsonStation = mapper.writeValueAsString(new StationDto(station));

        Mockito.when(service.addStation(requestDto)).thenReturn(station);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/stations")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(jsonStation));
    }

    @Test
    @SneakyThrows
    @DisplayName("Stations - Controller - Create test - No auth")
    public void stationCreateNoAuthTest() {
        StationDto requestDto = new StationDto("name", "district");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/stations")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @SneakyThrows
    @WithMockUser
    @DisplayName("Stations - Controller - Update test - Correct")
    public void stationUpdateCorrectTest() {
        StationDto requestDto = new StationDto("name", "district");

        Station station = getStationForTest(1L, "name");
        String jsonStation = mapper.writeValueAsString(new StationDto(station));

        Mockito.when(service.updateStation(1L, requestDto)).thenReturn(station);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/stations/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(jsonStation));
    }

    @Test
    @SneakyThrows
    @DisplayName("Stations - Controller - Update test - No auth")
    public void stationUpdateNoAuthTest() {
        StationDto requestDto = new StationDto("name", "district");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/stations/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Stations - Controller - Delete test - Correct")
    public void stationDeleteCorrectTest() {
        Mockito.doNothing().when(service).deleteStation(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/stations/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @SneakyThrows
    @WithMockUser
    @DisplayName("Stations - Controller - Delete test - No auth")
    public void stationDeleteNoAuthTest() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/stations/1"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @SneakyThrows
    @WithMockUser
    @DisplayName("Stations - Controller - Advice - Incorrect body")
    public void stationAdviceIncorrectBodyTest() {
        StationDto requestDto = new StationDto("name", "district");

        Mockito.when(service.addStation(requestDto)).thenThrow(new IncorrectBodyException(
                "Wrong data!"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/stations")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Wrong data!"));
    }

    @Test
    @SneakyThrows
    @WithMockUser
    @DisplayName("Stations - Controller - Advice - No data")
    public void stationAdviceNoDataTest() {
        StationDto requestDto = new StationDto("name", "district");

        Mockito.when(service.addStation(requestDto)).thenThrow(new NoDataException(
                "Wrong data!"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/stations")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Wrong data!"));
    }

    private Station getStationForTest(Long id, String name) {
        return new Station(
                id, name, "district", false
        );
    }
}