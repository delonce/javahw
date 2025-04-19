package bus_app.services;

import bus_app.dto.buses.BusRequestDto;
import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import bus_app.models.Bus;
import bus_app.repositories.BusRepository;
import bus_app.repositories.DepartmentRepository;
import bus_app.repositories.DriverRepository;
import bus_app.repositories.PathRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BusService {

    private final BusRepository busRepository;
    private final PathRepository pathRepository;
    private final DepartmentRepository departmentRepository;
    private final DriverRepository driverRepository;

    public List<Bus> readAllBuses() {
        return busRepository.findAll();
    }

    public Bus createBus(BusRequestDto bus) {
        try {
            Bus newBus = new Bus(
                    bus.getNumber(),
                    pathRepository.findById(bus.getPath()).get(),
                    departmentRepository.findById(bus.getDepartment()).get(),
                    driverRepository.findAllById(bus.getDrivers()),
                    bus.getSeatsNumber(),
                    bus.getType(),
                    bus.getIsActive(),
                    false
            );

            return busRepository.saveAndFlush(newBus);
        } catch (NoSuchElementException ex) {
            throw new IncorrectBodyException("Wrong create data!");
        } catch (DataAccessException ex) {
            throw new IncorrectBodyException(ex.getMessage());
        }
    }

    public Bus updateBus(BusRequestDto bus) {
        Bus updatableBus;
        try {
            updatableBus = busRepository.findById(bus.getNumber()).get();
        } catch (NoSuchElementException ex) {
            throw new NoDataException("No bus with number: " + bus.getNumber() + "!");
        } catch (DataAccessException ex) {
            throw new IncorrectBodyException(ex.getMessage());
        }

        try {
            if (bus.getPath() != null) {
                updatableBus.setPath(pathRepository.findById(bus.getPath()).get());
            }
            if (bus.getDepartment() != null) {
                updatableBus.setDepartment(departmentRepository.findById(bus.getDepartment()).get());
            }
            if (bus.getDrivers() != null) {
                updatableBus.setDrivers(bus.getDrivers().stream()
                        .map(driverRepository::findById).map(Optional::get).toList());
            }
            if (bus.getSeatsNumber() != null) {
                updatableBus.setSeatsNumber(bus.getSeatsNumber());
            }
            if (bus.getType() != null) {
                updatableBus.setType(bus.getType());
            }
            if (bus.getIsActive() != null) {
                updatableBus.setIsActive(bus.getIsActive());
            }

            return busRepository.saveAndFlush(updatableBus);
        } catch (NoSuchElementException ex) {
            throw new IncorrectBodyException("Wrong update data!");
        } catch (DataAccessException ex) {
            throw new IncorrectBodyException(ex.getMessage());
        }
    }

    public void deleteBus(String number) {
        try {
            Bus bus = busRepository.findById(number).get();

            busRepository.delete(bus);
        } catch (NoSuchElementException ex) {
            throw new NoDataException("No bus with number: " + number + "!");
        } catch (DataAccessException ex) {
            throw new IncorrectBodyException(ex.getMessage());
        }
    }
}