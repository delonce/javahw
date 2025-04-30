package app.service_test;

import bus_app.dto.buses.BusRequestDto;
import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import bus_app.models.Bus;
import bus_app.models.Department;
import bus_app.models.Driver;
import bus_app.models.Path;
import bus_app.repositories.BusRepository;
import bus_app.repositories.DepartmentRepository;
import bus_app.repositories.DriverRepository;
import bus_app.repositories.PathRepository;
import bus_app.services.BusService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
public class BusServiceTest {

    @Mock
    private BusRepository busRepository;

    @Mock
    private PathRepository pathRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private BusService service;

    @Test
    @DisplayName("Buses - Service - Read test")
    public void busReadTest() {
        Bus testBus1 = getBusForTest("3829292", 34, "бензин");
        Bus testBus2 = getBusForTest("gjiej33", 21, "электрический");

        Mockito.when(busRepository.findAll()).thenReturn(List.of(testBus1, testBus2));

        assertThat(service.readAllBuses(), equalTo(List.of(testBus1, testBus2)));
    }

    @Test
    @DisplayName("Buses - Service - Create test - Correct")
    public void busCreateCorrectTest() {
        BusRequestDto busRequestDto = new BusRequestDto("4839020",
                "v3", 1L, List.of(1L, 2L), 34, "бензин", true);

        Mockito.when(pathRepository.findById("v3")).thenReturn(Optional.of(getPathForTest()));
        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.of(getDepForTest()));
        Mockito.when(driverRepository.findAllById(List.of(1L, 2L))).thenReturn(List.of(
                getDriverForTest(1L), getDriverForTest(2L)));

        service.createBus(busRequestDto);
    }

    @Test
    @DisplayName("Buses - Service - Create test - Wrong data")
    public void busCreateWrongDataTest() {
        BusRequestDto busRequestDto = new BusRequestDto("4839020",
                "v3", 1L, List.of(1L, 2L), 34, "бензин", true);

        Mockito.when(pathRepository.findById("v3")).thenReturn(Optional.empty());

        try {
            service.createBus(busRequestDto);

            assert false;
        } catch (IncorrectBodyException ex) {
            assertThat(ex.getMessage(), equalTo("Wrong create data!"));
        }

        Mockito.when(pathRepository.findById("v3")).thenReturn(Optional.of(getPathForTest()));
        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            service.createBus(busRequestDto);

            assert false;
        } catch (IncorrectBodyException ex) {
            assertThat(ex.getMessage(), equalTo("Wrong create data!"));
        }
    }

    @Test
    @DisplayName("Buses - Service - Create test - Element is exist")
    public void busCreateElementExistTest() {
        BusRequestDto busRequestDto = new BusRequestDto("4839020",
                null, null, null, 20, null, null);

        Bus testBus = getBusForTest("4839020", 34, "бензин");
        Mockito.when(busRepository.findById("4839020")).thenReturn(
                Optional.of(testBus)
        );

        testBus.setSeatsNumber(20);
        Mockito.when(busRepository.saveAndFlush(testBus)).thenReturn(testBus);

        Bus result = service.createBus(busRequestDto);

        assertThat(result.getSeatsNumber(), equalTo(20));
        assertThat(result.getNumber(), equalTo("4839020"));
    }

    @Test
    @DisplayName("Buses - Service - Update test - Correct")
    public void busUpdateCorrectTest() {
        BusRequestDto busRequestDto = new BusRequestDto("4839020",
                null, null, null, 20, null, null);

        Bus testBus = getBusForTest("4839020", 34, "бензин");
        Mockito.when(busRepository.findById("4839020")).thenReturn(
                Optional.of(testBus)
        );

        testBus.setSeatsNumber(20);
        Mockito.when(busRepository.saveAndFlush(testBus)).thenReturn(testBus);

        Bus result = service.createBus(busRequestDto);

        assertThat(result.getSeatsNumber(), equalTo(20));
        assertThat(result.getNumber(), equalTo("4839020"));
    }

    @Test
    @DisplayName("Buses - Service - Update test - No element")
    public void busUpdateNoElementTest() {
        BusRequestDto busRequestDto = new BusRequestDto("4839020",
                null, null, null, 20, null, null);

        Mockito.when(busRepository.findById("4839020")).thenReturn(Optional.empty());

        try {
            service.updateBus(busRequestDto);

            assert false;
        } catch (NoDataException ex) {
            assertThat(ex.getMessage(), equalTo("No bus with number: 4839020!"));
        }
    }

    @Test
    @DisplayName("Buses - Service - Update test - Wrong data")
    public void busUpdateWrongDataTest() {
        BusRequestDto busRequestDto = new BusRequestDto("4839020",
                null, 2L, null, null, null, null);

        Mockito.when(busRepository.findById("4839020")).thenReturn(
                Optional.of(getBusForTest("4839020", 34, "бензин"))
        );
        Mockito.when(departmentRepository.findById(2L)).thenReturn(Optional.empty());

        try {
            service.updateBus(busRequestDto);

            assert false;
        } catch (IncorrectBodyException ex) {
            assertThat(ex.getMessage(), equalTo("Wrong update data!"));
        }
    }

    @Test
    @DisplayName("Buses - Service - Delete test - Correct")
    public void busDeleteCorrectTest() {
        Bus bus = getBusForTest("4839020", 20, "бензин");

        Mockito.when(busRepository.findById("4839020")).thenReturn(Optional.of(bus));
        Mockito.doNothing().when(busRepository).deleteById("4839020");

        service.deleteBus("4839020");
    }

    @Test
    @DisplayName("Buses - Service - Delete test - No element")
    public void busDeleteNoElementTest() {
        Mockito.when(busRepository.findById("4839020")).thenReturn(Optional.empty());

        try {
            service.deleteBus("4839020");

            assert false;
        } catch (NoDataException ex) {
            assertThat(ex.getMessage(), equalTo("No bus with number: 4839020!"));
        }
    }

    private Bus getBusForTest(String number, int seats, String type) {
        return new Bus(
                number, getPathForTest(), getDepForTest(), List.of(),
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
        return new Path("323", null, null, List.of(), 1341413, false);
    }
}