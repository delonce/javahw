package app.service_test;

import bus_app.dto.drivers.DriverDto;
import bus_app.exceptions.NoDataException;
import bus_app.models.Driver;
import bus_app.repositories.DriverRepository;
import bus_app.services.DriverService;
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
public class DriverServiceTest {

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DriverService service;

    @Test
    @DisplayName("Drivers - Service - Read test")
    public void driverReadTest() {
        Driver testDriver1 = getDriverForTest(1L, "3829292");
        Driver testDriver2 = getDriverForTest(2L, "gjiej33");

        Mockito.when(driverRepository.findAll()).thenReturn(List.of(testDriver1, testDriver2));

        assertThat(service.readAllDrivers(), equalTo(List.of(testDriver1, testDriver2)));
    }

    @Test
    @DisplayName("Drivers - Service - Create test - Correct")
    public void driverCreateCorrectTest() {
        DriverDto driverDto = new DriverDto("name", 34, "phone");

        Driver testDriver = new Driver(null, "name", 34, "phone", false);
        Driver testDriverWithId = new Driver(1L, "name", 34, "phone", false);

        Mockito.when(driverRepository.saveAndFlush(testDriver)).thenReturn(testDriverWithId);

        Driver driver = service.addDriver(driverDto);

        assertThat(driver.getName(), equalTo(driverDto.getName()));
        assertThat(driver.getAge(), equalTo(driverDto.getAge()));
        assertThat(driver.getPhone(), equalTo(driverDto.getPhone()));
    }

    @Test
    @DisplayName("Drivers - Service - Update test - Correct")
    public void driverUpdateCorrectTest() {
        DriverDto driverDto = new DriverDto("new_name", null, null);
        Driver oldDriver = new Driver(1L, "name", 34, "phone", false);

        Mockito.when(driverRepository.findById(1L)).thenReturn(Optional.of(oldDriver));

        oldDriver.setName("new_name");
        Mockito.when(driverRepository.saveAndFlush(oldDriver)).thenReturn(oldDriver);

        Driver newDriver = service.updateDriver(1L, driverDto);

        assertThat(newDriver.getName(), equalTo(driverDto.getName()));
        assertThat(newDriver.getAge(), equalTo(oldDriver.getAge()));
        assertThat(newDriver.getPhone(), equalTo(oldDriver.getPhone()));
    }

    @Test
    @DisplayName("Drivers - Service - Update test - No element")
    public void driverUpdateNoElementTest() {
        DriverDto driverDto = new DriverDto("new_name", null, null);

        Mockito.when(driverRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            service.updateDriver(1L, driverDto);

            assert false;
        } catch (NoDataException ex) {
            assertThat(ex.getMessage(), equalTo("No driver with id: 1!"));
        }
    }

    @Test
    @DisplayName("Drivers - Service - Delete test - Correct")
    public void driverDeleteCorrectTest() {
        Driver oldDriver = new Driver(1L, "name", 34, "phone", false);
        Mockito.when(driverRepository.findById(1L)).thenReturn(Optional.of(oldDriver));

        service.deleteDriver(1L);
    }

    @Test
    @DisplayName("Drivers - Service - Delete test - No element")
    public void driverDeleteNoElementTest() {
        Mockito.when(driverRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            service.deleteDriver(1L);

            assert false;
        } catch (NoDataException ex) {
            assertThat(ex.getMessage(), equalTo("No driver with id: 1!"));
        }
    }

    private Driver getDriverForTest(Long id, String name) {
        return new Driver(
                id, name, 34, "phone", false
        );
    }
}