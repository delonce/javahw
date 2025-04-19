package bus_app.services;

import bus_app.dto.drivers.DriverDto;
import bus_app.exceptions.NoDataException;
import bus_app.models.Driver;
import bus_app.repositories.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;

    public List<Driver> readAllDrivers() {
        return driverRepository.findAll();
    }

    public Driver addDriver(DriverDto driver) {
        Driver newDriver = new Driver(
                null,
                driver.getName(),
                driver.getAge(),
                driver.getPhone(),
                false
        );

        return driverRepository.saveAndFlush(newDriver);
    }

    public Driver updateDriver(Long id, DriverDto driver) {
        try {
            Driver updatableDriver = driverRepository.findById(id).get();

            if (driver.getName() != null) {
                updatableDriver.setName(driver.getName());
            }
            if (driver.getAge() != null) {
                updatableDriver.setAge(driver.getAge());
            }
            if (driver.getPhone() != null) {
                updatableDriver.setPhone( driver.getPhone());
            }

            return driverRepository.saveAndFlush(updatableDriver);
        } catch (NoSuchElementException ex) {
            throw new NoDataException("No driver with id: " + id + "!");
        }
    }

    public void deleteDriver(Long id) {
        try {
            Driver driver = driverRepository.findById(id).get();

            driverRepository.delete(driver);
        } catch (NoSuchElementException ex) {
            throw new NoDataException("No driver with id: " + id + "!");
        }
    }
}