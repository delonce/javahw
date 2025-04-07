package app;

import app.controllers.EarthquakeController;
import app.models.entities.EarthquakeEntity;
import app.repositories.EarthquakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = EarthquakeController.class)
public class EarthquakeControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private EarthquakeRepository repository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @DisplayName("Controller: get mag test")
    public void magTest() throws Exception {
        List<EarthquakeEntity> entities = new ArrayList<>(List.of(
                new EarthquakeEntity(1L, "title1", LocalDateTime.now(), 1.1, "place1"),
                new EarthquakeEntity(2L, "title2", LocalDateTime.now(), 2.8, "place2")
        ));

        Mockito.when(repository.findByMagAfter(1.0)).thenReturn(entities);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/earthquakes/mag")
                .param("mag", "1.0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mag").value("1.1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].mag").value("2.8"));
    }

    @Test
    @DisplayName("Controller: get time test")
    public void timeTest() throws Exception {
        LocalDateTime date1 = LocalDateTime.of(2025, 1, 20, 12, 33, 1);
        LocalDateTime date2 = LocalDateTime.of(2020, 6, 3, 21, 4, 17);

        List<EarthquakeEntity> entities = new ArrayList<>(List.of(
                new EarthquakeEntity(1L, "title1", date1, 1.1, "place1"),
                new EarthquakeEntity(2L, "title2", date2, 2.8, "place2")
        ));

        Mockito.when(repository.findByTimeBetween(date1, date2)).thenReturn(entities);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/earthquakes/time")
                        .param("start", date1.toString())
                        .param("end", date2.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].time").value(date1.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].time").value(date2.toString()));
    }
}