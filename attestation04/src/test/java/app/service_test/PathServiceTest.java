package app.service_test;

import bus_app.dto.paths.PathRequestDto;
import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import bus_app.models.*;
import bus_app.repositories.PathRepository;
import bus_app.repositories.StationRepository;
import bus_app.services.PathService;
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
public class PathServiceTest {

    @Mock
    private PathRepository pathRepository;

    @Mock
    private StationRepository stationRepository;

    @InjectMocks
    private PathService service;

    @Test
    @DisplayName("Paths - Service - Read test")
    public void pathReadTest() {
        Path testPath1 = getPathForTest("3829292");
        Path testPath2 = getPathForTest("gjiej33");

        Mockito.when(pathRepository.findAll()).thenReturn(List.of(testPath1, testPath2));

        assertThat(service.readAllPaths(), equalTo(List.of(testPath1, testPath2)));
    }

    @Test
    @DisplayName("Paths - Service - Create test - Correct")
    public void pathCreateCorrectTest() {
        PathRequestDto pathRequestDto = new PathRequestDto(
                "3829292", 1L, 2L, List.of(1L, 2L, 3L), List.of(12L, 13L, 43L),
                83282);

        Mockito.when(stationRepository.findById(1L)).thenReturn(Optional.of(getStationForTest(1L, "name_1")));
        Mockito.when(stationRepository.findById(2L)).thenReturn(Optional.of(getStationForTest(2L, "name_2")));
        Mockito.when(stationRepository.findById(3L)).thenReturn(Optional.of(getStationForTest(3L, "name_3")));

        service.addPath(pathRequestDto);
    }

    @Test
    @DisplayName("Paths - Service - Create test - Wrong data")
    public void pathCreateWrongDataTest() {
        PathRequestDto pathRequestDto = new PathRequestDto(
                "3829292", 1L, 2L, List.of(1L, 2L, 3L), List.of(12L, 13L, 43L),
                83282);

        Mockito.when(stationRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            service.addPath(pathRequestDto);

            assert false;
        } catch (IncorrectBodyException ex) {
            assertThat(ex.getMessage(), equalTo("Wrong create data!"));
        }

        Mockito.when(stationRepository.findById(1L)).thenReturn(Optional.of(getStationForTest(1L, "name_1")));
        Mockito.when(stationRepository.findById(2L)).thenReturn(Optional.empty());

        try {
            service.addPath(pathRequestDto);

            assert false;
        } catch (IncorrectBodyException ex) {
            assertThat(ex.getMessage(), equalTo("Wrong create data!"));
        }
    }

    @Test
    @DisplayName("Paths - Service - Create test - Element is exist")
    public void pathCreateElementExistTest() {
        PathRequestDto pathRequestDto = new PathRequestDto(
                "3829292", null, null, null, null,
                1313);

        Path testPath = getPathForTest("3829292");
        Mockito.when(pathRepository.findById("3829292")).thenReturn(Optional.of(testPath));

        testPath.setDuration(pathRequestDto.getDuration());
        Mockito.when(pathRepository.saveAndFlush(testPath)).thenReturn(testPath);

        Path result = service.addPath(pathRequestDto);

        assertThat(result.getDuration(), equalTo(1313));
    }

    @Test
    @DisplayName("Paths - Service - Create test - Arrays are not the same size")
    public void pathCreateWrongSizeTest() {
        PathRequestDto pathRequestDto = new PathRequestDto(
                "3829292", 1L, 2L, List.of(2L, 3L), List.of(12L, 13L, 43L),
                83282);

        try {
            service.addPath(pathRequestDto);

            assert false;
        } catch (IncorrectBodyException ex) {
            assertThat(ex.getMessage(), equalTo("Stations array size is not equals a size of times array!"));
        }
    }

    @Test
    @DisplayName("Paths - Service - Update test - Correct")
    public void pathUpdateCorrectTest() {
        PathRequestDto pathRequestDto = new PathRequestDto(
                "3829292", null, null, null, null,
                1313);

        Path testPath = getPathForTest("3829292");
        Mockito.when(pathRepository.findById("3829292")).thenReturn(Optional.of(testPath));

        testPath.setDuration(pathRequestDto.getDuration());
        Mockito.when(pathRepository.saveAndFlush(testPath)).thenReturn(testPath);

        Path result = service.updatePath(pathRequestDto);

        assertThat(result.getDuration(), equalTo(1313));
    }

    @Test
    @DisplayName("Buses - Service - Update test - No element")
    public void busUpdateNoElementTest() {
        PathRequestDto pathRequestDto = new PathRequestDto(
                "3829292", null, null, null, null,
                1313);

        Mockito.when(pathRepository.findById("3829292")).thenReturn(Optional.empty());

        try {
            service.updatePath(pathRequestDto);

            assert false;
        } catch (NoDataException ex) {
            assertThat(ex.getMessage(), equalTo("No path with number: 3829292!"));
        }
    }

    @Test
    @DisplayName("Paths - Service - Update test - Wrong data")
    public void pathUpdateWrongDataTest() {
        PathRequestDto pathRequestDto = new PathRequestDto(
                "3829292", 1L, null, null, null,
                1313);

        Mockito.when(pathRepository.findById("3829292")).thenReturn(Optional.of(getPathForTest("3829292")));
        Mockito.when(stationRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            service.updatePath(pathRequestDto);

            assert false;
        } catch (IncorrectBodyException ex) {
            assertThat(ex.getMessage(), equalTo("Wrong update data!"));
        }
    }

    @Test
    @DisplayName("Paths - Service - Delete test - Correct")
    public void pathDeleteCorrectTest() {
        Path path = getPathForTest("4839020");

        Mockito.when(pathRepository.findById("4839020")).thenReturn(Optional.of(path));

        service.deletePath("4839020");
    }

    @Test
    @DisplayName("Paths - Service - Delete test - No element")
    public void pathDeleteNoElementTest() {
        Mockito.when(pathRepository.findById("4839020")).thenReturn(Optional.empty());

        try {
            service.deletePath("4839020");

            assert false;
        } catch (NoDataException ex) {
            assertThat(ex.getMessage(), equalTo("No path with number: 4839020!"));
        }
    }

    private Path getPathForTest(String number) {
        Station begin = getStationForTest(1L, "name_1");
        Station end = getStationForTest(2L, "name_2");
        Station middle = getStationForTest(3L, "name_3");

        return new Path(number, begin, end, List.of(
                new PathStation(null, begin, 1L),
                new PathStation(null, middle, 2L),
                new PathStation(null, end, 3L)),
                1341413, false);
    }

    private Station getStationForTest(Long id, String name) {
        return new Station(
                id, name, "district", false
        );
    }
}