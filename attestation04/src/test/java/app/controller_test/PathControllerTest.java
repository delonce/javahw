package app.controller_test;

import app.TestWithContainer;
import bus_app.BusApplication;
import bus_app.controllers.PathController;
import bus_app.dto.paths.PathRequestDto;
import bus_app.dto.paths.PathResponseDto;
import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import bus_app.models.*;
import bus_app.repositories.BusUserRepository;
import bus_app.security.bus_users.BusUserService;
import bus_app.security.jwt.JwtFilter;
import bus_app.security.jwt.JwtService;
import bus_app.services.PathService;
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
@SpringBootTest(classes = {PathController.class, JwtService.class,
        BusUserService.class, JwtFilter.class, BusUserRepository.class})
@ContextConfiguration(classes = BusApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
public class PathControllerTest extends TestWithContainer {

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockitoBean
    private PathService service;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @SneakyThrows
    @DisplayName("Paths - Controller - Read test")
    public void pathReadTest() {
        List<Path> paths = List.of(
                getPathForTest("403"),
                getPathForTest("2df")
        );

        Mockito.when(service.readAllPaths()).thenReturn(paths);

        String pathsJson = mapper.writeValueAsString(paths.stream()
                .map(PathResponseDto::new).toList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/paths"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(pathsJson));
    }

    @Test
    @SneakyThrows
    @WithMockUser
    @DisplayName("Paths - Controller - Create test - Correct")
    public void pathCreateCorrectTest() {
        PathRequestDto requestDto = new PathRequestDto(
                "403", 1L, 2L, List.of(1L, 2L),
                List.of(1321L, 12214L), 821913
        );

        Path path = getPathForTest("403");
        String jsonPath = mapper.writeValueAsString(new PathResponseDto(path));

        Mockito.when(service.addPath(requestDto)).thenReturn(path);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/paths")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(jsonPath));
    }

    @Test
    @SneakyThrows
    @DisplayName("Paths - Controller - Create test - No auth")
    public void pathCreateNoAuthTest() {
        PathRequestDto requestDto = new PathRequestDto(
                "403", 1L, 2L, List.of(1L, 2L),
                List.of(1321L, 12214L), 821913
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/paths")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @SneakyThrows
    @WithMockUser
    @DisplayName("Paths - Controller - Update test - Correct")
    public void pathUpdateCorrectTest() {
        PathRequestDto requestDto = new PathRequestDto(
                "403", 1L, 2L, List.of(1L, 2L),
                List.of(1321L, 12214L), 821913
        );

        Path path = getPathForTest("403");
        String jsonPath = mapper.writeValueAsString(new PathResponseDto(path));

        Mockito.when(service.updatePath(requestDto)).thenReturn(path);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/paths")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(jsonPath));
    }

    @Test
    @SneakyThrows
    @DisplayName("Paths - Controller - Update test - No auth")
    public void pathUpdateNoAuthTest() {
        PathRequestDto requestDto = new PathRequestDto(
                "403", 1L, 2L, List.of(1L, 2L),
                List.of(1321L, 12214L), 821913
        );

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/paths")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Paths - Controller - Delete test - Correct")
    public void pathDeleteCorrectTest() {
        Mockito.doNothing().when(service).deletePath("23");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/paths")
                        .content("23"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @SneakyThrows
    @WithMockUser
    @DisplayName("Paths - Controller - Delete test - No auth")
    public void pathDeleteNoAuthTest() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/paths")
                        .content("23"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @SneakyThrows
    @WithMockUser
    @DisplayName("Paths - Controller - Advice - Incorrect body")
    public void pathAdviceIncorrectBodyTest() {
        PathRequestDto requestDto = new PathRequestDto(
                "403", 1L, 2L, List.of(1L, 2L),
                List.of(1321L, 12214L), 821913
        );

        Mockito.when(service.addPath(requestDto)).thenThrow(new IncorrectBodyException(
                "Wrong data!"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/paths")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Wrong data!"));
    }

    @Test
    @SneakyThrows
    @WithMockUser
    @DisplayName("Paths - Controller - Advice - No data")
    public void pathAdviceNoDataTest() {
        PathRequestDto requestDto = new PathRequestDto(
                "403", 1L, 2L, List.of(1L, 2L),
                List.of(1321L, 12214L), 821913
        );

        Mockito.when(service.addPath(requestDto)).thenThrow(new NoDataException(
                "Wrong data!"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/paths")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Wrong data!"));
    }

    private Path getPathForTest(String number) {
        return new Path(number, getStationForTest(), getStationForTest(), List.of(), 1341413, false);
    }

    private Station getStationForTest() {
        return new Station(1L, "station_name", "station_district", false);
    }
}