package app.controller_test;

import app.TestWithContainer;
import bus_app.BusApplication;
import bus_app.controllers.DepartmentController;
import bus_app.dto.departments.DepartmentDto;
import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import bus_app.models.*;
import bus_app.repositories.BusUserRepository;
import bus_app.security.bus_users.BusUserService;
import bus_app.security.jwt.JwtFilter;
import bus_app.security.jwt.JwtService;
import bus_app.services.DepartmentService;
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
@SpringBootTest(classes = {DepartmentController.class, JwtService.class,
        BusUserService.class, JwtFilter.class, BusUserRepository.class})
@ContextConfiguration(classes = BusApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
public class DepartmentControllerTest extends TestWithContainer {

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockitoBean
    private DepartmentService service;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @SneakyThrows
    @DisplayName("Departments - Controller - Read test")
    public void departmentReadTest() {
        List<Department> departments = List.of(
                getDepForTest("403"),
                getDepForTest("2df")
        );

        Mockito.when(service.readAllDepartments()).thenReturn(departments);

        String departmentsJson = mapper.writeValueAsString(departments.stream()
                .map(DepartmentDto::new).toList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/departments"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(departmentsJson));
    }

    @Test
    @SneakyThrows
    @WithMockUser
    @DisplayName("Departments - Controller - Create test - Correct")
    public void departmentCreateCorrectTest() {
        DepartmentDto requestDto = new DepartmentDto(
                "name", "address"
        );

        Department department = getDepForTest("name");
        String jsonDep = mapper.writeValueAsString(new DepartmentDto(department));

        Mockito.when(service.addDepartment(requestDto)).thenReturn(department);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/departments")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(jsonDep));
    }

    @Test
    @SneakyThrows
    @DisplayName("Departments - Controller - Create test - No auth")
    public void departmentCreateNoAuthTest() {
        DepartmentDto requestDto = new DepartmentDto(
                "name", "address"
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/departments")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @SneakyThrows
    @WithMockUser
    @DisplayName("Departments - Controller - Update test - Correct")
    public void departmentUpdateCorrectTest() {
        DepartmentDto requestDto = new DepartmentDto(
                "name", "address"
        );

        Department department = getDepForTest("name");
        String jsonDep = mapper.writeValueAsString(new DepartmentDto(department));

        Mockito.when(service.updateDepartment(1L, requestDto)).thenReturn(department);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/departments/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(jsonDep));
    }

    @Test
    @SneakyThrows
    @DisplayName("Departments - Controller - Update test - No auth")
    public void departmentUpdateNoAuthTest() {
        DepartmentDto requestDto = new DepartmentDto(
                "name", "address"
        );

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/departments/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Departments - Controller - Delete test - Correct")
    public void departmentDeleteCorrectTest() {
        Mockito.doNothing().when(service).deleteDepartment(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/departments/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @SneakyThrows
    @WithMockUser
    @DisplayName("Departments - Controller - Delete test - No auth")
    public void departmentDeleteNoAuthTest() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/departments/1"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @SneakyThrows
    @WithMockUser
    @DisplayName("Departments - Controller - Advice - Incorrect body")
    public void departmentAdviceIncorrectBodyTest() {
        DepartmentDto requestDto = new DepartmentDto(
                "name", "address"
        );

        Mockito.when(service.addDepartment(requestDto)).thenThrow(new IncorrectBodyException(
                "Wrong data!"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/departments")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Wrong data!"));
    }

    @Test
    @SneakyThrows
    @WithMockUser
    @DisplayName("Departments - Controller - Advice - No data")
    public void departmentAdviceNoDataTest() {
        DepartmentDto requestDto = new DepartmentDto(
                "name", "address"
        );

        Mockito.when(service.addDepartment(requestDto)).thenThrow(new NoDataException(
                "Wrong data!"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/departments")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Wrong data!"));
    }

    private Department getDepForTest(String name) {
        return new Department(1L, name, "dep_address", false);
    }
}