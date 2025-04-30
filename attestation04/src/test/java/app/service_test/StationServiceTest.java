package app.service_test;

import bus_app.dto.stations.StationDto;
import bus_app.exceptions.NoDataException;
import bus_app.models.Station;
import bus_app.repositories.StationRepository;
import bus_app.services.StationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(MockitoExtension.class)
public class StationServiceTest {

    @Mock
    private StationRepository stationRepository;

    @InjectMocks
    private StationService service;

    @Test
    @DisplayName("Stations - Service - Read test")
    public void stationReadTest() {
        Station testStation1 = getStationForTest(1L, "3829292");
        Station testStation2 = getStationForTest(2L, "gjiej33");

        Mockito.when(stationRepository.findAll()).thenReturn(List.of(testStation1, testStation2));

        assertThat(service.readAllStations(), equalTo(List.of(testStation1, testStation2)));
    }

    @Test
    @DisplayName("Stations - Service - Create test - Correct")
    public void stationCreateCorrectTest() {
        StationDto stationDto = new StationDto("name", "district");

        Station testStation = new Station(null, "name", "district", false);
        Station testStationWithId = new Station(1L, "name", "district", false);

        Mockito.when(stationRepository.saveAndFlush(testStation)).thenReturn(testStationWithId);

        Station station = service.addStation(stationDto);

        assertThat(station.getName(), equalTo(stationDto.getName()));
        assertThat(station.getDistrict(), equalTo(stationDto.getDistrict()));
    }

    @Test
    @DisplayName("Stations - Service - Update test - Correct")
    public void stationUpdateCorrectTest() {
        StationDto stationDto = new StationDto("new_name", null);
        Station oldStation = new Station(1L, "name", "district", false);

        Mockito.when(stationRepository.findById(1L)).thenReturn(Optional.of(oldStation));

        oldStation.setName("new_name");
        Mockito.when(stationRepository.saveAndFlush(oldStation)).thenReturn(oldStation);

        Station newStation = service.updateStation(1L, stationDto);

        assertThat(newStation.getName(), equalTo(stationDto.getName()));
        assertThat(newStation.getDistrict(), equalTo(oldStation.getDistrict()));
    }

    @Test
    @DisplayName("Stations - Service - Update test - No element")
    public void stationUpdateNoElementTest() {
        StationDto stationDto = new StationDto("new_name", null);

        Mockito.when(stationRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            service.updateStation(1L, stationDto);

            assert false;
        } catch (NoDataException ex) {
            assertThat(ex.getMessage(), equalTo("No station with id: 1!"));
        }
    }

    @Test
    @DisplayName("Stations - Service - Delete test - Correct")
    public void stationDeleteCorrectTest() {
        Station oldStation = new Station(1L, "name","district", false);
        Mockito.when(stationRepository.findById(1L)).thenReturn(Optional.of(oldStation));

        service.deleteStation(1L);
    }

    @Test
    @DisplayName("Stations - Service - Delete test - No element")
    public void stationDeleteNoElementTest() {
        Mockito.when(stationRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            service.deleteStation(1L);

            assert false;
        } catch (NoDataException ex) {
            assertThat(ex.getMessage(), equalTo("No station with id: 1!"));
        }
    }

    private Station getStationForTest(Long id, String name) {
        return new Station(
                id, name, "district", false
        );
    }
}